package ru.sstu.web.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ru.sstu.web.core.Context;

/**
 * <code>JsfCotroller</code> is most common controller for JSF projects.
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public class JsfController implements Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -8494289597095907327L;

	/**
	 * @return Context for JSF projects.
	 */
	protected Context getContext() {
		return JsfUtil.getContext();
	}

	/**
	 * @return message resources
	 */
	protected JsfMessages getMessages() {
		return JsfUtil.getMessages();
	}

	/**
	 * Forwards request.
	 *
	 * @param page page where request to be forwarded to
	 * @throws IOException      if some error occurs
	 * @throws ServletException if some error occurs
	 */
	protected void forward(String page) throws ServletException, IOException {
		HttpServletRequest request = getContext().getRequest();
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, getContext().getResponse());
	}

	/**
	 * Completes response.
	 */
	protected void complete() {
		FacesContext.getCurrentInstance().responseComplete();
	}
}
