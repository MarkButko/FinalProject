package mark.butko.model.criteria.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import mark.butko.model.criteria.FilterCriteria;

public class RegistrationDateFilterCriteria implements FilterCriteria {

	private LocalDate from;
	private LocalDate to;

	public RegistrationDateFilterCriteria(LocalDate from, LocalDate to) {
		super();
		this.from = from;
		this.to = to;
	}

	@Override
	public String getSQLString() {
		return " ( registration_date BETWEEN ? AND ? ) ";
	}

	@Override
	public int setParameters(PreparedStatement statement, Integer startIndex) throws SQLException {
		statement.setDate(startIndex, Date.valueOf(from));
		statement.setDate(startIndex + 1, Date.valueOf(to));
		return startIndex + 2;
	}

}
