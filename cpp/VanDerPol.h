
/*
 * VanDerPol.h
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include<vector>
#include<armadillo>
#include "OdeFun.h"

#ifndef VANDERPOL_H_
#define VANDERPOL_H_

class VanDerPol: public OdeFun {
public:
	VanDerPol();
	virtual ~VanDerPol();
	VanDerPol(std::vector<double> params);
	arma::vec evalF(double t,arma::vec x);
	int dim;
private:
	std::vector<double> params;
	double epsilon;
	};
#endif /* DUFFING_H_ */
