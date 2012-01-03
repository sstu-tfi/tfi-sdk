package ru.sstu.properties.core;

/**
 * Just simple settings class for unit test.
 *
 * @author Denis_Murashev
 * @since Properties 1.0
 */
public class SimpleSettings {

	@Property("test.string")
	private String stringValue;

	@Property("test.int")
	private int intValue;

	@Property("test.double")
	private double doubleValue;

	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}

	/**
	 * @param intValue the intValue to set
	 */
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	/**
	 * @return the doubleValue
	 */
	public double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * @param doubleValue the doubleValue to set
	 */
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}
}
