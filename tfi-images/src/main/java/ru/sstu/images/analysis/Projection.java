package ru.sstu.images.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <code>Projection</code> class represents image projection (horizontal or
 * vertical).
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public final class Projection {

	private float[] values;

	private Projection() {
	}

	/**
	 * Calculate horizontal projection of image.
	 *
	 * @param image image
	 * @return horizontal projection
	 */
	public static Projection horizontal(Image image) {
		Projection projection = new Projection();
		projection.values = new float[image.getWidth()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				projection.values[i] += image.getBrightness(i, j);
			}
		}
		return projection;
	}

	/**
	 * Calculate vertical projection of image.
	 *
	 * @param image image
	 * @return vertical projection
	 */
	public static Projection vertical(Image image) {
		Projection projection = new Projection();
		projection.values = new float[image.getHeight()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				projection.values[i] += image.getBrightness(j, i);
			}
		}
		return projection;
	}

	/**
	 * @return the values
	 */
	public float[] getValues() {
		return values;
	}

	/**
	 * Searches for peaks with given peak foot value.
	 *
	 * @param peakFoot     peak foot as part of peak top value
	 * @param minPeakWidth minimal width of peak in pixels
	 * @return list of found peaks
	 */
	public List<Peak> findPeaks(float peakFoot, int minPeakWidth) {
		List<Peak> peaks = new ArrayList<Peak>();
		SortedSet<Integer> available = new TreeSet<Integer>();
		for (int i = 0; i < values.length; i++) {
			available.add(i);
		}
		while (!available.isEmpty()) {
			float max = 0.0f;
			int index = -1;
			for (Integer i : available) {
				if (values[i] > max) {
					max = values[i];
					index = i;
				}
			}
			if (index == -1) {
				break;
			}
			Peak peak = createPeak(index, available, peakFoot);
			Set<Integer> subSet = new HashSet<Integer>(available
					.subSet(peak.getLeft(), peak.getRight() + 1));
			available.removeAll(subSet);
			if (peak.getWidth() >= minPeakWidth) {
				float area = 0;
				for (int i = peak.getLeft(); i <= peak.getRight(); i++) {
					area += values[i];
				}
				peak.setArea(area);
				peaks.add(peak);
			}
		}
		return peaks;
	}

	private Peak createPeak(int index, Set<Integer> available, float peakFoot) {
		final float threshold = peakFoot * values[index];
		int left = index;
		int right = index;
		while (left > 0 && values[left] > threshold
				&& available.contains(left)) {
			left--;
		}
		while (right < values.length - 1 && values[right] > threshold
				&& available.contains(right)) {
			right++;
		}
		left = Math.max(left, 0);
		right = Math.min(right, values.length - 1);
		return new Peak(left, index, right, values[index]);
	}
}
