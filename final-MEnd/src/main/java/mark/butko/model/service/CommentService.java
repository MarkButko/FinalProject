package mark.butko.model.service;

import java.util.List;

import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.ProposalDao;
import mark.butko.model.dto.Comment;

public class CommentService {
	private static final CommentService INSTANCE = new CommentService();
	private DaoFactory daoFactory = DaoFactory.getInstance();

	private CommentService() {
	};

	public static CommentService getInstance() {
		return INSTANCE;
	}

	public List<Comment> getCommentsPageOrderedByDate(Integer limit, Integer offset) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findCommentsPage("date", limit, offset);
		}
	}

	public Integer countAll() {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.countComments();
		}
	}
}
