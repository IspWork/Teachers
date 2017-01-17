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

import web.engineering.beans.StudentBean;

import com.google.gson.JsonObject;

@WebServlet("/selectStudentProfile")
public class SelectStudentProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em
				.createQuery("SELECT u FROM StudentBean u WHERE u.id=:idparameter");
		q.setParameter("idparameter", Long.valueOf(request.getParameter("id")));

		StudentBean student = (StudentBean) q.getSingleResult();

		JsonObject obj = new JsonObject();
		obj.addProperty("firstName", student.getFirstName());
		obj.addProperty("middleName", student.getMiddleName());
		obj.addProperty("lastName", student.getLastName());
		obj.addProperty("faculty", student.getFaculty());
		obj.addProperty("facultyNumber", student.getFacultyNumber());
		obj.addProperty("educationType", student.getEducationType());

		response.getWriter().append(obj.toString());
	}

}
