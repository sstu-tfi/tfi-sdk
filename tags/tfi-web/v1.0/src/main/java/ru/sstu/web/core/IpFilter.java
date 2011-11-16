package ru.sstu.web.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <code>IpFilter</code> class filters disallowed IP addresses.
 * <p/>
 * The filter should have following initial parameters:
 * <ol>
 *   <li><code>ipPatterns</code> - comma separated list of allowed IP addresses.
 * </ol>
 *
 * @author Denis_Murashev
 * @since Web 1.0
 */
public class IpFilter implements Filter {

	/**
	 * Start page name.
	 */
	private List<String> ipPatterns = new ArrayList<String>();

	/**
	 * {@inheritDoc}
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		StringTokenizer tokenizer = new StringTokenizer(filterConfig
				.getInitParameter("ipPatterns"), ",");
		while (tokenizer.hasMoreTokens()) {
			ipPatterns.add(tokenizer.nextToken());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String ip = request.getRemoteAddr();
		if (checkIp(ip)) {
			chain.doFilter(request, response);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * @param ip client IP
	 * @return <code>true</code> if IP is allowed
	 */
	private boolean checkIp(String ip) {
		for (String pattern : ipPatterns) {
			if (ip.matches(pattern)) {
				return true;
			}
		}
		return false;
	}
}
