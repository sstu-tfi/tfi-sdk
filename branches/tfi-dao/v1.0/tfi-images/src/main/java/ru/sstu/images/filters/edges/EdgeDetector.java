package ru.sstu.images.filters.edges;

import java.awt.image.Kernel;

import ru.sstu.images.analysis.Image;
import ru.sstu.images.filters.AddFilter;
import ru.sstu.images.filters.ConvolveFilter;
import ru.sstu.images.filters.Filter;

/**
 * <code>EdgeDetector</code> class is used for edge detection in images.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public abstract class EdgeDetector implements Filter {

	@Override
	public Image filter(Image image) {
		Filter addFilter = new AddFilter(vertical(), horizontal());
		return addFilter.filter(image);
	}

	/**
	 * Provides filter for vertical edge detection.
	 *
	 * @return {@link Filter}
	 */
	public Filter vertical() {
		Kernel vertical = KernelUtil.createKernel(getVerticalMatrix());
		if (isSymmetric()) {
			return new ConvolveFilter(vertical, true);
		}
		Filter left = new ConvolveFilter(vertical, true);
		float[] flippedMatrix = KernelUtil.flipVertical(getVerticalMatrix());
		Kernel flippedKernel = KernelUtil.createKernel(flippedMatrix);
		Filter right = new ConvolveFilter(flippedKernel, true);
		return new AddFilter(left, right);
	}

	/**
	 * Provides filter for horizontal edge detection.
	 *
	 * @return {@link Filter}
	 */
	public Filter horizontal() {
		float[] matrix = KernelUtil.rotateRight90(getVerticalMatrix());
		Kernel horizontal = KernelUtil.createKernel(matrix);
		if (isSymmetric()) {
			return new ConvolveFilter(horizontal, true);
		}
		Filter top = new ConvolveFilter(horizontal, true);
		float[] flippedMatrix = KernelUtil.flipHorizontal(matrix);
		Kernel flippedKernel = KernelUtil.createKernel(flippedMatrix);
		Filter bottom = new ConvolveFilter(flippedKernel, true);
		return new AddFilter(top, bottom);
	}

	/**
	 * Provides kernel matrix for vertical edge detection.
	 *
	 * @return matrix
	 */
	protected abstract float[] getVerticalMatrix();

	/**
	 * @return <code>true</code> if kernel matrix is symmetric
	 */
	protected abstract boolean isSymmetric();
}
