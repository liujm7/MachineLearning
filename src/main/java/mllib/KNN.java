package mllib;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.*;

/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/11/25
 * @package_name: mllib
 */
public class KNN {
    private double[][] feature;
    private Object[] labels;
    private int sampleSize;
    private int featureSize;
    private double[] maxList;
    private double[] minList;

    public KNN(double[][] feature, Object[] labels) {
        this.feature = feature;
        this.labels = labels;
        sampleSize = labels.length;
        featureSize = feature[0].length;
        maxList = new double[featureSize];
        minList = new double[featureSize];
        for (int i = 0; i < featureSize; i++) {
            minList[i] = Double.MAX_VALUE;
        }
        autoNorm();

    }

    public void autoNorm() {
        for (double[] line : feature) {
            for (int i = 0; i < featureSize; i++) {
                if (line[i] > maxList[i])
                    maxList[i] = line[i];
                if (line[i] < minList[i])
                    minList[i] = line[i];
            }
        }
        for (int i = 0; i < sampleSize; i++) {
            for (int j = 0; j < featureSize; j++) {
                feature[i][j] = (feature[i][j] - minList[j]) / (maxList[j] - minList[j] + 1);
//                System.out.println(i + ":" + j + "---" + feature[i][j]);
            }
        }
    }

    public static double calculateDistance(double[] vector1, double[] vector2) {
        int len = vector1.length;
        double distance = 0;
        for (int i = 0; i < len; i++) {
            distance += Math.pow(vector1[i] - vector2[i], 2);
        }
        distance = Math.sqrt(distance);
//        System.out.println(distance);
        return distance;
    }

    public Object classify(double[] inX, int k) {
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        int size = labels.length;
        for (int i = 0; i < size; i++) {
            map.put(i, calculateDistance(inX, feature[i]));
        }
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return 1;
                } else if (o1.getValue() < o2.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        Map<Object, Integer> labelAndCounts = new HashMap<Object, Integer>();
        for (int i = 0; i < k; i++) {
            Object key = labels[list.get(i).getKey()];
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

    public double[] getMaxList() {
        return maxList;
    }

    public double[] getMinList() {
        return minList;
    }
}
