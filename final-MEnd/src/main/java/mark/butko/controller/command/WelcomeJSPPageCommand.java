package mark.butko.controller.command;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.model.entity.User;

public class WelcomeJSPPageCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(WelcomeJSPPageCommand.class.getName());

	private final Map<User.Role, String> path = new EnumMap<>(User.Role.class);

	{
		path.put(User.Role.ADMIN, JSPPath.ADMIN_PAGE);
		path.put(User.Role.GUEST, JSPPath.LOGIN);
		path.put(User.Role.CUSTOMER, JSPPath.CUSTOMER_PROPOSALS);
		path.put(User.Role.MANAGER, JSPPath.MASTER_PAGE);
		path.put(User.Role.MASTER, JSPPath.MASTER_PAGE);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");

		LOGGER.debug("Welcome page for {} : {}", user.getEmail(), path.get(user.getRole()));

		forward(request, response, path.get(user.getRole()));
	}

}
