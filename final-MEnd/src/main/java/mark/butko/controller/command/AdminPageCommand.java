package mark.butko.controller.command;

import java.io.IOException;
import java.time.LocalDate;
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
import mark.butko.model.criteria.SortCriteria;
import mark.butko.model.criteria.impl.EmailSortCriteria;
import mark.butko.model.criteria.impl.NameSortCriteria;
import mark.butko.model.criteria.impl.RegistrationDateFilterCriteria;
import mark.butko.model.criteria.impl.RegistrationDateSortCriteria;
import mark.butko.model.criteria.impl.RoleFilterCriteria;
import mark.butko.model.entity.User;
import mark.butko.model.service.UserService;

public class AdminPageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger(AdminPageCommand.class.getName());

	UserService userService = UserService.getInstance();
	private Map<String, SortCriteria> sortCriteriaMap = new HashMap<>();
	private static final String DEFAULT_SORT_TYPE = "date";

	{
		sortCriteriaMap.put("name", new NameSortCriteria());
		sortCriteriaMap.put("date", new RegistrationDateSortCriteria());
		sortCriteriaMap.put("email", new EmailSortCriteria());
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<FilterCriteria> filters = new ArrayList<>();

		addIfExistsRoleFilter(request, filters);
		addIfExistsDateFilter(request, filters);

		SortCriteria sortCriteria = extractSortCriteria(request);

		List<User> users = userService.getByFiltersSortedList(filters, sortCriteria);

		session.setAttribute("roles", User.Role.values());
		session.setAttribute("users", users);

		forward(request, response, JSPPath.ADMIN_PAGE);
	}

	private SortCriteria extractSortCriteria(HttpServletRequest request) {
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

	private void addIfExistsRoleFilter(HttpServletRequest request, List<FilterCriteria> filters) {
		HttpSession session = request.getSession();

		String role = Optional.ofNullable(request.getParameter("role"))
				.orElse((String) session.getAttribute("role"));

		LOGGER.debug("addIfExistsRoleFilter :: role = {}", role);

		if (role != null && !role.isEmpty()) {
			FilterCriteria roleFilterCriteria = new RoleFilterCriteria(User.Role.valueOf(role));
			filters.add(roleFilterCriteria);

			session.setAttribute("role", role);
		}
	}

	private void addIfExistsDateFilter(HttpServletRequest request, List<FilterCriteria> filters) {
		HttpSession session = request.getSession();

		String dateFrom = Optional.ofNullable(request.getParameter("date_from"))
				.orElse((String) session.getAttribute("date_from"));
		String dateTo = Optional.ofNullable(request.getParameter("date_to"))
				.orElse((String) session.getAttribute("date_to"));

		LOGGER.debug("addIfExistsDateFilter :: dateFrom = {}", dateFrom);
		LOGGER.debug("addIfExistsDateFilter :: dateTo = {}", dateTo);

		if (dateFrom != null
				&& dateTo != null
				&& !dateFrom.isEmpty()
				&& !dateTo.isEmpty()) {

			LocalDate from = LocalDate.parse(dateFrom);
			LocalDate to = LocalDate.parse(dateTo);

			FilterCriteria registrationDateFilterCriteria = new RegistrationDateFilterCriteria(from, to);
			filters.add(registrationDateFilterCriteria);

			session.setAttribute("date_from", dateFrom);
			session.setAttribute("date_to", dateTo);
		}
	}

}
