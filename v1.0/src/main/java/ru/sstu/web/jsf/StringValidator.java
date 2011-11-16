package ru.sstu.web.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * <code>StringValidator</code> class is most common JSF validator for text
 * fields.
 *
 * @author Denis_Murashev
 *
 * @since Web 1.0
 */
public class StringValidator extends JsfValidator<String> {

	/**
	 * @param context context
	 * @param input   input
	 * @param value   value
	 */
	public StringValidator(FacesContext context, UIInput input, String value) {
		super(context, input, value);
	}

	/**
	 * Checks field's length.
	 *
	 * @param min        min length
	 * @param max        max length
	 * @param resourceId message id
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkLength(int min, int max, String resourceId) {
		return checkLength(min, max, resourceId, (Object[]) null);
	}

	/**
	 * Checks field's length.
	 *
	 * @param min        min length
	 * @param max        max length
	 * @param resourceId message id
	 * @param params     additional parameters
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkLength(int min, int max, String resourceId,
			Object... params) {
		if (getValue() == null) {
			sendError(resourceId, params);
			return false;
		}
		int length = getValue().length();
		if (length > max || length < min) {
			sendError(resourceId, params);
			return false;
		}
		return true;
	}

	/**
	 * Checks field's length.
	 *
	 * @param regexp     regular expression
	 * @param resourceId message id
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkRegexp(String regexp, String resourceId) {
		return checkRegexp(regexp, resourceId, (Object[]) null);
	}

	/**
	 * Checks field's length.
	 *
	 * @param regexp     regular expression
	 * @param resourceId message id
	 * @param params     additional parameters
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkRegexp(String regexp, String resourceId,
			Object... params) {
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(getValue());
		if (!matcher.matches()) {
			sendError(resourceId, params);
			return false;
		}
		return true;
	}
}
