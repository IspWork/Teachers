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

@WebServlet("/addStudentServlet")
public class AddStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	protected void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM StudentBean u");

		String facN = req.getParameter("facultyNumber");
		@SuppressWarnings("unchecked")
		List<StudentBean> l = q.getResultList();
		PrintWriter out = response.getWriter();
		boolean printIncorrect = true;

		/**
		 * Checks if there is a student with the same faculty number as the one
		 * that the client has entered. If there is such a student, the new
		 * student is not added to the system. If there is not a student with
		 * this faculty number, then the new student is added to the system.
		 */
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getFacultyNumber().equals(facN)) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Student with this faculty number already Exists!!');");
				out.println("location='studentAdd.html';");
				out.println("</script>");
				printIncorrect = false;
				break;
			}
		}

		if (printIncorrect) {
			StudentBean user = new StudentBean();

			user.setFirstName(req.getParameter("firstName"));
			user.setMiddleName(req.getParameter("middleName"));
			user.setLastName(req.getParameter("lastName"));
			user.setFaculty(req.getParameter("faculty"));
			user.setFacultyNumber(req.getParameter("facultyNumber"));
			user.setEducationType(req.getParameter("educationType"));

			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();

			response.sendRedirect("studentAddAnswer.html");
		}

	}

}
