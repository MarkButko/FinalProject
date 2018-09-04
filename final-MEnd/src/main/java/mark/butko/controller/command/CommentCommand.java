package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.ServletPath;
import mark.butko.model.service.ProposalService;

/**
 * Command that is invoked if user wants to leave comment. Expected request
 * parameters : id, comment, manager_id. Redirects user to
 * {@value ServletPath#CUSTOMER_PROPOSALS} page
 * 
 * @author markg
 * @see Command
 */
public class CommentCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(CommentCommand.class.getName());
	private ProposalService proposalService = ProposalService.getInstance();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String comment = request.getParameter("comment");
		String stringId = (String) request.getParameter("id");
		Integer id = Integer.parseInt(stringId);
		proposalService.comment(comment, id);

		LOGGER.info("userID = {}", id);

		redirect(request, response, ServletPath.CUSTOMER_PROPOSALS);
	}
}
