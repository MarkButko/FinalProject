package mark.butko.model.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Proposal {

	private Integer id;
	private User author;
	private User master;
	private User manager;

	private LocalDate date;
	private String message;
	private String rejectionCause;

	private Status status;
	private String comment;
	private Long price;

	public Proposal() {
	}

	public Proposal(User author, String message) {
		this.author = author;
		this.message = message;
		this.date = LocalDate.now();
		this.status = Status.NEW;
	}

	@Override
	public String toString() {
		return "Proposal [id=" + id +
				", date=" + date +
				", message=" + message +
				", rejectionCause=" + rejectionCause
				+ ", status=" + status +
				", comment=" + comment +
				", price=" + price + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRejectionCause() {
		return rejectionCause;
	}

	public void setRejectionCause(String rejectionCause) {
		this.rejectionCause = rejectionCause;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public static enum Status {
		NEW(0), ACCEPTED(1), REJECTED(2), COMPLETED(3);

		private int dbValue;

		private static final Map<Integer, Status> values = new HashMap<>();

		static {
			for (Status Status : Status.values()) {
				values.put(Status.dbValue, Status);
			}
		}

		Status(int dbValue) {
			this.dbValue = dbValue;
		}

		public static Status fromDBValue(int dbValue) {
			return values.getOrDefault(dbValue, Status.NEW);
		}

		public int getDbValue() {
			return dbValue;
		}
	}

	public static class Builder {

		private Proposal proposal = new Proposal();

		public Builder author(User author) {
			proposal.setAuthor(author);
			return this;
		}

		public Builder master(User master) {
			proposal.setMaster(master);
			return this;
		}

		public Builder manager(User manager) {
			proposal.setManager(manager);
			return this;
		}

		public Builder date(LocalDate date) {
			proposal.setDate(date);
			return this;
		}

		public Builder message(String message) {
			proposal.setMessage(message);
			return this;
		}

		public Builder rejectionCause(String rejectionCause) {
			proposal.setRejectionCause(rejectionCause);
			return this;
		}

		public Builder status(Status status) {
			proposal.setStatus(status);
			return this;
		}

		public Builder comment(String comment) {
			proposal.setComment(comment);
			return this;
		}

		public Builder price(Long price) {
			proposal.setPrice(price);
			return this;
		}

		public Builder id(Integer id) {
			proposal.setId(id);
			return this;
		}

		public Proposal build() {
			return this.proposal;
		}
	}
}
