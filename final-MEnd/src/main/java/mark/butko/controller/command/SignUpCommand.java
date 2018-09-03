package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.checker.UserInputChecker;
import mark.butko.model.entity.User;
import mark.butko.model.service.UserService;
import mark.butko.model.service.exception.UserAlreadyExistsException;

public class SignUpCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class.getName());
	UserService userService = UserService.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (name == null || email == null || password == null) {
			forward(request, response, JSPPath.SIGN_UP);
			return;
		}

		request.setAttribute("email", email);
		request.setAttribute("name", name);

		if (!UserInputChecker.isEmailCorrect(email)) {
			request.setAttribute("email_error_message", "Invalid email");
			forward(request, response, JSPPath.SIGN_UP);
			return;
		}

		if (!UserInputChecker.isNameCorrect(name)) {
			request.setAttribute("name_error_message", "Invalid name");
			forward(request, response, JSPPath.SIGN_UP);
			return;
		}

		if (!UserInputChecker.isPasswordCorrect(password)) {
			request.setAttribute("password_error_message", "Invalid password");
			forward(request, response, JSPPath.SIGN_UP);
			return;
		}

		User user = new User(name, email, password);
		try {
			userService.add(user);
			User registeredUser = userService.getByEmail(email);

			if (registeredUser == null) {
				LOGGER.info("registration failed for: {}", user);
			} else {
				LOGGER.info("new user created : {}", registeredUser);
				redirect(request, response, ServletPath.LOGIN);
				return;
			}
		} catch (UserAlreadyExistsException exception) {
			request.setAttribute("email_exists_error_message", "Busy email");
			LOGGER.info("Attempt to register with busy email : {}", email);
		}
		forward(request, response, JSPPath.SIGN_UP);
	}
}
