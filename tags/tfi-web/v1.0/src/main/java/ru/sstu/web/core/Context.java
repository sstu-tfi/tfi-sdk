package ru.sstu.web.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <code>Context</code> class provides utility methods for getting different
 * scopes, attributes and parameters.
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public interface Context {

	/**
	 * Provides session attribute.
	 *
	 * @param <T> return type
	 * @param name attribute name
	 * @return attribute value
	 */
	<T> T getSessionAttribute(String name);

	/**
	 * Provides request attribute.
	 *
	 * @param <T> return type
	 * @param name attribute name
	 * @return attribute value
	 */
	<T> T getRequestAttribute(String name);

	/**
	 * Provides request parameter.
	 *
	 * @param name parameter name
	 * @return parameter value
	 */
	String getRequestParameter(String name);

	/**
	 * Provides HTTP session.
	 *
	 * @return session
	 */
	HttpSession getSession();

	/**
	 * Provides HTTP request.
	 *
	 * @return request
	 */
	HttpServletRequest getRequest();

	/**
	 * Provides HTTP response.
	 *
	 * @return response
	 */
	HttpServletResponse getResponse();
}
