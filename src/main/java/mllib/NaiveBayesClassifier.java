package mllib;

import entity.Point;

import java.util.*;


/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/10/18
 * @package_name: mllib
 */
//朴素贝叶斯算法，本版本只是demo，只能用与特征为1和0的训练
public class NaiveBayesClassifier {
    private double[] w;//权值向量组
    private ArrayList<Point> arrayList;
    private int labelNums; // 类别数量
    private double lambda = 1; //平滑因子
    private int featuresNums; // 特征数量
    private int counts; // 训练样本数
    private HashMap<Double, Integer> labelAndCounts = new HashMap<Double, Integer>(); //每个类别出现的次数
    private HashMap<Double, Double> labelAndProb = new HashMap<Double, Double>(); //每个类别出现的次数
    private HashMap<Double, HashMap<Double, Integer>> sumTermFreq = new HashMap<Double, HashMap<Double, Integer>>();
    private HashMap<Double, HashMap<Double, Double>> theta = new HashMap<Double, HashMap<Double, Double>>();

    /**
     * Description:构造方法，进行初始化
     *
     * @param arrayList:训练集合
     * @param lambda:平滑指数
     */
    public NaiveBayesClassifier(ArrayList<Point> arrayList, double lambda) {
        this.arrayList = arrayList;
        this.lambda = lambda;
        this.counts = arrayList.size();
        this.featuresNums = arrayList.get(0).x.length;
        Set<Double> set = new HashSet<Double>();
        for (Point point : arrayList) {
            set.add(point.y);
            if (this.labelAndCounts.keySet().contains(point)) {
                this.labelAndCounts.put(point.y, this.labelAndCounts.get(point.y) + 1);
            } else {
                this.labelAndCounts.put(point.y, 1);
            }
        }
        this.labelNums = set.size();

    }

    /**
     * Description:构造方法，初始化
     *
     * @param arrayList:训练集合
     */
    public NaiveBayesClassifier(ArrayList<Point> arrayList) {
        this.arrayList = arrayList;
        this.lambda = 1.;
        this.counts = arrayList.size();
        this.featuresNums = arrayList.get(0).x.length;
        Set<Double> set = new HashSet<Double>();
        for (Point point : arrayList) {
            set.add(point.y);
            if (this.labelAndCounts.keySet().contains(point)) {
                this.labelAndCounts.put(point.y, this.labelAndCounts.get(point.y) + 1);
            } else {
                this.labelAndCounts.put(point.y, 1);
            }
        }
        this.labelNums = set.size();
    }

    /**
     * Description: 训练过程
     */
    public void Train() {
        //可修改，训练过程可以认为初始化
        Iterator iter = labelAndCounts.entrySet().iterator();//遍历每一类别的统计数
        while (iter.hasNext()) {
            //计算每一类别的统计后的log化的概率
            Map.Entry entry = (Map.Entry) iter.next();
            double key = (Double) entry.getKey();
            int val = (Integer) entry.getValue();
            labelAndProb.put(key, Math.log((val + lambda) / (counts + labelNums * lambda)));
        }
        //遍历训练集合
        for (Point point : arrayList) {
            double label = point.y;
            double[] x = point.x;
            HashMap<Double, Integer> freq = new HashMap<Double, Integer>();
            if (!sumTermFreq.containsKey(label)) {
                for (int j = 0; j < featuresNums; j++) {
                    if (x[j] > 0)
                        freq.put((double) j, 1);
                    else {
                        freq.put((double) j, 0);
                    }
                }
            } else {
                HashMap<Double, Integer> termFreq = sumTermFreq.get(label);
                for (int j = 0; j < featuresNums; j++) {
                    if (x[j] > 0) {
                        freq.put((double) j, termFreq.get((double) j) + 1);
                    } else {
                        freq.put((double) j, termFreq.get((double) j));
                    }

                }
            }
            sumTermFreq.put(label, freq);
        }


        Iterator<Map.Entry<Double, HashMap<Double, Integer>>> labelIter = sumTermFreq.entrySet().iterator();
        while (labelIter.hasNext()) {
            Map.Entry entry = labelIter.next();
            double label = (Double) entry.getKey();
            double thetaLogDeamon = Math.log(labelAndCounts.get(label) + 2.0 * lambda);
            HashMap<Double, Integer> freq = sumTermFreq.get(label);
            HashMap<Double, Double> prob = new HashMap<Double, Double>();
            for (int j = 0; j < featuresNums; j++) {
                Double value = Math.log(freq.get((double) j) + lambda) - thetaLogDeamon;
                prob.put((double) j, value);
            }
            theta.put(label, prob);
        }
    }

    /**
     * Description:输出每一类别的概率
     */
    public void printTheta() {
        Iterator iter = theta.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println(entry);
        }
    }

    /**
     * Description:预测单个数据
     *
     * @param x:数据点
     * @return
     */
    public double Predictor(double[] x) {
        System.out.println(Arrays.toString(x));
        HashMap<Double, Double> probabilityMap = new HashMap<Double, Double>();
        Iterator labelAndProbability = labelAndProb.entrySet().iterator();
        while (labelAndProbability.hasNext()) {
            Map.Entry entry = (Map.Entry) labelAndProbability.next();
            double label = (Double) entry.getKey();
            double probability = (Double) entry.getValue();
            for (int j = 0; j < featuresNums; j++) {
                if (x[j] > 0) {
                    HashMap<Double, Double> thetai = theta.get(label);
                    if (thetai.containsKey((double) j)) {
                        probability += thetai.get((double) j);
                    }
                }
            }
            probabilityMap.put(label, probability);
        }
        Iterator probabilityIter = probabilityMap.entrySet().iterator();
        double maxIndex = 1;
        double maxProbability = -10;
        while (probabilityIter.hasNext()) {
            Map.Entry entry = (Map.Entry) probabilityIter.next();
            double key = (Double) entry.getKey();
            double probability = (Double) entry.getValue();
            if (probability > maxProbability) {
                maxIndex = key;
                maxProbability = probability;
            }

        }
        return maxIndex;
    }


    /**
     * Description:测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        Point p1 = new Point(new double[]{1, 1, 0, 0}, 1);
        Point p2 = new Point(new double[]{1, 1, 0, 0}, 1);
        Point p3 = new Point(new double[]{0, 0, 1, 1}, 4);
        Point p4 = new Point(new double[]{0, 0, 1, 1}, 4);
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        NaiveBayesClassifier classifier = new NaiveBayesClassifier(list);
        classifier.Train();
        classifier.printTheta();
//        classifier.printTheta();
//        double[] w = classifier.getW();
//        System.out.println("权重向量w为:" + Arrays.toString(w));
//        ArrayList<double[]> arrayList = new ArrayList<double[]>();
//        arrayList.add(new double[]{0, 0, 0, 1});
//        arrayList.add(new double[]{1, 0, 0, 0});
//        arrayList.add(new double[]{2, 1, 0, 0});
//        arrayList.add(new double[]{2, 1, 0, 1});
        Double predictor = classifier.Predictor(new double[]{1, 1, 0, 0});
        System.out.println("预测结果为:" + predictor);
        Double predictor2 = classifier.Predictor(new double[]{0, 0, 1, 1});
        System.out.println("预测结果为:" + predictor2);

    }

}


