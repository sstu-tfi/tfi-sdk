package ru.sstu.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code ClasspathResourceLoaderServlet} class provides resources loaded
 * from CLASSPATH.
 *
 * <p>Relative path to resource should be in request parameter named "path".</p>
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public class ClasspathResourceLoaderServlet
		extends AbstractResourceLoaderServlet {

	private static final long serialVersionUID = 763837971848133420L;

	/**
	 * Buffer size for resources loading.
	 */
	private static final int CHUNK_SIZE = 65536;

	@Override
	protected Object getObject(HttpServletRequest request) {
		return request.getParameter("path");
	}

	@Override
	protected String getMimeType(Object object) {
		return "application/octet-stream";
	}

	@Override
	protected void write(Object object, OutputStream output)
			throws IOException {
		InputStream input = getClass().getResourceAsStream((String) object);
		byte[] buffer = new byte[CHUNK_SIZE];
		try {
			int bytes = input.read(buffer);
			while (bytes != -1) {
				output.write(buffer, 0, bytes);
				bytes = input.read(buffer);
			}
		} finally {
			input.close();
		}
	}
}
