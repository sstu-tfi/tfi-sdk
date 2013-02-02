package ru.sstu.docs;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * <code>ImageUtil</code> class is helper class for reading image data.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
final class ImageUtil {

	/**
	 * Chunk size.
	 */
	private static final int CHUNK_SIZE = 32 * 1024;

	/**
	 * Bits in byte.
	 */
	private static final int BITS_PER_BYTE = 8;

	/**
	 * Bytes in integer.
	 */
	private static final int BYTES_PER_INT = 4;

	/**
	 * Byte mask.
	 */
	private static final int MASK = 0xFF;

	/**
	 * Initial header length.
	 */
	private static final int INI_HEADER_LENGTH = 10;

	/**
	 * Initial length offset.
	 */
	private static final int INI_LENGTH_OFFSET = 18;

	/**
	 * Initial CRC offset.
	 */
	private static final int INI_CRC_OFFSET = 8;

	/**
	 * Initial extra length offset.
	 */
	private static final int INI_EXLENGTH_OFFSET = 4;

	/**
	 * Result extra bytes.
	 */
	private static final int RES_EXTRA_BYTES = 20;

	/**
	 * Result CRC offset.
	 */
	private static final int RES_CRC_OFFSET = 14;

	/**
	 * Result length offset.
	 */
	private static final int RES_LENGTH_OFFSET = 18;

	/**
	 * Result extra length offset.
	 */
	private static final int RES_EXLENGTH_OFFSET = 22;

	/**
	 * Result title offset.
	 */
	private static final int RES_TITLE_OFFSET = 26;

	/**
	 * Header.
	 */
	private static final byte[] HEADER = new byte[]{
		0x50, 0x4B, 0x03, 0x04,
		0x14, 0x00, 0x02, 0x00,
		0x08, 0x00, (byte) 0xB6, 0x75,
		0x04, 0x37,
		0, 0, 0, 0,
		0, 0, 0, 0,
		0, 0, 0, 0,
		0, 0, 0, 0,
	};

	/**
	 * No ImageUtil instances needed.
	 */
	private ImageUtil() {
	}

	/**
	 * Converts Zipped WMZ data to Windows MetaFile (WMF).
	 *
	 * @param title image title
	 * @param data  image data
	 * @return converted image
	 * @throws DocumentException if cannot convert image
	 */
	public static byte[] readWMZ(String title, byte[] data)
			throws DocumentException {
		try {
			int dataBytes = data.length;
			int length = dataBytes - INI_LENGTH_OFFSET;
			byte[] crc = new byte[BYTES_PER_INT];
			System.arraycopy(data, dataBytes - INI_CRC_OFFSET, crc, 0,
					BYTES_PER_INT);
			byte[] exLength = new byte[BYTES_PER_INT];
			System.arraycopy(data, dataBytes - INI_EXLENGTH_OFFSET, exLength, 0,
					BYTES_PER_INT);

			byte[] header = HEADER.clone();
			System.arraycopy(crc, 0, header, RES_CRC_OFFSET, BYTES_PER_INT);
			System.arraycopy(toBytes(length), 0, header, RES_LENGTH_OFFSET,
					BYTES_PER_INT);
			System.arraycopy(exLength, 0, header, RES_EXLENGTH_OFFSET,
					BYTES_PER_INT);
			System.arraycopy(toBytes(title.length()), 0, header,
					RES_TITLE_OFFSET, BYTES_PER_INT);

			byte[] bytes = new byte[dataBytes + RES_EXTRA_BYTES
					+ title.length()];
			System.arraycopy(header, 0, bytes, 0, header.length);
			byte[] nameBytes = title.getBytes();
			System.arraycopy(nameBytes, 0, bytes, header.length,
					nameBytes.length);
			System.arraycopy(data, INI_HEADER_LENGTH, bytes,
					header.length + nameBytes.length,
					dataBytes - INI_HEADER_LENGTH);

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ZipInputStream input = new ZipInputStream(
					new ByteArrayInputStream(bytes));
			ZipEntry entry = input.getNextEntry();
			if (entry != null) {
				copy(input, output);
				return output.toByteArray();
			}
			throw new DocumentException("Error reading WMZ file");
		} catch (FileNotFoundException e) {
			throw new DocumentException(e);
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Copies from input to output.
	 *
	 * @param input  input stream
	 * @param output output stream
	 * @throws DocumentException if some error occurs
	 */
	private static void copy(InputStream input, OutputStream output)
			throws DocumentException {
		try {
			BufferedInputStream in = new BufferedInputStream(input);
			byte[] buffer = new byte[CHUNK_SIZE];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			output.flush();
			in.close();
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Converts integer to array of bytes.
	 *
	 * @param value integer value
	 * @return array of bytes
	 */
	private static byte[] toBytes(int value) {
		byte[] bytes = new byte[BYTES_PER_INT];
		for (int i = 0; i < BYTES_PER_INT; i++) {
			int shift = BITS_PER_BYTE * i;
			int mask = MASK << shift;
			bytes[i] = (byte) ((value & mask) >> shift);
		}
		return bytes;
	}
}
