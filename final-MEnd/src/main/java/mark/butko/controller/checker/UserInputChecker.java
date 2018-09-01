package mark.butko.controller.checker;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInputChecker {

	private static final Logger LOGGER = LogManager.getLogger(UserInputChecker.class.getName());

	private RegexContainer regexContainer;

	public UserInputChecker(Locale locale) {
		LOGGER.debug("Locale : {}", locale.getLanguage().toUpperCase());
		this.regexContainer = RegexContainer.valueOf(locale.getLanguage().toUpperCase());
	}

	public boolean isNameCorrect(String name) {
		if (name == null) {
			return false;
		}
		LOGGER.debug("name.matches({}) : {}", name, name.matches(regexContainer.getName()));
		LOGGER.debug("name regex : {}", regexContainer.getName());
		return name.matches(regexContainer.getName());
	}

	public boolean isEmailCorrect(String email) {
		if (email == null) {
			return false;
		}
		LOGGER.debug("email.matches({}) : {}", email, email.matches(regexContainer.getEmail()));
		return email.matches(regexContainer.getEmail());
	}

	public boolean isPasswordCorrect(String password) {
		LOGGER.debug("password : {}", password);
		if (password == null || password.isEmpty()) {
			return false;
		}
		return true;
	}
}
