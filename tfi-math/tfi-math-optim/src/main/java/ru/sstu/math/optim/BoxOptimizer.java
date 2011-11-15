package ru.sstu.math.optim;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MathException;
import org.apache.commons.math.MaxEvaluationsExceededException;
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.MultivariateRealOptimizer;
import org.apache.commons.math.optimization.RealConvergenceChecker;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.random.RandomVectorGenerator;

import ru.sstu.math.uniform.UniformGenerator;

/**
 * Class <code>BoxOptimizer</code> is the optimizer engine for Box method.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public final class BoxOptimizer implements MultivariateRealOptimizer {

	/**
	 * Box reflection factor.
	 */
	private static final double ALPHA = 1.3;

	/**
	 * Default accuracy.
	 */
	private static final double DEFAULT_ACCURACY = 1.0e-7;

	/**
	 * MultivariateRealFunction to be optimized.
	 */
	private MultivariateRealFunction function;

	/**
	 * Explicit constraint.
	 */
	private Interval[] intervals;

	/**
	 * Implicit constraints.
	 */
	private Constraint[] constraints = new Constraint[0];

	/**
	 * Generator of complex points.
	 */
	private RandomVectorGenerator generator;

	/**
	 * Current point.
	 */
	private RealPointValuePair current;

	/**
	 * Center point.
	 */
	private RealPointValuePair center;

	/**
	 * The complex itself.
	 */
	private RealPointValuePair[] points;

	/**
	 * Accuracy.
	 */
	private double accuracy = DEFAULT_ACCURACY;

	/**
	 * Maximal number of iterations allowed.
	 */
	private int maxIterations;

	/**
	 * Maximal number of evaluations allowed.
	 */
	private int maxEvaluations;

	/**
	 * Number of evaluations already performed.
	 */
	private int evaluations;

	/**
	 * Convergence checker.
	 */
	private RealConvergenceChecker checker;

	/**
	 * Complex points comparator.
	 */
	private Comparator<RealPointValuePair> comparator;

	/**
	 * Creates new BoxOptimizer instance.
	 *
	 * @param intervals   explicit constraint
	 * @param constraints implicit constraints
	 * @param generator   specific generator
	 */
	private BoxOptimizer(Interval[] intervals, Constraint[] constraints,
			RandomVectorGenerator generator) {
		this.intervals = intervals;
		if (constraints != null) {
			this.constraints = constraints;
		}
		this.generator = generator;
	}

	/**
	 * Creates new BoxOptimizer instance.
	 *
	 * @param intervals  explicit constraint
	 * @return optimizer instance
	 */
	public static BoxOptimizer getBoxOptimizer(Interval[] intervals) {
		return new BoxOptimizer(intervals, null,
				UniformGenerator.getLPTauGenerator(intervals.length));
	}

	/**
	 * Creates new BoxOptimizer instance.
	 *
	 * @param intervals   explicit constraint
	 * @param constraints implicit constraints
	 * @return optimizer instance
	 */
	public static BoxOptimizer getBoxOptimizer(Interval[] intervals,
			Constraint[] constraints) {
		return new BoxOptimizer(intervals, constraints,
				UniformGenerator.getLPTauGenerator(intervals.length));
	}

	/**
	 * Creates new BoxOptimizer instance.
	 *
	 * @param intervals  explicit constraint
	 * @param generator  specific generator
	 * @return optimizer instance
	 */
	public static BoxOptimizer getBoxOptimizer(Interval[] intervals,
			RandomVectorGenerator generator) {
		return new BoxOptimizer(intervals, null, generator);
	}

	/**
	 * Creates new BoxOptimizer instance.
	 *
	 * @param intervals   explicit constraint
	 * @param constraints implicit constraints
	 * @param generator   specific generator
	 * @return optimizer instance
	 */
	public static BoxOptimizer getBoxOptimizer(Interval[] intervals,
			Constraint[] constraints, RandomVectorGenerator generator) {
		return new BoxOptimizer(intervals, constraints, generator);
	}

	/**
	 * {@inheritDoc}
	 */
	public RealPointValuePair optimize(final MultivariateRealFunction f,
			final GoalType goalType, final double[] startPoint)
			throws FunctionEvaluationException {
		if (intervals.length != startPoint.length) {
			throw new IllegalArgumentException();
		}
		function = f;
		setMaxEvaluations(Integer.MAX_VALUE);
		setMaxIterations(Integer.MAX_VALUE);
		if (checker == null) {
			setConvergenceChecker(new RealConvergenceChecker() {

				public boolean converged(int iteration,
						RealPointValuePair previous,
						RealPointValuePair current) {
					return accuracy > getSigma() && accuracy > getDiameter();
				}
			});
		}
		points = new RealPointValuePair[2 * startPoint.length];
		current = new RealPointValuePair(startPoint, evaluate(startPoint));
		comparator = new Comparator<RealPointValuePair>() {
				public int compare(final RealPointValuePair o1,
						final RealPointValuePair o2) {
					final double v1 = o1.getValue();
					final double v2 = o2.getValue();
					return (goalType == GoalType.MINIMIZE)
							? Double.compare(v1, v2) : Double.compare(v2, v1);
				}
		};
		prepareComplex();
		while (!checker.converged(0, current, current)) {
			try {
				if (points[points.length - 1].getValue() < current.getValue()) {
					compressComplex();
				} else {
					expandComplex();
				}
			} catch (MathException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return points[0];
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMaxIterations() {
		return maxIterations;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMaxEvaluations() {
		return maxEvaluations;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMaxEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getIterations() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getEvaluations() {
		return evaluations;
	}

	/**
	 * {@inheritDoc}
	 */
	public RealConvergenceChecker getConvergenceChecker() {
		return checker;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setConvergenceChecker(RealConvergenceChecker checker) {
		this.checker = checker;
	}

	/**
	 * Evaluates function.
	 *
	 * @param args arguments
	 * @return value
	 * @throws FunctionEvaluationException if cannot evaluate
	 */
	double evaluate(double... args) throws FunctionEvaluationException {
		if (args == null) {
			throw new IllegalArgumentException();
		}
		++evaluations;
		if (evaluations > maxEvaluations) {
			throw new FunctionEvaluationException(
					new MaxEvaluationsExceededException(maxEvaluations), args);
		}
		return function.value(args);
	}

	/**
	 * Checks for initial point.
	 *
	 * @return <code>true</code> if check was successful
	 */
	private boolean checkInitials() {
		double[] point = current.getPoint();
		if (checkPoint(point)) {
			throw new IllegalArgumentException();
		}
		for (Constraint constraint : constraints) {
			if (constraint.checkPoint(current.getPoint())) {
				throw new IllegalArgumentException();
			}
		}
		return true;
	}

	/**
	 * Prepares complex points.
	 */
	private void prepareComplex() {
		try {
			checkInitials();
			for (int i = 0; i < points.length; i++) {
				points[i] = new RealPointValuePair(current.getPoint(),
						evaluate(current.getPointRef()));
				current = generateRandomPoint();
			}
			Arrays.sort(points, comparator);
			calculateCenterPoint();
		} catch (MathException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @return center of complex
	 * @throws MathException if some error occurs
	 */
	private RealPointValuePair calculateCenterPoint() throws MathException {
		int dimension = points.length / 2;
		double[] point = new double[dimension];
		for (int i = 0; i < points.length - 1; i++) {
			for (int j = 0; j < dimension; j++) {
				point[j] += points[i].getPoint()[j];
			}
		}
		final double denom = points.length - 1;
		for (int i = 0; i < dimension; i++) {
			point[i] /= denom;
		}
		center = new RealPointValuePair(point, evaluate(point));
		return center;
	}

	/**
	 * @return new generated random point
	 * @throws MathException if some error occurs
	 */
	private RealPointValuePair generateRandomPoint() throws MathException {
		boolean failure;
		double[] point;
		do {
			point = generator.nextVector();
			for (int i = 0; i < point.length; i++) {
				Interval interval = intervals[i];
				point[i] = interval.getMin() + point[i] * interval.getWidth();
			}
			failure = false;
			// Checking of implicit constraints
			for (Constraint constraint : constraints) {
				if (constraint.checkPoint(point)) {
					failure = true;
				}
			}
		} while (failure);
		double value = evaluate(point);
		current = new RealPointValuePair(point, value);
		return current;
	}

	/**
	 * Compressing of complex by moving the point to the center.
	 *
	 * @throws MathException if some error occurs
	 */
	private void compressComplex() throws MathException {
		current = moveToCenter(current, center);
	}

	/**
	 * Expanding the complex by reflecting the point.
	 *
	 * @throws MathException if some error occurs
	 */
	private void expandComplex() throws MathException {
		points[points.length - 1] = current;
		Arrays.sort(points, comparator);
		calculateCenterPoint();
		current = reflect(points[points.length - 1], center);
		checkConstraints();
	}

	/**
	 * Checking if point satisfying the constraints.
	 *
	 * @return <code>true</code> if check was successful
	 * @throws MathException if some error occurs
	 */
	private boolean checkConstraints() throws MathException {
		double[] point = current.getPoint();
		if (checkPoint(point)) {
			point = getSatisfiedPoint(point);
			current = new RealPointValuePair(point, evaluate(point));
		}
		for (Constraint constraint : constraints) {
			while (constraint.checkPoint(current.getPoint())) {
				current = moveToCenter(current, center);
			}
		}
		return true;
	}

	/**
	 * @param point point to check
	 * @return true if point does not satisfies the constraint
	 */
	private boolean checkPoint(double[] point) {
		if (intervals.length != point.length) {
			return true;
		}
		for (int i = 0; i < intervals.length; i++) {
			double x = point[i];
			Interval interval = intervals[i];
			if (x < interval.getMin() && x > intervals[i].getMax()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param point given point
	 * @return point that satisfies constraint conditions
	 */
	private double[] getSatisfiedPoint(double[] point) {
		if (intervals.length != point.length) {
			return null;
		}
		for (int i = 0; i < intervals.length; i++) {
			double x = point[i];
			Interval interval = intervals[i];
			if (x < interval.getMin()) {
				point[i] = x;
			} else if (x > interval.getMax()) {
				point[i] = x;
			}
		}
		return point;
	}

	/**
	 * Moving the point to the center.
	 *
	 * @param point point to be moved
	 * @param pivot center the point is being moved to
	 * @return new position of point
	 * @throws MathException if some error occurs
	 */
	private RealPointValuePair moveToCenter(RealPointValuePair point,
			RealPointValuePair pivot) throws MathException {
		double[] result = new double[point.getPoint().length];
		for (int i = 0; i < point.getPoint().length; i++) {
			result[i] = (point.getPoint()[i] + pivot.getPoint()[i]) / 2.0;
		}
		double value = evaluate(result);
		return new RealPointValuePair(result, value);
	}

	/**
	 * Reflecting the point using given point as a center.
	 *
	 * @param point point to be reflected
	 * @param pivot the center of reflection
	 * @return new position of point
	 * @throws MathException if some error occurs
	 */
	private RealPointValuePair reflect(RealPointValuePair point,
			RealPointValuePair pivot) throws MathException {
		double[] result = new double[point.getPoint().length];
		for (int i = 0; i < point.getPoint().length; i++) {
			result[i] = (1 + ALPHA) * pivot.getPoint()[i]
					- ALPHA * point.getPoint()[i];
		}
		double value = evaluate(result);
		return new RealPointValuePair(result, value);
	}

	/**
	 * Calculating of distance to given point.
	 *
	 * @param start start point
	 * @param end   end point
	 * @return distance
	 */
	private double distance(RealPointValuePair start, RealPointValuePair end) {
		double value = 0;
		double[] a = start.getPoint();
		double[] b = end.getPoint();
		for (int i = 0; i < a.length; i++) {
			double data = a[i] - b[i];
			value += data * data;
		}
		return Math.sqrt(value);
	}

	/**
	 * @return current sigma
	 */
	private double getSigma() {
		double mean = 0.0;
		for (RealPointValuePair complexPoint : points) {
			mean += complexPoint.getValue();
		}
		mean /= points.length;
		double sigma = 0.0;
		for (RealPointValuePair point : points) {
			double value = point.getValue() - mean;
			sigma += value * value;
		}
		sigma /= points.length;
		return Math.sqrt(sigma);
	}

	/**
	 * @return diameter of complex
	 */
	private double getDiameter() {
		double diameter = 0.0;
		for (int i = 0; i < points.length - 1; i++) {
			RealPointValuePair point = points[i];
			for (int j = i + 1; j < points.length; j++) {
				double value = distance(point, points[j]);
				if (value > diameter) {
					diameter = value;
				}
			}
		}
		return diameter;
	}
}
