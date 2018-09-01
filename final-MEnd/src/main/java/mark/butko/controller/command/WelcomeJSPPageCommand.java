package mark.butko.controller.command;

import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.model.entity.User;

public class WelcomeJSPPageCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(WelcomeJSPPageCommand.class.getName());

	private final Map<User.Role, String> path = new EnumMap<>(User.Role.class);

	{
		path.put(User.Role.ADMIN, JSPPath.ADMIN_PAGE);
		path.put(User.Role.GUEST, JSPPath.LOGIN);
		path.put(User.Role.CUSTOMER, JSPPath.PROPOSALS);
		path.put(User.Role.MANAGER, JSPPath.MASTER_PAGE);
		path.put(User.Role.MASTER, JSPPath.MASTER_PAGE);
	}

	@Override
	public String execute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");

		LOGGER.debug("Welcome page for {} : {}", user.getEmail(), path.get(user.getRole()));

		return path.get(user.getRole());
	}

}
