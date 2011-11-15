package ru.sstu.images.filters;

import java.awt.image.Kernel;

/**
 * <code>SharpenFilter</code> class represents sharpen filter.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class SharpenFilter extends ConvolveFilter {

	private static final int SIZE = 3;

	private static final float[] MATRIX = new float[] {
			 0.0f, -1.0f,  0.0f,
			-1.0f, +5.0f, -1.0f,
			 0.0f, -1.0f,  0.0f,
	};

	/**
	 * Initializes new instance of blur filter.
	 */
	public SharpenFilter() {
		super(new Kernel(SIZE, SIZE, MATRIX));
	}
}
