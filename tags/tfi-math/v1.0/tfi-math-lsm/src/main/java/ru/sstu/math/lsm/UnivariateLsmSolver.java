package ru.sstu.math.lsm;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

/**
 * <code>UnivariateLsmSolver</code> interface searches for univariate
 * fitting function using Least Square Method.
 *
 * @author Denis_Murashev
 * @since Math 1.0
 */
public interface UnivariateLsmSolver {

	/**
	 * X coordinate index.
	 */
	int X = 0;

	/**
	 * Y coordinate index.
	 */
	int Y = 1;

	/**
	 * Searches for fitting function.
	 *
	 * @param points points
	 * @param basis  functions basis
	 * @return coefficients
	 * @throws MathException if evaluation error occurs
	 */
	double[] solve(double[][] points, UnivariateRealFunction[] basis)
			throws MathException;
}
