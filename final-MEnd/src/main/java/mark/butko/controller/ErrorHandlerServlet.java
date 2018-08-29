package mark.butko.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/ServerError")
public class ErrorHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ErrorHandlerServlet.class.getName());

	@Override
	public void init() throws ServletException {
		LOGGER.debug("Exception handler init");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");

		LOGGER.debug("", throwable);

		request.setAttribute("exception_type", throwable.getClass().getName());
		request.setAttribute("status_code", statusCode);
		request.setAttribute("servlet_name", servletName);

		request.getServletContext()
				.getRequestDispatcher(JSPPath.EXCEPTION)
				.forward(request, response);
	}

}
