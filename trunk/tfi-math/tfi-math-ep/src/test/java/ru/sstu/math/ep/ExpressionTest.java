package ru.sstu.math.ep;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * <code>ExpressionTest</code> class tests {@link Expression} methods.
 *
 * @author Denis A. Murashev
 * @author Dmitry_Petrov
 * @since Math 2.0
 */
public class ExpressionTest extends TestCase {

	/**
	 * Tests {@link Expression#simplify()} method.
	 *
	 * @throws Exception if some error occurs
	 * @since Math 2.0
	 */
	public void testSimplify() throws Exception {
		Parser parser = new Parser();
		final TestItem[] data = {
			new TestItem("sqrt(1^2 + 2^2 + 2^2)", "3.0"),
			new TestItem("abs(-2^3 * sqrt(4) + 1)", "15.0"),
			new TestItem("arcsin(10^2 - 33 * 3) * 180 / pi", "90.0"),
			new TestItem("arctg(30 / 3 - 2 * 4.5) * 180 / pi", "45.0"),
		};
		for (TestItem item : data) {
			Expression expression = parser.compile(item.expression);
			expression.simplify();
			String actual = expression.toString();
			assertEquals(item.expected, actual);
		}
	}

	/**
	 * Tests {@link Expression#derivative(String)} method.
	 *
	 * @throws Exception if some error occurs
	 * @since Math 2.0
	 */
	public void testDerivative() throws Exception {
		Parser parser = new Parser();
		final String argumentName = "t";
		Map<String, Double> arg = new HashMap<String, Double>();
		final TestItem[] data = {
			new TestItem("sin(t)", 0.0, 1.0),
			new TestItem("cos(t) + 5 * sin(t) + 5", 0.0, 5.0),
			new TestItem("5 * t^3 * t^2", 2.0, 400.0),
			new TestItem("(t^3)/(5 * t^2) ", 2.0, 0.2),
		};
		for (TestItem item : data) {
			arg.put(argumentName, item.argument);
			Expression expression = parser.compile(item.expression,
					arg.keySet());
			double actual = expression.derivative(argumentName).evaluate(arg);
			assertEquals(item.expected, actual);
		}
	}

	/**
	 * Test data holder.
	 *
	 * @author Denis_Murashev
	 */
	private static final class TestItem {

		/**
		 * Expression.
		 */
		private final String expression;

		/**
		 * Argument value.
		 */
		private final double argument;

		/**
		 * Expected value.
		 */
		private final Object expected;

		/**
		 * @param expression expression
		 * @param argument   argument value
		 * @param expected   expected value
		 */
		private TestItem(String expression, double argument, Object expected) {
			this.expression = expression;
			this.argument = argument;
			this.expected = expected;
		}

		/**
		 * @param expression expression
		 * @param expected   expected value
		 */
		private TestItem(String expression, Object expected) {
			this.expression = expression;
			this.argument = 0.0;
			this.expected = expected;
		}
	}
}
