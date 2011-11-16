package ru.sstu.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * <code>ClasspathResourceLoaderServlet</code> class provides resources loaded
 * from CLASSPATH.
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public class ClasspathResourceLoaderServlet
		extends AbstractResourceLoaderServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -3914353623240525668L;

	/**
	 * Buffer size for resources loading.
	 */
	private static final int CHUNK_SIZE = 65536;

	/**
	 * Logger.
	 */
	private static Logger log = Logger
			.getLogger(ClasspathResourceLoaderServlet.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object getObject(HttpServletRequest request) {
		String path = request.getParameter("path");
		if (path == null) {
			log.error("Path is not specified.");
			return null;
		}
		return getClass().getResourceAsStream(path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMimeType(Object object) {
		return "application/octet-stream";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void write(Object object, OutputStream output)
			throws IOException {
		InputStream input = (InputStream) object;
		if (input == null) {
			log.error("InputStream cannot be created.");
			return;
		}
		byte[] buffer = new byte[CHUNK_SIZE];
		int bytes = input.read(buffer);
		while (bytes != -1) {
			output.write(buffer);
			bytes = input.read(buffer);
		}
		input.close();
	}
}
