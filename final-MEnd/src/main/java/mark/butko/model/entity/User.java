package mark.butko.model.entity;

import java.util.HashMap;
import java.util.Map;

public class User {

	private Role role;
	private String name;
	private String email;
	private String password;

	public User() {
	}

	public User(Role role, String name, String email, String password) {
		this.role = role;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [role=" + role + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public enum Role {
		CUSTOMER(0), MASTER(1), MANAGER(2), ADMIN(3), GUEST(4);

		private int dbValue;

		private static final Map<Integer, Role> values = new HashMap<>();

		static {
			for (Role role : Role.values()) {
				values.put(role.dbValue, role);
			}
		}

		Role(int dbValue) {
			this.dbValue = dbValue;
		}

		public static Role fromDBValue(int dbValue) {
			return values.getOrDefault(dbValue, Role.CUSTOMER);
		}

		public int getDBValue() {
			return dbValue;
		}
	}

	public static class Builder {

		private User user = new User();

		public Builder name(String name) {
			user.setName(name);
			return this;
		}

		public Builder role(Role role) {
			user.setRole(role);
			return this;
		}

		public Builder email(String email) {
			user.setEmail(email);
			return this;
		}

		public Builder password(String password) {
			user.setPassword(password);
			return this;
		}

		public User build() {
			return user;
		}
	}
}
