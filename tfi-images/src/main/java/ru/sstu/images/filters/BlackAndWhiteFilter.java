package ru.sstu.images.filters;

import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

import ru.sstu.images.analysis.Image;

/**
 * <code>BlackAndWhiteFilter</code> class changes all pixel colors to black or
 * white.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class BlackAndWhiteFilter extends RasterFilter {

	/**
	 * Initializes filter.
	 *
	 * @param threshhold black and white threshold
	 */
	public BlackAndWhiteFilter(float threshhold) {
		super(new LookupOp(new ShortLookupTable(0, getData(threshhold)), null));
	}

	private static short[] getData(float threashhold) {
		short[] data = new short[Image.MAX_COLOR + 1];
		int median = (int) (Image.MAX_COLOR * threashhold);
		for (int i = 0; i < median; i++) {
			data[i] = 0;
		}
		for (int i = median; i < data.length; i++) {
			data[i] = Image.MAX_COLOR;
		}
		return data;
	}
}
