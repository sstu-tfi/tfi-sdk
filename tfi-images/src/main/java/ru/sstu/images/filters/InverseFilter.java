package ru.sstu.images.filters;

import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

/**
 * <code>InverseFilter</code> class inverts image pixels brightness using
 * {@link LookupOp}.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class InverseFilter extends RasterFilter {

	private static final int MAX_COLOR = 255;

	/**
	 * Initializes inverse filter.
	 */
	public InverseFilter() {
		super(new LookupOp(new ShortLookupTable(0, getData()), null));
	}

	private static short[] getData() {
		short[] data = new short[MAX_COLOR + 1];
		for (int i = 0; i < data.length; i++) {
			data[i] = (short) (MAX_COLOR - i);
		}
		return data;
	}
}
