package ru.sstu.math.optim;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.UnivariateRealOptimizer;

/**
 * Class <code>GoldenSectionOptimizerTest</code> tests
 * {@link GoldenSectionOptimizer} class.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public class GoldenSectionOptimizerTest extends TestCase {

	/**
	 * Delta.
	 */
	private static final double DELTA = 1e-7;

	/**
	 * Test function.
	 */
	private static final UnivariateRealFunction FUNCTION =
		new UnivariateRealFunction() {
			public double value(double x) {
				return x * x - 2 * x + 1;
			}
		};

	/**
	 * Tests {@link GoldenSectionOptimizer} class functionality.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testOptimizer() throws Exception {
		UnivariateRealOptimizer optimizer = new GoldenSectionOptimizer(DELTA);
		assertEquals(1.0,
				optimizer.optimize(FUNCTION, GoalType.MINIMIZE, 0.0, 2.0),
				DELTA);
	}
}
