package ru.sstu.tables;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <code>XlsTableReader</code> class provides data read from old Excel 2003
 * file.
 *
 * @author Dmitry_Petrov
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class XlsTableReader extends ExcelTableReader {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void init(InputStream input, int index) throws TableException {
		try {
			POIFSFileSystem fileSystem = new POIFSFileSystem(input);
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			Sheet sheet = workBook.getSheetAt(index);
			setRows(sheet.rowIterator());
		} catch (IOException e) {
			throw new TableException(e);
		}
	}
}
