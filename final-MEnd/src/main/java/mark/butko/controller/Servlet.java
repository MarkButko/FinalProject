package mark.butko.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mark.butko.controller.command.Command;
import mark.butko.controller.command.CommentCommand;
import mark.butko.controller.command.LoginCommand;
import mark.butko.controller.command.ProposalsPaginationCommand;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Command> commands = new HashMap<>();

	@Override
	public void init() throws ServletException {
		// log init
		System.out.println("init servlet");
		commands.put("login", new LoginCommand(new UserService(), new ProposalService()));
		commands.put("comment", new CommentCommand(new ProposalService()));
		commands.put("proposalsPagination", new ProposalsPaginationCommand(new ProposalService()));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("POST");
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandPath = request.getRequestURI().replaceAll(".*/", "");
		System.out.println(commandPath + " =  path new");
		Command command = commands.getOrDefault(commandPath, (r) -> "/default");
		String page = command.execute(request);

		System.out.println("servlet: page = " + page);

		request.getServletContext()
				.getRequestDispatcher(page)
				.forward(request, response);
	}

}
