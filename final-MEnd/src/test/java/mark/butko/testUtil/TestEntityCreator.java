package mark.butko.testUtil;

import mark.butko.model.entity.User;
import mark.butko.model.entity.User.Role;

public class TestEntityCreator {

	public static User getUser(String email, String password, User.Role role) {
		return new User.Builder()
				.email(email)
				.password(password)
				.role(role)
				.build();
	}

	public static User getUser(Role role) {
		return new User.Builder()
				.role(role)
				.build();
	}
}
