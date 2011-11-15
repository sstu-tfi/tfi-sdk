package ru.sstu.math.ep;

import java.util.Map;

import org.apache.commons.math.MathException;

/**
 * Class <code>Expression</code> holds mathematical expression as tree,
 * can be evaluated, and can be differentiated.
 *
 * @author Denis A. Murashev
 * @since Math 2.0
 */
public final class Expression {

	/**
	 * Root node.
	 */
	private AbstractNode root;

	/**
	 * Only {@link Parser} or <code>Expression</code> can instantiate it.
	 *
	 * @param root root node
	 */
	Expression(AbstractNode root) {
		this.root = root;
	}

	/**
	 * Evaluates expression.
	 *
	 * @param arg arguments
	 * @return expression value
	 * @throws MathException if cannot evaluate expression
	 */
	public double evaluate(Map<String, ? extends Number> arg)
			throws MathException {
		return root.evaluate(arg);
	}

	/**
	 * Simplifies compiled expression.
	 *
	 * @throws MathException if some error occurs
	 */
	public void simplify() throws MathException {
		root = root.simplify();
	}

	/**
	 * Differentiates expression.
	 *
	 * @param arg name of variable
	 * @return derivative
	 * @throws MathException if some error occurs
	 */
	public Expression derivative(String arg) throws MathException {
		return new Expression(root.derivative(arg));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return root.toString();
	}
}
