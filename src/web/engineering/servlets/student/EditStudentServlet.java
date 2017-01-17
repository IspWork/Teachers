package web.engineering.servlets.student;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editStudent")
public class EditStudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String facN = req.getParameter("facN");
		String edu = req.getParameter("education");
		String faculty = req.getParameter("faculty");

		Query query = em
				.createQuery(
						"UPDATE StudentBean SET firstName = ?1, middleName = ?2, lastName = ?3, educationType = ?4, faculty = ?5  WHERE facultyNumber = ?6")
				.setParameter(1, firstName).setParameter(2, middleName)
				.setParameter(3, lastName).setParameter(4, edu)
				.setParameter(5, faculty).setParameter(6, facN);

		em.getTransaction().begin();
		query.executeUpdate();
		em.getTransaction().commit();

		resp.sendRedirect("editAnswer.html");

	}

}
