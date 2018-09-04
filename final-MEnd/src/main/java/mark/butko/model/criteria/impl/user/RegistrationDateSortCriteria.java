package mark.butko.model.criteria.impl.user;

import mark.butko.model.criteria.SortCriteria;

public class RegistrationDateSortCriteria implements SortCriteria {

	@Override
	public String getSQLString() {
		return " registration_date DESC";
	}
}
