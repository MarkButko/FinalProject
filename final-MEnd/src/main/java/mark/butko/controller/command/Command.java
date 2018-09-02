package mark.butko.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	default void redirect(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	default void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		request.getServletContext().getRequestDispatcher(path).forward(request, response);
	}
}