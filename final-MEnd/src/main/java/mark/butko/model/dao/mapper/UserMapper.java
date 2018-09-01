package mark.butko.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import mark.butko.model.dao.impl.UserColumn;
import mark.butko.model.entity.User;

public class UserMapper implements Mapper<User> {

	@Override
	public User extractFromResultSet(ResultSet rs) throws SQLException {
		if (rs.getString(UserColumn.EMAIL) == null) {
			return null;
		}
		return new User.Builder()
				.id(rs.getInt(UserColumn.ID))
				.name(rs.getString(UserColumn.NAME))
				.money(rs.getLong(UserColumn.MONEY))
				.email(rs.getString(UserColumn.EMAIL))
				.password(rs.getString(UserColumn.PASSWORD))
				.role(User.Role.fromDBValue(rs.getInt(UserColumn.ROLE)))
				.date(rs.getDate(UserColumn.REGISTRATION_DATE).toLocalDate())
				.build();
	}

	@Override
	public User makeUnique(Map<Integer, User> cache, User user) {
		cache.putIfAbsent(user.getId(), user);
		return cache.get(user.getId());
	}
}
