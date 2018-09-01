package mark.butko.model.criteria.impl.proposal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.Proposal.Status;

public class StatusFilterCriteria implements FilterCriteria {

	Proposal.Status status;

	public StatusFilterCriteria(Status status) {
		super();
		this.status = status;
	}

	@Override
	public String getSQLString() {
		return " status = ? ";
	}

	@Override
	public int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException {
		statement.setInt(startIndex, status.getDbValue());
		return startIndex + 1;
	}

}
