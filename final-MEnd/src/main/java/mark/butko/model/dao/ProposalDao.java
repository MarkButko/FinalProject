package mark.butko.model.dao;

import java.util.List;

import mark.butko.model.entity.Proposal;

public interface ProposalDao extends GenericDao<Proposal> {

	List<Proposal> findByUserId(Integer userId);

	List<Proposal> findPage(Integer userId, String orderProperty, Integer limit, Integer offset);

}
