package ru.sstu.math.util;

import junit.framework.TestCase;

import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.log4j.Logger;

/**
 * Class <code>MatrixUtilTest</code> is unit test for {@link MatrixUtil} class.
 *
 * @author Denis A. Murashev
 * @since Math 2.0
 */
public class MatrixUtilTest extends TestCase {

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(MatrixUtilTest.class);

	/**
	 * Delta.
	 */
	private static final double DELTA = 1e-10;

	/**
	 * Tests {@link MatrixUtil#multiply(RealMatrix, PolynomialFunction[])}
	 * method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testMultiply() throws Exception {
		double[][] data = {
				{1.0, 1.0},
				{0.0, 1.0},
		};
		RealMatrix matrix = new Array2DRowRealMatrix(data);
		PolynomialFunction[] column = {
				PolynomialUtil.create(1.0),
				PolynomialUtil.create(0.0, 1.0),
		};
		double[][] result = {
				{1.0, 1.0},
				{0.0, 1.0},
		};
		int i = 0;
		for (PolynomialFunction p : MatrixUtil.multiply(matrix, column)) {
			log.debug("Result polynom: " + p);
			for (int j = 0; j < p.getCoefficients().length; j++) {
				assertEquals(result[i][j], p.getCoefficients()[j], DELTA);
			}
			i++;
		}
	}
}
