package mark.butko.model.dao;

import mark.butko.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {

	public abstract ProposalDao createProposalDao();

	public abstract UserDao createUserDao();

	private static class InstanceHolder {
		private static final DaoFactory INSTANCE = new JDBCDaoFactory();
	}

	public static DaoFactory getInstance() {
		return InstanceHolder.INSTANCE;
	}

	protected DaoFactory() {
	};
}
