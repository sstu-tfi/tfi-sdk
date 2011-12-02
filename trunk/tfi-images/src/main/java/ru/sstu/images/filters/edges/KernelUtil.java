package ru.sstu.images.filters.edges;

import java.awt.image.Kernel;

class KernelUtil {

	private static final int SIZE = 3;

	private static final int[] FLIP_VERTICAL = {2, 1, 0, 5, 4, 3, 8, 7, 6};

	private static final int[] FLIP_HORIZONTAL = {6, 7, 8, 3, 4, 5, 0, 1, 2};

	private static final int[] ROTATE_LEFT_90 = {2, 5, 8, 1, 4, 7, 0, 3, 6};

	private static final int[] ROTATE_RIGHT_90 = {6, 3, 0, 7, 4, 1, 8, 5, 2};

	private KernelUtil() {
	}

	/**
	 * Creates kernel for given matrix.
	 *
	 * @param matrix matrix
	 * @return kernel
	 */
	public static Kernel createKernel(float[] matrix) {
		if (matrix.length != SIZE * SIZE) {
			throw new IllegalArgumentException("Only 3x3 kernels supported");
		}
		return new Kernel(SIZE, SIZE, matrix);
	}

	/**
	 * Flips matrix vertically.
	 *
	 * @param matrix matrix
	 * @return new kernel
	 */
	public static float[] flipVertical(float[] matrix) {
		return process(matrix, FLIP_VERTICAL);
	}

	/**
	 * Flips matrix horizontally.
	 *
	 * @param matrix matrix
	 * @return new kernel
	 */
	public static float[] flipHorizontal(float[] matrix) {
		return process(matrix, FLIP_HORIZONTAL);
	}

	/**
	 * Rotates matrix left (90 degrees).
	 *
	 * @param matrix matrix
	 * @return new kernel
	 */
	public static float[] rotateLeft90(float[] matrix) {
		return process(matrix, ROTATE_LEFT_90);
	}

	/**
	 * Rotates matrix right (90 degrees).
	 *
	 * @param matrix matrix
	 * @return new kernel
	 */
	public static float[] rotateRight90(float[] matrix) {
		return process(matrix, ROTATE_RIGHT_90);
	}

	private static float[] process(float[] origin, int[] indeces) {
		if (origin.length != SIZE * SIZE) {
			throw new IllegalArgumentException("Only 3x3 kernels supported");
		}
		float[] data = new float[origin.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = origin[indeces[i]];
		}
		return data;
	}
}
