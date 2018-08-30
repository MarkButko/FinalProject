package mark.butko.model.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import mark.butko.model.dao.impl.UserMySQLQuery;

public class CriteriaUtil {

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
}
