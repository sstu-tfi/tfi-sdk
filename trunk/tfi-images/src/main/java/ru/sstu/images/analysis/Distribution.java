package ru.sstu.images.analysis;

/**
 * <code>Distribution</code> class provides image brightness distribution
 * properties.
 *
 * @author denis_murashev
 * @since Images 1.0
 */
public class Distribution {

	private float average;

	private float dispersion;

	/**
	 * Calculates distribution properties for given {@link Image}.
	 *
	 * @param image image
	 */
	public Distribution(Image image) {
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				float value = image.getBrightness(i, j);
				average += value;
				dispersion += value * value;
			}
		}
		int count = image.getWidth() * image.getHeight();
		average /= count;
		dispersion /= count;
		dispersion = dispersion - average * average;
	}

	/**
	 * @return the average
	 */
	public float getAverage() {
		return average;
	}

	/**
	 * @return the dispersion
	 */
	public float getDispersion() {
		return dispersion;
	}
}
