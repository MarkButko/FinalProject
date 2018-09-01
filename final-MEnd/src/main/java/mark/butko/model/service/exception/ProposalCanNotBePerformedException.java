package mark.butko.model.service.exception;

public class ProposalCanNotBePerformedException extends Exception {

	public ProposalCanNotBePerformedException() {
		super();
	}

	public ProposalCanNotBePerformedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProposalCanNotBePerformedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProposalCanNotBePerformedException(String message) {
		super(message);
	}

	public ProposalCanNotBePerformedException(Throwable cause) {
		super(cause);
	}

}
