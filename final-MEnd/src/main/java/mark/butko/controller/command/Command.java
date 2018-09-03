package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface that all commands that are called from servlet should implement
 * 
 * @author markg
 *
 */
public interface Command {

	/**
	 * Processes user request and sends response back by calling
	 * {@link Command#forward(HttpServletRequest,HttpServletResponse)} or
	 * {@link Command#redirect(HttpServletRequest,HttpServletResponse)} methods or
	 * calls another command to complete processing
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException if forwarding fails
	 * @throws IOException      if forwarding or redirecting fails
	 */
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	/**
	 * redirects user to another page
	 * 
	 * @param request
	 * @param response
	 * @param path     relative to context path
	 * @throws IOException
	 */
	default void redirect(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	/**
	 * Forwards a request from a servlet to another resource (servlet, JSP file, or
	 * HTML file) on the server.
	 * 
	 * @param request
	 * @param response
	 * @param path     relative to context path
	 * @throws ServletException
	 * @throws IOException
	 */
	default void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		request.getServletContext().getRequestDispatcher(path).forward(request, response);
	}
}