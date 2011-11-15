package ru.sstu.tables;

import java.util.Date;

/**
 * <code>SampleEntity</code> class is just sample entity for unit tests.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class SampleEntity {

	/**
	 * String column.
	 */
	private static final int COLUMN_STRING = 0;

	/**
	 * Integer column.
	 */
	private static final int COLUMN_INT = 1;

	/**
	 * Double column.
	 */
	private static final int COLUMN_DOUBLE = 2;

	/**
	 * Date column.
	 */
	private static final int COLUMN_DATE = 4;

	/**
	 * Text field.
	 */
	@ColumnIndex(COLUMN_STRING)
	private String text;

	/**
	 * Number field.
	 */
	@ColumnIndex(COLUMN_INT)
	private int number;

	/**
	 * Double value field.
	 */
	@ColumnIndex(COLUMN_DOUBLE)
	private double value;

	/**
	 * Date field.
	 */
	@ColumnIndex(COLUMN_DATE)
	private Date date;

	/**
	 * Default constructor.
	 */
	public SampleEntity() {
	}

	/**
	 * Initializes entity.
	 *
	 * @param text   text value
	 * @param number integer value
	 * @param value  floating point value
	 * @param date   date value
	 */
	public SampleEntity(String text, int number, double value, Date date) {
		this.text = text;
		this.number = number;
		this.value = value;
		this.date = date;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
