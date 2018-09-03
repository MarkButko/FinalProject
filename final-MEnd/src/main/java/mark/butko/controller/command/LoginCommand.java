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
import mark.butko.model.service.UserService;
import mark.butko.model.service.exception.LoginException;
import mark.butko.model.service.exception.WrongEmailException;
import mark.butko.model.service.exception.WrongPasswordException;

public class LoginCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());

	private UserService userService;

	public LoginCommand(UserService userService) {
		this.userService = userService;
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

		Set<String> loggedUsers = (HashSet<String>) request
				.getServletContext()
				.getAttribute("loggedUsers");
		User user = (User) request.getSession().getAttribute("user");

		if (loggedUsers.contains(email)) { // then logged in user tries to login again
											// (can access login page from other browser)
			request.setAttribute("email_error_message", "User is already logged in");
			request.setAttribute("email", email);
			forward(request, response, JSPPath.LOGIN);
			LOGGER.debug("Attempt to login by already logged in user : {}", email);
			return;
		}
		if ((user != null) && (user.getRole() != User.Role.GUEST)) {// then logged in user tries to access login page
			redirect(request, response, ServletPath.WELCOME_PAGE);
			LOGGER.debug("Attempt to login by already logged in user : {}", email);
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
