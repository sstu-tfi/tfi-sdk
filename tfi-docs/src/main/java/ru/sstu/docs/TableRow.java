package ru.sstu.docs;

import java.util.ArrayList;

/**
 * <code>TableRow</code> class represents row of cells.
 *
 * @author Denis A. Murashev
 * @author Alexander A. Shatunov
 * @since Docs 1.0
 */
public class TableRow extends Container<TableCell> implements Block<TableCell> {

	/**
	 * Initializes table row.
	 */
	public TableRow() {
		super(new ArrayList<TableCell>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#TABLE_ROW}
	 */
	public int getType() {
		return TABLE_ROW;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getText() {
		StringBuilder buffer = new StringBuilder();
		for (Node block : this) {
			buffer.append(block.getText());
		}
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public void postProcess(DocumentReader reader) throws DocumentException {
		for (Node node : this) {
			node.postProcess(reader);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("<tr>");
		for (Node node : this) {
			buffer.append(node);
		}
		buffer.append("</tr>");
		return buffer.toString();
	}
}
