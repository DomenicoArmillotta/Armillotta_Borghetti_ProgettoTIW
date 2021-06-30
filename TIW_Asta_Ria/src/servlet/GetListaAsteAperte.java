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

import beans.AstaAperta;

import beans.Utente;
import dao.AstaApertaDao;

import misc.ConnectionHandler;

@WebServlet("/GetListaAsteAperte")
public class GetListaAsteAperte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetListaAsteAperte() {
		super();
	}

	// inizializza e si connette al Datavase
	public void init() throws ServletException {

		connection = ConnectionHandler.getConnection(getServletContext());

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("utente");

		// creo lista ASTE APERTE con la query del database grazie al dao
		AstaApertaDao astaDao = new AstaApertaDao(connection);
		List<AstaAperta> listaAsteAperte = new ArrayList<AstaAperta>();

		try {
			listaAsteAperte = astaDao.getAllAsteByUser(utente.getId_utente());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		String json = gson.toJson(listaAsteAperte);

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
