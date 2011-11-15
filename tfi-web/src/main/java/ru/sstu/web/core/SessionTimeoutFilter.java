package ru.sstu.web.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>SessionTimeoutFilter</code> class filters invalid sessions.
 * <p/>
 * The filter should have following initial parameters:
 * <ol>
 *   <li><code>startPage</code> - the page to which request will be redirected
 *   in case of invalid session.
 * </ol>
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public class SessionTimeoutFilter implements Filter {

	/**
	 * Start page name.
	 */
	private String startPage = "";

	/**
	 * {@inheritDoc}
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		startPage = filterConfig.getInitParameter("startPage");
	}

	/**
	 * {@inheritDoc}
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			if (checkRedirect((HttpServletRequest) request)) {
				((HttpServletResponse) response).sendRedirect(startPage);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * Checks if current request can be redirected to start page.
	 *
	 * @param request request
	 * @return <code>true</code> if request can be redirected
	 */
	private boolean checkRedirect(HttpServletRequest request) {
		return request.getSession().getId() != null
				&& !request.isRequestedSessionIdValid()
				&& !request.getRequestURI().contains(startPage);
	}
}
