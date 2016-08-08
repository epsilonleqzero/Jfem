/*
 * Duffing.cpp
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include <cmath>
#include "Duffing.h"


using namespace std;
using namespace arma;


Duffing::Duffing() {
	delta=0.5;
	gamma=0.5;
	w=0.5;
        bta=0.5;
	dim=2;
}

Duffing::Duffing(vector<double> params){
	dim=2;
        bta=params[3];
	gamma=params[2];
	w=params[1];
	delta=params[0];
}

vec Duffing::evalF(double t,vec x){
	vec u=zeros<vec>(2);
	u(0)=x(1);
	u(1)=bta*x(0)-pow(x(0),3.0)-delta*x(1)+gamma*cos(w*t);
	return u;
	return u;
}

Duffing::~Duffing() {
	// TODO Auto-generated destructor stub
}

