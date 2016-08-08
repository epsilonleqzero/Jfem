/*
 * Lorenz.h
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include<vector>
#include<armadillo>
#include "OdeFun.h"

#ifndef LORENZ_H_
#define LORENZ_H_

class Lorenz: public OdeFun {
public:
	Lorenz();
	virtual ~Lorenz();
	Lorenz(std::vector<double> params);
	arma::vec evalF(double t,arma::vec x);
	int dim;
private:
	std::vector<double> params;
	double sigma;
	double rho;
	double beta;
	};
#endif /* DUFFING_H_ */
