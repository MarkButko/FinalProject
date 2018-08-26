package mark.butko.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mark.butko.model.entity.Proposal;

public class ProposalMapper implements Mapper<Proposal> {

	@Override
	public Proposal extractFromResultSet(ResultSet rs) throws SQLException {
		return new Proposal.Builder()
				.id(rs.getInt("id"))
				.date(rs.getDate("date").toLocalDate())
				.message(rs.getString("message"))
				.rejectionCause(rs.getString("rejection_cause"))
				.status(Proposal.Status.fromDBValue(rs.getInt("status")))
				.price(rs.getLong("price"))
				.comment(rs.getString("comment"))
				.build();
	}
}
