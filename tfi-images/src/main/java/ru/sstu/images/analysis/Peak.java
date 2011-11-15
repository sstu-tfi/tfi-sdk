package ru.sstu.images.analysis;

import java.util.Comparator;

/**
 * <code>Peak</code> class represents peak in some value distribution.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class Peak {

	/**
	 * Peak comparator by value.
	 */
	public static final Comparator<Peak> VALUE_COMPARATOR
			= new Comparator<Peak>() {
				@Override
				public int compare(Peak o1, Peak o2) {
					return Float.compare(o1.value, o2.value);
				}
	};

	/**
	 * Peak comparator by appearance.
	 */
	public static final Comparator<Peak> APPEARANCE_COMPARATOR
			= new Comparator<Peak>() {
				@Override
				public int compare(Peak o1, Peak o2) {
					return o1.left - o2.left;
				}
	};

	/**
	 * Peak comparator by width.
	 */
	public static final Comparator<Peak> WIDTH_COMPARATOR
			= new Comparator<Peak>() {
				@Override
				public int compare(Peak o1, Peak o2) {
					return o2.getWidth() - o1.getWidth();
				}
	};

	private int left;
	private int center;
	private int right;
	private float value;
	private float area;

	/**
	 * Initializes peak.
	 *
	 * @param left   left border
	 * @param center point with maximum value
	 * @param right  right border
	 * @param value  maximum value
	 */
	public Peak(int left, int center, int right, float value) {
		this.left = left;
		this.center = center;
		this.right = right;
		this.value = value;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @return the center
	 */
	public int getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(int center) {
		this.center = center;
	}

	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * Provides peak width.
	 *
	 * @return peak width
	 */
	public int getWidth() {
		return right - left;
	}

	/**
	 * @return the area
	 */
	public float getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(float area) {
		this.area = area;
	}

	/**
	 * Checks if given point belongs to peak.
	 *
	 * @param x point to be checked
	 * @return <code>true</code> if given point belongs to peak
	 */
	public boolean contains(int x) {
		return left <= x && x <= right;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d) Max value %f at %d", left, right,
				value, center);
	}
}
