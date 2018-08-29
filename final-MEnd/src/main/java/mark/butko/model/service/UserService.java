package mark.butko.model.service;

import java.util.Optional;

import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.UserDao;
import mark.butko.model.entity.User;

public class UserService {

	DaoFactory daoFactory = DaoFactory.getInstance();

	public User login(String email, String password) throws LoginException {
		Optional<User> user;
		try (UserDao userDao = daoFactory.createUserDao()) {
			user = userDao.findByEmail(email);
		}
		if (user.isPresent()) {
			if (user.get().getPassword().equals(password)) {
				System.out.println("user service getProposals " + (user.get().getProposals() == null));
				return user.get();
			}
			throw new LoginException("Password does not match.");
		}
		throw new LoginException("User with email " + email + " is not found.");
	}

	public Integer countProposals(Integer userId) {
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.countProposals(userId);
		}
	}
}
