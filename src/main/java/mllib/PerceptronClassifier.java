package mllib;

import java.util.ArrayList;
import java.util.Arrays;

import static common.Util.DotProduct;

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

//感知机简单实现
public class PerceptronClassifier {

    private double[] w;//权值向量组
    private double b = 0.;//阈值
    private double eta = 1.;//学习率
    ArrayList<Point> arrayList;

    /**
     * Description:初始化构造方法
     *
     * @param arrayList:输入训练数据
     * @param eta:学习率
     */
    public PerceptronClassifier(ArrayList<Point> arrayList, Double eta) {
        this.arrayList = arrayList;
        this.w = new double[arrayList.get(0).x.length];
        this.eta = eta;
    }

    /**
     * Description:构造方法初始化需求值
     *
     * @param arrayList:输入训练数据集
     */
    public PerceptronClassifier(ArrayList<Point> arrayList) {
        this.arrayList = arrayList;
        this.w = new double[arrayList.get(0).x.length];
        this.eta = 1.;
    }

    /**
     * Description:更新b和w
     *
     * @param point:训练样本
     */
    private void UpadateWandB(Point point) {
        for (int i = 0; i < w.length; i++) {
            w[i] += w[i] + eta * point.y * point.x[i];
        }
        b += eta * point.y;
        return;
    }


    /**
     * Description: 进行数据分类
     */
    public void Train() {
        boolean flag = false;
        System.out.println(Arrays.toString(w));
        System.out.println(b);
        int counts = 0;
        while (!flag) {
            counts++;
            for (int i = 0; i < arrayList.size(); i++) {
                Point point = arrayList.get(i);
                if (point.y * (b + DotProduct(w, point.x)) <= 0) {
                    UpadateWandB(point);
                    System.out.println(Arrays.toString(w));
                    System.out.println(b);
                    break;
                }
                if (i == arrayList.size() - 1)
                    flag = true;
            }
            if (counts > 1000)
                break;
        }
    }


    /**
     * Description :单个样本点进行预测
     *
     * @param x:需要预测的样本点
     * @return 返回预测值
     */
    public double Predictor(double[] x) {
        if (DotProduct(w, x) + b > 0)
            return 1;
        return -1;
    }

    /**
     * Description: 一群样本点进行预测
     *
     * @param list:需要预测的样本点集合
     * @return 返回预测值集合
     */
    public ArrayList<Double> Predictor(ArrayList<double[]> list) {
        ArrayList<Double> predictList = new ArrayList<Double>();
        for (double[] x : list) {
            if (DotProduct(w, x) + b > 0) {
                predictList.add(1.);
            } else {
                predictList.add(-1.);
            }
        }
        return predictList;
    }


    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    /**
     * Description:测试方法
     *
     * @param args
     */
    public static void main(String[] args) {

        Point p1 = new Point(new double[]{0, 0, 0, 1}, -1);
        Point p2 = new Point(new double[]{1, 0, 0, 0}, 1);
        Point p3 = new Point(new double[]{2, 1, 0, 0}, 1);
        Point p4 = new Point(new double[]{2, 1, 0, 1}, -1);
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        PerceptronClassifier classifier = new PerceptronClassifier(list);
        classifier.Train();
        double[] w = classifier.getW();
        double b = classifier.getB();
        System.out.println("权重向量w为:" + Arrays.toString(w));
        System.out.println("阈值b:" + b);
        ArrayList<double[]> arrayList = new ArrayList<double[]>();
        arrayList.add(new double[]{0, 0, 0, 1});
        arrayList.add(new double[]{1, 0, 0, 0});
        arrayList.add(new double[]{2, 1, 0, 0});
        arrayList.add(new double[]{2, 1, 0, 1});
        ArrayList<Double> predictList = classifier.Predictor(arrayList);
        System.out.println(predictList);

    }

}
