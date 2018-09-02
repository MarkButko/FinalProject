package mark.butko.controller.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mark.butko.model.entity.User;

@WebListener
public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = LogManager.getLogger(SessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.info("Session Created");
		HttpSession session = event.getSession();
		session.setAttribute("language", "EN");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LOGGER.info("Session Destroyed");
		Set<String> loggedUsers = (HashSet<String>) se.getSession()
				.getServletContext()
				.getAttribute("loggedUsers");
		User user = (User) se.getSession().getAttribute("user");
		if (user != null) {
			String userEmail = user.getEmail();
			loggedUsers.remove(userEmail);
			se.getSession()
					.getServletContext()
					.setAttribute("loggedUsers", loggedUsers);
		}
	}

}
