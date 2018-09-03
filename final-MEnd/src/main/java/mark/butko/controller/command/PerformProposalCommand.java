package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mark.butko.controller.path.JSPPath;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;
import mark.butko.model.service.exception.ProposalCanNotBePerformedException;
import mark.butko.model.service.exception.ProposalDoesNotExistsException;

public class PerformProposalCommand implements Command {

	ProposalService proposalService = ProposalService.getInstance();
	UserService userService = UserService.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String proposalIdString = request.getParameter("proposal_id");
		String masterIdString = request.getParameter("master_id");

		if (proposalIdString == null ||
				masterIdString == null) {
			forward(request, response, JSPPath.EXCEPTION);
		}

		Integer proposalId = Integer.parseInt(proposalIdString);
		Integer masterId = Integer.parseInt(masterIdString);
		try {
			proposalService.perform(proposalId, masterId);
		} catch (ProposalDoesNotExistsException | ProposalCanNotBePerformedException e) {
			request.setAttribute("error_message", "Failed perform proposal");
		}
		redirect(request, response, ServletPath.MASTER_PAGE);
	}
}
