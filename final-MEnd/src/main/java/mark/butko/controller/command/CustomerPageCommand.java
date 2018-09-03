package mark.butko.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.controller.path.JSPPath;
import mark.butko.model.criteria.FilterCriteria;
import mark.butko.model.criteria.PageCriteria;
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.criteria.impl.proposal.ProposalDateSortCriteria;
import mark.butko.model.criteria.impl.proposal.ProposalFilterByUserCriteria;
import mark.butko.model.criteria.impl.proposal.ProposalPageCriteria;
import mark.butko.model.dao.impl.column.ProposalColumn;
import mark.butko.model.entity.Proposal;
import mark.butko.model.entity.User;
import mark.butko.model.service.ProposalService;

public class CustomerPageCommand implements Command {

	private static final Logger LOGGER = LogManager.getLogger(CustomerPageCommand.class.getName());
	private static final String DEFAULT_SORT_TYPE = ProposalColumn.DATE;
	private static final Integer ROWS_ON_PAGE = 5;

	private ProposalService proposalService = ProposalService.getInstance();

	private Map<String, SortCriteria> sortCriteriaMap = new HashMap<>();
	{
		sortCriteriaMap.put(ProposalColumn.DATE, new ProposalDateSortCriteria());
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<FilterCriteria> filters = new ArrayList<>();

		filters.add(new ProposalFilterByUserCriteria(user.getId()));

		SortCriteria sortCriteria = extractSortCriteriaOrDefault(request);// default :date
		LOGGER.debug(" :: SortCriteria : {}", sortCriteria.getClass());
		PageCriteria pageCriteria = extractPageCriteria(request);

		Integer pageCount = proposalService.countPages(filters);
		Integer lastPage = pageCount / ROWS_ON_PAGE
				+ ((pageCount % ROWS_ON_PAGE) == 0 ? 0 : 1);

		List<Proposal> proposals;
		Integer currentPage;
		if (lastPage > pageCriteria.getPage()) {
			proposals = proposalService.getPage(filters, sortCriteria, pageCriteria);
			currentPage = pageCriteria.getPage();
		} else {
			proposals = proposalService.getPage(filters, sortCriteria,
					new ProposalPageCriteria(lastPage, ROWS_ON_PAGE));
			currentPage = lastPage;
		}

		request.setAttribute("lastPage", lastPage);
		request.setAttribute("proposals", proposals);
		request.setAttribute("currentPage", currentPage);

		forward(request, response, JSPPath.CUSTOMER_PROPOSALS);
	}

	private PageCriteria extractPageCriteria(HttpServletRequest request) {
		Integer currentPage;
		Integer futurePage;

		String currentPageString = request.getParameter("currentPage");
		String futurePageString = request.getParameter("futurePage");

		if (currentPageString == null
				|| currentPageString.isEmpty()) {
			LOGGER.debug("extractPageCriteria :: currentPage = {}",
					currentPageString);
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(currentPageString);
		}
		LOGGER.debug("extractPageCriteria :: currentPage = {}", currentPage);

		if ("previous".equals(futurePageString)) {
			futurePage = currentPage - 1;
		} else if ("next".equals(futurePageString)) {
			futurePage = currentPage + 1;
		} else {
			futurePage = currentPage;
		}

		return new ProposalPageCriteria(futurePage, ROWS_ON_PAGE);
	}

	private SortCriteria extractSortCriteriaOrDefault(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String sortType = Optional.ofNullable(request.getParameter("sort-type"))
				.orElse((String) session.getAttribute("sort-type"));

		LOGGER.debug("extractSortCriteria :: sortType = {}", sortType);

		if (sortType == null
				|| sortType.isEmpty()
				|| !sortCriteriaMap.containsKey(sortType)) {

			LOGGER.debug("extractSortCriteria :: defaultSortType");

			session.setAttribute("sort-type", DEFAULT_SORT_TYPE);
			return sortCriteriaMap.get(DEFAULT_SORT_TYPE);
		}

		session.setAttribute("sort-type", sortType);
		return sortCriteriaMap.get(sortType);
	}

//	private void addIfExistsDateFilter(HttpServletRequest request, List<FilterCriteria> filters) {
//		HttpSession session = request.getSession();
//
//		String dateFrom = Optional.ofNullable(request.getParameter("date_from"))
//				.orElse((String) session.getAttribute("date_from"));
//		String dateTo = Optional.ofNullable(request.getParameter("date_to"))
//				.orElse((String) session.getAttribute("date_to"));
//
//		LOGGER.debug("addIfExistsDateFilter :: dateFrom = {}", dateFrom);
//		LOGGER.debug("addIfExistsDateFilter :: dateTo = {}", dateTo);
//
//		if (dateFrom != null
//				&& dateTo != null
//				&& !dateFrom.isEmpty()
//				&& !dateTo.isEmpty()) {
//
//			LocalDate from = LocalDate.parse(dateFrom);
//			LocalDate to = LocalDate.parse(dateTo);
//
//			FilterCriteria proposalCreationDateFilterCriteria = new ProposalDateFilterCriteria(from, to);
//			filters.add(proposalCreationDateFilterCriteria);
//
//			session.setAttribute("date_from", dateFrom);
//			session.setAttribute("date_to", dateTo);
//		}
//	}
//
//	private void addIfExistsStatusFilter(HttpServletRequest request, List<FilterCriteria> filters) {
//		HttpSession session = request.getSession();
//
//		String status = Optional.ofNullable(request.getParameter("status_filter"))
//				.orElse((String) session.getAttribute("status_filter"));
//
//		LOGGER.debug("addIfExistsStatusFilter :: status_filter = {}", status);
//
//		if (status != null && !status.isEmpty()) {
//			FilterCriteria statusFilterCriteria = new StatusFilterCriteria(Proposal.Status.valueOf(status));
//			filters.add(statusFilterCriteria);
//
//			session.setAttribute("status", status);
//		}
//	}
}
