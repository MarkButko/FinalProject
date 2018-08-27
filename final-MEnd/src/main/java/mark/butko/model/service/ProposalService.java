package mark.butko.model.service;

import java.util.List;

import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.ProposalDao;
import mark.butko.model.entity.Proposal;

public class ProposalService {

	DaoFactory daoFactory = DaoFactory.getInstance();

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

	public class ProposalsListBuilder {

		private String orderProperty;
		private Integer limit;
		private Integer offset;
		private Integer userId;

		public String getOrderProperty() {
			return orderProperty;
		}

		public ProposalsListBuilder setOrderProperty(String orderProperty) {
			this.orderProperty = orderProperty;
			return this;
		}

		public Integer getLimit() {
			return limit;
		}

		public ProposalsListBuilder setLimit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Integer getOffset() {
			return offset;
		}

		public ProposalsListBuilder setOffset(Integer offset) {
			this.offset = offset;
			return this;
		}

		public Integer getUserId() {
			return userId;
		}

		public ProposalsListBuilder setUserId(Integer userId) {
			this.userId = userId;
			return this;
		}

		public List<Proposal> getList() {
			try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
				return proposalDao.findPage(userId, orderProperty, limit, offset);
			}
		};
	}

}
