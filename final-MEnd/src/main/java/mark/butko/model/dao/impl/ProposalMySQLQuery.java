package mark.butko.model.dao.impl;

public final class ProposalMySQLQuery {

	public static final String CREATE = "INSERT INTO " +
			"proposal (message, date, status, author_id) " +
			"VALUES (?, ?, ?, ?)";

	public static final String UPDATE = "UPDATE proposal SET " +
			"message = ?, status = ?, rejection_cause = ?, comment = ?, price = ?, master_id = ?, manager_id = ? " +
			"WHERE proposal.id = ?";

	public static final String FIND_BY_ID = "SELECT * FROM proposal WHERE proposal.id = ?";

	public static final String FIND_ALL = "SELECT * FROM proposal";

	public static final String COUNT_ALL = "SELECT COUNT(*) as count FROM proposal";

	public static final String COUNT_COMMENTS = "SELECT COUNT(ALL comment) as count FROM proposal";

	public static final String DELETE = "DELETE FROM proposal WHERE id = ?";

	public static final String FIND_BY_USER_ID = "SELECT * FROM proposal JOIN user" +
			" ON user.id = proposal.author_id WHERE author_id = ?";

	public static final String FIND_PROPOSALS_PAGE = "SELECT * FROM proposal JOIN user" +
			" ON user.id = proposal.author_id WHERE author_id = ?" +
			" ORDER BY ? LIMIT ? OFFSET ?";

	public static final String FIND_COMMENTS_PAGE = "SELECT comment, name FROM proposal JOIN user" +
			" ON user.id = proposal.author_id" +
			" WHERE comment IS NOT NULL" +
			" ORDER BY ? LIMIT ? OFFSET ?";
}
