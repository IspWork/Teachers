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

@WebServlet("/editTeacher")
public class EditTeacherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM TeacherBean u");
		boolean free = true;

		String userName = req.getParameter("userName");
		String id = req.getParameter("teacherID");
		String password = req.getParameter("password");
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String Email = req.getParameter("Email");
		String phone = req.getParameter("phone");
		String faculty = req.getParameter("faculty");

		@SuppressWarnings("unchecked")
		List<TeacherBean> l = q.getResultList();
		PrintWriter out = resp.getWriter();

		/**
		 * Checks if the edited teacher username does not exists in the database
		 * table under a different teacher If it exists, the application alerts
		 * the client that the username already exists under a different teacher
		 * and the edited information is not committed.
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getUserName().equals(userName)
					&& Integer.valueOf(l.get(i).getTeacherIdentification()) != Integer
							.valueOf(id)) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Username already exists!');");
				out.println("location='teacherListAdmin.html';");
				out.println("</script>");
				free = false;
				break;
			}
		}
		if (free) {
			Query query = em
					.createQuery(
							"UPDATE TeacherBean SET userName = ?1, firstName = ?2, middleName = ?3, lastName = ?4, Email = ?5, phone = ?6, faculty = ?7,password = ?8   WHERE teacherIdentification = ?9")
					.setParameter(1, userName).setParameter(2, firstName)
					.setParameter(3, middleName).setParameter(4, lastName)
					.setParameter(5, Email).setParameter(6, phone)
					.setParameter(7, faculty).setParameter(8, password)
					.setParameter(9, id);

			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();

			resp.sendRedirect("editAnswer.html");
		}

	}
}
