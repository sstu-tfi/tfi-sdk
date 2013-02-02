package ru.sstu.docs;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;

/**
 * <code>TextWriter</code> class is common {@link DocumentWriter} implementation
 * for iText library.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
abstract class TextWriter implements DocumentWriter {

	/**
	 * Text rise.
	 */
	private static final float TEXT_RISE = 4.0f;

	/**
	 * Writs given document to given specified output stream.
	 *
	 * @param document document to be written
	 * @param output   output stream
	 * @throws DocumentException if some error occurs
	 */
	public void write(Document document, OutputStream output)
			throws DocumentException {
		com.lowagie.text.Document doc = new com.lowagie.text.Document();
		prepareWriter(doc, output);
		doc.open();
		prepareDocument(document, doc);
		doc.close();
	}

	/**
	 * Prepares specific writer.
	 *
	 * @param document iText document to be written
	 * @param output   output stream
	 * @throws DocumentException if some error occurs
	 */
	protected abstract void prepareWriter(com.lowagie.text.Document document,
			OutputStream output) throws DocumentException;

	/**
	 * Prepares document.
	 *
	 * @param document source document
	 * @param iTextDoc iText document
	 * @throws DocumentException if some error occurs
	 */
	private void prepareDocument(Document document,
			com.lowagie.text.Document iTextDoc) throws DocumentException {
		try {
			for (Node block : document) {
				if (block.getType() == Node.PARAGRAPH) {
					Phrase phrase = createParagraph((Paragraph) block);
					iTextDoc.add(new com.lowagie.text.Paragraph(phrase));
				} else if (block.getType() == Node.TABLE) {
					iTextDoc.add(createTable((TableBlock) block));
				}
			}
		} catch (com.lowagie.text.DocumentException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Creates table.
	 *
	 * @param table source table
	 * @return table
	 * @throws DocumentException if some error occurs
	 */
	private Table createTable(TableBlock table) throws DocumentException {
		try {
			int size = 0;
			for (TableCell cell : table.get(0)) {
				size += cell.getColspan();
			}
			Table t = new Table(size);
			int rowIndex = 0;
			for (TableRow row : table) {
				int colIndex = 0;
				for (TableCell cell : row) {
					Cell c = new Cell(createParagraph(cell));
					if (cell.getColspan() != 1) {
						c.setColspan(cell.getColspan());
					}
					t.addCell(c, rowIndex, colIndex);
					colIndex++;
				}
				rowIndex++;
			}
			return t;
		} catch (BadElementException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Creates paragraph.
	 *
	 * @param paragraph source paragraph
	 * @return paragraph
	 * @throws DocumentException if some error occurs
	 */
	private com.lowagie.text.Paragraph createParagraph(List<Atom> paragraph)
			throws DocumentException {
		try {
			com.lowagie.text.Paragraph p = new com.lowagie.text.Paragraph();
			for (Atom node : paragraph) {
				if (node.getType() == Node.TEXT_NODE) {
					p.add(createChunk((TextNode) node));
				} else if (node.getType() == Node.IMAGE) {
					byte[] data = ((Image) node).getData();
					com.lowagie.text.Image image =
							com.lowagie.text.Image.getInstance(data);
					if (paragraph.size() == 1) {
						p.add(image);
					} else {
						p.add(new Chunk(image, 0, 0));
					}
				}
			}
			return p;
		} catch (MalformedURLException e) {
			throw new DocumentException(e);
		} catch (BadElementException e) {
			throw new DocumentException(e);
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Creates text chunk.
	 *
	 * @param text source text node
	 * @return result text chunk
	 * @throws DocumentException if some error occurs
	 */
	private Chunk createChunk(TextNode text) throws DocumentException {
		Chunk chunk = new Chunk(text.getText());
		Font font = FontManager.getInstance().getFont(text.getFont());
		if (text.isBold()) {
			font.setStyle(Font.BOLD);
		}
		if (text.isItalic()) {
			font.setStyle(Font.ITALIC);
		}
		if (text.isUnderlined()) {
			font.setStyle(Font.UNDERLINE);
		}
		if (text.isSuperscript()) {
			chunk.setTextRise(TEXT_RISE);
		} else if (text.isSubscript()) {
			chunk.setTextRise(-TEXT_RISE);
		}
		chunk.setFont(font);
		return chunk;
	}
}
