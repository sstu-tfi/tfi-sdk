package ru.sstu.math.ep;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math.MathException;

/**
 * <code>ParserTest</code> class tests {@link Parser}.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public class ParserTest extends TestCase {

	/**
	 * Random generator.
	 */
	private static final Random RANDOM = new Random();

	/**
	 * Delta.
	 */
	private static final double DELTA = 1e-12;

	/**
	 * Evaluates expression.
	 *
	 * @param expression expression
	 * @param arg        arguments
	 * @return result
	 * @throws MathException if some error occurs
	 */
	private static double evaluate(String expression, Map<String, Double> arg)
			throws MathException {
		Parser parser = new Parser();
		if (arg != null) {
			return parser.compile(expression, arg.keySet()).evaluate(arg);
		}
		return parser.compile(expression).evaluate(arg);
	}

	/**
	 * Initializes x value.
	 *
	 * @param arg argument
	 * @return x value
	 */
	private static double initX(Map<String, Double> arg) {
		double x = RANDOM.nextDouble();
		arg.put("x", x);
		return x;
	}

	/**
	 * Initializes y value.
	 *
	 * @param arg argument
	 * @return y value
	 */
	private static double initY(Map<String, Double> arg) {
		double y = RANDOM.nextDouble();
		arg.put("y", y);
		return y;
	}

	/**
	 * Initializes z value.
	 *
	 * @param arg argument
	 * @return z value
	 */
	private static double initZ(Map<String, Double> arg) {
		double z = RANDOM.nextDouble();
		arg.put("z", z);
		return z;
	}

	/**
	 * Tests operations.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testOperations() throws Exception {
		Map<String, Double> arg = new HashMap<String, Double>();
		double x = initX(arg);
		double y = initY(arg);
		double z = initZ(arg);
		double expected = z - y + x;
		double actual = evaluate("z - y + x", arg);
		assertEquals(expected, actual, DELTA);
		expected = z / y * x;
		actual = evaluate("z / y * x", arg);
		assertEquals(expected, actual, DELTA);
	}

	/**
	 * Tests parser.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testFunctions() throws Exception {
		Map<String, Double> arg = new HashMap<String, Double>();
		double x = initX(arg);
		double y = initY(arg);
		double z = initZ(arg);
		double expected = Math.sqrt(x * x + y * y + z * z);
		double actual = evaluate("sqrt(x^2 + y^2 + z^2)", arg);
		assertEquals(expected, actual, DELTA);

		expected = Math.sin(x) + Math.cos(y) + Math.tan(z);
		actual = evaluate("sin(x) + cos(y) + tg(z)", arg);
		assertEquals(expected, actual, DELTA);

		expected = Math.exp(x) + Math.exp(y) + Math.exp(z);
		actual = evaluate("e^x + e^y + e^z", arg);
		assertEquals(expected, actual, DELTA);

		expected = Math.PI;
		actual = evaluate("pi", null);
		assertEquals(expected, actual, DELTA);
	}
}
