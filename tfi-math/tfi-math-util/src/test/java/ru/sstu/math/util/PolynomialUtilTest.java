package ru.sstu.math.util;

import org.apache.commons.math.analysis.polynomials.PolynomialFunction;

import junit.framework.TestCase;

/**
 * <code>PolynomTest</code> class is unit test for {@link PolynomialUtil} class.
 *
 * @author Denis A. Murashev
 * @since Math 2.0
 */
public class PolynomialUtilTest extends TestCase {

	/**
	 * Test for {@link PolynomialUtil#integrate(PolynomialFunction, double)}
	 * method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testIntegrate() throws Exception {
		final double[] c = {1, 2, 3};
		PolynomialFunction p = new PolynomialFunction(c);
		final double[] expected = {1, 1, 1, 1};
		double[] actual = PolynomialUtil.integrate(p, 1).getCoefficients();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}
}
