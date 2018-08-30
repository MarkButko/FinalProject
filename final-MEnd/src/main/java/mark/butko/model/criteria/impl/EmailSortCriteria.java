package mark.butko.model.criteria.impl;

import mark.butko.model.criteria.SortCriteria;

public class EmailSortCriteria implements SortCriteria {

	@Override
	public String getSQLString() {
		return " email ";
	}

}
