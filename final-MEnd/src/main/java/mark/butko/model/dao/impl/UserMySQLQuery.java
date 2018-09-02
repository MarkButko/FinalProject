package mark.butko.model.dao.impl;

public final class UserMySQLQuery {

	public static final String CREATE = "INSERT INTO " +
			"user (name, email, password, money, registration_date, role) " +
			"VALUES (?, ?, ?, ?, ?, ?)";

	public static final String UPDATE = "UPDATE user SET " +
			"name = ?, email = ?, password = ?, money = ?, role = ? " +
			"WHERE user_id = ?";

	public static final String FIND_BY_ID_WITH_PROPOSALS = "SELECT * FROM user JOIN proposal ON" +
			" user_id = proposal.author_id WHERE user_id = ?";

	public static final String FIND_BY_ID = "SELECT * FROM user WHERE user_id = ?";

	public static final String FIND_CUSTOMER_BY_PROPOSAL_ID = "SELECT * FROM user JOIN proposal" +
			" ON user_id = proposal.author_id WHERE proposal_id = ?";

	public static final String FIND_MANAGER_BY_PROPOSAL_ID = "SELECT * FROM user  JOIN proposal" +
			" ON user_id = proposal.manager_id WHERE proposal_id = ?";

	public static final String FIND_MASTER_BY_PROPOSAL_ID = "SELECT * FROM user JOIN proposal" +
			" ON user_id = proposal.master_id WHERE proposal_id = ?";

	public static final String FIND_BY_EMAIL = "SELECT * FROM user LEFT JOIN proposal" +
			" ON user_id = proposal.author_id WHERE user.email = ?";

	public static final String FIND_ALL = "SELECT * FROM user";

	public static final String DELETE = "DELETE FROM user WHERE user_id = ?";

	public static final String WITHDRAW = "UPDATE user SET user.money = user.money - ? " +
			" WHERE user_id = ?";

	public static final String COUNT_PROPOSALS = "SELECT COUNT(*) as count FROM proposal JOIN user" +
			" ON user_id = proposal.author_id WHERE author_id = ?";

	public static final String WHERE = " WHERE ";

	public static final String ORDER_BY = " ORDER BY ";

	public static final String AND = " AND ";
}
