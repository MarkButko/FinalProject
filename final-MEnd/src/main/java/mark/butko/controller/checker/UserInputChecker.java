package mark.butko.controller.checker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInputChecker {

	private static final Logger LOGGER = LogManager.getLogger(UserInputChecker.class.getName());

	private static final String NAME_REGEX = "^[\\w\\W]{3,20}";
	private static final String PASSWORD_REGEX = "^[\\w\\W]{4,30}$";
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	private static final String PRICE_REGEX = "[\\d]+";

	public static boolean isPriceCorrect(String priceString) {
		if (priceString == null) {
			return false;
		}
		LOGGER.debug("price.matches({}) : {}", priceString, priceString.matches(PRICE_REGEX));
		return priceString.matches(PRICE_REGEX);
	}

	public static boolean isNameCorrect(String name) {
		if (name == null) {
			return false;
		}
		LOGGER.debug("name.matches({}) : {}", name, name.matches(NAME_REGEX));
		return name.matches(NAME_REGEX);
	}

	public static boolean isEmailCorrect(String email) {
		if (email == null) {
			return false;
		}
		LOGGER.debug("email.matches({}) : {}", email, email.matches(EMAIL_REGEX));
		return email.matches(EMAIL_REGEX);
	}

	public static boolean isPasswordCorrect(String password) {
		LOGGER.debug("password : {}", password);
		if (password == null || password.isEmpty()) {
			return false;
		}
		return password.matches(PASSWORD_REGEX);
	}
}
