package web.engineering.servlets.teacher;

import java.io.IOException;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/readTeacher")
public class ReadTeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("SELECT u FROM TeacherBean u");
		@SuppressWarnings("unchecked")
		List<TeacherBean> l = q.getResultList();
		JsonArray ar = new JsonArray();

		// creates a JsonObject for every teacher in the database
		// then adds the object to a JsonArray.
		for (TeacherBean user : l) {
			JsonObject obj = new JsonObject();
			obj.addProperty("firstName", user.getFirstName());
			obj.addProperty("lastName", user.getLastName());
			obj.addProperty("middleName", user.getMiddleName());
			obj.addProperty("id", user.getId());
			ar.add(obj);
		}

		response.getWriter().append(ar.toString());

	}

}
