package common;

import java.util.ArrayList;

/**
 * @version: 1.0
 * @author: Liujm
 * @site: https://github.com/liujm7
 * @contact: kaka206@163.com
 * @software: Idea
 * @date： 2017/10/18
 * @package_name: common
 */
public class Util {


    /**
     * Description: 进行点乘
     *
     * @param x1:数组1
     * @param x2:数组2
     * @return 点乘的结果
     */
    public static double DotProduct(double[] x1, double[] x2) {
        if (x1.length != x2.length) {
            System.out.println("向量长度不相等");
            return -1.;
        }
        Integer len = x1.length;
        Double sum = 0.;
        for (int i = 0; i < len; i++) {
            sum += x1[i] * x2[i];
        }
        return sum;
    }

    public static double DotProduct(double[] x1, ArrayList<Double> x2) {
        if (x1.length != x2.size()) {
            System.out.println("向量长度不相等");
            return -1.;
        }
        Integer len = x1.length;
        Double sum = 0.;
        for (int i = 0; i < len; i++) {
            sum += x1[i] * x2.get(i);
        }
        return sum;
    }


    public static double DotProduct(ArrayList<Double> x1, ArrayList<Double> x2) {
        if (x1.size() != x2.size()) {
            System.out.println("向量长度不相等");
            return -1.;
        }
        Integer len = x1.size();
        Double sum = 0.;
        for (int i = 0; i < len; i++) {
            sum += x1.get(i) * x2.get(i);
        }
        return sum;
    }
}
