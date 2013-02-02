package ru.sstu.docs;


/**
 * <code>Node</code> interface represents single abstract document node.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public interface Node {

	/**
	 * Unknown node.
	 */
	int UNKNOWN = -1;

	/**
	 * Document node.
	 */
	int DOCUMENT = 1;

	/**
	 * Paragraph node.
	 */
	int PARAGRAPH = 3;

	/**
	 * Text node.
	 */
	int TEXT_NODE = 4;

	/**
	 * Image node.
	 */
	int IMAGE = 5;

	/**
	 * Table node.
	 */
	int TABLE = 6;

	/**
	 * Table row node.
	 */
	int TABLE_ROW = 7;

	/**
	 * Table cell node.
	 */
	int TABLE_CELL = 8;

	/**
	 * List node.
	 */
	int LIST = 9;

	/**
	 * List item node.
	 */
	int LIST_ITEM = 10;

	/**
	 * Provides Node's type.
	 *
	 * @return type
	 */
	int getType();

	/**
	 * Provides unformatted text content.
	 *
	 * @return text
	 */
	String getText();

	/**
	 * Post processing structure of node.
	 * Probably simplifies or removes unnecessary child nodes.
	 * Or adds images to the reader.
	 *
	 * @param reader document reader
	 * @throws DocumentException if some error occurs
	 */
	void postProcess(DocumentReader reader) throws DocumentException;
}
