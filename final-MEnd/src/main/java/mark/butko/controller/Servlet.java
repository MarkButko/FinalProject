package mark.butko.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.command.Command;
import mark.butko.controller.command.CommandProvider;

@WebServlet("/get/*")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(Servlet.class.getName());

	@Override
	public void init() throws ServletException {
		LOGGER.debug("init servlet");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("doGet : {}", request.getRequestURI());
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("doPost : {}", request.getRequestURI());
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandPath = request.getRequestURI().replaceAll(".*/", "");
		LOGGER.debug("commandPath = {}", commandPath);

		Command command = CommandProvider.getCommandOrWelcomePage(commandPath);
		command.execute(request, response);
	}

}
