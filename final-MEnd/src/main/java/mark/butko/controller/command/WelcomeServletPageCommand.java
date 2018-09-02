package mark.butko.controller.command;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.User;

public class WelcomeServletPageCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(WelcomeServletPageCommand.class.getName());

	private final Map<User.Role, String> path = new EnumMap<>(User.Role.class);

	{
		path.put(User.Role.ADMIN, ServletPath.ADMIN_PAGE);
		path.put(User.Role.GUEST, ServletPath.LOGIN);
		path.put(User.Role.CUSTOMER, ServletPath.PAGINATION);
		path.put(User.Role.MANAGER, ServletPath.MANAGER_PAGE);
		path.put(User.Role.MASTER, ServletPath.MASTER_PAGE);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		String page;

		if (user == null) {
			page = JSPPath.LOGIN;
		} else {
			LOGGER.debug("Welcome page for {} : {}", user.getEmail(), path.get(user.getRole()));
			page = path.get(user.getRole());
		}
		redirect(request, response, page);
	}

}
