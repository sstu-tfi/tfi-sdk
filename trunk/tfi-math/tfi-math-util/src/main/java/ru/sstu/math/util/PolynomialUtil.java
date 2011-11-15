package ru.sstu.math.util;

import org.apache.commons.math.analysis.polynomials.PolynomialFunction;

/**
 * <code>PolynomialUtil</code> class contains of useful utility methods for
 * {@link PolynomialFunction} class.
 *
 * @author Denis A. Murashev
 * @since Math 2.0
 */
public final class PolynomialUtil {

	/**
	 * No instances needed.
	 */
	private PolynomialUtil() {
	}

	/**
	 * @return zero polynomial
	 */
	public static PolynomialFunction create() {
		double[] c = {0.0};
		return new PolynomialFunction(c);
	}

	/**
	 * @param c coefficients
	 * @return polynomial
	 */
	public static PolynomialFunction create(double... c) {
		return new PolynomialFunction(c);
	}

	/**
	 * @param p polynomial
	 * @return copy of polynomial
	 */
	public static PolynomialFunction create(PolynomialFunction p) {
		return new PolynomialFunction(p.getCoefficients());
	}

	/**
	 * Provides sum of polynomials.
	 *
	 * @param p first polynomial
	 * @param a second polynomial
	 * @return sum
	 */
	public static PolynomialFunction sum(PolynomialFunction p, double... a) {
		return p.add(create(a));
	}

	/**
	 * Provides difference of polynomials.
	 *
	 * @param p first polynomial
	 * @param a second polynomial
	 * @return difference
	 */
	public static PolynomialFunction diff(PolynomialFunction p, double... a) {
		return p.subtract(create(a));
	}

	/**
	 * Provides product of polynomial and scalar value.
	 *
	 * @param p polynomial
	 * @param a constant
	 * @return product
	 */
	public static PolynomialFunction multiply(PolynomialFunction p, double a) {
		return p.multiply(create(a));
	}

	/**
	 * Divides polynomial by constant.
	 *
	 * @param p polynomial
	 * @param a constant
	 * @return result of division
	 */
	public static PolynomialFunction divide(PolynomialFunction p, double a) {
		return p.multiply(create(1.0 / a));
	}

	/**
	 * Multiplies polynomial by itself for <code>exponent</code> times.
	 *
	 * @param p polynomial
	 * @param e exponent
	 * @return polynomial to the <code>exponent</code> power
	 */
	public static PolynomialFunction power(PolynomialFunction p, int e) {
		PolynomialFunction result = create(1.0);
		for (int i = 0; i < e; i++) {
			result = result.multiply(p);
		}
		return result;
	}

	/**
	 * Provides integral of the polynomial.
	 *
	 * @param p polynomial
	 * @param a integration constant
	 * @return integral
	 */
	public static PolynomialFunction integrate(PolynomialFunction p, double a) {
		double[] initial = p.getCoefficients();
		int power = initial.length + 1;
		double[] values = new double[power];
		values[0] = a;
		for (int i = 1; i < power; i++) {
			values[i] = initial[i - 1] / i;
		}
		return new PolynomialFunction(values);
	}
}
