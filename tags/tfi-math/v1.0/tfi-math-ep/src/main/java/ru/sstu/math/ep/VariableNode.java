package ru.sstu.math.ep;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Variable node.
 *
 * @author Denis_Murashev
 * @since Math 1.0
 */
final class VariableNode extends ValueNode {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 2790791459877599138L;

	/**
	 * @param name name
	 */
	VariableNode(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	protected double evaluate(Map<String, ? extends Number> arg) {
		return arg.get(getName()).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	protected AbstractNode derivative(String arg) {
		return getName().equalsIgnoreCase(arg)
				? new ConstantNode(1.0)
				: Constants.ZERO_NODE;
	}

	/**
	 * {@inheritDoc}
	 */
	protected Set<String> getVariables() {
		return new HashSet<String>(Arrays.asList(getName()));
	}
}
