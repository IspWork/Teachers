package web.engineering.servlets.student;

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

import web.engineering.beans.StudentBean;

@WebServlet("/removeStudentServlet")
public class RemoveStudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("SELECT u FROM StudentBean u");
		String facNumber = req.getParameter("facultyNumber");
		boolean error = true;

		@SuppressWarnings("unchecked")
		List<StudentBean> l = q.getResultList();
		PrintWriter out = resp.getWriter();

		/**
		 * checks if there is such a faculty number in the list, if there is, it
		 * removes the student with the corresponding faculty number from the
		 * database table
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getFacultyNumber().equals(facNumber)) {
				Query query = em
						.createQuery("DELETE FROM StudentBean WHERE facultyNumber = "
								+ facNumber);
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
			out.println("alert('There is no student with this faculty number!');");
			out.println("location='removeStudent.html';");
			out.println("</script>");
		}

	}

}
