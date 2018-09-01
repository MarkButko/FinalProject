package mark.butko.model.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.dao.impl.UserMySQLQuery;

public class CriteriaUtil {

	private static final Logger LOGGER = LogManager.getLogger(CriteriaUtil.class.getName());

	public static String createSQLString(List<? extends FilterCriteria> filters) {
		StringBuffer buffer = new StringBuffer();
		String result = "";

		if (!filters.isEmpty()) {
			for (FilterCriteria criteria : filters) {
				buffer.append(criteria.getSQLString());
				buffer.append(UserMySQLQuery.AND);
			}
			result = buffer.substring(0, buffer.toString().length() - UserMySQLQuery.AND.length());
		}
		return result;
	}

	public static int setParameters(List<? extends FilterCriteria> filters, PreparedStatement statement, int startIndex)
			throws SQLException {
		for (FilterCriteria criteria : filters) {
			startIndex = criteria.setParameters(statement, startIndex);
		}
		return startIndex;
	}

	public static void setParameters(List<? extends FilterCriteria> filters, PageCriteria pageCriteria,
			PreparedStatement statement, int startIndex) throws SQLException {
		startIndex = setParameters(filters, statement, startIndex);
		LOGGER.debug("filters params set");
		pageCriteria.setParameters(statement, startIndex);
		LOGGER.debug("page params set");
	}
}
