package mark.butko.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.controller.ServletPath;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;
import mark.butko.model.service.LoginException;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;

public class LoginCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());

	private UserService userService;
	private ProposalService proposalService;

	public LoginCommand(UserService userService, ProposalService proposalService) {
		this.userService = userService;
		this.proposalService = proposalService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String path = null;

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			return JSPPath.LOGIN;
		}

		try {
			User user = userService.login(email, password);
			request.getSession().setAttribute("user", user);
			List<Proposal> proposals = proposalService.getProposalsByUserId(user.getId());
			request.setAttribute("proposals", proposals);

			LOGGER.info("Successed login: {}", user.getEmail());

			path = ServletPath.WELCOME_PAGE;
		} catch (LoginException e) {
			request.setAttribute("error_message", "Wrong login or password");
			path = JSPPath.LOGIN;
			LOGGER.info("Fail login: {} - {}", email, password);
		}
		return path;
	}
}
