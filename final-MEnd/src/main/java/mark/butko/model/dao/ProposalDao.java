package mark.butko.model.dao;

import java.util.List;

import mark.butko.dto.Comment;
import mark.butko.model.entity.Proposal;

public interface ProposalDao extends GenericDao<Proposal> {

	List<Proposal> findByUserId(Integer userId);

	List<Proposal> findProposalsPage(Integer userId, String orderProperty, Integer limit, Integer offset);

	List<Comment> findCommentsPage(String orderProperty, Integer limit, Integer offset);

	Integer countAll();

	Integer countComments();
}
