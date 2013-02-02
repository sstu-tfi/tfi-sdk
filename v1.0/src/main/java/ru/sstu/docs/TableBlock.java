package ru.sstu.docs;

import java.util.ArrayList;

/**
 * <code>TableBlock</code> class represents table.
 *
 * @author Denis A. Murashev
 * @author Alexander A. Shatunov
 * @since Docs 1.0
 */
public class TableBlock extends Container<TableRow> implements Block<TableRow> {

	/**
	 * Initializes table block.
	 */
	public TableBlock() {
		super(new ArrayList<TableRow>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#TABLE}
	 */
	public int getType() {
		return TABLE;
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
		StringBuilder buffer = new StringBuilder("<table>");
		for (Node node : this) {
			buffer.append(node);
		}
		buffer.append("</table>");
		return buffer.toString();
	}
}
