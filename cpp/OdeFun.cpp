/*
 * OdeFun.cpp
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */

#include "OdeFun.h"
#include <cmath>

using namespace std;
using namespace arma;

OdeFun::OdeFun() {
	vector<double> start(1);
	start[0]=1;
	params=start;
	dim =2;
}

OdeFun::OdeFun(vector<double> params){
	this->params=params;
	dim=2;
}

vec OdeFun::evalF(double t,vec x){
	return x;
}

OdeFun::~OdeFun() {
	// TODO Auto-generated destructor stub
}
