package mark.butko.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mark.butko.model.dao.impl.column.ProposalColumn;
import mark.butko.model.entity.Proposal;

public class ProposalMapper implements Mapper<Proposal> {

	@Override
	public Proposal extractFromResultSet(ResultSet rs) throws SQLException {
		if (rs.getString(ProposalColumn.MESSAGE) == null) {
			return null;
		}
		return new Proposal.Builder()
				.id(rs.getInt(ProposalColumn.ID))
				.date(rs.getDate(ProposalColumn.DATE).toLocalDate())
				.message(rs.getString(ProposalColumn.MESSAGE))
				.rejectionCause(rs.getString(ProposalColumn.REJECTION_CAUSE))
				.status(Proposal.Status.fromDBValue(rs.getInt(ProposalColumn.STATUS)))
				.price(rs.getLong(ProposalColumn.PRICE))
				.comment(rs.getString(ProposalColumn.COMMENT))
				.build();
	}

	@Override
	public Proposal makeUnique(Map<Integer, Proposal> cache, Proposal proposal) {
		cache.putIfAbsent(proposal.getId(), proposal);
		return cache.get(proposal.getId());
	}
}
