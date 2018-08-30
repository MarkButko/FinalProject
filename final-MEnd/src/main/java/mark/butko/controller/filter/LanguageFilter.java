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
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter("/*")
public class LanguageFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(LanguageFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.trace("path : {}", ((HttpServletRequest) req).getRequestURI());
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		String language;

		if (request.getParameter("language") != null) {
			language = request.getParameter("language");

		} else if (session.getAttribute("language") != null) {
			language = (String) session.getAttribute("language");

		} else {
			language = request.getLocale().getLanguage();
		}

		session.setAttribute("language", language);
		LOGGER.trace("language = {}", language);

		chain.doFilter(request, resp);
	}

	@Override
	public void destroy() {
		LOGGER.info("destroy");
	}
}
