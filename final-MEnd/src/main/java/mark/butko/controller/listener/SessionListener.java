package mark.butko.controller.listener;

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

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		User user = (User) session.getAttribute("user");
		session.removeAttribute("user");
		session.invalidate();
		LOGGER.info("Session invalidate for user : {}", user.getEmail());
		LOGGER.info("Session Destroyed");
	}

}
