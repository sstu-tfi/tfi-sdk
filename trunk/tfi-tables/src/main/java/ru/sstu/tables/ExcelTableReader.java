package ru.sstu.tables;

import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * <code>ExcelTableReader</code> class provides data read from Excel file.
 *
 * @author Dmitry_Petrov
 * @author Denis_Murashev
 * @since Tables 1.0
 */
abstract class ExcelTableReader extends FileTableReader {

	/**
	 * Rows iterator.
	 */
	private Iterator<Row> rows;

	/**
	 * Current row.
	 */
	private Row row;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void nextLine() throws TableException {
		row = rows.next();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean hasNextLine() {
		return rows.hasNext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object nextValue(int index) throws TableException {
		return row.getCell(index);
	}

	/**
	 * Sets rows data.
	 *
	 * @param rows the rows to set
	 */
	protected void setRows(Iterator<Row> rows) {
		this.rows = rows;
	}

	static {
		Mapping.addConverter(Cell.class, String.class, new Converter() {
			public String convert(Object value) {
				Cell cell = (Cell) value;
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					return String.valueOf(cell.getNumericCellValue());
				}
				return cell.getStringCellValue();
			}
		});
		Mapping.addConverter(Cell.class, short.class, new Converter() {
			public Short convert(Object value) {
				return ((Number) ((Cell) value).getNumericCellValue())
						.shortValue();
			}
		});
		Mapping.addConverter(Cell.class, int.class, new Converter() {
			public Integer convert(Object value) {
				return ((Number) ((Cell) value).getNumericCellValue())
						.intValue();
			}
		});
		Mapping.addConverter(Cell.class, long.class, new Converter() {
			public Long convert(Object value) {
				return ((Number) ((Cell) value).getNumericCellValue())
						.longValue();
			}
		});
		Mapping.addConverter(Cell.class, float.class, new Converter() {
			public Float convert(Object value) {
				return ((Number) ((Cell) value).getNumericCellValue())
						.floatValue();
			}
		});
		Mapping.addConverter(Cell.class, double.class, new Converter() {
			public Double convert(Object value) {
				return ((Number) ((Cell) value).getNumericCellValue())
						.doubleValue();
			}
		});
		Mapping.addConverter(Cell.class, boolean.class, new Converter() {
			public Boolean convert(Object value) {
				return ((Cell) value).getBooleanCellValue();
			}
		});
		Mapping.addConverter(Cell.class, Date.class, new Converter() {
			public Date convert(Object value) {
				Cell cell = (Cell) value;
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					try {
						return (Date) Mapping.getConverter(String.class,
								Date.class).convert(cell.getStringCellValue());
					} catch (TableException e) {
						throw new RuntimeException(e);
					}
				}
				return cell.getDateCellValue();
			}
		});
	}
}
