package ru.sstu.math.lsm;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;

/**
 * <code>UnivariateLsmSolverImpl</code> class is default implementation of
 * {@link UnivariateLsmSolver} interface.
 *
 * @author Denis_Murashev
 * @since Math 1.0
 */
public class UnivariateLsmSolverImpl implements UnivariateLsmSolver {

	/**
	 * {@inheritDoc}
	 */
	public double[] solve(double[][] points, UnivariateRealFunction[] basis)
			throws FunctionEvaluationException {
		double[][] a = new double[basis.length][basis.length];
		double[] b = new double[basis.length];
		for (int k = 0; k < a.length; k++) {
			for (int i = 0; i < a.length; i++) {
				a[k][i] = 0.0;
				for (double[] point : points) {
					a[k][i] += basis[k].value(point[X])
							* basis[i].value(point[X]);
				}
			}
			b[k] = 0.0;
			for (double[] point : points) {
				b[k] += basis[k].value(point[X]) * point[Y];
			}
		}

		RealMatrix matrix = new Array2DRowRealMatrix(a);
		LUDecomposition decomposition = new LUDecompositionImpl(matrix);
		return decomposition.getSolver().solve(b);
	}
}
