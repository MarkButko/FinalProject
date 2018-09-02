package mark.butko.model.dao;

import java.util.List;

import mark.butko.dto.Comment;
import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.PageCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.Proposal.Status;

public interface ProposalDao extends GenericDao<Proposal> {

	List<Proposal> findByUserId(Integer userId);

	List<Proposal> findProposalsPage(Integer userId, String orderProperty, Integer limit, Integer offset);

	List<Comment> findCommentsPage(String orderProperty, Integer limit, Integer offset);

	Integer countAll();

	Integer countComments();

	List<Proposal> findByFiltersSortedList(List<? extends FilterCriteria> filters, SortCriteria order);

	List<Proposal> findMasterList(List<? extends FilterCriteria> filters, SortCriteria sortCriteria);

	List<Proposal> findPage(List<? extends FilterCriteria> filters, SortCriteria sortCriteria,
			PageCriteria pageCriteria);

	Integer countByStatus(Status status);

	Integer countPages(List<? extends FilterCriteria> filters);

	Integer addComment(int id, String comment);

	Integer reject(Proposal proposal, Integer managerId);

	void accept(Proposal proposal, Integer managerId);
}
