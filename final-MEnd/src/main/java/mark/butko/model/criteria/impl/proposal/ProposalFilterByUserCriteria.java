package mark.butko.model.criteria.impl.proposal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import mark.butko.model.criteria.FilterCriteria;

public class ProposalFilterByUserCriteria implements FilterCriteria {

	Integer userId;

	public ProposalFilterByUserCriteria(Integer userId) {
		super();
		this.userId = userId;
	}

	@Override
	public String getSQLString() {
		return " author_id = ? ";
	}

	@Override
	public int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException {
		statement.setInt(startIndex, userId);
		return startIndex + 1;
	}

}
