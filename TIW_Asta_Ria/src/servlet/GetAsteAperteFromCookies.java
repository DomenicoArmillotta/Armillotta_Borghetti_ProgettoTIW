package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.AstaAperta;
import dao.AstaApertaDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class GetAsteAperteFromCookies
 */
@WebServlet("/GetAsteAperteFromCookies")
public class GetAsteAperteFromCookies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAsteAperteFromCookies() {
		super();
		// TODO Auto-generated constructor stub
	}

	// inizializza e si connette al Datavase
	public void init() throws ServletException {

		connection = ConnectionHandler.getConnection(getServletContext());

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String rawCookieString = request.getParameter("cookieAstaIdList");
		String[] parti = rawCookieString.split(" ");
		AstaApertaDao dao = new AstaApertaDao(connection);
		List<AstaAperta> astaApertaLista = new ArrayList<AstaAperta>();
		List<AstaAperta> toReturn = new ArrayList<AstaAperta>();

		List<String> id = new ArrayList<String>();

		astaApertaLista = null;

		for (int i = 0; i < parti.length; i++) {
			if (!parti[i].equals("vendo"))
				id.add(parti[i]);
		}

		try {
			astaApertaLista = dao.getAllAste();
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore interno al database");
			return;
		}

		// cherrypicking
		for (String ids : id) {
			for (AstaAperta a : astaApertaLista) {
				if (a.getAsta_id() == Integer.parseInt(ids))
					toReturn.add(a);
			}
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss a").create();
		String json = gson.toJson(toReturn);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
