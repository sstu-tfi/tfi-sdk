package ru.sstu.docs;

import junit.framework.TestCase;

/**
 * <code>DocXmlReaderTest</code> class is unit test for {@link DocXmlReader}.
 *
 * @author Denis_Murashev
 * @since Docs 2.0.1
 */
public class DocXmlReaderTest extends TestCase {

	/**
	 * Expected strings.
	 */
	private static final String[] STRINGS = {
		"First paragraph.",
		"Second paragraph.",
	};

	/**
	 * Tests {@link DocXmlReader#read(java.io.InputStream)}.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testRead() throws Exception {
		DocumentReader reader = new DocXmlReader();
		Document document = reader.read(getClass()
				.getResourceAsStream("/test.docxml"));
		for (int i = 0; i < STRINGS.length; i++) {
			assertEquals(STRINGS[i], document.get(i).getText());
		}
		assertEquals(1, reader.getImages().size());
	}
}
