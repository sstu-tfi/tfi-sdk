package ru.sstu.web.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@code MessagesTest} class is unit tests for {@link Messages} class.
 *
 * @author denis_murashev
 * @since Web 1.1
 */
public class MessagesTest {

	private static final String[] BUNDLES = new String[]{"test", "test1"};

	private static final Messages MESSAGES = new Messages() {
		@Override
		public String getString(String bundle, String resourceId,
				Object... params) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle);
			try {
				return resourceBundle.getString(resourceId);
			} catch (MissingResourceException e) {
				return null;
			}
		}
	};

	static {
		MESSAGES.setBundles(BUNDLES);
	}

	/**
	 * Tests {@link Messages#getString(String)} method.
	 */
	@Test
	public void testGetString() {
		String expected = "value";
		String actual = MESSAGES.getString("key");
		Assert.assertEquals(expected, actual);

		expected = "value";
		actual = MESSAGES.getString("key", (Object[]) null);
		Assert.assertEquals(expected, actual);

		expected = "value";
		actual = MESSAGES.getString("test", "key", (Object[]) null);
		Assert.assertEquals(expected, actual);

		Assert.assertNull(MESSAGES.getString(""));

		expected = "value1";
		actual = MESSAGES.getString("key1");
		Assert.assertEquals(expected, actual);

		Assert.assertEquals(BUNDLES.length, MESSAGES.getBundles().length);
	}
}
