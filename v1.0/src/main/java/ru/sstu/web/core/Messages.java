package ru.sstu.web.core;

/**
 * <code>Messages</code> class provides access to message properties.
 *
 * @author Alexander_Ignatov
 * @author Denis_Murashev
 * @since Web 1.0
 */
public abstract class Messages {

	/**
	 * Bundles names.
	 */
	private String[] bundles = new String[0];

	/**
	 * Get the resource string with the given ID from the default bundle.
	 *
	 * @param resourceId resource ID
	 * @return message summary
	 */
	public String getString(String resourceId) {
		return getString(resourceId, (Object[]) null);
	}

	/**
	 * Get the resource string with the given ID and parameters from the
	 * default bundle.
	 *
	 * @param resourceId resource ID
	 * @param params parameters
	 * @return message summary
	 */
	public String getString(String resourceId, Object... params) {
		String string = null;
		for (String bundle : bundles) {
			string = getString(bundle, resourceId, params);
			if (string != null) {
				return string;
			}
		}
		return string;
	}

	/**
	 * Get the resource string with the given ID and parameters from the bundle.
	 *
	 * @param bundle resource bundle
	 * @param resourceId resource ID
	 * @param params parameters
	 * @return message summary
	 */
	public abstract String getString(String bundle, String resourceId,
			Object... params);

	/**
	 * @return the bundles
	 */
	protected String[] getBundles() {
		return bundles;
	}

	/**
	 * @param bundles the bundles to set
	 */
	protected void setBundles(String[] bundles) {
		this.bundles = bundles;
	}
}
