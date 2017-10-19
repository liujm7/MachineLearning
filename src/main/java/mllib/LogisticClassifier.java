package mllib;

import java.util.ArrayList;
import java.util.Arrays;

import static common.Util.*;

import entity.Point;

/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/10/18
 * @package_name: mllib
 */
//简单逻辑回归
public class LogisticClassifier {
    private double[] w;//权值向量组
    private ArrayList<Point> arrayList;
    private int m;
    private double alpha;


    /**
     * Description:初始化构造方法
     *
     * @param arrayList:输入训练数据
     * @param alpha:学习率
     */
    public LogisticClassifier(ArrayList<Point> arrayList, Double alpha) {
        this.arrayList = arrayList;
        this.w = new double[arrayList.get(0).x.length + 1];
        this.alpha = alpha;
        this.m = arrayList.size();
    }

    /**
     * Description:构造方法初始化需求值
     *
     * @param arrayList:输入训练数据集
     */
    public LogisticClassifier(ArrayList<Point> arrayList) {
        this.arrayList = arrayList;
        this.w = new double[arrayList.get(0).x.length + 1];
        this.alpha = 1;
        this.m = arrayList.size();
    }

    /**
     * Description:更新w
     */
    private void UpadateW() {
        for (int j = 0; j < w.length; j++) {
            Double sum = 0.;
            for (int i = 0; i < m; i++) {
                ArrayList<Double> tmp = new ArrayList<Double>();
                tmp.add(1.);
                for (double xi : arrayList.get(i).x) {
                    tmp.add(xi);
                }
                Double h = Sigmod(DotProduct(w, tmp));
                sum += (h - arrayList.get(i).y) * tmp.get(j);
            }
            w[j] += w[j] - alpha * 1 / m * sum;
        }
    }

    /**
     * Description: 进行数据分类
     */
    public void Train() {
        System.out.println(Arrays.toString(w));
        UpadateW();
    }


    public double Predictor(double[] x) {
        ArrayList<Double> tmp = new ArrayList<Double>();
        tmp.add(1.);
        for (double xi : x) {
            tmp.add(xi);
        }
        if (Sigmod(DotProduct(w, tmp)) >= 0.5)
            return 1.;
        return 0;
    }


    public ArrayList<Double> Predictor(ArrayList<double[]> list) {
        ArrayList<Double> predictList = new ArrayList<Double>();
        for (double[] x : list) {
            ArrayList<Double> tmp = new ArrayList<Double>();
            tmp.add(1.);
            for (double xi : x) {
                tmp.add(xi);
            }
            if (Sigmod(DotProduct(w, tmp)) >= 0.5) {
                predictList.add(1.);
            } else {
                predictList.add(0.);
            }
        }
        return predictList;
    }


    private double Sigmod(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    /**
     * Description:测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        Point p1 = new Point(new double[]{0, 0, 0, 1}, 0);
        Point p2 = new Point(new double[]{1, 0, 0, 0}, 1);
        Point p3 = new Point(new double[]{2, 1, 0, 0}, 1);
        Point p4 = new Point(new double[]{2, 1, 0, 1}, 0);
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        LogisticClassifier classifier = new LogisticClassifier(list);
        classifier.Train();
        double[] w = classifier.getW();
        System.out.println("权重向量w为:" + Arrays.toString(w));
        ArrayList<double[]> arrayList = new ArrayList<double[]>();
        arrayList.add(new double[]{0, 0, 0, 1});
        arrayList.add(new double[]{1, 0, 0, 0});
        arrayList.add(new double[]{2, 1, 0, 0});
        arrayList.add(new double[]{2, 1, 0, 1});
        ArrayList<Double> predictList = classifier.Predictor(arrayList);
        System.out.println("预测结果为:" + predictList);

    }
}
