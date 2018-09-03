package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.checker.UserInputChecker;
import mark.butko.controller.path.ServletPath;
import mark.butko.model.entity.Proposal;
import mark.butko.model.service.ProposalService;

public class AcceptProposalCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(AcceptProposalCommand.class.getName());

	private ProposalService proposalService = ProposalService.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String priceString = request.getParameter("price");
		String proposalIdString = request.getParameter("proposal_id");
		String managerIdString = request.getParameter("manager_id");

		if (UserInputChecker.isPriceCorrect(priceString)) {
			Integer price = Integer.parseInt(priceString);
			Integer proposalId = Integer.parseInt(proposalIdString);
			Integer managerId = Integer.parseInt(managerIdString);

			Proposal proposal = proposalService.getById(proposalId);
			proposal.setPrice(price.longValue());
			proposalService.accept(proposal, managerId);
		}
		redirect(request, response, ServletPath.MANAGER_PAGE);
	}

}
