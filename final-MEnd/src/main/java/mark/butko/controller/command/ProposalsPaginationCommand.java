package mark.butko.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;
import mark.butko.model.service.ProposalService;
import mark.butko.model.service.UserService;

public class ProposalsPaginationCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(ProposalsPaginationCommand.class.getName());

	private ProposalService proposalService;
	private UserService userService = new UserService();
	private final int ROWS_ON_PAGE = 5;
	private int currentPage = 1;

	public ProposalsPaginationCommand(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	@Override
	public String execute(HttpServletRequest request) {
		Integer pageNumber;

		String page = request.getParameter("page");
		if ("previous".equals(page)) {
			pageNumber = currentPage - 1;
		} else if ("next".equals(page)) {
			pageNumber = currentPage + 1;
		} else {
			pageNumber = currentPage;
		}
		currentPage = pageNumber;

		User user = (User) request.getSession().getAttribute("user");
		Integer userId = user.getId();

		Integer offset = (pageNumber - 1) * ROWS_ON_PAGE;

		List<Proposal> proposals = proposalService.new ProposalsPageListBuilder()
				.setUserId(userId)
				.setLimit(ROWS_ON_PAGE)
				.setOffset(offset)
				.getList();

		if (proposals == null || proposals.isEmpty()) {
			proposals = proposalService.new ProposalsPageListBuilder()
					.setUserId(userId)
					.setLimit(ROWS_ON_PAGE)
					.setOffset(0)
					.getList();
			currentPage = 1;
		}

		Integer lastPage = userService.countProposals(userId) / ROWS_ON_PAGE
				+ ((userService.countProposals(userId) % ROWS_ON_PAGE) == 0 ? 0 : 1);

		request.setAttribute("proposals", proposals);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);

		LOGGER.debug("currentPage = {}, lastPage = {}", currentPage, lastPage);
		return JSPPath.PROPOSALS;
	}

}
