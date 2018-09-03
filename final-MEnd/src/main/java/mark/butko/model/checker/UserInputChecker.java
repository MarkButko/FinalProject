package mark.butko.model.checker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that holds regular expressions to match against user input and methods
 * that should be used with input fields corresponding to names of these methods
 * 
 * @author markg
 *
 */
public class UserInputChecker {

	private static final Logger LOGGER = LogManager.getLogger(UserInputChecker.class.getName());

	private static final String NAME_REGEX = "^[\\w\\W]{3,20}$";
	private static final String PASSWORD_REGEX = "^[\\w\\W]{4,30}$";
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	private static final String PRICE_REGEX = "[\\d]+";

	/**
	 * Checks if price was typed by user correctly
	 *
	 * @param string representation of price
	 * @return true if passed as parameter string can be parsed to positive whole
	 *         number
	 */
	public static boolean isPriceCorrect(String priceString) {
		if (priceString == null) {
			return false;
		}
		LOGGER.debug("price.matches({}) : {}", priceString, priceString.matches(PRICE_REGEX));
		return priceString.matches(PRICE_REGEX);
	}

	/**
	 * Checks if name was typed by user correctly
	 *
	 * @param user name
	 * @return true if passed as parameter string is valid name
	 */
	public static boolean isNameCorrect(String name) {
		if (name == null) {
			return false;
		}
		LOGGER.debug("name.matches({}) : {}", name, name.matches(NAME_REGEX));
		return name.matches(NAME_REGEX);
	}

	/**
	 * Checks if email was typed by user correctly
	 *
	 * @param user email
	 * @return true if passed as parameter string is valid email
	 */
	public static boolean isEmailCorrect(String email) {
		if (email == null) {
			return false;
		}
		LOGGER.debug("email.matches({}) : {}", email, email.matches(EMAIL_REGEX));
		return email.matches(EMAIL_REGEX);
	}

	/**
	 * Checks if password is strong enough
	 *
	 * @param user password
	 * @return true if passed as parameter string can be safely used as password
	 */
	public static boolean isPasswordCorrect(String password) {
		LOGGER.debug("password : {}", password);
		if (password == null || password.isEmpty()) {
			return false;
		}
		return password.matches(PASSWORD_REGEX);
	}
}
