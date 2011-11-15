package ru.sstu.math.optim;

/**
 * Class <code>Interval</code> represents 1-dimensional interval.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public class Interval implements Constraint {

	/**
	 * Minimal value.
	 */
	private double min;

	/**
	 * Maximum value.
	 */
	private double max;

	/**
	 * Creating interval with given values.
	 *
	 * @param min minimal value
	 * @param max maximum value
	 */
	public Interval(double min, double max) {
		this.min = min;
		this.max = max;
		checkData();
	}

	/**
	 * @return minimal value
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Sets minimal value.
	 *
	 * @param min minimal value
	 */
	public void setMin(double min) {
		this.min = min;
		checkData();
	}

	/**
	 * @return maximum value
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Sets maximum value.
	 *
	 * @param max maximum value
	 */
	public void setMax(double max) {
		this.max = max;
		checkData();
	}

	/**
	 * @return the width of interval
	 */
	public double getWidth() {
		return max - min;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean checkPoint(double[] point) {
		if (point.length != 1) {
			throw new IllegalArgumentException();
		}
		return point[0] < min || point[0] > max;
	}

	/**
	 * Checks data.
	 *
	 * @return <code>true</code> if check was successful
	 */
	private boolean checkData() {
		if (max < min) {
			throw new IllegalArgumentException();
		}
		return true;
	}
}
