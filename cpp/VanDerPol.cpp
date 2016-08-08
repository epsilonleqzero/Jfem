/*
 * VanDerPol.cpp
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include <cmath>
#include "VanDerPol.h"


using namespace std;
using namespace arma;


VanDerPol::VanDerPol() {
	epsilon=0.5;
	dim=2;
}

VanDerPol::VanDerPol(vector<double> params){
	dim=2;
	epsilon=params[0];
}

vec VanDerPol::evalF(double t,vec x){
	vec u=zeros<vec>(2);
	u(0)=x(1);
	u(1)=(epsilon*(1-pow(x(0),2.0))*x(1))-x(0);
	return u;
}

VanDerPol::~VanDerPol() {
	// TODO Auto-generated destructor stub
}

