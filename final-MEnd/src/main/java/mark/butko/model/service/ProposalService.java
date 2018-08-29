package mark.butko.model.service;

import java.util.List;

import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.ProposalDao;
import mark.butko.model.dao.UserDao;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;

public class ProposalService {

	DaoFactory daoFactory = DaoFactory.getInstance();

	public void createProposal(String message, Integer userId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao();
				UserDao userDao = daoFactory.createUserDao()) {

			User user = userDao.findById(userId);
			Proposal proposal = new Proposal(user, message);

			proposalDao.create(proposal);
		}
	}

	public void comment(String comment, Integer id) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			Proposal proposal = proposalDao.findById(id);
			proposal.setComment(comment);
			proposalDao.update(proposal);
		}
	}

	public List<Proposal> getProposalsByUserId(Integer userId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findByUserId(userId);
		}
	}

	public Integer countAll() {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.countAll();
		}
	}

	public class ProposalsPageListBuilder {

		private String orderProperty;
		private Integer limit;
		private Integer offset;
		private Integer userId;

		public String getOrderProperty() {
			return orderProperty;
		}

		public ProposalsPageListBuilder setOrderProperty(String orderProperty) {
			this.orderProperty = orderProperty;
			return this;
		}

		public Integer getLimit() {
			return limit;
		}

		public ProposalsPageListBuilder setLimit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Integer getOffset() {
			return offset;
		}

		public ProposalsPageListBuilder setOffset(Integer offset) {
			this.offset = offset;
			return this;
		}

		public Integer getUserId() {
			return userId;
		}

		public ProposalsPageListBuilder setUserId(Integer userId) {
			this.userId = userId;
			return this;
		}

		public List<Proposal> getList() {
			try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
				return proposalDao.findProposalsPage(userId, orderProperty, limit, offset);
			}
		};
	}

}
