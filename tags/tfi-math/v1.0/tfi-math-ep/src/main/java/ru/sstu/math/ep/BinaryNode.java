package ru.sstu.math.ep;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.MathException;

import ru.sstu.math.ep.BinaryNodeUtil.OperatorWrapper;

/**
 * <code>BinaryNode</code> class represents binary operation node.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
final class BinaryNode extends AbstractNode {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -4102544932238277044L;

	/**
	 * Operator.
	 */
	private Operator operator;

	/**
	 * Left operand.
	 */
	private AbstractNode leftNode;

	/**
	 * Right operand.
	 */
	private AbstractNode rightNode;

	/**
	 * Operator initialization.
	 *
	 * @param name     name
	 * @param operator operator
	 */
	BinaryNode(String name, Operator operator) {
		super(name);
		this.operator = operator;
	}


	/**
	 * Operator initialization.
	 *
	 * @param operator operator
	 */
	BinaryNode(OperatorWrapper operator) {
		super(operator.getName());
		this.operator = operator;
	}

	/**
	 * {@inheritDoc}
	 */
	protected double evaluate(Map<String, ? extends Number> arg)
			throws MathException {
		return operator.evaluate(leftNode.evaluate(arg),
				rightNode.evaluate(arg));
	}

	/**
	 * {@inheritDoc}
	 */
	public AbstractNode simplify() throws MathException {
		if (leftNode.getVariables().isEmpty()
				&& rightNode.getVariables().isEmpty()) {
			return new ConstantNode(evaluate(null));
		}
		leftNode = leftNode.simplify();
		rightNode = rightNode.simplify();
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public AbstractNode derivative(String arg) throws MathException {
		if (!getVariables().contains(arg)) {
			return Constants.ZERO_NODE;
		}
		DerivativeRule<BinaryNode> rule = BinaryNodeUtil.getRule(getName());
		if (rule == null) {
			throw new UnsupportedDerivativeException();
		}
		return rule.derivative(this, arg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setChildren(AbstractNode[] children) throws MathException {
		if (children.length != 2) {
			throw new WrongNumberOfOperandsException();
		}
		leftNode = (children[0] != null) ? children[0] : Constants.ZERO_NODE;
		rightNode = children[1];
	}

	/**
	 * {@inheritDoc}
	 */
	protected Set<String> getVariables() {
		Set<String> dependencies = new HashSet<String>();
		dependencies.addAll(leftNode.getVariables());
		dependencies.addAll(rightNode.getVariables());
		return dependencies;
	}

	/**
	 * @return priority of operator
	 */
	public int getPriority() {
		return operator.getPriority();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append('(');
		buffer.append(leftNode.toString());
		buffer.append(getName());
		buffer.append(rightNode.toString());
		buffer.append(')');
		return buffer.toString();
	}

	/**
	 * @return the leftNode
	 */
	AbstractNode getLeftNode() {
		return leftNode;
	}

	/**
	 * @return the rightNode
	 */
	AbstractNode getRightNode() {
		return rightNode;
	}

	/**
	 * The operation should have exactly 2 operands.
	 *
	 * @author Denis_Murashev
	 */
	public static class WrongNumberOfOperandsException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = -7332021057353879464L;
	}
}
