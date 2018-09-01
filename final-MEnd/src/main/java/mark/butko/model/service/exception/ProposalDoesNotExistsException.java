package mark.butko.model.service.exception;

public class ProposalDoesNotExistsException extends Exception {

	public ProposalDoesNotExistsException() {
		super();
	}

	public ProposalDoesNotExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProposalDoesNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProposalDoesNotExistsException(String message) {
		super(message);
	}

	public ProposalDoesNotExistsException(Throwable cause) {
		super(cause);
	}

}
