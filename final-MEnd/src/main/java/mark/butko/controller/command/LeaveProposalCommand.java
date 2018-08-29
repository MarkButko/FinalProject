package mark.butko.controller.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.controller.ServletPath;
import mark.butko.model.entity.User;
import mark.butko.model.service.ProposalService;

public class LeaveProposalCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(LeaveProposalCommand.class.getName());

	private ProposalService proposalService;

	public LeaveProposalCommand(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String path = null;

		User user = (User) request.getSession().getAttribute("user");
		String message = request.getParameter("message");

		if (message == null) {
			path = JSPPath.LEAVE_PROPOSAL;
		} else {
			proposalService.createProposal(message, user.getId());
			path = ServletPath.PAGINATION;
		}
		return path;
	}

}
