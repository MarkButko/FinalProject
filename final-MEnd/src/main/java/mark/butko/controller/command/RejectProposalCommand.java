package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.Proposal;
import mark.butko.model.service.ProposalService;

public class RejectProposalCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(RejectProposalCommand.class.getName());

	private ProposalService proposalService;

	public RejectProposalCommand(ProposalService proposalService) {
		super();
		this.proposalService = proposalService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String rejectionCause = request.getParameter("rejection_cause");
		String proposalIdString = request.getParameter("proposal_id");
		String managerIdString = request.getParameter("manager_id");

		// TODO check rejectionCause is valid
		if (proposalIdString == null) {
			redirect(request, response, ServletPath.MANAGER_PAGE);
		}

		Integer proposalId = Integer.parseInt(proposalIdString);
		Integer managerId = Integer.parseInt(managerIdString);

		Proposal proposal = proposalService.getById(proposalId);
		proposal.setRejectionCause(rejectionCause);
		proposalService.reject(proposal, managerId);

		redirect(request, response, ServletPath.MANAGER_PAGE);
	}
}
