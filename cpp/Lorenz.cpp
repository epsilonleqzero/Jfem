
/*
 * Duffing.cpp
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include <cmath>
#include "Lorenz.h"


using namespace std;
using namespace arma;


Lorenz::Lorenz() {
	sigma=1.0;
	rho=1.0;
	beta=1.0;
	dim=3;
}

Lorenz::Lorenz(vector<double> params){
	dim=3;
	beta=params[0];
	rho=params[1];
	sigma=params[2];
}

vec Lorenz::evalF(double t,vec x){
	vec u=zeros<vec>(dim);
	u(0)=sigma*(x(1)-x(0));
	u(1)=x(0)*(rho-x(2));
	u(2)=x(0)*x(1)-beta*x(2);
	return u;
}

Lorenz::~Lorenz() {
	// TODO Auto-generated destructor stub
}

