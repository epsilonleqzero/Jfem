/*
 * RkFehl.h
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include <vector>
#include <armadillo>
#include "OdeFun.h"
#include "Duffing.h"
#include "Lorenz.h"
#include "VanDerPol.h"

#ifndef RKFEHL_H_
#define RKFEHL_H_

class RkFehl {
public:
	RkFehl();
	RkFehl(std::vector<double> params,std::string fun,
			double tf,double h,arma::vec x);
	virtual ~RkFehl();
	int dim;
	std::vector<arma::vec> y;
	std::vector<double> t;
private:
	OdeFun * ode;
	double h;
	arma::vec nextStep(double t, arma::vec x);
	std::vector<arma::vec> testStep(double t,
			arma::vec x,double h);

};

#endif /* RKFEHL_H_ */
