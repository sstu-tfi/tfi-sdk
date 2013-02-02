package ru.sstu.docs;

/**
 * <code>Image</code> class represents image embedded in document.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class Image implements Atom {

	/**
	 * URL.
	 */
	private String url;

	/**
	 * Alternative text.
	 */
	private String alt;

	/**
	 * Image data.
	 */
	private byte[] data;

	/**
	 * {@inheritDoc}
	 *
	 * @return {@link Node#IMAGE}
	 */
	public int getType() {
		return IMAGE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @return empty string
	 */
	public String getText() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public void postProcess(DocumentReader reader) throws DocumentException {
		final String wmz = ".wmz";
		if (url.toLowerCase().endsWith(wmz)) {
			data = ImageUtil.readWMZ(url, data);
			String name = url.replaceAll(wmz, ".wmf");
			url = name;
			alt = name;
		}
		reader.addImage(this);
	}

	/**
	 * @return the URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the URL to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the alternative text
	 */
	public String getAlt() {
		return alt;
	}

	/**
	 * @param alt the alternative text to set
	 */
	public void setAlt(String alt) {
		this.alt = alt;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<img src=\"");
		buffer.append(url);
		buffer.append("\" alt=\"");
		buffer.append(alt);
		buffer.append("\"/>");
		return buffer.toString();
	}
}
