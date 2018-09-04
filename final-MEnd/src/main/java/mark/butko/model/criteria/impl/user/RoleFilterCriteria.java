package mark.butko.model.criteria.impl.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.entity.User;

public class RoleFilterCriteria implements FilterCriteria {

	private User.Role role;

	public RoleFilterCriteria(User.Role role) {
		this.role = role;
	}

	@Override
	public String getSQLString() {
		return "user.role = ?";
	}

	@Override
	public int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException {
		statement.setInt(startIndex, role.getDBValue());
		return startIndex + 1;
	}
}
