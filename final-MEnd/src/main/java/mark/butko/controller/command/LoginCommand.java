package mark.butko.controller.command;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;
import mark.butko.model.service.exception.LoginException;
import mark.butko.model.service.exception.WrongEmailException;
import mark.butko.model.service.exception.WrongPasswordException;

public class LoginCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());

	private UserService userService;
	private ProposalService proposalService;

	public LoginCommand(UserService userService, ProposalService proposalService) {
		this.userService = userService;
		this.proposalService = proposalService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			// then page requested for the first time
			forward(request, response, JSPPath.LOGIN);
			return;
		}

		User user = (User) request.getSession().getAttribute("user");
		Set<String> loggedUsers = (HashSet<String>) request
				.getServletContext()
				.getAttribute("loggedUsers");
		if ((user != null) && loggedUsers.contains(user.getEmail())) {
			redirect(request, response, ServletPath.WELCOME_PAGE);
			LOGGER.debug("Attempt to login by already logged in user : {}", user.getEmail());
			return;
		}

		try {
			user = userService.login(email, password);

			loggedUsers.add(user.getEmail());
			request.getServletContext().setAttribute("loggedUsers", loggedUsers);
			request.getSession().setAttribute("user", user);

			LOGGER.info("Successed login: {}", user.getEmail());
			redirect(request, response, ServletPath.WELCOME_PAGE);
			return;
		} catch (WrongEmailException e) {
			request.setAttribute("email_error_message", "Wrong email");
		} catch (WrongPasswordException e) {
			request.setAttribute("password_error_message", "Wrong password");
		} catch (LoginException e) {
			request.setAttribute("login_error_message", "Login failed");
		}
		LOGGER.info("Fail login: email {} - pass {}", email, password);

		request.setAttribute("email", email);
		forward(request, response, JSPPath.LOGIN);
	}
}
