package mark.butko.dto;

public class Comment {

	private String comment;
	private String authorName;

	public Comment() {
		super();
	}

	public Comment(String comment, String authorName) {
		super();
		this.comment = comment;
		this.authorName = authorName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
}
