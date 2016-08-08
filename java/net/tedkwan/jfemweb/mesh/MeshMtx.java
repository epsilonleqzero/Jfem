/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.jfemweb.mesh;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jblas.DoubleMatrix;

/**
 *
 * @author Devils
 */
public class MeshMtx {

    private DoubleMatrix nodes;
    private DoubleMatrix elems;
    private DoubleMatrix x;
    private DoubleMatrix y;
    private double h;
    private int n;

    /**
     * Constructor for MeshMtx
     * 
     * 
     * @param x1 - double for lower x bound.
     * @param x2 - double for upper x bound.
     * @param y1 - double for lower y bound.
     * @param y2 - double for upper y bound.
     * @param h - square size.
     */
    public MeshMtx(double x1, double x2, double y1, double y2, double h) {
        this.h = h;
        this.n = (int) Math.round(1 / h);
        DoubleMatrix xr = DoubleMatrix.linspace((int) x1, (int) x2, (int) n + 1);
        DoubleMatrix yr = DoubleMatrix.linspace((int) y1, (int) y2, (int) n + 1);
        x = DoubleMatrix.zeros(xr.length, yr.length);
        y = DoubleMatrix.zeros(xr.length, yr.length);
        for (int i = 0; i < yr.rows; i++) {
            x.putRow(i, xr);
        }
        yr = yr.transpose();
        for (int i = 0; i < xr.rows; i++) {
            y.putColumn(i, yr);
        }
        double[] xarr = x.toArray();
        double[] yarr = y.toArray();

        DoubleMatrix xdmtx = new DoubleMatrix(xarr);
        DoubleMatrix ydmtx = new DoubleMatrix(yarr);
        int N = xdmtx.rows;
        nodes = DoubleMatrix.concatHorizontally(xdmtx, ydmtx);
        int ni = x.rows;
        List<Integer> xs = IntStream.rangeClosed(ni, N - ni)
                .filter(i -> i % ni == 0).boxed()
                .collect(Collectors.toList());
        List<Integer> t2nidxMap = IntStream.rangeClosed(1, N - ni)
                .filter(i -> !xs.contains(i))
                .boxed().collect(Collectors.toList());
        int NE = t2nidxMap.size();
        elems = DoubleMatrix.zeros(NE, 3);
        int j = 0;
        DoubleMatrix curr;
        for (Integer k : t2nidxMap) {
            curr = new DoubleMatrix(new double[]{k + ni - 1, k + ni, k - 1});
            elems.putRow(j, curr.transpose());
            curr = new DoubleMatrix(new double[]{k, k - 1, k + ni});
            elems = DoubleMatrix.concatVertically(elems, curr.transpose());
            j++;
        }
        System.out.println(elems.rows);
//        stiff.pu
    }

    public DoubleMatrix getNodes() {
        return nodes;
    }

    public void setNodes(DoubleMatrix nodes) {
        this.nodes = nodes;
    }

    public DoubleMatrix getElems() {
        return elems;
    }

    public void setElems(DoubleMatrix elems) {
        this.elems = elems;
    }

    public DoubleMatrix getX() {
        return x;
    }

    public void setX(DoubleMatrix x) {
        this.x = x;
    }

    public DoubleMatrix getY() {
        return y;
    }

    public void setY(DoubleMatrix y) {
        this.y = y;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

}
