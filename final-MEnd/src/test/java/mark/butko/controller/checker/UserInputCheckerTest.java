package mark.butko.controller.checker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class UserInputCheckerTest {

	@DataProvider
	public static Object[][] names() {
		return new Object[][] {
				{ "GG", false },
				{ "Valid", true },
				{ "*nn", true }
		};
	}

	@Test
	@UseDataProvider("names")
	public void isNameCorrectTest(String name, Boolean expected) {
		boolean actual = UserInputChecker.isNameCorrect(name);
		assertEquals(expected, actual);
	}

	@DataProvider
	public static Object[][] emails() {
		return new Object[][] {
				{ "invalid", false },
				{ "Valid@mail.com", true },
				{ "can`tbe@nEmail", false }
		};
	}

	@Test
	@UseDataProvider("emails")
	public void isEmailCorrectTest(String email, Boolean expected) {
		boolean actual = UserInputChecker.isEmailCorrect(email);
		assertEquals(expected, actual);
	}

	@DataProvider
	public static Object[][] passwords() {
		return new Object[][] {
				{ "1", false },
				{ "5g1", false },
				{ "valid", true }
		};
	}

	@Test
	@UseDataProvider("passwords")
	public void isPasswordCorrectTest(String password, Boolean expected) {
		boolean actual = UserInputChecker.isPasswordCorrect(password);
		assertEquals(expected, actual);
	}

	@DataProvider
	public static Object[][] prices() {
		return new Object[][] {
				{ "word", false },
				{ "-13", false },
				{ "26", true },
				{ "26,4", false },
				{ "26.5", false }
		};
	}

	@Test
	@UseDataProvider("prices")
	public void isPriceCorrectTest(String price, Boolean expected) {
		boolean actual = UserInputChecker.isPriceCorrect(price);
		assertEquals(expected, actual);
	}

}
