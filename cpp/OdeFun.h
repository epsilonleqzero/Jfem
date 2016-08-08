/*
 * OdeFun.h
 *
 *  Created on: Aug 6, 2016
 *      Author: Devils
 */
#include <armadillo>
#include <vector>

#ifndef ODEFUN_H_
#define ODEFUN_H_

class OdeFun {
public:
	OdeFun();
	OdeFun(std::vector<double> params);
	virtual arma::vec evalF(double t,arma::vec x);
	virtual ~OdeFun();
	int dim;
private:
    std::vector<double> params;
};


#endif /* ODEFUN_H_ */
