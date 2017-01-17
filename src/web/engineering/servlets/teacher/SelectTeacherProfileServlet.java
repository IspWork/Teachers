package web.engineering.servlets.teacher;

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

import web.engineering.beans.TeacherBean;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class SelectProfileServlet
 */
//
@WebServlet("/selectTeacherProfile")
public class SelectTeacherProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em
				.createQuery("SELECT u FROM TeacherBean u WHERE u.id=:idparameter");
		q.setParameter("idparameter", Long.valueOf(request.getParameter("id")));

		TeacherBean user = (TeacherBean) q.getSingleResult();

		JsonObject obj = new JsonObject();
		obj.addProperty("userName", user.getUserName());
		obj.addProperty("teacherId", user.getTeacherIdentification());
		obj.addProperty("firstName", user.getFirstName());
		obj.addProperty("middleName", user.getMiddleName());
		obj.addProperty("lastName", user.getLastName());
		obj.addProperty("Email", user.getEmail());
		obj.addProperty("phone", user.getPhone());
		obj.addProperty("faculty", user.getFaculty());
		obj.addProperty("password", user.getPassword());

		response.getWriter().append(obj.toString());
	}

}
