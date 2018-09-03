package mark.butko.controller.command;

import static mark.butko.testUtil.TestEntityCreator.getUser;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.User;

@RunWith(DataProviderRunner.class)
public class LoginCommandTest {

	public static LoginCommand loginCommand = spy(new LoginCommand());

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	ServletContext servletContext;

	@Mock
	HttpSession session;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doNothing().when(loginCommand)
				.forward(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
		doNothing().when(loginCommand)
				.redirect(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
		when(request.getServletContext()).thenReturn(servletContext);
		when(servletContext.getAttribute("loggedUsers")).thenReturn(loggedUsers);
		when(request.getSession()).thenReturn(session);
	}

	@DataProvider
	public static Object[][] empties() {
		return new Object[][] {
				{ null, "password", },
				{ "", "password" },
				{ "email@m.com", null },
				{ "email@m.com", "" }
		};
	}

	@UseDataProvider("empties")
	@Test
	public void empty_parameters_then_forward_to_login(String email, String password)
			throws ServletException, IOException {
		when(request.getParameter("email")).thenReturn(email);
		when(request.getParameter("password")).thenReturn(password);
		loginCommand.execute(request, response);
		verify(loginCommand).forward(request, response, JSPPath.LOGIN);
	}

	@DataProvider
	public static Object[][] logged() {
		return new Object[][] {
				{ "email@m.com", "password", },
				{ "anotherEmail@m.com", "password" },
		};
	}

	public static Set<String> loggedUsers = new HashSet<>(
			Arrays.asList("email@m.com", "anotherEmail@m.com"));

	@UseDataProvider("logged")
	@Test
	public void logged_user_access_from_another_browser_then_reject_login(String email, String password)
			throws ServletException, IOException {
		when(request.getParameter("email")).thenReturn(email);
		when(request.getParameter("password")).thenReturn(password);

		loginCommand.execute(request, response);
		verify(loginCommand).forward(request, response, JSPPath.LOGIN);
		verify(loginCommand, never()).redirect(request, response, ServletPath.WELCOME_PAGE);
	}

	@DataProvider
	public static Object[][] notLogged() {
		return new Object[][] {
				{ "notLogged@m.com", "password" }
		};
	}

	@UseDataProvider("notLogged")
	@Test
	public void logged_user_access_from_current_browser_but_another_tab_then_welcome_page(String email, String password)
			throws ServletException, IOException {
		when(request.getParameter("email")).thenReturn(email);
		when(request.getParameter("password")).thenReturn(password);
		when(session.getAttribute("user")).thenReturn(getUser(email, password, User.Role.CUSTOMER));
		loginCommand.execute(request, response);
		verify(loginCommand, never()).forward(request, response, JSPPath.LOGIN);
		verify(loginCommand).redirect(request, response, ServletPath.WELCOME_PAGE);
	}
}
