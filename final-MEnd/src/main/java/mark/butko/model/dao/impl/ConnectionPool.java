package mark.butko.model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
	private static DataSource dataSource = null;

	private static class InstanceHolder {
		private static final ConnectionPool INSTANCE = new ConnectionPool();
	}

	private ConnectionPool() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/mend");
		} catch (NamingException e) {
			System.out.println(e);
		}
	}

	public static ConnectionPool getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println(e);// log
			return null;
		}
	}
}
