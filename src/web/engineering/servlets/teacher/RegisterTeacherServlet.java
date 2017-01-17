package web.engineering.servlets.teacher;

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

import web.engineering.beans.TeacherBean;

/**
 * Servlet implementation class RegisterServlet
 */

@WebServlet("/registerTeacherServlet")
public class RegisterTeacherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM TeacherBean u");

		String userName = req.getParameter("userName");
		String identification = req.getParameter("identification");
		boolean createUser = true;

		@SuppressWarnings("unchecked")
		List<TeacherBean> l = q.getResultList();
		PrintWriter out = response.getWriter();

		/**
		 * Checks if the teacher username and identification number do not match
		 * with other usernames or id numbers. If it does not, the new teacher
		 * is added to the system.
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getUserName().equals(userName)) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Username already exists');");
				out.println("location='teacherRegistration.html';");
				out.println("</script>");
				createUser = false;
				break;
			}
			if (l.get(i).getTeacherIdentification().equals(identification)) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Teacher ID already exists');");
				out.println("location='teacherRegistration.html';");
				out.println("</script>");
				createUser = false;
				break;
			}

		}

		if (createUser) {
			TeacherBean user = new TeacherBean();

			user.setUserName(req.getParameter("userName"));
			user.setTeacherIdentification(req.getParameter("identification"));
			user.setFirstName(req.getParameter("firstName"));
			user.setMiddleName(req.getParameter("middleName"));
			user.setLastName(req.getParameter("lastName"));
			user.setEmail(req.getParameter("Email"));
			user.setPhone(req.getParameter("phone"));
			user.setFaculty(req.getParameter("faculty"));
			user.setPassword(req.getParameter("password"));

			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			response.sendRedirect("registrationAnswer.html");
		}

	}
}
