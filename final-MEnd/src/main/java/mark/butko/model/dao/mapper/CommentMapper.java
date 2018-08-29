package mark.butko.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mark.butko.dto.Comment;

public class CommentMapper implements Mapper<Comment> {

	@Override
	public Comment extractFromResultSet(ResultSet rs) throws SQLException {
		String comment = rs.getString("comment");
		String authorName = rs.getString("name");
		return new Comment(comment, authorName);
	}

}
