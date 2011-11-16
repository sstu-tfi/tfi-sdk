package ru.sstu.math.lsm;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.optimization.RealPointValuePair;

/**
 * <code>MultivariateLsmSolverImpl</code> class is default implementation of
 * {@link MultivariateLsmSolver} interface.
 *
 * @author Denis_Murashev
 * @since Math 1.0
 */
public class MultivariateLsmSolverImpl implements MultivariateLsmSolver {

	/**
	 * {@inheritDoc}
	 */
	public double[] solve(RealPointValuePair[] points,
			MultivariateRealFunction[] basis) throws MathException {
		double[][] a = new double[basis.length][basis.length];
		double[] b = new double[basis.length];
		for (int k = 0; k < a.length; k++) {
			for (int i = 0; i < a.length; i++) {
				a[k][i] = 0.0;
				for (RealPointValuePair point : points) {
					a[k][i] += basis[k].value(point.getPointRef())
							* basis[i].value(point.getPointRef());
				}
			}
			b[k] = 0.0;
			for (RealPointValuePair point : points) {
				b[k] += basis[k].value(point.getPointRef())
						* point.getValue();
			}
		}

		RealMatrix matrix = new Array2DRowRealMatrix(a);
		LUDecomposition decomposition = new LUDecompositionImpl(matrix);
		return decomposition.getSolver().solve(b);
	}
}
