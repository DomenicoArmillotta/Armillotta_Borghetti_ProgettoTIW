package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AstaAperta;
import beans.Utente;
import dao.AstaApertaDao;
import misc.ConnectionHandler;


@WebServlet("/CloseAstaAperta")
@MultipartConfig
public class CloseAstaAperta  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	private Connection connection = null;

	public CloseAstaAperta() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recupero asta_id passato dal chimante
		Integer asta_id = null;
		try {
			asta_id = Integer.parseInt(request.getParameter("asta_id"));
		} catch (NumberFormatException | NullPointerException e) {
			// for debugging only e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect param values");
			return;
		}
		
		//controllo asta aperta esistenza
		AstaApertaDao dao = new AstaApertaDao(connection);
		AstaAperta astaAperta = null;
		try {
			astaAperta = dao.getAstaApertaById(asta_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore interno del database");
			return;
		}
		
		if (astaAperta == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Errore asta gia chiusa o non esistente");
			return;
		}
		
		
		
		//controllo che l'asta sia sua DA FARE
		HttpSession session = request.getSession();
		Utente user = (Utente) session.getAttribute("utente");
		
		
		
		//chiude l'asta selezionata e la posiziona nelle aste Chiuse eliminandola dalle asta aperta
		try {
			dao.closeAsta(asta_id,user.getIndirizzo_spedizione());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Asta non chiudibile");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Errore di Parsing");
			e.printStackTrace();
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
	
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
