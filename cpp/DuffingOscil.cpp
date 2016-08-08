
#include <iostream>
#include <armadillo>
#include "RkFehl.h"
#include "net_tedkwan_cpp_nar_DuffingOscil.h"
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
using namespace std;
using namespace arma;

/**
 * Pars has 5 fields:
 * 
 * gamma - index 0.
 * h - index 1.
 * tf - index 2.
 * x0 - index 3.
 * y0 - index 4.
 * 
 * 
 * @param pars
 * @return 
 */
JNIEXPORT jdoubleArray JNICALL Java_net_tedkwan_cpp_nar_DuffingOscil_rk4(JNIEnv *env, jobject jobj, jdoubleArray jarray) {
    unsigned int i;
    jboolean isCopy1;

    jdouble* srcArrayElems =
            env -> GetDoubleArrayElements(jarray, &isCopy1);
    jint n = env -> GetArrayLength(jarray);
    vector<double> pars(4);
    pars[2]= srcArrayElems[1];
    pars[3]= srcArrayElems[4];
    double h = srcArrayElems[0];
    pars[0]= srcArrayElems[2];
    pars[1]= srcArrayElems[3];
    double tf = srcArrayElems[5];
    vec x = zeros<vec>(2);
    x(0) = srcArrayElems[6];
    x(1) = srcArrayElems[7];
    //x(2) = 0.75;
    //Rkfun testrk(gamma,h,tf,x);
    RkFehl testrk(pars,"vanderpol",tf,h,x);
    vector<vec> y=testrk.y;
    vector<double> t=testrk.t;
    
    
    vector<double> res;
    jboolean isCopy2;
    unsigned int k=t.size();
    
    for(int j=0;j<3;j++){
        for (i = 0; i < k; i++) {
        if(j<2){
            res.push_back(y[i](j));
        }
        else{
            res.push_back(t[i]);
        }
    }
//        for(int j=0;j<4;j++){
//        for (i = 0; i < k; i++) {
//        if(j<3){
//            res.push_back(y[i](j));
//        }
//        else{
//            res.push_back(t[i]);
//        }
//    }
        
    }

    cout << n << endl;

    if (isCopy1 == JNI_TRUE) {
        env -> ReleaseDoubleArrayElements(jarray, srcArrayElems, JNI_ABORT);
    }
    int lenres=res.size();
    jdoubleArray result = env -> NewDoubleArray(lenres);
    env->SetDoubleArrayRegion(result, 0, lenres, &res[0]);
//    if (isCopy2 == JNI_TRUE) {
//        env -> ReleaseDoubleArrayElements(jarray, destArrayElems, 0);
//    }

    return result;
}
