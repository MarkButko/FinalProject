package mark.butko.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.JSPPath;
import mark.butko.dto.Comment;
import mark.butko.model.service.CommentService;

public class CommentsCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(CommentsCommand.class.getName());

	private CommentService commentService;
	private final int ROWS_ON_PAGE = 5;
	private int currentPage = 1;

	public CommentsCommand(CommentService commentService) {
		this.commentService = commentService;
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

		Integer offset = (pageNumber - 1) * ROWS_ON_PAGE;

		List<Comment> comments = commentService.getCommentsPageOrderedByDate(ROWS_ON_PAGE, offset);

		if (comments == null || comments.isEmpty()) {
			comments = commentService.getCommentsPageOrderedByDate(ROWS_ON_PAGE, 0);
			currentPage = 1;
		}

		Integer lastPage = commentService.countAll() / ROWS_ON_PAGE
				+ ((commentService.countAll() % ROWS_ON_PAGE) == 0 ? 0 : 1);

		request.setAttribute("comments", comments);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("lastPage", lastPage);

		LOGGER.debug("currentPage = {}, lastPage = {}", currentPage, lastPage);
		return JSPPath.COMMENTS;
	}
}
