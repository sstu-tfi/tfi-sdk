package ru.sstu.images.filters;

import java.awt.image.Kernel;

/**
 * <code>BlurFilter</code> class represents blur filter.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class BlurFilter extends ConvolveFilter {

	private static final int SIZE = 3;

	private static final float[] MATRIX = new float[] {
			0.111f, 0.111f, 0.111f,
			0.111f, 0.112f, 0.111f,
			0.111f, 0.111f, 0.111f,
	};

	/**
	 * Initializes new instance of blur filter.
	 */
	public BlurFilter() {
		super(new Kernel(SIZE, SIZE, MATRIX));
	}
}
