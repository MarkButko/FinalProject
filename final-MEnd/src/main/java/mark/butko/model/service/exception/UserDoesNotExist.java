package mark.butko.model.service;

public class UserDoesNotExist extends Exception {

	public UserDoesNotExist() {
		super();
	}

	public UserDoesNotExist(String message) {
		super(message);
	}

}
