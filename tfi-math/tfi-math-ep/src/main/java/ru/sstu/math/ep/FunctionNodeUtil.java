package ru.sstu.math.ep;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math.MathException;
import org.apache.commons.math.analysis.UnivariateRealFunction;

/**
 * <code>FunctionNodeUtil</code> class provides utility methods for working with
 * {@link FunctionNode}.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
final class FunctionNodeUtil {

	/**
	 * Square root function.
	 */
	static final FunctionWrapper SQRT = new FunctionWrapper("sqrt") {
		public double value(double arg) {
			return Math.sqrt(arg);
		}
	};

	/**
	 * Exponent function.
	 */
	static final FunctionWrapper EXP = new FunctionWrapper("exp") {
		public double value(double arg) {
			return Math.exp(arg);
		}
	};

	/**
	 * Natural logarithm function.
	 */
	static final FunctionWrapper LN = new FunctionWrapper("ln") {
		public double value(double arg) {
			return Math.log(arg);
		}
	};

	/**
	 * Logarithm function.
	 */
	static final FunctionWrapper LOG = new FunctionWrapper("log") {
		public double value(double arg) {
			return Math.log10(arg);
		}
	};

	/**
	 * Signum function.
	 */
	static final FunctionWrapper SIGN = new FunctionWrapper("sign") {
		public double value(double arg) {
			return Math.signum(arg);
		}
	};

	/**
	 * Absolute value function.
	 */
	static final FunctionWrapper ABS = new FunctionWrapper("abs") {
		public double value(double arg) {
			return Math.abs(arg);
		}
	};

	/**
	 * Sine function.
	 */
	static final FunctionWrapper SIN = new FunctionWrapper("sin") {
		public double value(double arg) {
			return Math.sin(arg);
		}
	};

	/**
	 * Cosine function.
	 */
	static final FunctionWrapper COS = new FunctionWrapper("cos") {
		public double value(double arg) {
			return Math.cos(arg);
		}
	};

	/**
	 * Tangent function.
	 */
	static final FunctionWrapper TG = new FunctionWrapper("tg") {
		public double value(double arg) {
			return Math.tan(arg);
		}
	};

	/**
	 * Cotangent function.
	 */
	static final FunctionWrapper CTG = new FunctionWrapper("ctg") {
		public double value(double arg) {
			return 1.0 / Math.tan(arg);
		}
	};

	/**
	 * Arc sine function.
	 */
	static final FunctionWrapper ASIN = new FunctionWrapper("arcsin") {
		public double value(double arg) {
			return Math.asin(arg);
		}
	};

	/**
	 * Arc cosine function.
	 */
	static final FunctionWrapper ACOS = new FunctionWrapper("arccos") {
		public double value(double arg) {
			return Math.acos(arg);
		}
	};

	/**
	 * Arc tangent function.
	 */
	static final FunctionWrapper ATG = new FunctionWrapper("arctg") {
		public double value(double arg) {
			return Math.atan(arg);
		}
	};

	/**
	 * Hyperbolic sine function.
	 */
	static final FunctionWrapper SH = new FunctionWrapper("sh") {
		public double value(double arg) {
			return Math.sinh(arg);
		}
	};

	/**
	 * Hyperbolic cosine function.
	 */
	static final FunctionWrapper CH = new FunctionWrapper("ch") {
		public double value(double arg) {
			return Math.cosh(arg);
		}
	};

	/**
	 * Hyperbolic tangent function.
	 */
	static final FunctionWrapper TH = new FunctionWrapper("th") {
		public double value(double arg) {
			return Math.tanh(arg);
		}
	};

	/**
	 * Available functions.
	 */
	private static final Map<String, UnivariateRealFunction> FUNCTIONS
			= new HashMap<String, UnivariateRealFunction>();

	static {
		addFunction(SQRT);
		addFunction(EXP);
		addFunction(LN);
		addFunction(LOG);
		addFunction(SIGN);
		addFunction(ABS);
		addFunction(SIN);
		addFunction(COS);
		addFunction(TG);
		addFunction(CTG);
		addFunction(ASIN);
		addFunction(ACOS);
		addFunction(ATG);
		addFunction(SH);
		addFunction(CH);
		addFunction(TH);
	}

	/**
	 * Available derivative rules.
	 */
	private static final Map<String, DerivativeRule<FunctionNode>> RULES
		= new HashMap<String, DerivativeRule<FunctionNode>>();

	static {
		RULES.put(SQRT.getName(), new SqrtRule());
		RULES.put(EXP.getName(), new ExpRule());
		RULES.put(LN.getName(), new LnRule());
		RULES.put(LOG.getName(), new LogRule());
		RULES.put(SIGN.getName(), new SignRule());
		RULES.put(ABS.getName(), new AbsRule());
		RULES.put(SIN.getName(), new SinRule());
		RULES.put(COS.getName(), new CosRule());
		RULES.put(TG.getName(), new TgRule());
		RULES.put(CTG.getName(), new CtgRule());
		RULES.put(ASIN.getName(), new AsinRule());
		RULES.put(ACOS.getName(), new AcosRule());
		RULES.put(ATG.getName(), new AtgRule());
		RULES.put(SH.getName(), new ShRule());
		RULES.put(CH.getName(), new ChRule());
		RULES.put(TH.getName(), new ThRule());
	}

	/**
	 * No instances needed.
	 */
	private FunctionNodeUtil() {
	}

	/**
	 * Looking for function with given name.
	 *
	 * @param name function name
	 * @return function instance
	 */
	public static FunctionNode getNode(String name) {
		UnivariateRealFunction function = FUNCTIONS.get(name);
		return (function != null) ? new FunctionNode(name, function) : null;
	}

	/**
	 * Provides derivative rule for given function name.
	 *
	 * @param name name
	 * @return rule
	 */
	public static DerivativeRule<FunctionNode> getRule(String name) {
		return RULES.get(name);
	}

	/**
	 * Adds new function.
	 *
	 * @param function function's implementation
	 */
	public static void addFunction(FunctionWrapper function) {
		FUNCTIONS.put(function.getName(), function);
	}

	/**
	 * Simple function wrapper.
	 *
	 * @author Denis_Murashev
	 */
	protected abstract static class FunctionWrapper
			implements UnivariateRealFunction {

		/**
		 * Function name.
		 */
		private final String name;

		/**
		 * @param name name
		 */
		private FunctionWrapper(String name) {
			this.name = name;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}

	/**
	 * Abstract complex function derivative rule.
	 *
	 * @author Denis_Murashev
	 */
	private abstract static class FunctionRule
			implements DerivativeRule<FunctionNode> {

		/**
		 * {@inheritDoc}
		 */
		public final AbstractNode derivative(FunctionNode node, String arg)
				throws MathException {
			BinaryNode result = new BinaryNode(BinaryNodeUtil.MULTIPLY);
			result.setChildren(new AbstractNode[]{
				simpleDerivative(node),
				node.getNode().derivative(arg),
			});
			return result;
		}

		/**
		 * @param node node
		 * @return simple derivative
		 * @throws MathException if some error occurs
		 */
		protected abstract AbstractNode simpleDerivative(FunctionNode node)
				throws MathException;
	}

	/**
	 * Rule for square root function.
	 *
	 * @author Denis_Murashev
	 */
	private static class SqrtRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode function = new FunctionNode(SQRT);
			function.setChildren(new AbstractNode[]{null, node.getNode()});
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{
				new ConstantNode(1.0 / 2.0),
				function,
			});
			return result;
		}
	}

	/**
	 * Rule for exponent function.
	 *
	 * @author Denis_Murashev
	 */
	private static class ExpRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode result = new FunctionNode(EXP);
			result.setChildren(new AbstractNode[]{null, node.getNode()});
			return result;
		}
	}

	/**
	 * Rule for natural logarithm function.
	 *
	 * @author Denis_Murashev
	 */
	private static class LnRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{
				new ConstantNode(1.0),
				node.getNode(),
			});
			return result;
		}
	}

	/**
	 * Rule for 10 logarithm function.
	 *
	 * @author Denis_Murashev
	 */
	private static class LogRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			final double ten = 10;
			BinaryNode result = new BinaryNode(BinaryNodeUtil.MULTIPLY);
			result.setChildren(new AbstractNode[]{
				new ConstantNode(Math.log(ten)),
				node.getNode(),
			});
			return result;
		}
	}

	/**
	 * Rule for signum function.
	 *
	 * @author Denis_Murashev
	 */
	private static class SignRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			return new ConstantNode();
		}
	}

	/**
	 * Rule for absolute value function.
	 *
	 * @author Denis_Murashev
	 */
	private static class AbsRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode result = new FunctionNode(SIGN);
			result.setChildren(new AbstractNode[]{null, node.getNode()});
			return result;
		}
	}

	/**
	 * Rule for sine function.
	 *
	 * @author Denis_Murashev
	 */
	private static class SinRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode result = new FunctionNode(COS);
			result.setChildren(new AbstractNode[]{null, node.getNode()});
			return result;
		}
	}

	/**
	 * Rule for cosine function.
	 *
	 * @author Denis_Murashev
	 */
	private static class CosRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			AbstractNode left = new ConstantNode(-1.0);
			FunctionNode right = new FunctionNode(SIN);
			right.setChildren(new AbstractNode[]{null, node.getNode()});
			BinaryNode result = new BinaryNode(BinaryNodeUtil.MULTIPLY);
			result.setChildren(new AbstractNode[]{left, right});
			return result;
		}
	}

	/**
	 * Rule for tangent function.
	 *
	 * @author Denis_Murashev
	 */
	private static class TgRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode left = new FunctionNode(COS);
			left.setChildren(new AbstractNode[]{null, node.getNode()});
			AbstractNode right = new ConstantNode(2.0);
			BinaryNode bottom = new BinaryNode(BinaryNodeUtil.POWER);
			bottom.setChildren(new AbstractNode[]{left, right});
			AbstractNode top = new ConstantNode(1.0);
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}

	/**
	 * Rule for cotangent function.
	 *
	 * @author Denis_Murashev
	 */
	private static class CtgRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode left = new FunctionNode(SIN);
			left.setChildren(new AbstractNode[]{null, node.getNode()});
			AbstractNode right = new ConstantNode(2.0);
			BinaryNode bottom = new BinaryNode(BinaryNodeUtil.POWER);
			bottom.setChildren(new AbstractNode[]{left, right});
			AbstractNode top = new ConstantNode(-1.0);
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}

	/**
	 * Rule for arc sine function.
	 *
	 * @author Denis_Murashev
	 */
	private static class AsinRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			AbstractNode left = new ConstantNode(1.0);
			BinaryNode right = new BinaryNode(BinaryNodeUtil.MULTIPLY);
			right.setChildren(new AbstractNode[]{
				node.getNode(),
				node.getNode(),
			});
			AbstractNode difference = new BinaryNode(BinaryNodeUtil.MINUS);
			difference.setChildren(new AbstractNode[]{left, right});
			AbstractNode bottom = new FunctionNode(SQRT);
			bottom.setChildren(new AbstractNode[]{null, difference});
			AbstractNode top = new ConstantNode(1.0);
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}

	/**
	 * Rule for arc cosine function.
	 *
	 * @author Denis_Murashev
	 */
	private static class AcosRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			AbstractNode left = new ConstantNode(1.0);
			BinaryNode right = new BinaryNode(BinaryNodeUtil.MULTIPLY);
			right.setChildren(new AbstractNode[]{
				node.getNode(),
				node.getNode(),
			});
			AbstractNode difference = new BinaryNode(BinaryNodeUtil.MINUS);
			difference.setChildren(new AbstractNode[]{left, right});
			AbstractNode bottom = new FunctionNode(SQRT);
			bottom.setChildren(new AbstractNode[]{null, difference});
			AbstractNode top = new ConstantNode(-1.0);
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}

	/**
	 * Rule for arc tangent function.
	 *
	 * @author Denis_Murashev
	 */
	private static class AtgRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			AbstractNode left = new ConstantNode(1.0);
			BinaryNode right = new BinaryNode(BinaryNodeUtil.MULTIPLY);
			right.setChildren(new AbstractNode[]{
				node.getNode(),
				node.getNode(),
			});
			AbstractNode bottom = new BinaryNode(BinaryNodeUtil.PLUS);
			bottom.setChildren(new AbstractNode[]{left, right});
			AbstractNode top = new ConstantNode(1.0);
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}

	/**
	 * Rule for hyperbolic sine function.
	 *
	 * @author Denis_Murashev
	 */
	private static class ShRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode result = new FunctionNode(CH);
			result.setChildren(new AbstractNode[]{null, node.getNode()});
			return result;
		}
	}

	/**
	 * Rule for hyperbolic cosine function.
	 *
	 * @author Denis_Murashev
	 */
	private static class ChRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode result = new FunctionNode(SH);
			result.setChildren(new AbstractNode[]{null, node.getNode()});
			return result;
		}
	}

	/**
	 * Rule for hyperbolic tangent function.
	 *
	 * @author Denis_Murashev
	 */
	private static class ThRule extends FunctionRule {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected AbstractNode simpleDerivative(FunctionNode node)
				throws MathException {
			FunctionNode left = new FunctionNode(CH);
			left.setChildren(new AbstractNode[]{null, node.getNode()});
			AbstractNode right = new ConstantNode(2.0);
			BinaryNode bottom = new BinaryNode(BinaryNodeUtil.POWER);
			bottom.setChildren(new AbstractNode[]{left, right});
			AbstractNode top = new ConstantNode(1.0);
			BinaryNode result = new BinaryNode(BinaryNodeUtil.DIVIDE);
			result.setChildren(new AbstractNode[]{top, bottom});
			return result;
		}
	}
}
