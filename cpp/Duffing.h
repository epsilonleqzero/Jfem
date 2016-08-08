/*
 * Duffing.h
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include<vector>
#include<armadillo>

#ifndef DUFFING_H_
#define DUFFING_H_

#include "OdeFun.h"

class Duffing: public OdeFun {
public:
	Duffing();
	virtual ~Duffing();
	Duffing(std::vector<double> params);
	arma::vec evalF(double t,arma::vec x);
	int dim;
private:
	std::vector<double> params;
	double gamma;
        double bta;
	double w;
	double delta;
	};
#endif /* DUFFING_H_ */
