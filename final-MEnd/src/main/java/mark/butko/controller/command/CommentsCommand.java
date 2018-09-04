package mark.butko.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.model.dto.Comment;
import mark.butko.model.service.CommentService;

/**
 * Command that is invoked if user wants to see comments. Expected request
 * parameters : currentPage, futurePage (previous or next) Forwards user to
 * {@value JSPPath#COMMENTS} page
 * 
 * @author markg
 * @see Command
 */
public class CommentsCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(CommentsCommand.class.getName());

	private CommentService commentService = CommentService.getInstance();
	private final int ROWS_ON_PAGE = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer futurePage;
		Integer currentPage;
		List<Comment> comments;
		String currentPageString = request.getParameter("currentPage");

		if (currentPageString == null || currentPageString.isEmpty()) {
			LOGGER.debug(":: currentPage = {}", currentPageString);
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(currentPageString);
		}

		String futurePageString = request.getParameter("futurePage");
		if ("previous".equals(futurePageString)) {
			futurePage = currentPage - 1;
		} else if ("next".equals(futurePageString)) {
			futurePage = currentPage + 1;
		} else {
			futurePage = currentPage;
		}
		currentPage = futurePage;

		Integer commentsAmount = commentService.countAll();
		Integer lastPage = commentsAmount / ROWS_ON_PAGE
				+ ((commentsAmount % ROWS_ON_PAGE) == 0 ? 0 : 1);

		if (lastPage > currentPage) {
			Integer offset = (currentPage - 1) * ROWS_ON_PAGE;
			comments = commentService.getCommentsPageOrderedByDate(ROWS_ON_PAGE, offset);
		} else {
			Integer offset = (lastPage - 1) * ROWS_ON_PAGE;
			comments = commentService.getCommentsPageOrderedByDate(ROWS_ON_PAGE, offset);
			currentPage = lastPage;
		}

		request.setAttribute("comments", comments);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);

		LOGGER.debug("currentPage = {}, lastPage = {}", currentPage, lastPage);
		forward(request, response, JSPPath.COMMENTS);
	}
}
