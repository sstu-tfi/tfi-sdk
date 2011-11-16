package ru.sstu.math.ep;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math.MathException;

/**
 * <code>ValueNodeUtil</code> class provides utility methods for working with
 * {@link ValueNode}.
 *
 * @author Denis_Murashev
 * @since Math 1.0
 */
final class ValueNodeUtil {

	/**
	 * Available variables.
	 */
	private static final Map<String, ValueNode> VARIABLES
			= new HashMap<String, ValueNode>();

	static {
		addConstant("e", Math.E);
		addConstant("pi", Math.PI);
	}

	/**
	 * No instances needed.
	 */
	private ValueNodeUtil() {
	}

	/**
	 * Looking for instance of ValueNode with given name.
	 *
	 * @param name the constant or variable name
	 * @return ValueNode instance for given name
	 * @throws MathException if cannot get node
	 */
	static AbstractNode getNode(String name) throws MathException {
		AbstractNode node = VARIABLES.get(name);
		if (node != null) {
			return node;
		}
		try {
			final double value = Double.parseDouble(name);
			return new ConstantNode(value);
		} catch (NumberFormatException ignored) {
			throw new UnexpectedTokenException();
		}
	}

	/**
	 * Adds new constant value.
	 *
	 * @param name  constant name
	 * @param value constant value
	 */
	private static void addConstant(String name, Number value) {
		VARIABLES.put(name, new ConstantNode(value.doubleValue()));
	}

	/**
	 * Adds variables names.
	 *
	 * @param names variables names
	 */
	static void setVariables(Iterable<String> names) {
		for (String n : names) {
			final String name = n.toLowerCase();
			VARIABLES.put(name, new VariableNode(name));
		}
	}

	/**
	 * Unexpected token.
	 *
	 * @author Denis_Murashev
	 */
	public static class UnexpectedTokenException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 6204706670210452470L;
	}
}
