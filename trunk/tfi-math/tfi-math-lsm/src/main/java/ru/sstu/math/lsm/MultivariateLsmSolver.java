package ru.sstu.math.lsm;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.RealPointValuePair;

/**
 * <code>MultivariateLsmSolver</code> searches for multivariate fitting function
 * using Least Square Method.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
public interface MultivariateLsmSolver {

	/**
	 * Searches for fitting function.
	 *
	 * @param points points
	 * @param basis  functions basis
	 * @return coefficients
	 * @throws MathException if evaluation error occurs
	 */
	double[] solve(RealPointValuePair[] points,
			MultivariateRealFunction[] basis) throws MathException;
}
