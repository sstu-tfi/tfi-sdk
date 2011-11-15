package ru.sstu.docs;

import junit.framework.TestCase;

/**
 * <code>DocxReaderTest</code> class is unit test for {@link DocxReader}.
 *
 * @author Denis_Murashev
 * @since Docs 2.0.1
 */
public class DocxReaderTest extends TestCase {

	/**
	 * Expected strings.
	 */
	private static final String[] STRINGS = {
		"First paragraph.",
		"Second paragraph.",
		"Formula: .",
	};

	/**
	 * Tests {@link DocxReader#read(java.io.InputStream)}.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testRead() throws Exception {
		DocumentReader reader = new DocxReader();
		Document document = reader.read(getClass()
				.getResourceAsStream("/test.docx"));
		for (int i = 0; i < STRINGS.length; i++) {
			assertEquals(STRINGS[i], document.get(i).getText());
		}
		assertEquals(1, reader.getImages().size());
	}
}
