package ru.sstu.docs;

/**
 * <code>TextNode</code> class represents single text node.
 *
 * @author Denis A. Murashev
 * @author Alexander A. Shatunov
 * @since Docs 1.0
 */
public class TextNode implements Atom {

	/**
	 * Normal style.
	 */
	public static final int STYLE_NORMAL = 0;

	/**
	 * Bold style.
	 */
	public static final int STYLE_BOLD = 1;

	/**
	 * Italic style.
	 */
	public static final int STYLE_ITALIC = 2;

	/**
	 * Underlined style.
	 */
	public static final int STYLE_UNDERLINED = 4;

	/**
	 * Superscript style.
	 */
	public static final int SUPERSCRIPT = 16;

	/**
	 * Subscript style.
	 */
	public static final int SUBSCRIPT = 32;

	/**
	 * Node text.
	 */
	private String text = "";

	/**
	 * Node style.
	 */
	private int style = STYLE_NORMAL;

	/**
	 * Node font.
	 */
	private String font = "";

	/**
	 * Constructs empty node.
	 */
	public TextNode() {
	}

	/**
	 * Constructs new text node.
	 *
	 * @param text node text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Constructs new text node.
	 *
	 * @param text  node text
	 * @param style node style
	 */
	public TextNode(String text, int style) {
		this.text = text;
		this.style = style;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#TEXT_NODE}
	 */
	public int getType() {
		return TEXT_NODE;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getText() {
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	public void postProcess(DocumentReader reader) throws DocumentException {
		if (text != null && text.trim().length() == 0) {
			style = STYLE_NORMAL;
			font = "";
		}
	}

	/**
	 * Sets node text.
	 *
	 * @param text text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return true if text is bold
	 */
	public boolean isBold() {
		return (style & STYLE_BOLD) != 0;
	}

	/**
	 * Sets text style bold.
	 *
	 * @param bold bold
	 */
	public void setBold(boolean bold) {
		if (bold) {
			style |= STYLE_BOLD;
		} else {
			style &= ~STYLE_BOLD;
		}
	}

	/**
	 * @return true if text is italic
	 */
	public boolean isItalic() {
		return (style & STYLE_ITALIC) != 0;
	}

	/**
	 * Sets text style italic.
	 *
	 * @param italic italic
	 */
	public void setItalic(boolean italic) {
		if (italic) {
			style |= STYLE_ITALIC;
		} else {
			style &= ~STYLE_ITALIC;
		}
	}

	/**
	 * @return true if text is underlined
	 */
	public boolean isUnderlined() {
		return (style & STYLE_UNDERLINED) != 0;
	}

	/**
	 * Sets text style underlined.
	 *
	 * @param underlined underlined
	 */
	public void setUnderlined(boolean underlined) {
		if (underlined) {
			style |= STYLE_UNDERLINED;
		} else {
			style &= ~STYLE_UNDERLINED;
		}
	}

	/**
	 * @return true if text is superscript
	 */
	public boolean isSuperscript() {
		return (style & SUPERSCRIPT) != 0;
	}

	/**
	 * Sets superscript.
	 *
	 * @param superscript is superscript
	 */
	public void setSuperscript(boolean superscript) {
		style &= ~SUPERSCRIPT;
		if (superscript) {
			style &= ~SUBSCRIPT;
			style |= SUPERSCRIPT;
		}
	}

	/**
	 * @return true if text is subscript
	 */
	public boolean isSubscript() {
		return (style & SUBSCRIPT) != 0;
	}

	/**
	 * Sets subscript.
	 *
	 * @param subscript is subscript
	 */
	public void setSubscript(boolean subscript) {
		style &= ~SUBSCRIPT;
		if (subscript) {
			style &= ~SUPERSCRIPT;
			style |= SUBSCRIPT;
		}
	}

	/**
	 * Provides font.
	 *
	 * @return font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * Sets font.
	 *
	 * @param font font
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * Tests styles of two text nodes for equality.
	 *
	 * @param node text node
	 * @return true if styles are equal
	 */
	public boolean hasEqualStyles(TextNode node) {
		return style == node.style && font.equalsIgnoreCase(node.font);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(text);
		if (isBold()) {
			buffer.insert(0, "<b>");
			buffer.append("</b>");
		}
		if (isItalic()) {
			buffer.insert(0, "<i>");
			buffer.append("</i>");
		}
		if (isUnderlined()) {
			buffer.insert(0, "<u>");
			buffer.append("</u>");
		}
		if (isSuperscript()) {
			buffer.insert(0, "<sup>");
			buffer.append("</sup>");
		}
		if (isSubscript()) {
			buffer.insert(0, "<sub>");
			buffer.append("</sub>");
		}
		if (!"".equals(font)) {
			buffer.insert(0, "\">");
			buffer.insert(0, font);
			buffer.insert(0, "<span style=\"font-family:");
			buffer.append("</span>");
		}
		return buffer.toString();
	}
}
