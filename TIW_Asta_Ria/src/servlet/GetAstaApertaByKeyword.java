package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.AstaAperta;
import dao.AstaApertaDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class GetAstaApertaByKeyword
 */
@WebServlet("/GetAstaApertaByKeyword")
@MultipartConfig
public class GetAstaApertaByKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAstaApertaByKeyword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		AstaApertaDao astaDao = new AstaApertaDao(connection);
		List<AstaAperta> listaAsteAperte = new ArrayList<AstaAperta>();
		String keyword = StringEscapeUtils.escapeJava(request.getParameter("keyword"));
		listaAsteAperte = null;
		try {
			listaAsteAperte = astaDao.astaPerKeyword(keyword);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore interno al database");
			return;
		}

		// se non ci sono aste con quella keyword, allora manda messaggio di errore
		if (listaAsteAperte.size() == 0 || listaAsteAperte == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("non ci sono aste per la keyword : " + keyword + " ! fai un altra ricerca ");
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss a").create();
		String json = gson.toJson(listaAsteAperte);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
