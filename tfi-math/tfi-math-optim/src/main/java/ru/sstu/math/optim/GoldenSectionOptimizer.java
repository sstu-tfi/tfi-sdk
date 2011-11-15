package ru.sstu.math.optim;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MaxIterationsExceededException;
import org.apache.commons.math.analysis.UnivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.univariate
		.AbstractUnivariateRealOptimizer;

/**
 * Class <code>GoldenSectionOptimizer</code> optimize functions using "golden
 * section" method.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public class GoldenSectionOptimizer extends AbstractUnivariateRealOptimizer {

	/**
	 * Constant for Golden Section (sqrt(5) - 1) / 2.
	 */
	private static final double MAJOR = 0.61803398874989484820458683436564;

	/**
	 * Constant for Golden Section (3 - sqrt(5)) / 2.
	 */
	private static final double MINOR = 0.38196601125010515179541316563436;

	/**
	 * Default count of iterations.
	 */
	private static final int DEFAULT_ITERATIONS = 100;

	/**
	 * Default accuracy.
	 */
	private static final double DEFAULT_ACCURACY = 1E-10;

	/**
	 * Creates new instance of optimizer.
	 */
	public GoldenSectionOptimizer() {
		super(DEFAULT_ITERATIONS, DEFAULT_ACCURACY);
	}

	/**
	 * Creates new instance of optimizer.
	 *
	 * @param delta delta
	 */
	public GoldenSectionOptimizer(double delta) {
		super(DEFAULT_ITERATIONS, delta);
	}

	/**
	 * {@inheritDoc}
	 */
	public double optimize(final UnivariateRealFunction f,
			final GoalType goalType, final double min, final double max,
			final double startValue)
			throws MaxIterationsExceededException, FunctionEvaluationException {
		return optimize(f, goalType, min, max);
	}

	/**
	 * {@inheritDoc}
	 */
	public double optimize(final UnivariateRealFunction f,
			final GoalType goalType, final double min, final double max)
			throws MaxIterationsExceededException, FunctionEvaluationException {
		clearResult();
		Interval interval = new Interval(min, max);
		double left = min + MINOR * interval.getWidth();
		double right = min + MAJOR * interval.getWidth();
		double leftValue = computeObjectiveValue(f, left);
		double rightValue = computeObjectiveValue(f, right);
		if (goalType == GoalType.MAXIMIZE) {
			leftValue = -leftValue;
			rightValue = -rightValue;
		}
		int count = 0;
		while (count < maximalIterationCount) {
			// Check stopping criterion.
			if ((right - left) > absoluteAccuracy) {
				if (leftValue < rightValue) {
					interval.setMax(right);
					right = left;
					rightValue = leftValue;
					left = interval.getMin() + MINOR * interval.getWidth();
					leftValue = computeObjectiveValue(f, left);
				} else {
					interval.setMin(left);
					left = right;
					leftValue = rightValue;
					right = interval.getMin() + MAJOR * interval.getWidth();
					rightValue = computeObjectiveValue(f, right);
				}
			} else { // termination
				double x = (left + right) / 2;
				double fx = computeObjectiveValue(f, x);
				setResult(x, (goalType == GoalType.MAXIMIZE) ? -fx : fx, count);
				return x;
			}
			++count;
		}
		throw new MaxIterationsExceededException(maximalIterationCount);
	}
}
