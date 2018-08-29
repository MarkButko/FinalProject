package mark.butko.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.model.entity.User;

public class LogoutCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class.getName());

	@Override
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			LOGGER.info("Session invalidate for user : {}", user.getEmail());
			session.removeAttribute("user");
			session.invalidate();
		} else {
			LOGGER.debug("Null session invalidation attemp");
		}
		return JSPPath.LOGIN;
	}

}
