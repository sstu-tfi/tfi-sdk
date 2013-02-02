package ru.sstu.docs;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Paragraph</code> class represents paragraph.
 *
 * @author Denis A. Murashev
 * @author Alexander A. Shatunov
 * @since Docs 1.0
 */
public class Paragraph extends Container<Atom> implements Block<Atom> {

	/**
	 * Constructs empty paragraph.
	 */
	public Paragraph() {
		super(new ArrayList<Atom>());
	}

	/**
	 * Constructs simple paragraph using given text.
	 *
	 * @param text text
	 */
	public Paragraph(String text) {
		super(new ArrayList<Atom>());
		TextNode node = new TextNode();
		node.setText(text);
		this.add(node);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#PARAGRAPH}
	 */
	public int getType() {
		return PARAGRAPH;
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
	public synchronized void postProcess(DocumentReader reader)
			throws DocumentException {
		for (Node node : this) {
			node.postProcess(reader);
		}
		List<Atom> items = new ArrayList<Atom>(this);
		clear();
		TextNode current = null;
		for (Atom node : items) {
			if (node instanceof TextNode) {
				TextNode next = (TextNode) node;
				if (current != null) {
					if (next.hasEqualStyles(current)) {
						String text = current.getText() + next.getText();
						current.setText(text);
					} else {
						add(current);
						current = next;
					}
				} else {
					current = next;
				}
			} else {
				if (current != null) {
					add(current);
					current = null;
				}
				add(node);
			}
		}
		if (current != null) {
			add(current);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("<p>");
		for (Node node : this) {
			buffer.append(node);
		}
		buffer.append("</p>");
		return buffer.toString();
	}
}
