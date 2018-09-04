package mark.butko.model.criteria.impl.user;

import mark.butko.model.criteria.SortCriteria;

public class EmailSortCriteria implements SortCriteria {

	@Override
	public String getSQLString() {
		return " email ";
	}

}
