package ru.sstu.docs;

import java.util.ArrayList;

/**
 * <code>ListItem</code> class represents list item text block.
 *
 * @author Alexander A. Shatunov
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class ListItem extends Container<Atom> implements Block<Atom> {

	/**
	 * Initializes list item.
	 */
	public ListItem() {
		super(new ArrayList<Atom>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#LIST}
	 */
	public int getType() {
		return LIST_ITEM;
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("<li>");
		for (Node node : this) {
			buffer.append(node);
		}
		buffer.append("</li>");
		return buffer.toString();
	}
}
