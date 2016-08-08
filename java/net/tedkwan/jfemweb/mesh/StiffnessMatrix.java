/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.jfemweb.mesh;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 *
 * @author Devils
 */
public class StiffnessMatrix {
    
    private DoubleMatrix area;
    private DoubleMatrix stiffness;
    
    
    public StiffnessMatrix(DoubleMatrix nodes, DoubleMatrix elems) {
        int N = nodes.rows; int NE = elems.rows;
        HashMap<Integer,DoubleMatrix> ve = new HashMap<>();
        DoubleMatrix eidx=DoubleMatrix.zeros(3,2);
        eidx.putRow(0, new DoubleMatrix(new double[] {2,1}));
        eidx.putRow(1, new DoubleMatrix(new double[] {0,2}));
        eidx.putRow(2, new DoubleMatrix(new double[] {1,0}));
        for(int k=0;k<3;k++){
            int [] rowrng1=elems.getColumn((int) eidx.get(k,0)).toIntArray();
            int [] rowrng2=elems.getColumn((int) eidx.get(k,1)).toIntArray();
            DoubleMatrix veTemp=nodes.getRows(rowrng1);
            DoubleMatrix veTemp2=nodes.getRows(rowrng2);
            ve.put(k,veTemp.sub(veTemp2));
            DoubleMatrix Ai=veTemp.sub(veTemp2);
        }
        DoubleMatrix A1=ve.get(2).getColumn(0).mul(ve.get(1).getColumn(1));
        DoubleMatrix A2=ve.get(2).getColumn(1).mul(ve.get(1).getColumn(0));
        area=MatrixFunctions.abs(A2.sub(A1)).mul(0.5);
        
        DoubleMatrix ii=DoubleMatrix.zeros(9*NE,1);
        DoubleMatrix jj=DoubleMatrix.zeros(9*NE,1);
        DoubleMatrix sA=DoubleMatrix.zeros(9*NE,1);
        int index=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                List<Integer> range = IntStream.rangeClosed(index, index+NE-1)
                .boxed().collect(Collectors.toList());
                int [] idx =range.stream().mapToInt(k->k).toArray();
                DoubleMatrix ci=elems.getColumn(i);
                DoubleMatrix cj=elems.getColumn(j);
                ii.put(idx, ci);
                jj.put(idx, cj);
                DoubleMatrix prod=ve.get(i).mul(ve.get(j));
                DoubleMatrix sums = prod.rowSums().div(area.mul(4.0));
                sA.put(idx, sums);
                index=index+NE;
            }
        }
        //System.out.println(ii.rows);
        stiffness=DoubleMatrix.zeros(N, N);
        int [] iiar=ii.toIntArray();
        int [] jjar=jj.toIntArray();
        double [] sAar=sA.toArray();
        for(int i=0;i<iiar.length;i++){
            double spot=stiffness.get(iiar[i], jjar[i]);
            double curr=sAar[i];
            if(spot!=0){
                curr=curr+spot;
            }
            stiffness.put(iiar[i], jjar[i], curr);
        }
//        long endt=System.nanoTime();
//        long totalt=endt-startt;
//        double fullt= (double) totalt / 1000000000.0;
//        System.out.println("Elapsed time is " + fullt +" seconds");
    }

    public DoubleMatrix getArea() {
        return area;
    }

    public void setArea(DoubleMatrix area) {
        this.area = area;
    }

    public DoubleMatrix getStiffness() {
        return stiffness;
    }

    public void setStiffness(DoubleMatrix stiffness) {
        this.stiffness = stiffness;
    }
    
}
