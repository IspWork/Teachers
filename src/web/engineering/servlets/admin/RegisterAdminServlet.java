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

@WebServlet("/registerAdminServlet")
public class RegisterAdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM AdminBean u");

		String userName = req.getParameter("userName");
		boolean createUser = true;

		@SuppressWarnings("unchecked")
		List<AdminBean> l = q.getResultList();
		PrintWriter out = response.getWriter();

		/**
		 * Checks if the admin username matches with any other admin username in
		 * the database table. If it matches the new admin is not created, if it
		 * does not the new admin is added to the database table.
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getUserName().equals(userName)) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Admin with that username already exists');");
				out.println("location='registerAdmin.html';");
				out.println("</script>");
				createUser = false;
				break;
			}
		}

		if (createUser) {
			AdminBean admin = new AdminBean();
			admin.setUserName(req.getParameter("userName"));
			admin.setPassword(req.getParameter("password"));

			em.getTransaction().begin();
			em.persist(admin);
			em.getTransaction().commit();
			response.sendRedirect("registrationAnswer.html");
		}

	}

}
