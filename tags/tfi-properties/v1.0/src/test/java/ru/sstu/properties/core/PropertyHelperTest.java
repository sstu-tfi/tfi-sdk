package ru.sstu.properties.core;

import junit.framework.TestCase;

/**
 * <code>PropertyManagerTest</code> class is unit test for
 * {@link PropertyHelper} class.
 *
 * @author Denis_Murashev
 * @since Properties 1.0
 */
public class PropertyHelperTest extends TestCase {

	/**
	 * Tests {@link PropertyHelper#load(Object, java.io.InputStream)} method.
	 * @throws Exception if test fails
	 */
	public void testLoad() throws Exception {
		SimpleSettings settings = PropertyHelper.load(new SimpleSettings(),
				getClass().getResourceAsStream("/test.properties"));
		final String expectedString = "Sample string";
		assertEquals(expectedString, settings.getStringValue());
		final int expectedInt = 12;
		assertEquals(expectedInt, settings.getIntValue());
		final double expectedDouble = 1.2345;
		assertEquals(expectedDouble, settings.getDoubleValue());
	}
}
