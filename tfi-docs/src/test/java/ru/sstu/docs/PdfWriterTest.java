package ru.sstu.docs;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

/**
 * <code>PdfWriterTest</code> class is unit test for {@link PdfWriter}.
 *
 * @author Denis_Murashev
 * @since Docs 2.0.1
 */
public class PdfWriterTest extends TestCase {

	/**
	 * Tests {@link PdfWriter#write(Document, java.io.OutputStream)}.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testWrite() throws Exception {
		DocumentReader reader = new DocxReader();
		Document document = reader.read(getClass()
				.getResourceAsStream("/pdf.docx"));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		new PdfWriter().write(document, output);
		assertNotSame(0, output.toByteArray().length);
	}
}
