package mark.butko.model.dao.impl;

public final class UserMySQLQuery {

	public static final String CREATE = "INSERT INTO " +
			"user (name, email, password, money, registration_date, role) " +
			"VALUES (?, ?, ?, ?, ?, ?)";

	public static final String UPDATE = "UPDATE user SET " +
			"name = ?, email = ?, password = ?, money = ?, registration_date ?, role = ? " +
			"WHERE user.id = ?";

	public static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";

	public static final String FIND_ALL = "SELECT * FROM user";

	public static final String DELETE = "DELETE FROM user WHERE id = ?";

	private UserMySQLQuery() {
	};
}
