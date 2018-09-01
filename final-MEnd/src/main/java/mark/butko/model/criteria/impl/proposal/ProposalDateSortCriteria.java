package mark.butko.model.criteria.impl.proposal;

import mark.butko.model.criteria.SortCriteria;

public class ProposalDateSortCriteria implements SortCriteria {

	@Override
	public String getSQLString() {
		return " proposal.date DESC";
	}

}
