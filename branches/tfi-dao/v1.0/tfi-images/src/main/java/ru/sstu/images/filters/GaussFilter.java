package ru.sstu.images.filters;

import java.awt.image.Kernel;

/**
 * <code>GaussFilter</code> class represents Gaussian filter.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class GaussFilter extends ConvolveFilter {

	/**
	 * Initializes Gauus filter for given size and sigma.
	 *
	 * @param size  size of matrix. The size should be odd
	 * @param sigma sigma in Gaussian distribution
	 */
	public GaussFilter(int size, float sigma) {
		super(getKernel(size, sigma));
	}

	private static Kernel getKernel(int size, float sigma) {
		if (size % 2 == 0 || size < 0) {
			throw new IllegalArgumentException();
		}
		float[] matrix = new float[size * size];
		float sum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = i - size / 2;
				int y = i - size / 2;
				int r2 = x * x + y * y;
				matrix[i + j * size] = (float) Math.exp(r2 / sigma);
				sum += matrix[i + j * size];
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] /= sum;
		}
		return new Kernel(size, size, matrix);
	}
}
