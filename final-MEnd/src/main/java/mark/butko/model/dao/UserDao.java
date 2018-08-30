package mark.butko.model.dao;

import java.util.List;
import java.util.Optional;

import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.entity.User;

public interface UserDao extends GenericDao<User> {

	Optional<User> findByEmail(String email);

	Integer countProposals(Integer userId);

	List<User> findByFiltersSortedList(List<? extends FilterCriteria> filters, SortCriteria order);
}
