package web.engineering.servlets.student;

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

import web.engineering.beans.StudentBean;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/readStudent")
public class ReadStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "users";
	private EntityManagerFactory factory;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("SELECT u FROM StudentBean u");
		@SuppressWarnings("unchecked")
		List<StudentBean> l = q.getResultList();
		JsonArray ar = new JsonArray();

		// creates a JsonObject for every student in the database
		// then adds the object to a JsonArray.
		for (StudentBean student : l) {
			JsonObject obj = new JsonObject();
			obj.addProperty("firstName", student.getFirstName());
			obj.addProperty("lastName", student.getLastName());
			obj.addProperty("middleName", student.getMiddleName());
			obj.addProperty("id", student.getId());

			ar.add(obj);
		}

		response.getWriter().append(ar.toString());
	}

}
