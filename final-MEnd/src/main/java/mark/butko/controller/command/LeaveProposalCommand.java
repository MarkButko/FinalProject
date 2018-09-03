package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.service.ProposalService;

public class LeaveProposalCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(LeaveProposalCommand.class.getName());

	private ProposalService proposalService = ProposalService.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");
		String message = request.getParameter("message");

		if (message == null) {
			forward(request, response, JSPPath.LEAVE_PROPOSAL);
		} else {
			proposalService.createProposal(message, user.getId());
			redirect(request, response, ServletPath.PAGINATION);
		}
	}

}
