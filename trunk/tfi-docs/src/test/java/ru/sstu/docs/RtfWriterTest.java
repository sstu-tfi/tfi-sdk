package ru.sstu.docs;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

/**
 * <code>RtfWriterTest</code> class is unit test for {@link RtfWriter}.
 *
 * @author Denis_Murashev
 * @since Docs 1.0
 */
public class RtfWriterTest extends TestCase {

	/**
	 * Tests {@link RtfWriter#write(Document, java.io.OutputStream)}.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testWrite() throws Exception {
		DocumentReader reader = new DocxReader();
		Document document = reader.read(getClass()
				.getResourceAsStream("/rtf.docx"));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		new RtfWriter().write(document, output);
		assertNotSame(0, output.toByteArray().length);
	}
}
