package ru.sstu.tables;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <code>XlsxTableReader</code> class provides data read from Excel 2007 file.
 *
 * @author Dmitry_Petrov
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class XlsxTableReader extends ExcelTableReader {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void init(InputStream input, int index) throws TableException {
		try {
			XSSFWorkbook workBook = new XSSFWorkbook(OPCPackage.open(input));
			Sheet sheet = workBook.getSheetAt(index);
			setRows(sheet.rowIterator());
		} catch (IOException e) {
			throw new TableException(e);
		} catch (InvalidFormatException e) {
			throw new TableException(e);
		}
	}
}
