package web.engineering.servlets.student;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.engineering.beans.StudentBean;

import com.google.gson.JsonObject;

@WebServlet("/searchStudent")
public class SearchForStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;
	private String facNumber;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em
				.createQuery("SELECT u FROM StudentBean u WHERE u.facultyNumber = "
						+ facNumber);
		JsonObject obj = new JsonObject();

		// if the student is found, the student information is added to a
		// JsonObject that is then printed on the web page
		// if there is no such student the default Not Found information is
		// added to the JsonObject
		try {
			StudentBean student = (StudentBean) q.getSingleResult();
			obj.addProperty("found", "Found");
			obj.addProperty("firstName", student.getFirstName());
			obj.addProperty("middleName", student.getMiddleName());
			obj.addProperty("lastName", student.getLastName());
			obj.addProperty("faculty", student.getFaculty());
			obj.addProperty("facultyNumber", student.getFacultyNumber());
			obj.addProperty("educationType", student.getEducationType());

		} catch (NoResultException nre) {
			obj.addProperty("found", "Not Found");
			obj.addProperty("firstName", " NaN");
			obj.addProperty("middleName", " NaN");
			obj.addProperty("lastName", " NaN");
			obj.addProperty("faculty", " NaN");
			obj.addProperty("facultyNumber", " NaN");
			obj.addProperty("educationType", " NaN");
		}

		resp.getWriter().append(obj.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		facNumber = req.getParameter("facultyNumber");
		resp.sendRedirect("searchStudentAnswer.html");
	}

}
