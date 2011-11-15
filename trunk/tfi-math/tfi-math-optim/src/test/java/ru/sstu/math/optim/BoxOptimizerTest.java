package ru.sstu.math.optim;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;

/**
 * Unit tests for {@link BoxOptimizer} class.
 *
 * @author Denis_Murashev
 * @since Math 1.0
 */
public class BoxOptimizerTest extends TestCase {

	/**
	 * Test function.
	 */
	private static final MultivariateRealFunction FUNCTION
			= new MultivariateRealFunction() {

		public double value(double[] argument) {
			double x = argument[0];
			double y = argument[1];
			return x * x + y * y;
		}
	};

	/**
	 * Tests optimization.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testOptimizer() throws Exception {
		final double lineConstraint = 0.1;

		final Interval[] intervals = {
			new Interval(-1.0, 1.0),
			new Interval(-1.0, 1.0),
		};

		final Constraint[] constraints = {
			new Constraint() {

				public boolean checkPoint(double[] point) {
					return point[0] + point[1] <= lineConstraint;
				}
			},
		};
		BoxOptimizer optimizer = BoxOptimizer.getBoxOptimizer(intervals,
				constraints);
		final double sigma = 1e-5;
		optimizer.setAccuracy(sigma);
		double[] point = optimizer.optimize(FUNCTION, GoalType.MINIMIZE,
				new double[]{1.0, 1.0}).getPoint();
		assertEquals(lineConstraint / 2, point[0], sigma);
		assertEquals(lineConstraint / 2, point[1], sigma);
	}
}
