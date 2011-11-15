package ru.sstu.math.ep;

import java.util.Map;
import java.util.Set;

/**
 * <code>ConstantNode</code> class is used for constant nodes.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
final class ConstantNode extends ValueNode {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -6029853615974400991L;

	/**
	 * Constant value.
	 */
	private final double value;

	/**
	 * Zero constant.
	 */
	ConstantNode() {
		super(String.valueOf(0.0));
		this.value = 0.0;
	}

	/**
	 * @param value constant value
	 */
	ConstantNode(double value) {
		super(String.valueOf(value));
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	public double evaluate(Map<String, ? extends Number> arg) {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	public AbstractNode derivative(String arg) {
		return new ConstantNode();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<String> getVariables() {
		return Constants.EMPTY_SET;
	}
}
