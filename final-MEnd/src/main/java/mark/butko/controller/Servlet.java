package mark.butko.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.command.ChangeRoleCommand;
import mark.butko.controller.command.Command;
import mark.butko.controller.command.CommentCommand;
import mark.butko.controller.command.CommentsCommand;
import mark.butko.controller.command.LeaveProposalCommand;
import mark.butko.controller.command.LoginCommand;
import mark.butko.controller.command.LogoutCommand;
import mark.butko.controller.command.ProposalsPaginationCommand;
import mark.butko.controller.command.SignUpCommand;
import mark.butko.controller.command.UserListCommand;
import mark.butko.controller.command.WelcomePageCommand;
import mark.butko.model.service.CommentService;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;

@WebServlet("/get/*")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(Servlet.class.getName());
	private Map<String, Command> commands = new HashMap<>();

	@Override
	public void init() throws ServletException {
		LOGGER.debug("init servlet");

		commands.put("login", new LoginCommand(new UserService(), new ProposalService()));
		commands.put("logout", new LogoutCommand());
		commands.put("leaveProposal", new LeaveProposalCommand(new ProposalService()));
		commands.put("comments", new CommentsCommand(new CommentService()));
		commands.put("comment", new CommentCommand(new ProposalService()));
		//
		commands.put("proposalsPagination", new ProposalsPaginationCommand(new ProposalService()));
		commands.put("proposals", new ProposalsPaginationCommand(new ProposalService()));
		//
		commands.put("sign-up", new SignUpCommand());
		commands.put("welcomePage", new WelcomePageCommand());
		commands.put("users", new UserListCommand(new UserService()));
		commands.put("change_role", new ChangeRoleCommand(new UserService()));
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

		Command command = commands.getOrDefault(commandPath, (r) -> ServletPath.WELCOME_PAGE);
		String page = command.execute(request);

		request.getServletContext()
				.getRequestDispatcher(page)
				.forward(request, response);
	}

}
