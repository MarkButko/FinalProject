package mark.butko.model.dao.impl;

import static mark.butko.model.dao.impl.UserMySQLQuery.FIND_ALL;
import static mark.butko.model.dao.impl.UserMySQLQuery.ORDER_BY;
import static mark.butko.model.dao.impl.UserMySQLQuery.WHERE;
import static mark.butko.model.dao.impl.UserMySQLQuery.WITHDRAW;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.criteria.CriteriaUtil;
import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.dao.UserDao;
import mark.butko.model.dao.mapper.ProposalMapper;
import mark.butko.model.dao.mapper.UserMapper;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;

public class JDBCUserDao implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(JDBCUserDao.class.getName());
	private Connection connection;

	public JDBCUserDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Integer countProposals(Integer userId) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(UserMySQLQuery.COUNT_PROPOSALS)) {
			preparedStatement.setInt(1, userId);
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
	public Integer withdraw(Long price, User customer) {
		Integer rowsAffected = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(WITHDRAW)) {
			preparedStatement.setLong(1, price);
			preparedStatement.setInt(2, customer.getId());
			rowsAffected = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.warn("Withdraw failed for customer {} : on sum  {}", customer.getEmail(), price);
		}
		return rowsAffected;
	}

	@Override
	public Optional<User> findByEmail(String email) {

		User user = null;
		Proposal proposal;

		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.FIND_BY_EMAIL)) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();

			UserMapper userMapper = new UserMapper();
			ProposalMapper proposalMapper = new ProposalMapper();

			if (resultSet.next()) {
				user = userMapper.extractFromResultSet(resultSet);
				do {
					proposal = proposalMapper.extractFromResultSet(resultSet);
					user.getProposals().add(proposal);
				} while (resultSet.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return Optional.ofNullable(user);
	}

	@Override
	public User findCustomerByProposalId(Integer proposalId) {
		User user = null;

		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.FIND_CUSTOMER_BY_PROPOSAL_ID)) {
			statement.setInt(1, proposalId);
			ResultSet resultSet = statement.executeQuery();

			UserMapper userMapper = new UserMapper();
			if (resultSet.next()) {
				user = userMapper.extractFromResultSet(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Optional<User> findManagerByProposalId(Integer proposalId) {
		User manager = null;

		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.FIND_MANAGER_BY_PROPOSAL_ID)) {
			statement.setInt(1, proposalId);
			ResultSet resultSet = statement.executeQuery();

			UserMapper userMapper = new UserMapper();
			if (resultSet.next()) {
				manager = userMapper.extractFromResultSet(resultSet);
			}
			LOGGER.debug("findManagerByProposalId : manager {}", manager);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(manager);
	}

	@Override
	public Optional<User> findMasterByProposalId(Integer proposalId) {
		User user = null;

		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.FIND_MASTER_BY_PROPOSAL_ID)) {
			statement.setInt(1, proposalId);
			ResultSet resultSet = statement.executeQuery();

			UserMapper userMapper = new UserMapper();
			if (resultSet.next()) {
				user = userMapper.extractFromResultSet(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(user);
	}

	@Override
	public int create(User user) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.CREATE)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setLong(4, user.getMoney());
			statement.setDate(5, Date.valueOf(user.getRegistrationDate()));
			statement.setInt(6, user.getRole().getDBValue());

			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public User findById(int id) {

		User user = null;
		Proposal proposal;

		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.FIND_BY_ID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			ProposalMapper proposalMapper = new ProposalMapper();
			UserMapper userMapper = new UserMapper();

			if (resultSet.next()) {
				user = userMapper.extractFromResultSet(resultSet);
				do {
					proposal = proposalMapper.extractFromResultSet(resultSet);
					user.getProposals().add(proposal);
				} while (resultSet.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();// log
		}

		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.FIND_ALL)) {
			ResultSet resultSet = statement.executeQuery();
			UserMapper mapper = new UserMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return list;
	}

	@Override
	public int update(User user) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.UPDATE)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setLong(4, user.getMoney());
			statement.setInt(5, user.getRole().getDBValue());
			statement.setInt(6, user.getId());

			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
	}

	@Override
	public List<User> findByFiltersSortedList(List<? extends FilterCriteria> filters, SortCriteria order) {
		List<User> list = new ArrayList<>();

		String query = FIND_ALL + (filters.isEmpty() ? "" : WHERE)
				+ CriteriaUtil.createSQLString(filters)
				+ ORDER_BY + order.getSQLString();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			CriteriaUtil.setParameters(filters, statement, 1);
			ResultSet resultSet = statement.executeQuery();

			UserMapper mapper = new UserMapper();
			while (resultSet.next()) {
				list.add(mapper.extractFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.warn("SQL exception on query : {}", query);
		}
		return list;
	}

	@Override
	public int delete(int id) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(UserMySQLQuery.DELETE)) {
			statement.setInt(1, id);
			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
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
