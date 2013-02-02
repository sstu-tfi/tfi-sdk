package ru.sstu.docs;

import java.util.ArrayList;

/**
 * <code>TableCell</code> class represents cell of a table.
 *
 * @author Denis A. Murashev
 * @author Alexander A. Shatunov
 * @since Docs 1.0
 */
public class TableCell extends Container<Atom> implements Block<Atom> {

	/**
	 * Column span.
	 */
	private int colspan = 1;

	/**
	 * Initializes table cell.
	 */
	public TableCell() {
		super(new ArrayList<Atom>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#TABLE_CELL}
	 */
	public int getType() {
		return TABLE_CELL;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getText() {
		StringBuilder buffer = new StringBuilder();
		for (Atom node : this) {
			buffer.append(node.getText());
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
	 * Provides column span for the cell.
	 *
	 * @return the colspan
	 */
	public int getColspan() {
		return colspan;
	}

	/**
	 * @param colspan the colspan to set
	 */
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("<td");
		if (colspan != 1) {
			buffer.append(" colspan=\"").append(colspan).append("\"");
		}
		buffer.append(">");
		for (Node node : this) {
			buffer.append(node);
		}
		buffer.append("</td>");
		return buffer.toString();
	}
}
