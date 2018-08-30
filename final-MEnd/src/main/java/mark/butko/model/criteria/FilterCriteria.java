package mark.butko.model.criteria;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface FilterCriteria extends Criteria {

	int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException;
}
