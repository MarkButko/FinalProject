package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.service.UserService;
import mark.butko.model.service.exception.UserDoesNotExist;

public class ChangeRoleCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(ChangeRoleCommand.class.getName());

	UserService userService;

	public ChangeRoleCommand(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String roleString = request.getParameter("role");
		User.Role role = User.Role.valueOf(roleString);
		String IdString = (String) request.getParameter("id");
		Integer id = Integer.parseInt(IdString);

		try {
			userService.changeRole(id, role);
		} catch (UserDoesNotExist e) {
			LOGGER.warn("Attemt to change role of non existing userId : {}", id);
		}

		redirect(request, response, ServletPath.ADMIN_PAGE);
	}

}
