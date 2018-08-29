package mark.butko.controller.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = LogManager.getLogger(SessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.debug("Session Created");
		HttpSession session = event.getSession();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LOGGER.debug("Session Destroyed");
	}

}
