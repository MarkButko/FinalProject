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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.trace("path : {}", ((HttpServletRequest) request).getRequestURI());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		LOGGER.info("destroy");
	}

}
