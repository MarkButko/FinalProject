package mark.butko.controller.command;

import javax.servlet.http.HttpServletRequest;

import mark.butko.controller.JSPPath;
import mark.butko.controller.ServletPath;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;
import mark.butko.model.service.exception.ProposalCanNotBePerformedException;
import mark.butko.model.service.exception.ProposalDoesNotExistsException;

public class PerformProposalCommand implements Command {

	ProposalService proposalService;
	UserService userService;

	public PerformProposalCommand(ProposalService proposalService, UserService userService) {
		super();
		this.proposalService = proposalService;
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = ServletPath.MASTER_PAGE;

		String proposalIdString = request.getParameter("proposal_id");
		String masterIdString = request.getParameter("master_id");

		if (proposalIdString == null ||
				masterIdString == null) {
			page = JSPPath.EXCEPTION;
		}

		Integer proposalId = Integer.parseInt(proposalIdString);
		Integer masterId = Integer.parseInt(masterIdString);
		try {
			proposalService.perform(proposalId, masterId);
		} catch (ProposalDoesNotExistsException | ProposalCanNotBePerformedException e) {
			request.setAttribute("error_message", "Failed perform proposal");
		}
		return page;
	}
}
