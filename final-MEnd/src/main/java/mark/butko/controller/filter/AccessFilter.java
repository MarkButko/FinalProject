package mark.butko.controller.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.controller.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.entity.User.Role;

public class AccessFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(AccessFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("doFilter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestedPath = request.getRequestURI().substring(ServletPath.SERVLET_CONTEXT.length());

		if (requestedPath.matches(".*(css|jpg|png|gif|js|woff|woff2|ttf)$")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			user = new User();
			user.setRole(User.Role.GUEST);
			session.setAttribute("user", user);
		}

		LOGGER.debug("requestedPath = {}, user.role = {}", requestedPath, user.getRole().name());
		boolean hasRights = checkRights(user.getRole(), requestedPath);
		if (hasRights) {
			chain.doFilter(request, response);
		} else {
			request.getServletContext()
					.getRequestDispatcher(JSPPath.PAGE_NOT_FOUND)
					.forward(request, response);
		}
	}

	@Override
	public void destroy() {
		LOGGER.info("destroy");
	}

	private boolean checkRights(User.Role role, String path) {

		HashMap<User.Role, String> roleToPathMap = new HashMap<>();

		Set<String> allowedToAll = new HashSet<>(
				Arrays.asList(ServletPath.COMMENTS,
						ServletPath.SIGN_UP,
						ServletPath.LOGIN,
						ServletPath.EMPTY,
						ServletPath.LOGOUT,
						JSPPath.COMMENTS,
						JSPPath.SIGN_UP,
						JSPPath.LOGIN));

		roleToPathMap.put(Role.CUSTOMER, "customer");
		roleToPathMap.put(Role.MANAGER, "manager");
		roleToPathMap.put(Role.MASTER, "master");
		roleToPathMap.put(Role.ADMIN, "admin");
		roleToPathMap.put(Role.GUEST, "guest");

		if (allowedToAll.contains(path) || path.contains(roleToPathMap.get(role))) {
			return true;
		}

		return false;
	}
}
