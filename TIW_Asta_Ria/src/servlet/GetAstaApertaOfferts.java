package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import dao.OffertaDao;
import misc.ConnectionHandler;
import beans.Offerta;

@WebServlet("/GetAstaApertaOfferts")
public class GetAstaApertaOfferts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetAstaApertaOfferts() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer asta_id = null;
		OffertaDao offertaDao = new OffertaDao(connection);
		List<Offerta> listaOfferte = new ArrayList<Offerta>();
		listaOfferte = null;


		try {
			asta_id = Integer.parseInt(request.getParameter("asta_id"));
		} catch (NumberFormatException | NullPointerException e) {
			// only for debugging e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect param values");
			return;
		}

		// tutte le offerte dell'asta dall'asta id
		
		try {
			listaOfferte = offertaDao.getAllOfferteByAstaId(asta_id);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore interno del database");
			return;
		}

		// String immagine = astaAperta.getImg();

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		String json = gson.toJson(listaOfferte);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	// chiude la connessione
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
