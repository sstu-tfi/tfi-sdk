package ru.sstu.docs;

import java.io.OutputStream;

/**
 * <code>PdfWriter</code> class writes {@link Document} to PDF file.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class PdfWriter extends TextWriter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void prepareWriter(com.lowagie.text.Document document,
			OutputStream output) throws DocumentException {
		try {
			com.lowagie.text.pdf.PdfWriter.getInstance(document, output);
		} catch (com.lowagie.text.DocumentException e) {
			throw new DocumentException(e);
		}
	}
}
