package ru.sstu.math.lsm;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.RealPointValuePair;

/**
 * <code>MultivariateLsmSolverTest</code> contains unit tests for
 * {@link MultivariateLsmSolver}.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
public class MultivariateLsmSolverTest extends TestCase {

	/**
	 * Tests linear approximation.
	 *
	 * @throws Exception if error occurs
	 */
	public void testLinear() throws Exception {
		final RealPointValuePair[] points = {
			new RealPointValuePair(new double[]{0.0, 0.0}, 0.0),
			new RealPointValuePair(new double[]{0.0, 1.0}, 1.0),
			new RealPointValuePair(new double[]{1.0, 0.0}, 1.0),
		};
		final MultivariateRealFunction[] basis = {
			new MultivariateRealFunction() {

				public double value(double[] x) {
					return 1.0;
				}
			},
			new MultivariateRealFunction() {

				public double value(double[] x) {
					return x[0];
				}
			},
			new MultivariateRealFunction() {

				public double value(double[] x) {
					return x[1];
				}
			},
		};
		MultivariateLsmSolver solver = new MultivariateLsmSolverImpl();
		double[] actual = solver.solve(points, basis);
		double[] expected = {0.0, 1.0, 1.0};
		final double delta = 1e-7;
		for (int i = 0; i < basis.length; i++) {
			assertEquals(expected[i], actual[i], delta);
		}
	}
}
