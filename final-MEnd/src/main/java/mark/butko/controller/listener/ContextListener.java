package mark.butko.controller.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger LOGGER = LogManager.getLogger(ContextListener.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		LOGGER.debug("Context loaded");
		ServletContext servletContext = event.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
