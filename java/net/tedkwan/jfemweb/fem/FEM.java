/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.jfemweb.fem;

//import Mesh.MeshMtx;
import net.tedkwan.jfemweb.mesh.MeshMtx;
import java.util.Arrays;
import net.tedkwan.jfemweb.mesh.StiffnessMatrix;
import org.jblas.DoubleMatrix;
import org.jblas.Solve;

/**
 *
 * @author Devils
 */
public class FEM {
    private final MeshMtx mesh;//=new MeshMtx(0,1,0,1,0.125);
    private final DoubleMatrix nodes;
    private final DoubleMatrix elems;
    private final DoubleMatrix area;
    private final DoubleMatrix stiffness;
    private DoubleMatrix isBdNode;
    private DoubleMatrix bdNode;
    private DoubleMatrix freeNode;
    private final mtxFun f;
    private DoubleMatrix u;
    private final DoubleMatrix b;
    private final int N;
    private final int NE;
    
    public FEM(double x1,double x2,double y1,double y2,double h,mtxFun f,mtxFunBd g){
        this.f=f;
        mesh=new MeshMtx(x1,x2,y1,y2,h);
        nodes=mesh.getNodes(); N=nodes.rows;
        elems=mesh.getElems(); NE=elems.rows;
        StiffnessMatrix mtx=new StiffnessMatrix(nodes,elems);
        area=mtx.getArea(); stiffness=mtx.getStiffness();
        this.b=calcRHS(); findBoundary();
        u=DoubleMatrix.zeros(N);
        u.put(bdNode.toIntArray(), g.compute(nodes.getRows(bdNode.toIntArray())));
        DoubleMatrix r=b.sub(stiffness.mmul(u));
        int [] fna=freeNode.toIntArray();
        DoubleMatrix Af=stiffness.get(fna,fna);
        long startt=System.nanoTime();
        u.put(fna, Solve.solveSymmetric(Af, r.get(fna)));
        long endt=System.nanoTime();
        long totalt=endt-startt;
        double fullt= (double) totalt / 1000000000.0;
        System.out.println("Elapsed time is " + fullt +" seconds");
//        LinkedSparseMatrix A1=new LinkedSparseMatrix(new DenseMatrix(Af.toArray2()));
//        //DenseVector u1f=new DenseVector(Af.rows);
//        DenseVector b1=new DenseVector(r.toArray());
//        startt=System.nanoTime();
//        //DenseVector u1f=A1.solve(u1f,b1);
//        u.put(fna, Solve.solveSymmetric(Af, r.get(fna)));
//        endt=System.nanoTime();
//        totalt=endt-startt;
//        fullt= (double) totalt / 1000000000.0;
//        System.out.println("Elapsed time is " + fullt +" seconds");
    }
    
    private void findBoundary(){
        DoubleMatrix totalEdge=DoubleMatrix.concatVertically(
                elems.getColumns(new int[] {1,2}), elems.getColumns(new int[] {2,0}));
        totalEdge=DoubleMatrix.concatVertically(totalEdge,elems.getColumns(new int[] {0,1}));
        totalEdge=totalEdge.sortRows();
        DoubleMatrix fnd=DoubleMatrix.zeros(NE,N);
        int [] tE1=totalEdge.getColumn(0).toIntArray();
        int [] tE2=totalEdge.getColumn(1).toIntArray();
        for(int i=0;i<tE1.length;i++){
            double spot=fnd.get(tE1[i], tE2[i]);
            double curr=1.0;
            if(spot!=0){
                curr=curr+spot;
            }
            fnd.put(tE1[i], tE2[i],curr);
        }
        int [] nz=fnd.findIndices();
        DoubleMatrix ii=DoubleMatrix.zeros(nz.length);
        DoubleMatrix jj=DoubleMatrix.zeros(nz.length);
        DoubleMatrix s=DoubleMatrix.zeros(nz.length);
        int k=0;
        for(int i=0;i<fnd.rows;i++){
            for(int j=0;j<fnd.columns;j++){
                double spot=fnd.get(i, j);
                if(spot!=0){
                    ii.put(k, i);
                    jj.put(k, j);
                    s.put(k++, spot);
                }
            }
        }
        DoubleMatrix bdEdge=DoubleMatrix.zeros(totalEdge.rows,2);
        isBdNode=DoubleMatrix.zeros(N);
        for(int i=0;i<s.rows;i++){
           if(s.get(i)==1.0){
               bdEdge.put(i, 0, ii.get(i));
               bdEdge.put(i, 1, jj.get(i));
               isBdNode.put((int) ii.get(i), 1);
               isBdNode.put((int) jj.get(i), 1);
           }
        }
        bdNode=new DoubleMatrix(
                            Arrays.stream(isBdNode.findIndices()
                            ).asDoubleStream().toArray());
        freeNode=new DoubleMatrix(Arrays.stream((DoubleMatrix.ones(N).sub(isBdNode)).findIndices()
                            ).asDoubleStream().toArray());
    }
    
    private DoubleMatrix calcRHS(){
        DoubleMatrix mid1=(nodes.getRows(elems.getColumn(1).toIntArray())
                .add(nodes.getRows(elems.getColumn(2).toIntArray()))).div(2.0);
        DoubleMatrix mid2=(nodes.getRows(elems.getColumn(2).toIntArray())
                .add(nodes.getRows(elems.getColumn(0).toIntArray()))).div(2.0);
        DoubleMatrix mid3=(nodes.getRows(elems.getColumn(0).toIntArray())
                .add(nodes.getRows(elems.getColumn(1).toIntArray()))).div(2.0);
        DoubleMatrix bt1=(f.compute(mid2).add(f.compute(mid3))).mulColumnVector(area).div(6);
        DoubleMatrix bt2=(f.compute(mid3).add(f.compute(mid1))).mulColumnVector(area).div(6);
        DoubleMatrix bt3=(f.compute(mid1).add(f.compute(mid2))).mulColumnVector(area).div(6);
        DoubleMatrix bt=DoubleMatrix.concatVertically(bt1, bt2);
        bt=DoubleMatrix.concatVertically(bt, bt3);
        DoubleMatrix rhs=DoubleMatrix.zeros(nodes.rows);
        int [] elemar=elems.toIntArray();
        for(int i=0;i<elemar.length;i++){
            double spot=rhs.get(elemar[i]);
            double curr=bt.get(i);
            if(spot!=0){
                curr=curr+spot;
            }
            rhs.put(elemar[i],curr);
        }
        return rhs;
    }

    public MeshMtx getMesh() {
        return mesh;
    }

    public DoubleMatrix getNodes() {
        return nodes;
    }
    
    public DoubleMatrix getB() {
        return b;
    }

    public DoubleMatrix getElems() {
        return elems;
    }

    public DoubleMatrix getArea() {
        return area;
    }

    public DoubleMatrix getStiffness() {
        return stiffness;
    }

    public mtxFun getF() {
        return f;
    }

    public DoubleMatrix getU() {
        return u;
    }
    
}
