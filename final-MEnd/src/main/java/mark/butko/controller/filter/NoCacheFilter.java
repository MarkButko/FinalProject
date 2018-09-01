package mark.butko.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter("/*")
public class NoCacheFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(NoCacheFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("init");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.trace("path : {}", ((HttpServletRequest) request).getRequestURI());
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "must-revalidate");
//		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
