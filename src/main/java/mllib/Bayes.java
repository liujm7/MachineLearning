package mllib;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/11/29
 * @package_name: mllib
 */
public class Bayes {
    /**
     * Description：计算两个向量相加
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static double[] addVec(double[] vec1, int[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        double[] returnVec = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] + vec2[i];
        }
        return returnVec;
    }


    /**
     * Description：计算两个向量相加
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static double[] addVec(int[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        double[] returnVec = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] + vec2[i];
        }
        return returnVec;
    }

    /**
     * Description：计算两个向量相加
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static double[] addVec(double[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        double[] returnVec = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] + vec2[i];
        }
        return returnVec;
    }


    /**
     * Description：计算两个向量相加
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static int[] addVec(int[] vec1, int[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        int[] returnVec = new int[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] + vec2[i];
        }
        return returnVec;
    }

    /**
     * Description：计算两个向量相乘
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static double[] multyplyVec(double[] vec1, int[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        double[] returnVec = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] * vec2[i];
        }
        return returnVec;
    }

    /**
     * Description：计算两个向量相乘
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static double[] multyplyVec(int[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        double[] returnVec = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] * vec2[i];
        }
        return returnVec;
    }


    /**
     * Description：计算两个向量相乘
     *
     * @param vec1 向量1
     * @param vec2 向量2
     * @return 向量相加后的向量
     */
    private static double[] multyplyVec(double[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            throw new IllegalArgumentException("两个向量的元素数目不对应");
        }
        double[] returnVec = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            returnVec[i] = vec1[i] * vec2[i];
        }
        return returnVec;
    }

    /**
     * Description:获取某个向量的和
     *
     * @param vec 向量
     * @return 向量的和
     */
    private static double getVecSum(double[] vec) {
        double sum = 0;
        for (double ele : vec) {
            sum += ele;
        }
        return sum;
    }


    /**
     * Description:获取某个向量的和
     *
     * @param vec 向量
     * @return 向量的和
     */
    private static double getVecSum(int[] vec) {
        double sum = 0;
        for (double ele : vec) {
            sum += ele;
        }
        return sum;
    }

    /**
     * Description:创建字典索引，使用ArrayList来索引
     *
     * @param dataSet 分词后的文章总数
     * @return 返回一个字典
     */
    public static List createVocabList(List<List<Object>> dataSet) {
        Set<Object> vocabSet = new HashSet<Object>();
        for (List<Object> document : dataSet) {
            vocabSet.addAll(document);
        }
        List<Object> vocabList = new ArrayList<Object>();
        vocabList.addAll(vocabSet);
        return vocabList;
    }

    /**
     * Description:将分词后的文章转化成字典对应的向量(出现与否)
     *
     * @param vocabList 字典
     * @param document  文章
     * @return 返回词语向量
     */
    public static int[] setOfWordsVec(List vocabList, List<Object> document) {
        int[] returnVec = new int[vocabList.size()];
        for (Object word : document) {
            if (vocabList.indexOf(word) > -1) {
                returnVec[vocabList.indexOf(word)] = 1;
            } else {
                System.out.println(word + " is not in my vocabulary list.");
            }
        }
        return returnVec;
    }

    /**
     * Description:将分词后的文章转化成字典对应的向量,计数
     *
     * @param vocabList 字典
     * @param document  文章
     * @return 返回词语向量
     */
    public static int[] bagOfWordsVec(List vocabList, List<Object> document) {
        int[] returnVec = new int[vocabList.size()];
        for (Object word : document) {
            if (vocabList.indexOf(word) > -1) {
                returnVec[vocabList.indexOf(word)] += 1;
            } else {
                System.out.println(word + " is not in my vocabulary list.");
            }
        }
        return returnVec;
    }


    /**
     * Description:计算每个类别的占比
     *
     * @param trainCategory 训练集的类别向量
     * @return 返回每个类别的占比
     */
    public static Map<Object, Double> getCategoryProb(Object[] trainCategory) {
        int numEntries = trainCategory.length;
        HashMap<Object, Integer> categoryAndCounts = new HashMap<Object, Integer>();
        for (Object category : trainCategory) {
            if (!categoryAndCounts.containsKey(category)) {
                categoryAndCounts.put(category, 1);
            } else {
                categoryAndCounts.put(category, categoryAndCounts.get(category) + 1);
            }
        }
        HashMap<Object, Double> categoryAndProb = new HashMap<Object, Double>();
        for (Map.Entry<Object, Integer> entry : categoryAndCounts.entrySet()) {
            categoryAndProb.put(entry.getKey(), entry.getValue() * 1.0 / numEntries);
        }
        return categoryAndProb;
    }

    /**
     * Description:训练朴素贝叶斯
     *
     * @param trainMatrix   处理后的文章向量矩阵
     * @param trainCategory 文章对应的类别
     * @param categoryNums  类别种类数
     * @return 返回每个类别的每个词语概率向量
     */
    public static Map<Object, double[]> trainNB0(int[][] trainMatrix, Object[] trainCategory, int categoryNums) {
        int numTrainDocs = trainMatrix.length;
        int numWords = trainMatrix[0].length;
        Map<Object, double[]> categoryAndVect = new HashMap<Object, double[]>();
        Map<Object, Double> categoryAndDenom = new HashMap<Object, Double>();

        for (int i = 0; i < numTrainDocs; i++) {
            if (!categoryAndVect.containsKey(trainCategory[i])) {
                double[] pNum = new double[numWords];
                Arrays.fill(pNum, 1);//拉普拉斯平滑
                categoryAndVect.put(trainCategory[i], pNum);
                categoryAndDenom.put(trainCategory[i], (double) categoryNums);//分母拉普拉斯平滑
            }
            double[] pNum = categoryAndVect.get(trainCategory[i]);
            pNum = addVec(pNum, trainMatrix[i]);
            categoryAndVect.put(trainCategory[i], pNum);
            categoryAndDenom.put(trainCategory[i], categoryAndDenom.get(trainCategory[i]) + getVecSum(trainMatrix[i]));
        }
        for (Object key : categoryAndVect.keySet()) {
            double[] pVect = categoryAndVect.get(key);
            double denom = categoryAndDenom.get(key);
            for (int i = 0; i < pVect.length; i++) {
                pVect[i] = Math.log(pVect[i] / denom);
                //由于大部分因子都非常小，所以程序会下溢出或者 得到不正确的答案。所以对数化相加即可
            }
            categoryAndVect.put(key, pVect);
        }
        return categoryAndVect;
    }

    /**
     * Description: 分类函数
     *
     * @param vec2Classify    需要判别的向量
     * @param categoryAndVect 生成的每个类别对应的每个词语概率向量
     * @param categoryProb    每个类别的占比
     * @return 返回分类结果
     */
    public static Object classifyNB(int[] vec2Classify, Map<Object, double[]> categoryAndVect
            , Map<Object, Double> categoryProb) {
//        Map<Object, Double> vecProb = new HashMap<Object, Double>();
        double maxClassProb = -Double.MAX_VALUE;
        Object maxClass = new Object();
        for (Object key : categoryAndVect.keySet()) {
            double[] pVect = categoryAndVect.get(key);
            double prob = categoryProb.get(key);
            double p = getVecSum(multyplyVec(vec2Classify, pVect)) + Math.log(prob);
            if (p > maxClassProb) {
                maxClassProb = p;
                maxClass = key;
            }
        }
        return maxClass.toString();
    }

    /**
     * Description: 简单进行英文字符串分词
     *
     * @param bigString 字符串
     * @return 返回分词后的列表
     */
    public static List<Object> textParse(String bigString) {
        String[] listOfTokens = bigString.split("\\W+");
        List<Object> listOfString = new ArrayList<Object>();
        for (String vec : listOfTokens) {
            if (vec.length() > 2) {
                listOfString.add(vec.toLowerCase());
            }
        }
        return listOfString;
    }

    public static String readFile(String path) {
        File file = new File(path);
        Long fileLengthLong = file.length();
        byte[] fileContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String string = new String(fileContent);
        return string;
    }

    /**
     * 自带数据测试
     */
    public static void spamTest() {
        String dataDirectory = "D:\\projects\\MachineLearning\\data\\email\\";
        String spamDataDirectory = dataDirectory + "spam\\";
        String hamDataDirectory = dataDirectory + "ham\\";

        List<List<Object>> docList = new ArrayList<List<Object>>();
        List<Object> classList = new ArrayList<Object>();
        List<List<Object>> fullList = new ArrayList<List<Object>>();

        for (int i = 1; i < 26; i++) {
            String spamString = readFile(spamDataDirectory + i + ".txt");
            List<Object> wordList = textParse(spamString);
            docList.add(wordList);
            fullList.add(wordList);
            classList.add(1);
            String hamString = readFile(hamDataDirectory + i + ".txt");
            List<Object> wordList2 = textParse(hamString);
            docList.add(wordList2);
            fullList.add(wordList2);
            classList.add(0);
        }
        List<Object> vocabList = createVocabList(docList);
        List<Integer> traningSet = new ArrayList<Integer>();
        for (int i = 0; i < 50; i++) {
            traningSet.add(i);
        }
        List<Integer> testSet = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            int randIndex = new Random().nextInt(traningSet.size());
            testSet.add(traningSet.get(randIndex));
            traningSet.remove(randIndex);
        }

        int[][] trainMatrix = new int[traningSet.size()][vocabList.size()];
        List<Object> trainCategory = new ArrayList<Object>();
        int count = 0;
        for (int docIndex : traningSet) {
            int[] vec = bagOfWordsVec(vocabList, docList.get(docIndex));
            System.arraycopy(vec, 0, trainMatrix[count], 0, vec.length);
            trainCategory.add(classList.get(docIndex));
            count++;
        }
        Map<Object, Double> categoryProb = getCategoryProb(classList.toArray());
        Map<Object, double[]> categoryAndVect = trainNB0(trainMatrix, classList.toArray(), categoryProb.size());

        for (int docIndex : testSet) {
            System.out.println(docIndex + Arrays.toString(docList.get(docIndex).toArray()));
            int[] vec = bagOfWordsVec(vocabList, docList.get(docIndex));
            Object predict = classifyNB(vec, categoryAndVect, categoryProb);
            System.out.println("the doc real is " + classList.get(docIndex) + ";" + " predict is " + predict);
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    public static List<List<Object>> getDataSet() {
        List<List<Object>> postingList = new ArrayList<List<Object>>();
        Object[] documentArray1 = {"my", "dog", "has", "flea", "problems", "help", "please"};
        Object[] documentArray2 = {"maybe", "not", "take", "him", "to", "dog", "park", "stupid"};
        Object[] documentArray3 = {"my", "dalmation", "is", "so", "cute", "I", "love", "him"};
        Object[] documentArray4 = {"stop", "posting", "stupid", "worthless", "garbage"};
        Object[] documentArray5 = {"mr", "licks", "ate", "my", "steak", "how", "to", "stop", "him"};
        Object[] documentArray6 = {"quit", "buying", "worthless", "dog", "food", "stupid"};
        postingList.add(Arrays.asList(documentArray1));
        postingList.add(Arrays.asList(documentArray2));
        postingList.add(Arrays.asList(documentArray3));
        postingList.add(Arrays.asList(documentArray4));
        postingList.add(Arrays.asList(documentArray5));
        postingList.add(Arrays.asList(documentArray6));

        return postingList;
    }


    /**
     * 获取类别
     *
     * @return
     */
    public static List<Object> getClassVec() {
        List<Object> classVec = new ArrayList<Object>();
        classVec.addAll(Arrays.asList(new Object[]{0, 1, 0, 1, 0, 1}));
        return classVec;
    }

    /**
     * 测试函数
     */
    public static void testingNB() {
        List<List<Object>> listOfPosts = getDataSet();
        List<Object> listClasses = getClassVec();
        List myVocabList = createVocabList(listOfPosts);

        int[][] trainMatrix = new int[6][myVocabList.size()];
        for (int i = 0; i < listOfPosts.size(); i++) {
            int[] vec = setOfWordsVec(myVocabList, listOfPosts.get(i));
            System.arraycopy(vec, 0, trainMatrix[i], 0, vec.length);
        }
        Map<Object, Double> categoryProb = getCategoryProb(listClasses.toArray());
        Map<Object, double[]> categoryAndVect = trainNB0(trainMatrix, listClasses.toArray(), categoryProb.size());
        Object[] testEntry = {"love", "my", "dalmation"};
        int[] thisDoc = setOfWordsVec(myVocabList, Arrays.asList(testEntry));
        Object testClass = classifyNB(thisDoc, categoryAndVect, categoryProb);
        System.out.println(Arrays.toString(testEntry) + " classfied as " + testClass);
        Object[] testEntry2 = {"stupid", "garbage"};
        int[] thisDoc2 = setOfWordsVec(myVocabList, Arrays.asList(testEntry2));
        Object testClass2 = classifyNB(thisDoc2, categoryAndVect, categoryProb);
        System.out.println(Arrays.toString(testEntry2) + " classfied as " + testClass2);

    }


    public static void main(String[] args) {
//        testingNB();
//        System.out.println(textParse("abcde ; bcdefasd ; cadfasdf # easdfsd"));
        spamTest();
    }

}
