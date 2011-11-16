package ru.sstu.web.jsf;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * <code>NumberValidator</code> class is most common JSF validator for numeric
 * fields.
 *
 * @author Denis_Murashev
 *
 * @since Web 1.0
 */
public class NumberValidator extends JsfValidator<Number> {

	/**
	 * @param context context
	 * @param input   input
	 * @param value   value
	 */
	public NumberValidator(FacesContext context, UIInput input, Number value) {
		super(context, input, value);
	}

	/**
	 * Checks numeric field's value.
	 *
	 * @param min        min value
	 * @param max        max value
	 * @param resourceId message id
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkLimits(Number min, Number max, String resourceId) {
		return checkLimits(min, max, resourceId, new Object[0]);
	}

	/**
	 * Checks numeric field's value.
	 *
	 * @param min        min value
	 * @param max        max value
	 * @param resourceId message id
	 * @param parameters additional message parameters
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkLimits(Number min, Number max, String resourceId,
			Object... parameters) {
		if (getValue() == null) {
			sendError(resourceId, parameters);
			return false;
		}
		double value = getValue().doubleValue();
		if (value > max.doubleValue() || value < min.doubleValue()) {
			sendError(resourceId, parameters);
			return false;
		}
		return true;
	}
}
