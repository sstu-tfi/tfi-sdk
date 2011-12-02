package ru.sstu.images.filters;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * <code>ConvolveFilter</code> class is filter for raster objects using
 * {@link ConvolveOp}.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class ConvolveFilter extends RasterFilter {

	/**
	 * Initializes filter with given kernel.
	 *
	 * @param kernel kernel
	 */
	public ConvolveFilter(Kernel kernel) {
		super(new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null));
	}

	/**
	 * Initializes filter with given kernel.
	 *
	 * @param kernel    convolution matrix kernel
	 * @param cleanEdge true if image edge should be zero filled
	 */
	public ConvolveFilter(Kernel kernel, boolean cleanEdge) {
		super(new ConvolveOp(kernel,
				cleanEdge ? ConvolveOp.EDGE_ZERO_FILL : ConvolveOp.EDGE_NO_OP,
				null));
	}
}
