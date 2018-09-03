package mark.butko.controller.filter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
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
import mark.butko.testUtil.TestEntityCreator;

@RunWith(DataProviderRunner.class)
public class AccessFilterTest {

	public static AccessFilter accessFilter = spy(new AccessFilter());

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Mock
	HttpSession session;

	@Mock
	FilterChain chain;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doNothing().when(chain)
				.doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
		when(request.getSession()).thenReturn(session);
	}

	@DataProvider
	public static Object[][] omittedPath() {
		return new Object[][] {
				{ ServletPath.SERVLET_CONTEXT + "/some.css" },
				{ ServletPath.SERVLET_CONTEXT + "/some.jpg" },
				{ ServletPath.SERVLET_CONTEXT + "/some.gif" },
				{ ServletPath.SERVLET_CONTEXT + "/some.ttf" },
		};
	}

	@Test
	@UseDataProvider("omittedPath")
	public void omit_if_resource_file(String path) throws IOException, ServletException {
		when(request.getRequestURI()).thenReturn(path);
		accessFilter.doFilter(request, response, chain);
		verify(chain).doFilter(request, response);
	}

	@DataProvider
	public static Object[][] restrictedPath() {
		return new Object[][] {
				{ ServletPath.SERVLET_CONTEXT + "/some.jsp" },
				{ ServletPath.SERVLET_CONTEXT + "/someOther.jpg.jsp" },
		};
	}

	@Test
	@UseDataProvider("restrictedPath")
	public void restrict_if_jsp_file(String path) throws IOException, ServletException {
		when(request.getRequestURI()).thenReturn(path);
		accessFilter.doFilter(request, response, chain);
		verify(chain, never()).doFilter(request, response);
	}

	@DataProvider
	public static Object[][] restrictedPathForRole() {
		return new Object[][] {
				{ ServletPath.SERVLET_CONTEXT + JSPPath.ADMIN_PAGE, User.Role.CUSTOMER },
				{ ServletPath.SERVLET_CONTEXT + JSPPath.MANAGER_PAGE, User.Role.ADMIN },
				{ ServletPath.SERVLET_CONTEXT + JSPPath.LEAVE_PROPOSAL, User.Role.MANAGER }
		};
	}

	@Test
	@UseDataProvider("restrictedPathForRole")
	public void restrict_access_to_path_for_role(String path, User.Role role) throws IOException, ServletException {
		when(request.getRequestURI()).thenReturn(path);
		when(session.getAttribute("user")).thenReturn(TestEntityCreator.getUser(role));
		accessFilter.doFilter(request, response, chain);
		verify(chain, never()).doFilter(request, response);
	}

}
