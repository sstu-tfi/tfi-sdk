package ru.sstu.docs;

import java.io.OutputStream;

/**
 * <code>DocumentWriter</code> interface used for document writing.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public interface DocumentWriter {

	/**
	 * Writs given document to given specified output stream.
	 *
	 * @param document document
	 * @param output   output stream
	 * @throws DocumentException if some error occurs
	 */
	void write(Document document, OutputStream output) throws DocumentException;
}
