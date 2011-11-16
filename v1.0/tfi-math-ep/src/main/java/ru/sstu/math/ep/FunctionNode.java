package ru.sstu.math.ep;

import java.util.Map;
import java.util.Set;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

import ru.sstu.math.ep.FunctionNodeUtil.FunctionWrapper;

/**
 * <code>FunctionNode</code> class represents parser node for univariate
 * function.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
final class FunctionNode extends AbstractNode {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 7286619695315050178L;

	/**
	 * Parser node.
	 */
	private AbstractNode node;

	/**
	 * Function.
	 */
	private UnivariateRealFunction function;

	/**
	 * Function node initialization.
	 *
	 * @param name     function name
	 * @param function function
	 */
	FunctionNode(String name, UnivariateRealFunction function) {
		super(name);
		this.function = function;
	}

	/**
	 * Function node initialization.
	 *
	 * @param function function
	 */
	FunctionNode(FunctionWrapper function) {
		super(function.getName());
		this.function = function;
	}

	/**
	 * {@inheritDoc}
	 */
	protected double evaluate(Map<String, ? extends Number> arg)
			throws MathException {
		try {
			return function.value(node.evaluate(arg));
		} catch (FunctionEvaluationException e) {
			throw new MathException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public AbstractNode simplify() throws MathException {
		if (node.getVariables().isEmpty()) {
			return new ConstantNode(evaluate(null));
		}
		node = node.simplify();
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public AbstractNode derivative(String arg) throws MathException {
		if (!getVariables().contains(arg)) {
			return Constants.ZERO_NODE;
		}
		DerivativeRule<FunctionNode> rule = FunctionNodeUtil.getRule(getName());
		if (rule == null) {
			throw new UnsupportedDerivativeException();
		}
		return rule.derivative(this, arg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setChildren(AbstractNode[] children) throws MathException {
		if (children.length != 2 || children[0] != null) {
			throw new UnknownFunctionException();
		}
		if (children[1] == null) {
			throw new FunctionArgumentException();
		}
		node = children[1];
	}

	/**
	 * {@inheritDoc}
	 */
	protected Set<String> getVariables() {
		return node.getVariables();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getName());
		buffer.append('(');
		buffer.append(node.toString());
		buffer.append(')');
		return buffer.toString();
	}

	/**
	 * @return the node
	 */
	AbstractNode getNode() {
		return node;
	}

	/**
	 * Unknown function call.
	 *
	 * @author Denis_Murashev
	 */
	public static class UnknownFunctionException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = -6047433518634954422L;
	}

	/**
	 * Function without argument call.
	 *
	 * @author Denis_Murashev
	 */
	public static class FunctionArgumentException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 5916363231583492472L;
	}
}
