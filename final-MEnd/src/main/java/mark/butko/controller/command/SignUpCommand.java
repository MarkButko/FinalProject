package mark.butko.controller.command;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.controller.ServletPath;
import mark.butko.controller.util.UserInputChecker;
import mark.butko.model.entity.User;
import mark.butko.model.service.UserAlreadyExistsException;
import mark.butko.model.service.UserService;

public class SignUpCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class.getName());
	UserService userService = new UserService();

	@Override
	public String execute(HttpServletRequest request) {

		String page = JSPPath.SIGN_UP;

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String language = (String) request.getSession().getAttribute("language");
		Locale locale = new Locale(language);

		if (isCorrect(locale, name, email, password)) {
			User user = new User(name, email, password);
			try {
				userService.add(user);
				User registeredUser = userService.getByEmail(email);

				if (registeredUser == null) {
					LOGGER.info("userService add failed for: {}", user);
				} else {
					page = ServletPath.LOGIN;
					LOGGER.info("new user created : {}", registeredUser);
				}
			} catch (UserAlreadyExistsException exception) {
				request.setAttribute("errorMessage", "User with this email alredy exists");
				LOGGER.info("Attempt to register with busy email : {}", email);
			}
		} else {
			LOGGER.debug("incorect input");
		}
		return page;
	}

	public boolean isCorrect(Locale locale, String name, String email, String password) {
		UserInputChecker checker = new UserInputChecker(locale);
		return checker.isEmailCorrect(email)
				&& checker.isNameCorrect(name)
				&& checker.isPasswordCorrect(password);
	}
}
