/*
 * RkFehl.cpp
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */

#include "RkFehl.h"

using namespace std;
using namespace arma;

RkFehl::RkFehl() {
	h=0.5;
	dim=1;
	//*ode
}

RkFehl::RkFehl(vector<double> params,string fun,
		double tf,double h,vec x){
	if((fun.find("Duff")!=string::npos)
			|| (fun.find("duff")!=string::npos)){
		//Duffing duff(params);
		ode=new Duffing(params);
		dim=ode->dim;
	}
        else if((fun.find("Van")!=string::npos)
			|| (fun.find("van")!=string::npos)){
		ode=new VanDerPol(params);
		dim=ode->dim;
	}
        else if((fun.find("Lor")!=string::npos)
			|| (fun.find("lor")!=string::npos)){
		ode=new Lorenz(params);
		dim=ode->dim;
	}
	else{
		//OdeFun other(params);
		ode=new OdeFun(params);
		dim=ode->dim;
	}
	this->h=h;
	vec y1=x;
	t.push_back(0);
	y.push_back(y1);
	double tcurr=t[0];
	double hc=h;
	int i=1;
	double epsloc=pow(h,4);
	double kappa=0.84;
	while(tcurr<tf){
		//y1=nextStep(t[i-1], y[i-1]);
		vector<vec> yv=testStep(tcurr,y[i-1],hc);
		double epsi=(norm(yv[0]-yv[1]));
		int j=0;
		if(epsi<epsloc){
			hc=hc*kappa*pow(epsloc/epsi,0.2);
		}
		while(epsi>epsloc && j<30){
			j++;
			hc=hc*kappa*pow(epsloc/epsi,0.2);
			yv=testStep(tcurr,y[i-1],hc);
			epsi=(norm(yv[0]-yv[1]));
		}
		tcurr=t[i-1]+hc;
		t.push_back(tcurr);
		y.push_back(yv[1]);
		i++;
	}
	delete ode;
	cout << tcurr << endl;
}

vec RkFehl::nextStep(double t, vec x){
	double hc=h;
	vector<vec> yv=testStep(t,x,hc);

	return x;
}

vector<vec> RkFehl::testStep(double t, vec x,double hc){
	vec y4=zeros<vec>(dim);
	vec y5=zeros<vec>(dim);
	vec k1=ode->evalF(t,x)*hc;
	vec k2=ode->evalF(t+ hc/4.0,x+ k1/4.0)*hc;
	vec k3=ode->evalF(t+(3.0*hc/8.0),x+(3.0*k1/32.0) +
			(9.0*k2/32.0) )*hc;
	vec k4=ode->evalF(t+(12.0*hc/13.0),x+(1932.0*k1/2197.0)-
				(7200.0*k2/2197.0)+(7296.0*k3/2197.0) )*hc;
	vec k5=ode->evalF(t+hc,x+(439.0*k1/216.0)-
					(8.0)*k2+(3680.0*k3/513.0)-
					(845.0*k4/4104.0))*hc;
	vec k6=ode->evalF(t+hc/2,x-(8.0/27.0)*k1+
						(2*k2)-(3544.0*k3/2565.0)+
						(1859.0*k4/4104.0)-
						(11.0/40.0)*k5)*hc;
	y4=x+(25*k1/216.0)+(1408.0*k3/2565.0)
			+(2197.0*k4/4101.0)-(k5/5);
	y5=x+(16*k1/135.0)+(6656.0*k3/12825.0)
				+(28561.0*k4/56430.0)-(9.0*k5/50.0)
				+(2.0*k6/55.0);
	vector<vec> ret(2);
	ret[0]=y4;
	ret[1]=y5;
	return ret;
}

RkFehl::~RkFehl() {

}

