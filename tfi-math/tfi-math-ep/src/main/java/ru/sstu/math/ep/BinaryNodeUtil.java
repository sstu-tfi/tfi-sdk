package ru.sstu.math.ep;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math.MathException;

/**
 * <code>BinaryNodeUtil</code> class provides utility methods for working with
 * {@link BinaryNode}.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
final class BinaryNodeUtil {

	/**
	 * Plus operator.
	 */
	static final OperatorWrapper PLUS = new OperatorWrapper("+",
			Operator.LOW_PRIORITY) {

		public double evaluate(double left, double right) {
			return left + right;
		}
	};

	/**
	 * Minus operator.
	 */
	static final OperatorWrapper MINUS = new OperatorWrapper("-",
			Operator.LOW_PRIORITY) {

		public double evaluate(double left, double right) {
			return left - right;
		}
	};

	/**
	 * Multiply operator.
	 */
	static final OperatorWrapper MULTIPLY = new OperatorWrapper("*",
			Operator.MEDIUM_PRIORITY) {

		public double evaluate(double left, double right) {
			return left * right;
		}
	};

	/**
	 * Divide operator.
	 */
	static final OperatorWrapper DIVIDE = new OperatorWrapper("/",
			Operator.MEDIUM_PRIORITY) {

		public double evaluate(double left, double right) {
			return left / right;
		}
	};

	/**
	 * Module operator.
	 */
	static final OperatorWrapper MODULE = new OperatorWrapper("%",
			Operator.MEDIUM_PRIORITY) {

		public double evaluate(double left, double right) {
			return left % right;
		}
	};

	/**
	 * Power operator.
	 */
	static final OperatorWrapper POWER = new OperatorWrapper("^",
			Operator.HIGH_PRIORITY) {

		public double evaluate(double left, double right) {
			return Math.pow(left, right);
		}
	};

	/**
	 * Available operators.
	 */
	private static final Map<String, Operator> OPERATORS
		= new HashMap<String, Operator>();

	static {
		addOperator(PLUS);
		addOperator(MINUS);
		addOperator(MULTIPLY);
		addOperator(DIVIDE);
		addOperator(MODULE);
		addOperator(POWER);
	}

	/**
	 * Available derivative rules.
	 */
	private static final Map<String, DerivativeRule<BinaryNode>> RULES
		= new HashMap<String, DerivativeRule<BinaryNode>>();

	static {
		RULES.put(PLUS.getName(), new PlusRule());
		RULES.put(MINUS.getName(), new MinusRule());
		RULES.put(MULTIPLY.getName(), new MultiplyRule());
		RULES.put(DIVIDE.getName(), new DivideRule());
		RULES.put(POWER.getName(), new PowerRule());
	}

	/**
	 * No instances needed.
	 */
	private BinaryNodeUtil() {
	}

	/**
	 * Looking for binary operator with given name.
	 *
	 * @param name operator's name
	 * @return binary operator
	 */
	public static BinaryNode getNode(String name) {
		Operator operator = OPERATORS.get(name);
		return (operator != null) ? new BinaryNode(name, operator) : null;
	}

	/**
	 * Provides derivative rule for given operator name.
	 *
	 * @param name name
	 * @return rule
	 */
	public static DerivativeRule<BinaryNode> getRule(String name) {
		return RULES.get(name);
	}

	/**
	 * Adds new operator.
	 *
	 * @param operator operator's implementation
	 */
	private static void addOperator(OperatorWrapper operator) {
		OPERATORS.put(operator.getName(), operator);
	}

	/**
	 * Just common implementation of operator.
	 *
	 * @author Denis_Murashev
	 */
	protected abstract static class OperatorWrapper implements Operator {

		/**
		 * Operator name.
		 */
		private final String name;

		/**
		 * Operator priority.
		 */
		private final int priority;

		/**
		 * @param name     name
		 * @param priority priority
		 */
		private OperatorWrapper(String name, int priority) {
			this.name = name;
			this.priority = priority;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getPriority() {
			return priority;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}

	/**
	 * Derivative rule for addition.
	 *
	 * @author Denis_Murashev
	 */
	private static class PlusRule implements DerivativeRule<BinaryNode> {

		/**
		 * {@inheritDoc}
		 */
		public AbstractNode derivative(BinaryNode node, String arg)
				throws MathException {
			BinaryNode result = new BinaryNode(PLUS);
			result.setChildren(new AbstractNode[]{
				node.getLeftNode().derivative(arg),
				node.getRightNode().derivative(arg),
			});
			return result;
		}
	}

	/**
	 * Derivative rule for subtraction.
	 *
	 * @author Denis_Murashev
	 */
	private static class MinusRule implements DerivativeRule<BinaryNode> {

		/**
		 * {@inheritDoc}
		 */
		public AbstractNode derivative(BinaryNode node, String arg)
				throws MathException {
			BinaryNode result = new BinaryNode(MINUS);
			result.setChildren(new AbstractNode[]{
				node.getLeftNode().derivative(arg),
				node.getRightNode().derivative(arg),
			});
			return result;
		}
	}

	/**
	 * Derivative rule for multiplication.
	 *
	 * @author Denis_Murashev
	 */
	private static class MultiplyRule implements DerivativeRule<BinaryNode> {

		/**
		 * {@inheritDoc}
		 */
		public AbstractNode derivative(BinaryNode node, String arg)
				throws MathException {
			BinaryNode first = new BinaryNode(MULTIPLY);
			first.setChildren(new AbstractNode[]{
				node.getLeftNode().derivative(arg),
				node.getRightNode(),
			});
			BinaryNode second = new BinaryNode(MULTIPLY);
			second.setChildren(new AbstractNode[]{
				node.getLeftNode(),
				node.getRightNode().derivative(arg),
			});
			BinaryNode result = new BinaryNode(PLUS);
			result.setChildren(new AbstractNode[]{first, second});
			return result;
		}
	}

	/**
	 * Derivative rule for division.
	 *
	 * @author Denis_Murashev
	 */
	private static class DivideRule implements DerivativeRule<BinaryNode> {

		/**
		 * {@inheritDoc}
		 */
		public AbstractNode derivative(BinaryNode node, String arg)
				throws MathException {
			BinaryNode first = new BinaryNode(MULTIPLY);
			first.setChildren(new AbstractNode[]{
				node.getLeftNode().derivative(arg),
				node.getRightNode(),
			});
			BinaryNode second = new BinaryNode(MULTIPLY);
			second.setChildren(new AbstractNode[]{
				node.getLeftNode(),
				node.getRightNode().derivative(arg),
			});
			BinaryNode top = new BinaryNode(MINUS);
			top.setChildren(new AbstractNode[]{first, second});
			BinaryNode bottom = new BinaryNode(POWER);
			bottom.setChildren(new AbstractNode[]{
				node.getRightNode(),
				new ConstantNode(2),
			});
			BinaryNode result = new BinaryNode(DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}

	/**
	 * Derivative rule for power function.
	 *
	 * @author Denis_Murashev
	 */
	private static class PowerRule implements DerivativeRule<BinaryNode> {

		/**
		 * {@inheritDoc}
		 */
		public AbstractNode derivative(BinaryNode node, String arg)
				throws MathException {
			if (node.getRightNode().getVariables().contains(arg)) {
				return complex(node, arg);
			}
			return simple(node, arg);
		}

		/**
		 * Evaluates derivative in case of constant power.
		 *
		 * @param node node
		 * @param arg argument
		 * @return derivative
		 * @throws MathException if some error occurs
		 */
		private AbstractNode simple(BinaryNode node, String arg)
				throws MathException {
			BinaryNode power = new BinaryNode(MINUS);
			power.setChildren(new AbstractNode[]{
				node.getRightNode(),
				new ConstantNode(1.0),
			});
			BinaryNode first = new BinaryNode(POWER);
			first.setChildren(new AbstractNode[]{
				node.getLeftNode(),
				power,
			});
			BinaryNode second = new BinaryNode(MULTIPLY);
			second.setChildren(new AbstractNode[]{
				node.getRightNode(),
				first,
			});
			BinaryNode result = new BinaryNode(MULTIPLY);
			result.setChildren(new AbstractNode[]{
				second,
				node.getLeftNode().derivative(arg),
			});
			return result;
		}


		/**
		 * Evaluates derivative in case of non constant power.
		 *
		 * @param node node
		 * @param arg argument
		 * @return derivative
		 * @throws MathException if some error occurs
		 */
		private AbstractNode complex(BinaryNode node, String arg)
				throws MathException {
			FunctionNode function = new FunctionNode(FunctionNodeUtil.LN);
			function.setChildren(new AbstractNode[]{null, node.getLeftNode()});
			BinaryNode first = new BinaryNode(MULTIPLY);
			first.setChildren(new AbstractNode[]{
				node.getRightNode().derivative(arg),
				function,
			});
			BinaryNode second = new BinaryNode(DIVIDE);
			second.setChildren(new AbstractNode[]{
				node.getRightNode(),
				node.getLeftNode(),
			});
			BinaryNode third = new BinaryNode(MULTIPLY);
			third.setChildren(new AbstractNode[]{
				second,
				node.getLeftNode().derivative(arg),
			});
			BinaryNode fourth = new BinaryNode(PLUS);
			fourth.setChildren(new AbstractNode[]{first, third});
			BinaryNode fifth = new BinaryNode(POWER);
			fifth.setChildren(new AbstractNode[]{
				node.getLeftNode(),
				node.getRightNode(),
			});
			BinaryNode result = new BinaryNode(MULTIPLY);
			result.setChildren(new AbstractNode[]{fifth, fourth});
			return result;
		}
	}
}
