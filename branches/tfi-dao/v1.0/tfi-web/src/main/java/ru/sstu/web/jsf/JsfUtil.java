package ru.sstu.web.jsf;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.sstu.web.core.Context;

/**
 * <code>JsfUtil</code> class contains utility methods for JSF projects.
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
final class JsfUtil {

	/**
	 * Context implementation for JSF.
	 */
	private static Context context = new JsfContext();

	/**
	 * Resource bundle messages.
	 */
	private static JsfMessages messages = new JsfMessages();

	/**
	 * No instances needed.
	 */
	private JsfUtil() {
	}

	/**
	 * @return Context for JSF projects.
	 */
	static Context getContext() {
		return context;
	}

	/**
	 * @return message resources
	 */
	static JsfMessages getMessages() {
		return messages;
	}

	/**
	 * <code>JsfContext</code> class is default {@link Context} implementation
	 * for JSF.
	 *
	 * @author Denis_Murashev
	 * @since Web JSF 1.0
	 */
	private static final class JsfContext implements Context {

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		public <T> T getSessionAttribute(String name) {
			return (T) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get(name);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		public <T> T getRequestAttribute(String name) {
			return (T) FacesContext.getCurrentInstance().getExternalContext()
					.getRequestMap().get(name);
		}

		/**
		 * {@inheritDoc}
		 */
		public String getRequestParameter(String name) {
			return FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get(name);
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpSession getSession() {
			return (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletRequest getRequest() {
			return (HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest();
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletResponse getResponse() {
			return (HttpServletResponse) FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();
		}
	}
}
