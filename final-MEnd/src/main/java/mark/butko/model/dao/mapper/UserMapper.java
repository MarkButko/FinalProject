package mark.butko.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mark.butko.model.entity.User;

public class UserMapper implements Mapper<User> {

	@Override
	public User extractFromResultSet(ResultSet rs) throws SQLException {
		return new User.Builder()
				.id(rs.getInt("id"))
				.name(rs.getString("name"))
				.money(rs.getLong("money"))
				.email(rs.getString("email"))
				.password(rs.getString("password"))
				.role(User.Role.fromDBValue(rs.getInt("role")))
				.date(rs.getDate("registration_date").toLocalDate())
				.build();
	}

}
