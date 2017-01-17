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

@WebServlet("/removeTeacherServlet")
public class RemoveTeacherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("SELECT u FROM TeacherBean u");
		String teacherId = req.getParameter("teacherId");
		boolean error = true;

		@SuppressWarnings("unchecked")
		List<TeacherBean> l = q.getResultList();
		PrintWriter out = resp.getWriter();

		/**
		 * checks if there is such a teacherId in the list, if there is, it
		 * removes the teacher with the corresponding teacherId from the
		 * database table
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getTeacherIdentification().equals(teacherId)) {
				System.out.println(l.get(i).getId());
				Query query = em
						.createQuery("DELETE FROM TeacherBean WHERE teacherIdentification = "
								+ teacherId);
				em.getTransaction().begin();
				query.executeUpdate();
				em.getTransaction().commit();
				resp.sendRedirect("removeAnswer.html");

				error = false;
				break;
			}
		}

		if (error) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('No such teacher ID.');");
			out.println("location='removeTeacher.html';");
			out.println("</script>");
		}

	}

}
