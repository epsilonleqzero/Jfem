/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.cpp.nar;

import org.jblas.DoubleMatrix;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 *
 * @author Devils
 */
public class NativeStuff {

    static {
        NarSystem.loadLibrary();
    }

    public native double[] twice(double[] a);

    public final native String sayHello();

    public static void main(String[] args) {
        double[] a = {1.0, 2.0, 3.0, 4.0};
        NativeStuff stuff = new NativeStuff();
//            double [] b =stuff.twice(a);
//            for(int i=0;i<b.length;i++){
//                System.out.println(b[i]);
//            }
        DuffingOscil duff = new DuffingOscil();
        double[] initc = {0.1, 0.1, 1, 0.3,-1.0,30.0,0.3,0.6};
        double[] res = duff.rk4(initc);
        int r = res.length / 3;
        System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 3, res);
        double[] xData = outmtx.getColumn(0).toArray();
        double[] yData = outmtx.getColumn(1).toArray();
        //double[] zData = outmtx.getColumn(2).toArray();
//        for(int i=0;i<3;i++){
//            double [] curr=outmtx.getColumn(i).toArray();
//            for(int j=0;j<curr.length;j++){
//                System.out.println(curr[j]);
//            }
//        
//        }

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
//            int i=0;
//            for(int j=0;j<3;j++){
//                for(;i<(j+1)*r;i++){
//                   System.out.print(res[i] + " ");
//                }
//                System.out.println("");
//            }
        //System.out.println(stuff.sayHello() +" from Java");
    }

}
