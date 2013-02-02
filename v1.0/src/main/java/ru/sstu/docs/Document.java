package ru.sstu.docs;

import java.util.ArrayList;

/**
 * <code>Document</code> class represents abstract document.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class Document extends Container<Node> implements Block<Node> {

	/**
	 * Document initialization.
	 */
	public Document() {
		super(new ArrayList<Node>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#DOCUMENT}
	 */
	public int getType() {
		return DOCUMENT;
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
}
