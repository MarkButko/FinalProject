package mark.butko.model.service;

import java.util.List;

import mark.butko.dto.Comment;
import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.ProposalDao;

public class CommentService {

	DaoFactory daoFactory = DaoFactory.getInstance();

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
