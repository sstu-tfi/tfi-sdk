package ru.sstu.math.ep;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.MathException;

/**
 * <code>AbstractNode</code> class represents abstract node in expression.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
abstract class AbstractNode implements Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 3024745069969800975L;

	/**
	 * Node text representation.
	 */
	private final String name;

	/**
	 * @param name name
	 */
	protected AbstractNode(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/**
	 * Evaluates node evaluate for given argument.
	 *
	 * @param arg argument
	 * @return node evaluate
	 * @throws MathException if cannot evaluate node
	 */
	protected abstract double evaluate(Map<String, ? extends Number> arg)
			throws MathException;

	/**
	 * Simplifies node.
	 *
	 * @return simplified node
	 * @throws MathException if some error occurs
	 */
	protected abstract AbstractNode simplify() throws MathException;

	/**
	 * Differentiates node.
	 *
	 * @param arg argument name
	 * @return derivative
	 * @throws MathException if some error occurs
	 */
	protected abstract AbstractNode derivative(String arg) throws MathException;

	/**
	 * Sets children to the node.
	 *
	 * @param children current node children
	 * @throws MathException if there is wrong number of operands
	 */
	protected abstract void setChildren(AbstractNode[] children)
			throws MathException;

	/**
	 * Provides dependencies variables names.
	 *
	 * @return string names of variables of the node
	 */
	protected abstract Set<String> getVariables();

	/**
	 * The derivative is not supported.
	 *
	 * @author Denis_Murashev
	 */
	public static class UnsupportedDerivativeException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 5284288678707939128L;
	}
}
