package ru.sstu.math.ep;

import org.apache.commons.math.MathException;

/**
 * <code>DerivativeRule</code> describes specific rules for derivatives
 * calculation.
 *
 * @author Denis_Murashev
 * @param <T> concrete expression node
 * @since Math 2.0
 */
interface DerivativeRule<T extends AbstractNode> {

	/**
	 * Calculates derivative for given expression node.
	 *
	 * @param node node
	 * @param arg  argument name
	 * @return node for derivative
	 * @throws MathException if some error occurs
	 */
	AbstractNode derivative(T node, String arg) throws MathException;
}
