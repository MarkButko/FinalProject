package mark.butko.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.service.UserDoesNotExist;
import mark.butko.model.service.UserService;

public class ChangeRoleCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(ChangeRoleCommand.class.getName());

	UserService userService;

	public ChangeRoleCommand(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {

		String roleString = request.getParameter("role");
		User.Role role = User.Role.valueOf(roleString);
		String IdString = (String) request.getParameter("id");
		Integer id = Integer.parseInt(IdString);

		try {
			userService.changeRole(id, role);
		} catch (UserDoesNotExist e) {
			LOGGER.warn("Attemt to change role of non existing userId : {}", id);
		}

		return ServletPath.ADMIN_PAGE;
	}

}
