package ru.sstu.images.filters.edges;

import java.awt.image.Kernel;

import ru.sstu.images.filters.AddFilter;
import ru.sstu.images.filters.ConvolveFilter;
import ru.sstu.images.filters.Filter;

/**
 * <code>ConvolveEdgeDetector</code> class is used for edge detection in images.
 * The main feature of this detector is using of convolution matrix.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public abstract class ConvolveEdgeDetector extends EdgeDetector {

	@Override
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

	@Override
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
