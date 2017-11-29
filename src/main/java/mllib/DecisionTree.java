package mllib;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/11/26
 * @package_name: mllib
 */
public class DecisionTree {
    public static Object[][] createDataSet() {
        Object[][] dataSet = new Object[5][3];
        dataSet[0][0] = 1;
        dataSet[0][1] = 1;
        dataSet[0][2] = "yes";
        dataSet[1][0] = 1;
        dataSet[1][1] = 1;
        dataSet[1][2] = "yes";
        dataSet[2][0] = 1;
        dataSet[2][1] = 0;
        dataSet[2][2] = "no";
        dataSet[3][0] = 0;
        dataSet[3][1] = 1;
        dataSet[3][2] = "no";
        dataSet[4][0] = 0;
        dataSet[4][1] = 1;
        dataSet[4][2] = "no";
        return dataSet;
    }

    public static List<Object> createLabels() {
        List<Object> labels = new ArrayList<Object>();
        labels.add("no surfacing");
        labels.add("flippers");
        return labels;
    }


    /**
     * Description:计算数据集合信息熵
     *
     * @param dataSet 数据集,包括特征和标签，标签为最后一列
     * @return 返回信息熵
     */
    private static double calcShannonEnt(Object[][] dataSet) {
        int numEntries = dataSet.length - 1;
        int labelIndex = dataSet[0].length - 1;
        Map<Object, Integer> labelAndCounts = new HashMap<Object, Integer>();
        for (int i = 0; i < numEntries; i++) {
            Object currentLabel = dataSet[i][labelIndex];
            if (!labelAndCounts.containsKey(currentLabel)) {
                labelAndCounts.put(currentLabel, 1);
            } else {
                labelAndCounts.put(currentLabel, labelAndCounts.get(currentLabel) + 1);
            }
        }
        double shannonEnt = 0;
        for (Map.Entry<Object, Integer> entry : labelAndCounts.entrySet()) {
            double prob = entry.getValue() * 1.0 / numEntries;
            shannonEnt += prob * Math.log(prob) / Math.log(2);
        }
        return -shannonEnt;
    }

    /**
     * Description:按照某一列特征的某一个值切分数据值
     *
     * @param dataSet      数据集,包括特征和标签，标签为最后一列
     * @param featureIndex 特征的索引列
     * @param value        某一个切分数据集合
     * @return 返回切分后的数据集合
     */
    private static Object[][] splitDataSet(Object[][] dataSet, int featureIndex, Object value) {
        //暂时实现方式,由于不确定满足的value的样本数量，所以使用ArrayLis可变数组
        List<List<Object>> retDataSet = new ArrayList<List<Object>>();
        for (Object[] vector : dataSet) {
            if (vector[featureIndex] == value) {
                List<Object> reducedFeatVec = new ArrayList<Object>();
                for (int i = 0; i < vector.length; i++) {
                    if (i == featureIndex)
                        continue;
                    reducedFeatVec.add(vector[i]);
                }
                retDataSet.add(reducedFeatVec);
            }
        }
        //转换成数组是为了能够方便递归调用
        Object[][] retDataSetArray = new Object[retDataSet.size()][dataSet[0].length - 1];
        for (int i = 0; i < retDataSet.size(); i++) {
            for (int j = 0; j < dataSet[0].length - 1; j++) {
                retDataSetArray[i][j] = retDataSet.get(i).get(j);
            }
        }
        return retDataSetArray;

    }

    /**
     * Description:选择最好的切分特征
     *
     * @param dataSet 数据集,包括特征和标签，标签为最后一列
     * @return 特征的索引
     */
    private static int chooseBestFeatureSplit(Object[][] dataSet) {
        int entriesSize = dataSet.length;
        int numFeatures = dataSet[0].length - 1;
        double baseEntropy = calcShannonEnt(dataSet);
        double bestInfoGain = 0.0;
        int bestFeature = 0;
        for (int i = 0; i < numFeatures; i++) {
            HashSet<Object> uniqueValSet = new HashSet<Object>();
            for (Object[] vector : dataSet) {
                uniqueValSet.add(vector[i]);
            }
            double newEntropy = 0;
            for (Object value : uniqueValSet) {
                Object[][] subDataSet = splitDataSet(dataSet, i, value);
                double prob = subDataSet.length * 1.0 / entriesSize;
                newEntropy += prob * calcShannonEnt(subDataSet);
            }

            double infoGain = baseEntropy - newEntropy;
            if (infoGain > bestInfoGain) {
                bestInfoGain = infoGain;
                bestFeature = i;
            }
        }
        return bestFeature;
    }

    /**
     * Description：数据集合没有特征了，剩余的结点判断为数量最多的表桥
     *
     * @param labelList 标签列表
     * @return 返回数量最多的标签
     */
    private static Object majorityCnt(Object[] labelList) {
        Map<Object, Integer> labelAndCounts = new HashMap<Object, Integer>();
        for (Object key : labelList) {
            if (!labelAndCounts.containsKey(key)) {
                labelAndCounts.put(key, 1);
            } else {
                labelAndCounts.put(key, labelAndCounts.get(key) + 1);
            }
        }
        List<Map.Entry<Object, Integer>> labelAndCountsList = new ArrayList<Map.Entry<Object, Integer>>(labelAndCounts.entrySet());
        Collections.sort(labelAndCountsList, new Comparator<Map.Entry<Object, Integer>>() {
            public int compare(Map.Entry<Object, Integer> o1, Map.Entry<Object, Integer> o2) {
                if (o2.getValue() > o1.getValue()) {
                    return 1;
                } else if (o2.getValue() < o1.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return labelAndCountsList.get(0).getKey();

    }

    /**
     * Description：创建决策数
     *
     * @param dataSet 数据集,包括特征和标签，标签为最后一列
     * @param labels  特征索引列表
     * @return 决策树
     */
    public static Object createTree(Object[][] dataSet, List<Object> labels) {
        Object[] classList = new Object[dataSet.length];

        for (int i = 0; i < dataSet.length; i++) {
            classList[i] = dataSet[i][dataSet[0].length - 1];
        }

        //判断是否存在多个标签，如果只剩
        Object classFlag = classList[0];
        boolean flag = true;
        for (int i = 1; i < dataSet.length; i++) {
            if (classList[i] != classFlag) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return classList[0];
        }


        if (dataSet[0].length == 1)
            return majorityCnt(classList);

        int bestFeat = chooseBestFeatureSplit(dataSet);
        Object bestFeatLabel = labels.get(bestFeat);

        HashMap<Object, HashMap<Object, Object>> myTree = new HashMap<Object, HashMap<Object, Object>>();
        myTree.put(bestFeatLabel, new HashMap<Object, Object>());
        labels.remove(bestFeat);
        HashSet<Object> uniqueFeatValuesSet = new HashSet<Object>();
        for (Object[] vector : dataSet) {
            uniqueFeatValuesSet.add(vector[bestFeat]);
        }
        for (Object value : uniqueFeatValuesSet) {
            List<Object> subLabels = new ArrayList<Object>();
            subLabels.addAll(labels);
            Object subTree = createTree(splitDataSet(dataSet, bestFeat, value), subLabels);
            HashMap<Object, Object> subTreeMap = myTree.get(bestFeatLabel);
            subTreeMap.put(value, subTree);
            myTree.put(bestFeatLabel, subTreeMap);
        }

        return myTree;
    }

    public static Object classify(Object inputTree, List<Object> featLabels, Object[] testVec) {
        Object classLabel;
        if (inputTree instanceof Map) {
            Set keys = ((Map) inputTree).keySet();
            Object firstStr = keys.iterator().next();
            Object secondMap = ((Map) inputTree).get(firstStr);
            int featIndex = featLabels.indexOf(firstStr);
            if (secondMap instanceof Map) {
                for (Object secondKey : ((Map) secondMap).keySet()) {
                    if (testVec[featIndex] == secondKey) {
                        if (((Map) secondMap).get(secondKey) instanceof Map) {
                            classLabel = classify(((Map) secondMap).get(secondKey), featLabels, testVec);
                            return classLabel;
                        } else {
                            classLabel = ((Map) secondMap).get(secondKey);
                            return classLabel;
                        }
                    }
                }
            } else {
                return secondMap;
            }
        }
        return inputTree;

    }

    public static void main(String[] args) {
        Object[][] dataSet = createDataSet();
        List<Object> labels = createLabels();
        HashMap<Object, HashMap<Object, Object>> myTree = (HashMap) createTree(dataSet, labels);
        System.out.println(myTree);
        List<Object> newLabels = createLabels();
        Object[] testVec = {1, 1};
        Object a = classify(myTree, newLabels, testVec);
        System.out.println(a);
    }


}
