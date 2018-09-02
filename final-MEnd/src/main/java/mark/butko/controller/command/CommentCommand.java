package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.ServletPath;
import mark.butko.model.service.ProposalService;

public class CommentCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(CommentCommand.class.getName());
	private ProposalService proposalService;

	public CommentCommand(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String comment = request.getParameter("comment");
		String stringId = (String) request.getParameter("id");
		Integer id = Integer.parseInt(stringId);
		proposalService.comment(comment, id);

		LOGGER.info("userID = {}", id);

		redirect(request, response, ServletPath.PAGINATION);
	}
}
