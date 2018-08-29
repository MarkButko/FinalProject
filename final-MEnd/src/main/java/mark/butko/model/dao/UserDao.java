package mark.butko.model.dao;

import java.util.Optional;

import mark.butko.model.entity.User;

public interface UserDao extends GenericDao<User> {

	Optional<User> findByEmail(String email);

	Integer countProposals(Integer userId);
}
