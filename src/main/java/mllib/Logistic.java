package mllib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.E;

/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/11/29
 * @package_name: mllib
 */
public class Logistic {
    private static String dataSetPath = "D:\\projects\\MachineLearning\\data\\horseColic\\horseColicTraining.txt";
    private static String testSetPath = "D:\\projects\\MachineLearning\\data\\horseColic\\horseColicTest.txt";

    public static List<Object> loadDataSet(String dataSetPath) {
        List<Object> objectList = new ArrayList<Object>();
        int numEntries = 67;
        int numFeatures = 22;
        double[][] dataMatrix = new double[numEntries][numFeatures];
        double[] labelVec = new double[numEntries];
        File file = new File(dataSetPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File doesn't exist:" + dataSetPath);
        }
        int count = 0;
        try {
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                count++;
                if (count > numEntries)
                    break;
                if (count % 1000 == 0) {
                    System.out.println("counts:" + count);
                }
                String str = in.nextLine();
                String[] elements = str.split("\t");
                for (int i = 0; i < numFeatures; i++) {
                    dataMatrix[count - 1][i] = Double.parseDouble(elements[i]);
                }
                dataMatrix[count - 1][numFeatures - 1] = 1;
                labelVec[count - 1] = Double.parseDouble(elements[numFeatures - 1]);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        objectList.add(dataMatrix);
        objectList.add(labelVec);
        return objectList;
    }


    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }


    /**
     * Description: 梯度下降法
     *
     * @param dataMatrix 特征矩阵
     * @param labelVec   标签向量
     * @param alpha      学习率
     * @param iterations 迭代次数
     * @return 权重向量
     */
    public static double[] gradAscent(double[][] dataMatrix, double[] labelVec, double alpha, int iterations) {
        int entriesSize = dataMatrix.length;
        int featureSize = dataMatrix[0].length;
        //double alpha = 0.001;
        //int iterations = 500;
        double[] weights = new double[featureSize];
        Arrays.fill(weights, 1);
        for (int it = 0; it < iterations; it++) {
            double[] grad = new double[featureSize];
            double[] error = new double[entriesSize];
            for (int i = 0; i < entriesSize; i++) {
                double predict = 0;
                for (int j = 0; j < featureSize; j++) {
                    predict += dataMatrix[i][j] * weights[j];
                }
                error[i] = labelVec[i] - sigmoid(predict);
            }
            for (int j = 0; j < featureSize; j++) {
                for (int i = 0; i < entriesSize; i++) {
                    grad[j] += dataMatrix[i][j] * error[i];
                }
            }

            for (int i = 0; i < featureSize; i++) {
                for (int j = 0; j < entriesSize; j++) {
                    weights[i] += alpha * grad[i] / entriesSize;
                }

            }
        }
        return weights;
    }


    /**
     * Description:随机梯度下降法
     *
     * @param dataMatrix 特征矩阵
     * @param labelVec   标签向量
     * @param alpha      学习率
     * @param iterations 迭代次数
     * @return 权重向量
     */
    public static double[] stocGradAscent(double[][] dataMatrix, double[] labelVec, double alpha, int iterations) {
        int entriesSize = dataMatrix.length;
        int featureSize = dataMatrix[0].length;
        //double alpha = 0.001;
        double[] weights = new double[featureSize];
        Arrays.fill(weights, 0.1);

        for (int it = 1; it <= iterations; it++) {
            for (int i = 0; i < entriesSize; i++) {
                double predict = 0;
                for (int j = 0; j < featureSize; j++) {
                    predict += dataMatrix[i][j] * weights[j];
                }
                double error = labelVec[i] - sigmoid(predict);
                for (int j = 0; j < featureSize; j++) {
                    weights[j] += alpha * error * dataMatrix[i][j];
                }
            }
        }


        return weights;
    }


    /**
     * Description:改进版随机梯度下降版
     *
     * @param dataMatrix 特征矩阵
     * @param labelVec   标签向量
     * @param iterations 迭代次数
     * @return 权重向量
     */
    public static double[] stocGradAscent1(double[][] dataMatrix, double[] labelVec, int iterations) {
        int entriesSize = dataMatrix.length;
        int featureSize = dataMatrix[0].length;

        double[] weights = new double[featureSize];
        Arrays.fill(weights, 1);

        for (int it = 1; it <= iterations; it++) {
            for (int i = 0; i < entriesSize; i++) {
                double alpha = 4 / (1.0 + it + i) + 0.01;//常数项保证最小值，随着迭代次数减少
                double predict = 0;
                for (int j = 0; j < featureSize; j++) {
                    predict += dataMatrix[i][j] * weights[j];
                }
                double error = labelVec[i] - sigmoid(predict);
                for (int j = 0; j < featureSize; j++) {
                    weights[j] += alpha * error * dataMatrix[i][j];
                }
            }
        }


        return weights;
    }

    /**
     * Description: 分类
     *
     * @param vector  输入向量
     * @param weights 权重向量
     * @return 分类标签
     */
    public static double classifyVector(double[] vector, double[] weights) {
        double sum = 0.0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i] * weights[i];
        }
        double prob = sigmoid(sum);
        System.out.println(prob);
        if (prob > 0.5) {
            return 1.0;
        }
        return 0.0;
    }

    /**
     * Description:测试
     */
    public static void singleTest() {
        List<Object> trainList = loadDataSet(dataSetPath);
        double[][] dataMatrix = (double[][]) trainList.get(0);
        double[] labelVec = (double[]) trainList.get(1);
        double[] weights = gradAscent(dataMatrix, labelVec, 0.001, 500);
        for (double elements : weights) {
            System.out.print(elements + "\t");
        }
        System.out.println();
        int errorTrainCount = 0;
        List<Object> testList = loadDataSet(testSetPath);
        double[][] testDataMatrix = (double[][]) testList.get(0);
        double[] testLabelVec = (double[]) testList.get(1);
        double numTestVect = testDataMatrix.length;
        for (int i = 0; i < numTestVect; i++) {
            double predict = classifyVector(testDataMatrix[i], weights);
            if (predict != testLabelVec[i]) {
                errorTrainCount++;
            }
            System.out.println("the doc real is " + testLabelVec[i] + ";" + " predict is " + predict);

        }
        double errorRate = errorTrainCount / numTestVect;
        System.out.println("the test error rate is : " + errorRate);
    }

    public static void main(String[] args) {
        singleTest();
    }
}
