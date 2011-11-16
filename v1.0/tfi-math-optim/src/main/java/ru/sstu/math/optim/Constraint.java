package ru.sstu.math.optim;

/**
 * <code>Constraint</code> interface represents constraint for optimization.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public interface Constraint {

	/**
	 * Checks if given point matches constraint.
	 *
	 * @param point point to check
	 * @return true if point does not satisfies the constraint
	 */
	boolean checkPoint(double[] point);
}
