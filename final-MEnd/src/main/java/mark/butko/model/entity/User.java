package mark.butko.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

	private Integer id;
	private Role role;
	private String name;
	private String email;
	private String password;
	private Long money;
	private List<Proposal> proposals = new ArrayList<>();

	public User() {
	}

	public User(Role role, String name, String email, String password, Long money, List<Proposal> proposals) {
		this.role = role;
		this.name = name;
		this.email = email;
		this.password = password;
		this.money = money;
		this.proposals = proposals;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", money=" + money + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(List<Proposal> proposals) {
		this.proposals = proposals;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
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

	public static enum Role {
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

		public Builder money(Long money) {
			user.setMoney(money);
			return this;
		}

		public Builder id(Integer id) {
			user.setId(id);
			return this;
		}

		public User build() {
			return user;
		}
	}
}
