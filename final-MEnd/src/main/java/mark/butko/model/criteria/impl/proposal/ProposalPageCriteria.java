package mark.butko.model.criteria.impl.proposal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.criteria.PageCriteria;

public class ProposalPageCriteria implements PageCriteria {

	private static final Logger LOGGER = LogManager.getLogger(ProposalPageCriteria.class.getName());

	int page;
	int rowsOnPage;

	public ProposalPageCriteria(int page, int rowsOnPage) {
		super();
		this.page = page;
		this.rowsOnPage = rowsOnPage;
	}

	@Override
	public String getSQLString() {
		return " LIMIT ? OFFSET ? ";
	}

	@Override
	public int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException {

		statement.setInt(startIndex, rowsOnPage);
		LOGGER.debug("limit set to {}", rowsOnPage);
		statement.setInt(startIndex + 1, (page - 1) * rowsOnPage);
		LOGGER.debug("offset set to {}", (page - 1) * rowsOnPage);

		return startIndex + 2;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRowsOnPage() {
		return rowsOnPage;
	}

	public void setRowsOnPage(int rowsOnPage) {
		this.rowsOnPage = rowsOnPage;
	}
}
