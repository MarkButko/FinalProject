package mark.butko.model.criteria.impl.user;

import mark.butko.model.criteria.SortCriteria;

public class NameSortCriteria implements SortCriteria {

	@Override
	public String getSQLString() {
		return " name ";
	}

}
