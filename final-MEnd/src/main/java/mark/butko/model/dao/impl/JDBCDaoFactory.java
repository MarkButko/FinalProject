package mark.butko.model.dao.impl;

import java.sql.Connection;

import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.ProposalDao;
import mark.butko.model.dao.UserDao;

public class JDBCDaoFactory extends DaoFactory {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public ProposalDao createProposalDao() {
		return new JDBCProposalDao(getConnection());
	}

	@Override
	public UserDao createUserDao() {
		return new JDBCUserDao(getConnection());
	}

	private Connection getConnection() {
		return connectionPool.getConnection();
	}
}
