package mark.butko.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mark.butko.controller.Path;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;
import mark.butko.model.service.LoginException;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;

public class LoginCommand implements Command {

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

		try {
			User user = userService.login(email, password);
			request.getSession().setAttribute("user", user);
			List<Proposal> proposals = proposalService.getProposalsByUserId(user.getId());
			request.setAttribute("proposals", proposals);

			System.out.println("Login command proposals:");

			path = Path.SERVLET_PAGINATION;
		} catch (LoginException e) {
			request.setAttribute("error_message", "Wrong login or password");
			path = Path.LOGIN;
			// log
		}
		return path;
	}
}
