package mark.butko.model.dao.impl;

import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.COUNT_ALL;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.FIND_ALL;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.FIND_ALL_JOIN_ON_CUSTOMER;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.FIND_ALL_JOIN_ON_MANAGER;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.FIND_ALL_LEFT_JOIN_ON_CUSTOMER;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.FIND_ALL_LEFT_JOIN_ON_MANAGER;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.FIND_ALL_LEFT_JOIN_ON_MASTER;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.ORDER_BY;
import static mark.butko.model.dao.impl.query.ProposalMySQLQuery.WHERE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.criteria.CriteriaUtil;
import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.PageCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.dao.ProposalDao;
import mark.butko.model.dao.impl.column.ProposalColumn;
import mark.butko.model.dao.impl.query.ProposalMySQLQuery;
import mark.butko.model.dao.mapper.CommentMapper;
import mark.butko.model.dao.mapper.ProposalMapper;
import mark.butko.model.dao.mapper.UserMapper;
import mark.butko.model.dto.Comment;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.Proposal.Status;
import mark.butko.model.entity.User;

public class JDBCProposalDao implements ProposalDao {

	private static final Logger LOGGER = LogManager.getLogger(JDBCProposalDao.class.getName());

	private Connection connection;

	public JDBCProposalDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Proposal> findByFiltersSortedList(List<? extends FilterCriteria> filters, SortCriteria order) {
		List<Proposal> list = new ArrayList<>();

		String query = FIND_ALL + (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters)
				+ ORDER_BY + order.getSQLString();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			CriteriaUtil.setParameters(filters, statement, 1);
			ResultSet resultSet = statement.executeQuery();

			ProposalMapper mapper = new ProposalMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.warn("SQL exception on query : {}", query);
		}
		return list;
	}

	@Override
	public List<Proposal> findMasterList(List<? extends FilterCriteria> filters, SortCriteria sortCriteria) {
		List<Proposal> list = new ArrayList<>();

		String queryWithCustomers = FIND_ALL_JOIN_ON_CUSTOMER
				+ (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters)
				+ (sortCriteria == null ? "" : ORDER_BY)
				+ sortCriteria.getSQLString();
		LOGGER.debug("findMasterList: queryWithCustomers : {}", queryWithCustomers);

		String queryWithManagers = FIND_ALL_JOIN_ON_MANAGER
				+ (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters)
				+ (sortCriteria == null ? "" : ORDER_BY)
				+ sortCriteria.getSQLString();
		LOGGER.debug("findMasterList: queryWithManagers : {}", queryWithManagers);

		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.warn("findMasterList : Connection autoCommit failed while setting to false");
		}

		try (PreparedStatement statementCustomers = connection.prepareStatement(queryWithCustomers);
				PreparedStatement statementManagers = connection.prepareStatement(queryWithManagers)) {

			CriteriaUtil.setParameters(filters, statementCustomers, 1);
			CriteriaUtil.setParameters(filters, statementManagers, 1);

			ResultSet resultSetWithCustomers = statementCustomers.executeQuery();
			ResultSet resultSetWithManagers = statementManagers.executeQuery();

			LOGGER.debug("findMasterList : executed");

			ProposalMapper proposalMapper = new ProposalMapper();
			UserMapper userMapper = new UserMapper();

			while (resultSetWithCustomers.next()
					& resultSetWithManagers.next()) {
				Proposal proposal = proposalMapper.extractFromResultSet(resultSetWithCustomers);

				User customer = userMapper.extractFromResultSet(resultSetWithCustomers);
				User manager = userMapper.extractFromResultSet(resultSetWithManagers);

				proposal.setAuthor(customer);
				proposal.setManager(manager);

				list.add(proposal);
			}

			connection.commit();
			LOGGER.debug("findMasterList : commited");
			connection.setAutoCommit(true);
			LOGGER.debug("findMasterList : autocom true ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.warn("findMasterList : Rollback failed ");
			}
			LOGGER.warn("findMasterList : SQL exception on queries : {} || {}", queryWithCustomers, queryWithManagers);
		}
		return list;
	}

	@Override
	public List<Proposal> findPage(List<? extends FilterCriteria> filters, SortCriteria sortCriteria,
			PageCriteria pageCriteria) {
		Map<Integer, Proposal> proposals = new LinkedHashMap<>();
		Map<Integer, User> users = new HashMap<>();

		String queryWithCustomers = FIND_ALL_LEFT_JOIN_ON_CUSTOMER
				+ (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters)
				+ (sortCriteria == null ? "" : ORDER_BY)
				+ sortCriteria.getSQLString()
				+ (pageCriteria == null ? "" : pageCriteria.getSQLString());
		LOGGER.debug("findPage: queryWithCustomers : {}", queryWithCustomers);

		String queryWithManagers = FIND_ALL_LEFT_JOIN_ON_MANAGER
				+ (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters)
				+ (sortCriteria == null ? "" : ORDER_BY)
				+ sortCriteria.getSQLString()
				+ (pageCriteria == null ? "" : pageCriteria.getSQLString());
		LOGGER.debug("findPage: queryWithManagers : {}", queryWithManagers);

		String queryWithMasters = FIND_ALL_LEFT_JOIN_ON_MASTER
				+ (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters)
				+ (sortCriteria == null ? "" : ORDER_BY)
				+ sortCriteria.getSQLString()
				+ (pageCriteria == null ? "" : pageCriteria.getSQLString());
		LOGGER.debug("findPage: queryWithManagers : {}", queryWithMasters);

		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			LOGGER.warn("findPage : Connection autoCommit failed while setting to false");
		}

		try (PreparedStatement statementCustomers = connection.prepareStatement(queryWithCustomers);
				PreparedStatement statementManagers = connection.prepareStatement(queryWithManagers);
				PreparedStatement statementMasters = connection.prepareStatement(queryWithMasters)) {
			LOGGER.debug("findPage : prepared");

			CriteriaUtil.setParameters(filters, pageCriteria, statementCustomers, 1);
			CriteriaUtil.setParameters(filters, pageCriteria, statementManagers, 1);
			CriteriaUtil.setParameters(filters, pageCriteria, statementMasters, 1);
			LOGGER.debug("findPage : parameters set");

			ResultSet resultSetWithCustomers = statementCustomers.executeQuery();
			ResultSet resultSetWithManagers = statementManagers.executeQuery();
			ResultSet resultSetWithMasters = statementMasters.executeQuery();
			LOGGER.debug("findPage : executed ");

			ProposalMapper proposalMapper = new ProposalMapper();
			UserMapper userMapper = new UserMapper();

			while (resultSetWithCustomers.next()) {
				Proposal proposal = proposalMapper.extractFromResultSet(resultSetWithCustomers);
				User customer = userMapper.extractFromResultSet(resultSetWithCustomers);

				proposal = proposalMapper.makeUnique(proposals, proposal);
				customer = userMapper.makeUnique(users, customer);
				LOGGER.debug("findPage : customer.hash = {} ", System.identityHashCode(customer));

				proposal.setAuthor(customer);
				proposals.put(proposal.getId(), proposal);
			}

			while (resultSetWithManagers.next()) {
				Integer proposalId = resultSetWithManagers.getInt(ProposalColumn.ID);
				User manager = userMapper.extractFromResultSet(resultSetWithManagers);

				if (manager != null) {
					manager = userMapper.makeUnique(users, manager);
					LOGGER.debug("findPage : manager.hash = {} ", System.identityHashCode(manager));
					proposals.get(proposalId).setManager(manager);
				}
			}

			while (resultSetWithMasters.next()) {
				Integer proposalId = resultSetWithMasters.getInt(ProposalColumn.ID);
				User master = userMapper.extractFromResultSet(resultSetWithMasters);

				if (master != null) {
					master = userMapper.makeUnique(users, master);
					LOGGER.debug("findPage : master.hash = {} ", System.identityHashCode(master));
					proposals.get(proposalId).setMaster(master);
				}
			}

			connection.commit();
			LOGGER.debug("findMasterList : commited");
			connection.setAutoCommit(true);
			LOGGER.debug("findMasterList : autocom true ");
		} catch (SQLException e) {
			LOGGER.warn("findMasterList : SQL exception on during transaction");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.warn("findMasterList : Rollback failed ");
			}
		}
		return new ArrayList<>(proposals.values());
	}

	@Override
	public int create(Proposal proposal) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.CREATE)) {
			statement.setString(1, proposal.getMessage());
			statement.setDate(2, Date.valueOf(proposal.getDate()));
			statement.setInt(3, proposal.getStatus().getDbValue());
			statement.setInt(4, proposal.getAuthor().getId());
			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public Proposal findById(int id) {
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.FIND_BY_ID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			ProposalMapper mapper = new ProposalMapper();
			if (resultSet.next()) {
				return mapper.extractFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return null;
	}

	@Override
	public List<Proposal> findAll() {
		List<Proposal> list = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.FIND_ALL)) {
			ResultSet resultSet = statement.executeQuery();
			ProposalMapper mapper = new ProposalMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return list;
	}

	@Override
	public int delete(int id) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.DELETE)) {
			statement.setInt(1, id);
			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public Integer addComment(int id, String comment) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.COMMENT)) {
			statement.setString(1, comment);
			statement.setInt(2, id);
			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public Integer reject(Proposal proposal, Integer managerId) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.REJECT)) {
			statement.setString(1, proposal.getRejectionCause());
			statement.setInt(2, managerId);
			statement.setInt(3, Proposal.Status.REJECTED.getDbValue());
			statement.setInt(4, proposal.getId());
			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public void accept(Proposal proposal, Integer managerId) {
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.ACCEPT)) {
			statement.setLong(1, proposal.getPrice());
			statement.setInt(2, managerId);
			statement.setInt(3, Proposal.Status.ACCEPTED.getDbValue());
			statement.setInt(4, proposal.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
	}

	@Override
	public int update(Proposal proposal) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.UPDATE)) {

			statement.setString(1, proposal.getMessage());
			statement.setInt(2, proposal.getStatus().getDbValue());
			statement.setString(3, proposal.getRejectionCause());
			statement.setString(4, proposal.getComment());
			statement.setLong(5, proposal.getPrice());

			if (proposal.getMaster() != null) {
				statement.setInt(6, proposal.getMaster().getId());
				LOGGER.debug("update : Master ID set : {}", proposal.getMaster().getId());
			} else {
				statement.setNull(6, Types.INTEGER);
			}
			if (proposal.getManager() != null) {
				statement.setInt(7, proposal.getManager().getId());
				LOGGER.debug("update : Manager ID set : {}", proposal.getManager().getId());
			} else {
				statement.setNull(7, Types.INTEGER);
			}

			statement.setInt(8, proposal.getId());
			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public List<Proposal> findByUserId(Integer userId) {
		List<Proposal> list = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.FIND_BY_USER_ID)) {
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			ProposalMapper mapper = new ProposalMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Comment> findCommentsPage(String orderProperty, Integer limit, Integer offset) {
		List<Comment> list = new ArrayList<>();

		if (orderProperty == null || orderProperty.isEmpty()) {
			orderProperty = ProposalColumn.DATE;
		}

		LOGGER.debug("findCommentsPage :: orderProp = " + orderProperty
				+ " limit = " + limit + " offset = " + offset);

		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.FIND_COMMENTS_PAGE)) {
			statement.setString(1, orderProperty);
			statement.setInt(2, limit);
			statement.setInt(3, offset);
			ResultSet resultSet = statement.executeQuery();

			CommentMapper mapper = new CommentMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer countAll() {
		try (PreparedStatement preparedStatement = connection.prepareStatement(ProposalMySQLQuery.COUNT_ALL)) {
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer countByStatus(Status status) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(ProposalMySQLQuery.COUNT_BY_STATUS)) {
			preparedStatement.setInt(1, status.getDbValue());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer countPages(List<? extends FilterCriteria> filters) {
		String query = COUNT_ALL
				+ (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLStringUsingAnd(filters);

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			CriteriaUtil.setParameters(filters, preparedStatement, 1);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer countComments() {
		try (PreparedStatement preparedStatement = connection.prepareStatement(ProposalMySQLQuery.COUNT_COMMENTS)) {
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Proposal> findProposalsPage(Integer userId, String orderProperty, Integer limit, Integer offset) {
		List<Proposal> list = new ArrayList<>();

		if (orderProperty == null || orderProperty.isEmpty()) {
			orderProperty = ProposalColumn.DATE;
		}

		LOGGER.debug("findProposalsPage :: userId = " + userId + " orderProp = " + orderProperty
				+ " limit = " + limit + " offset = " + offset);

		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.FIND_PROPOSALS_PAGE)) {
			statement.setInt(1, userId);
			statement.setString(2, orderProperty);
			statement.setInt(3, limit);
			statement.setInt(4, offset);
			ResultSet resultSet = statement.executeQuery();

			ProposalMapper mapper = new ProposalMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
