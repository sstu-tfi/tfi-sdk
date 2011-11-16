package ru.sstu.math.ep;

/**
 * <code>Operator</code> interface represents binary operator.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
interface Operator {

	/**
	 * Low priority for + and -.
	 */
	int LOW_PRIORITY = 1;

	/**
	 * Medium priority for * and /.
	 */
	int MEDIUM_PRIORITY = 2;

	/**
	 * High priority for ^(power).
	 */
	int HIGH_PRIORITY = 3;

	/**
	 * Evaluates binary operator result.
	 *
	 * @param left  left operand
	 * @param right right operand
	 * @return result of operation
	 */
	double evaluate(double left, double right);

	/**
	 * Operator's priority.
	 *
	 * @return priority of the binary operator
	 */
	int getPriority();
}
