package mark.butko.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import mark.butko.model.dao.ProposalDao;
import mark.butko.model.dao.mapper.ProposalMapper;
import mark.butko.model.entity.Proposal;

public class JDBCProposalDao implements ProposalDao {
	private Connection connection;

	public JDBCProposalDao(Connection connection) {
		this.connection = connection;
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
	public int update(Proposal proposal) {
		int rowAffected = 0;
		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.UPDATE)) {

			statement.setString(1, proposal.getMessage());
			statement.setInt(2, proposal.getStatus().getDbValue());
			statement.setString(3, proposal.getRejectionCause());
			statement.setString(4, proposal.getComment());
			statement.setLong(5, proposal.getPrice());

			if (proposal.getManager() != null) {
				statement.setInt(6, proposal.getManager().getId());
			} else {
				statement.setNull(6, Types.INTEGER);
			}
			if (proposal.getManager() != null) {
				statement.setInt(7, proposal.getMaster().getId());
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
	public List<Proposal> findPage(Integer userId, String orderProperty, Integer limit, Integer offset) {
		List<Proposal> list = new ArrayList<>();

		if (orderProperty == null || orderProperty.isEmpty()) {
			orderProperty = ProposalColumn.DATE;
		}
		System.out.println("JDBCProposalDao: findPage: userId = " + userId + " orderProp = " + orderProperty
				+ " limit = " + limit + " offset = " + offset);

		try (PreparedStatement statement = connection.prepareStatement(ProposalMySQLQuery.FIND_PAGE)) {
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
