package ru.sstu.math.ep;

import org.apache.commons.math.MathException;

/**
 * <code>ValueNode</code> class represents single value expression node.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
abstract class ValueNode extends AbstractNode {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -4669551258567716979L;

	/**
	 * @param name name
	 */
	ValueNode(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	protected AbstractNode simplify() {
		return this;
	}

	/**
	 * Sets children to the node.
	 *
	 * @param children current node children
	 * @throws MathException if there is wrong number of operands
	 */
	protected void setChildren(AbstractNode[] children) throws MathException {
		if (children[0] != null && children[1] != null) {
			throw new UnexpectedChildException();
		}
	}

	/**
	 * Children to constant node cannot be added.
	 *
	 * @author Denis_Murashev
	 */
	public static class UnexpectedChildException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = -1987646258482215288L;
	}
}
