package ru.sstu.math.util;

import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;

/**
 * <code>MatrixUtil</code> class helps to work with matrices.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public final class MatrixUtil {

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(MatrixUtil.class);

	/**
	 * No instances needed.
	 */
	private MatrixUtil() {
	}

	/**
	 * Multiplies matrix of real numbers and column of polynomials.
	 *
	 * @param matrix matrix
	 * @param vector column (or vector)
	 * @return product
	 */
	public static PolynomialFunction[] multiply(RealMatrix matrix,
			PolynomialFunction[] vector) {
		int size = vector.length;
		PolynomialFunction[] result = new PolynomialFunction[size];
		for (int i = 0; i < size; i++) {
			result[i] = PolynomialUtil.create();
			for (int j = 0; j < size; j++) {
				PolynomialFunction p = PolynomialUtil.create(vector[j]);
				log.debug(p);
				p = PolynomialUtil.multiply(p, matrix.getEntry(i, j));
				log.debug(p);
				result[i] = result[i].add(p);
			}
			log.debug("Polynom created: " + result[i]);
		}
		return result;
	}
}
