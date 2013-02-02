package ru.sstu.web.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * <code>JsfValidator</code> class is most common JSF validator.
 *
 * @author Denis_Murashev
 *
 * @param <T> type of input value
 * @since Web 1.0
 */
public class JsfValidator<T> {

	/**
	 * Faces context.
	 */
	private final FacesContext context;

	/**
	 * UI input.
	 */
	private final UIInput input;

	/**
	 * Value.
	 */
	private final T value;

	/**
	 * @param context context
	 * @param input   input
	 * @param value   value
	 */
	public JsfValidator(FacesContext context, UIInput input, T value) {
		this.context = context;
		this.input = input;
		this.value = value;
	}

	/**
	 * Checks if field value equals to required value.
	 *
	 * @param object     required value
	 * @param resourceId message id
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkEquals(T object, String resourceId) {
		return checkEquals(object, resourceId, new Object[0]);
	}

	/**
	 * Checks if field value equals to required value.
	 *
	 * @param object     required value
	 * @param resourceId message id
	 * @param parameters message parameters
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkEquals(T object, String resourceId,
			Object... parameters) {
		if (value != null && !value.equals(object)) {
			sendError(resourceId, parameters);
			return false;
		}
		if (value == null && object != null) {
			sendError(resourceId, parameters);
			return false;
		}
		return true;
	}

	/**
	 * Checks if object with given name is already exists.
	 *
	 * @param object     changed object
	 * @param dbObject   existing object
	 * @param resourceId message id
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkExists(Object object, Object dbObject,
			String resourceId) {
		return checkExists(object, dbObject, resourceId, new Object[0]);
	}

	/**
	 * Checks if object with given name is already exists.
	 *
	 * @param object     changed object
	 * @param dbObject   existing object
	 * @param resourceId message id
	 * @param parameters message parameters
	 * @return <code>true</code> if check is successful
	 */
	public boolean checkExists(Object object, Object dbObject,
			String resourceId, Object... parameters) {
		if (dbObject == null) {
			return true;
		}
		if (!dbObject.equals(object)) {
			sendError(resourceId, parameters);
			return false;
		}
		return true;
	}

	/**
	 * Sets validation error.
	 *
	 * @param resourceId message resource id
	 */
	public void sendError(String resourceId) {
		sendError(resourceId, (Object[]) null);
	}

	/**
	 * Sets validation error.
	 *
	 * @param resourceId message resource id
	 * @param parameters message parameters
	 */
	public void sendError(String resourceId, Object... parameters) {
		input.setValid(false);
		FacesMessage message = getMessages().getMessage(resourceId,
				parameters);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(input.getClientId(context), message);
	}

	/**
	 * @return message resources
	 */
	protected JsfMessages getMessages() {
		return JsfUtil.getMessages();
	}

	/**
	 * @return the context
	 */
	protected FacesContext getContext() {
		return context;
	}

	/**
	 * @return the input
	 */
	protected UIInput getInput() {
		return input;
	}

	/**
	 * @return the value
	 */
	protected T getValue() {
		return value;
	}
}
