package ru.sstu.web.core;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * {@code AbstractResourceLoaderServlet} class provides base procedure for
 * resource loading.
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public abstract class AbstractResourceLoaderServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 8067708557392515012L;

	/**
	 * Logger.
	 */
	private static Logger log = Logger
			.getLogger(AbstractResourceLoaderServlet.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Object object = getObject(request);
		if (object == null) {
			log.error("Resource cannot be loaded.");
			return;
		}
		response.setContentType(getMimeType(object));
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			write(object, output);
			output.flush();
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * Provides object for resource.
	 *
	 * @param request HTTP request
	 * @return object for resource
	 */
	protected abstract Object getObject(HttpServletRequest request);

	/**
	 * Provides MIME type of resource.
	 *
	 * @param object resource object
	 * @return MIME type
	 */
	protected abstract String getMimeType(Object object);

	/**
	 * Writes resource bytes to HTTP response.
	 *
	 * @param object resource
	 * @param output output stream
	 * @throws IOException if some error occurs
	 */
	protected abstract void write(Object object, OutputStream output)
			throws IOException;
}
