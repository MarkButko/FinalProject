package mark.butko.model.criteria.impl;

import mark.butko.model.criteria.SortCriteria;

public class NameSortCriteria implements SortCriteria {

	@Override
	public String getSQLString() {
		return " name ";
	}

}
