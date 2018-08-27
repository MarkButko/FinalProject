package mark.butko.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mark.butko.model.dao.UserDao;
import mark.butko.model.dao.mapper.ProposalMapper;
import mark.butko.model.dao.mapper.UserMapper;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;

public class JDBCUserDao implements UserDao {
	private Connection connection;

	public JDBCUserDao(Connection connection) {
		this.connection = connection;
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
			statement.setDate(5, Date.valueOf(user.getRegistrationDate()));
			statement.setInt(6, user.getRole().getDBValue());

			rowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// log
		}
		return rowAffected;
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
