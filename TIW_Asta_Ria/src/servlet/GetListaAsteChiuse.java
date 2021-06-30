package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.AstaChiusa;

import beans.Utente;

import dao.AstaChiusaDao;

import misc.ConnectionHandler;

@WebServlet("/GetListaAsteChiuse")

public class GetListaAsteChiuse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetListaAsteChiuse() {
		super();
	}

	// inizializza e si connette al Datavase
	public void init() throws ServletException {

		connection = ConnectionHandler.getConnection(getServletContext());

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("utente");

		// creo lista ASTE CHIUSE con la query del database grazie al dao
		AstaChiusaDao ChiusaDao = new AstaChiusaDao(connection);
		List<AstaChiusa> listaAsteChiuse = new ArrayList<AstaChiusa>();
		listaAsteChiuse = null;

		try {
			listaAsteChiuse = ChiusaDao.getAllAsteByUser(utente.getId_utente());
		} catch (SQLException e) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.getWriter().println("errore interno del database");
			return;
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy MMM dd").create();
		String json = gson.toJson(listaAsteChiuse);

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(json);

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
