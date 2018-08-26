package mark.butko.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			statement.setInt(3, proposal.getStatus().getDBValue());
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
			statement.setInt(2, proposal.getStatus().getDBValue());
			// what if null ???
			statement.setString(3, proposal.getRejectionCause());
			statement.setString(4, proposal.getComment());
			statement.setLong(5, proposal.getPrice());
			statement.setInt(6, proposal.getManager().getId());
			statement.setInt(7, proposal.getMaster().getId());
			statement.setInt(8, proposal.getId());
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
