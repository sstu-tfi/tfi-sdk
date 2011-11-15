package ru.sstu.docs;

import java.io.OutputStream;

/**
 * <code>RtfWriter</code> class writes {@link Document} to RTF file.
 *
 * @author Denis A. Murashev
 * @since Docs 2.0
 */
public class RtfWriter extends TextWriter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void prepareWriter(com.lowagie.text.Document document,
			OutputStream output) throws DocumentException {
		com.lowagie.text.rtf.RtfWriter2.getInstance(document, output);
	}
}
