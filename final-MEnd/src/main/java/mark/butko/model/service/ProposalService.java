package mark.butko.model.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.PageCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.ProposalDao;
import mark.butko.model.dao.UserDao;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.Proposal.Status;
import mark.butko.model.entity.User;
import mark.butko.model.service.exception.ProposalCanNotBePerformedException;
import mark.butko.model.service.exception.ProposalDoesNotExistsException;

public class ProposalService {

	private static final Logger LOGGER = LogManager.getLogger(ProposalService.class.getName());
	DaoFactory daoFactory = DaoFactory.getInstance();

	public void createProposal(String message, Integer userId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao();
				UserDao userDao = daoFactory.createUserDao()) {

			User user = userDao.findByIdWithProposals(userId);
			Proposal proposal = new Proposal(user, message);

			proposalDao.create(proposal);
		}
	}

	public void comment(String comment, Integer id) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			proposalDao.addComment(id, comment);
		}
	}

	public Proposal getById(Integer proposalId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findById(proposalId);
		}
	}

	public void reject(Proposal proposal, Integer managerId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao();
				UserDao userDao = daoFactory.createUserDao()) {
			proposalDao.reject(proposal, managerId);
		}
	}

	public List<Proposal> getProposalsByUserId(Integer userId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findByUserId(userId);
		}
	}

	public void accept(Proposal proposal, Integer managerId) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao();
				UserDao userDao = daoFactory.createUserDao()) {
			proposalDao.accept(proposal, managerId);
		}
	}

	public Integer countAll() {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.countAll();
		}
	}

	public List<Proposal> getByFiltersSortedList(List<? extends FilterCriteria> filters, SortCriteria order) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findByFiltersSortedList(filters, order);
		}
	}

	public List<Proposal> getMasterList(List<FilterCriteria> filters, SortCriteria sortCriteria) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findMasterList(filters, sortCriteria);
		}
	}

	public List<Proposal> getPage(List<FilterCriteria> filters, SortCriteria sortCriteria, PageCriteria pageCriteria) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.findPage(filters, sortCriteria, pageCriteria);
		}
	}

	public Integer countByStatus(Status status) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.countByStatus(status);
		}
	}

	public Integer countPages(List<FilterCriteria> filters) {
		try (ProposalDao proposalDao = daoFactory.createProposalDao()) {
			return proposalDao.countPages(filters);
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

	public void perform(Integer proposalId, Integer masterId)
			throws ProposalDoesNotExistsException, ProposalCanNotBePerformedException {
		try (ProposalDao proposalDao = daoFactory.createProposalDao();
				UserDao userDao = daoFactory.createUserDao()) {

			Proposal proposal = proposalDao.findById(proposalId);
			if (proposal == null) {
				LOGGER.warn("perform: Not performed proposal = null");
				throw new ProposalDoesNotExistsException("id " + proposalId);
			}
			if (proposal.getStatus() != Proposal.Status.ACCEPTED) {
				LOGGER.warn("perform: Not performed proposal.status = {}, id = {}", proposal.getStatus(),
						proposal.getId());
				throw new ProposalCanNotBePerformedException("id " + proposalId + " : status " + proposal.getStatus());
			}
			proposal.setStatus(Proposal.Status.COMPLETED);

			User customer = userDao.findCustomerByProposalId(proposalId);
			Optional<User> manager = userDao.findManagerByProposalId(proposalId);
			User master = userDao.findById(masterId);

			proposal.setAuthor(customer);
			if (manager.isPresent()) {
				proposal.setManager(manager.get());
				LOGGER.debug("perform: Manager set {}", manager.get());
			}
			if (master != null) {
				proposal.setMaster(master);
				LOGGER.debug("perform : Master set : {}", master);
			}
			proposalDao.update(proposal);
			userDao.withdraw(proposal.getPrice(), customer);
		}
	}
}
