package ru.sstu.docs;

import java.util.ArrayList;

/**
 * <code>ListBlock</code> class represents list block.
 *
 * @author Alexander A. Shatunov
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class ListBlock extends Container<ListItem> implements Block<ListItem> {

	/**
	 * List block initialization.
	 */
	public ListBlock() {
		super(new ArrayList<ListItem>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#LIST}
	 */
	public int getType() {
		return LIST;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getText() {
		StringBuilder buffer = new StringBuilder();
		for (ListItem block : this) {
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
		StringBuilder buffer = new StringBuilder("<ul>");
		for (Node node : this) {
			buffer.append(node);
		}
		buffer.append("</ul>");
		return buffer.toString();
	}
}
