package ru.sstu.math.lsm;

import org.apache.commons.math.analysis.UnivariateRealFunction;

import junit.framework.TestCase;

/**
 * <code>UnivariateLsmSolverTest</code> contains unit tests for
 * {@link UnivariateLsmSolver}.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
public class UnivariateLsmSolverTest extends TestCase {

	/**
	 * Tests linear approximation.
	 *
	 * @throws Exception if error occurs
	 */
	public void testLinear() throws Exception {
		final double[][] points = {
			{0.0, 0.0},
			{1.0, 3.0},
			{2.0, 0.0},
		};
		final UnivariateRealFunction[] basis = {
			new UnivariateRealFunction() {

				public double value(double x) {
					return 1.0;
				}
			},
			new UnivariateRealFunction() {

				public double value(double x) {
					return x;
				}
			},
		};
		UnivariateLsmSolver solver = new UnivariateLsmSolverImpl();
		double[] actual = solver.solve(points, basis);
		double[] expected = {1.0, 0.0};
		final double delta = 1e-7;
		for (int i = 0; i < basis.length; i++) {
			assertEquals(expected[i], actual[i], delta);
		}
	}
}
