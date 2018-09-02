package mark.butko.model.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.dao.DaoFactory;
import mark.butko.model.dao.UserDao;
import mark.butko.model.entity.User;
import mark.butko.model.service.exception.LoginException;
import mark.butko.model.service.exception.UserAlreadyExistsException;
import mark.butko.model.service.exception.UserDoesNotExist;
import mark.butko.model.service.exception.WrongEmailException;
import mark.butko.model.service.exception.WrongPasswordException;

public class UserService {
	private static final Logger LOGGER = LogManager.getLogger(UserService.class.getName());

	DaoFactory daoFactory = DaoFactory.getInstance();

	public User login(String email, String password) throws LoginException {
		Optional<User> user;
		try (UserDao userDao = daoFactory.createUserDao()) {
			user = userDao.findByEmail(email);
		}
		if (user.isPresent()) {
			if (user.get().getPassword().equals(password)) {
				return user.get();
			}
			LOGGER.info("Wrong password : {} for email : {}", password, email);
			throw new WrongPasswordException("Password does not match.");
		}
		LOGGER.info("Wrong email : {}", email);
		throw new WrongEmailException("User with email " + email + " is not found.");
	}

	public User getByEmail(String email) {
		Optional<User> user;
		try (UserDao userDao = daoFactory.createUserDao()) {
			user = userDao.findByEmail(email);
			return user.isPresent() ? user.get() : null;
		}
	}

	public Integer countProposals(Integer userId) {
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.countProposals(userId);
		}
	}

	public void add(User user) throws UserAlreadyExistsException {
		try (UserDao userDao = daoFactory.createUserDao()) {
			if (userDao.findByEmail(user.getEmail()).isPresent()) {
				throw new UserAlreadyExistsException();
			} else {
				userDao.create(user);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void update(User user) {
		try (UserDao userDao = daoFactory.createUserDao()) {
			userDao.update(user);
		}
	}

	public void changeRole(Integer userId, User.Role role) throws UserDoesNotExist {
		try (UserDao userDao = daoFactory.createUserDao()) {
			User user = userDao.findByIdWithProposals(userId);
			if (user != null) {
				user.setRole(role);
				userDao.update(user);
			} else {
				throw new UserDoesNotExist();
			}
		}
	}

	public List<User> getAll() {
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.findAll();
		}
	}

	public List<User> getByFiltersSortedList(List<? extends FilterCriteria> filters, SortCriteria order) {
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.findByFiltersSortedList(filters, order);
		}
	}
}
