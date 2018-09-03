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
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.command.CommandProvider;
import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.entity.User.Role;

@WebFilter("/*")
public class AccessFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(AccessFilter.class.getName());

	private static final HashMap<User.Role, String> ROLE_TO_ALLOWED_PATH_MAP = new HashMap<>();

	private static final Set<String> ALLOWED_TO_ALL = new HashSet<>(
			Arrays.asList(
					ServletPath.WELCOME_PAGE,
					ServletPath.SIGN_UP,
					ServletPath.LOGIN,
					ServletPath.EMPTY,
					ServletPath.LOGOUT,
					JSPPath.SIGN_UP,
					JSPPath.LOGIN));

	static {
		ROLE_TO_ALLOWED_PATH_MAP.put(Role.CUSTOMER, "customer");
		ROLE_TO_ALLOWED_PATH_MAP.put(Role.MANAGER, "manager");
		ROLE_TO_ALLOWED_PATH_MAP.put(Role.MASTER, "master");
		ROLE_TO_ALLOWED_PATH_MAP.put(Role.ADMIN, "admin");
		ROLE_TO_ALLOWED_PATH_MAP.put(Role.GUEST, "guest");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String requestedPath = request.getRequestURI()
				.substring(ServletPath.SERVLET_CONTEXT.length());

		if (requestedPath.matches(".*(css|jpg|png|gif|js|woff|woff2|ttf|map)$")) {
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
		if (hasRights && !requestedPath.endsWith(".jsp")) {
			chain.doFilter(request, response);
		} else {
			LOGGER.info("access denied for user : {} {} on url : {}", user.getEmail(), user.getRole(), requestedPath);
			CommandProvider.getCommandOrWelcomePage("").execute(request, response);
		}
	}

	@Override
	public void destroy() {
		LOGGER.info("destroy");
	}

	private boolean checkRights(User.Role role, String path) {
		return (ALLOWED_TO_ALL.contains(path) || path.contains(ROLE_TO_ALLOWED_PATH_MAP.get(role)));
	}
}
