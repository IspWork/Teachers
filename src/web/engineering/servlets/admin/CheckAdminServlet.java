package web.engineering.servlets.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.engineering.beans.AdminBean;

import com.google.gson.JsonObject;

@WebServlet("/checkAdmin")
public class CheckAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;
	private String userName;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// creates a JsonObject with property username. This will be printed out
		// on the web page.
		JsonObject obj = new JsonObject();
		obj.addProperty("userName", userName);
		resp.getWriter().append(obj.toString());

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// selects all the Admins in the database table
		Query q = em.createQuery("SELECT u FROM AdminBean u");

		userName = req.getParameter("userName");
		String password = req.getParameter("password");
		boolean printIncorrect = true;

		@SuppressWarnings("unchecked")
		List<AdminBean> l = q.getResultList();
		PrintWriter out = resp.getWriter();

		/**
		 * Checks if there is an admin with the given by the client: username
		 * and password. If there is such an admin the servlet redirects the
		 * client to the loggedInAdmin.html page
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getUserName().equals(userName)
					&& l.get(i).getPassword().equals(password)) {
				out.println("<script type=\"text/javascript\">");
				out.println("location='loggedInAdmin.html';");
				out.println("</script>");
				printIncorrect = false;
				break;
			}
		}

		if (printIncorrect) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Incorrect username or password');");
			out.println("location='menu.html';");
			out.println("</script>");
		}
	}

}
