package mark.butko.model.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PageCriteria extends Criteria {

	int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException;

	public int getPage();

	public int getRowsOnPage();
}
