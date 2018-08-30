package mark.butko.model.dao.impl;

public final class UserMySQLQuery {

	public static final String CREATE = "INSERT INTO " +
			"user (name, email, password, money, registration_date, role) " +
			"VALUES (?, ?, ?, ?, ?, ?)";

	public static final String UPDATE = "UPDATE user SET " +
			"name = ?, email = ?, password = ?, money = ?, role = ? " +
			"WHERE user.id = ?";

	public static final String FIND_BY_ID = "SELECT * FROM user LEFT JOIN proposal" +
			" ON user.id = proposal.author_id WHERE user.id = ?";

	public static final String FIND_BY_EMAIL = "SELECT * FROM user LEFT JOIN proposal" +
			" ON user.id = proposal.author_id WHERE user.email = ?";

	public static final String FIND_ALL = "SELECT * FROM user";

	public static final String DELETE = "DELETE FROM user WHERE id = ?";

	public static final String COUNT_PROPOSALS = "SELECT COUNT(*) as count FROM proposal JOIN user" +
			" ON user.id = proposal.author_id WHERE author_id = ?";

	public static final String WHERE = " WHERE ";

	public static final String ORDER_BY = " ORDER BY ";

	public static final String AND = " AND ";
}
