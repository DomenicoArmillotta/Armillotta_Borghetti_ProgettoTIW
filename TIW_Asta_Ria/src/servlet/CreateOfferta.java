package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import dao.OffertaDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class CreateOfferta
 */
@WebServlet("/CreateOfferta")
@MultipartConfig
public class CreateOfferta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;

	public void init() throws ServletException {
		conn = ConnectionHandler.getConnection(getServletContext());
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateOfferta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		OffertaDao o = new OffertaDao(conn);
		AstaApertaDao astaApertaDao = new AstaApertaDao(conn);
		int asta_id = Integer.parseInt(request.getParameter("asta_id"));
		Utente username = (Utente) session.getAttribute("utente");
		String new_off = (String) request.getParameter("offerta");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedDate = null;

		AstaAperta a;
		try {
			a = astaApertaDao.getAstaApertaById(asta_id);
		} catch (SQLException e1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore interno al database");
			return;
		}

		float offerta_inserita = 0;
		float offerta_max_database = a.getMax_offerta();
		float minimo_rialzo = a.getMinimo_rialzo();

		// se l'utente inscerisce caratteri non numerici
		try {
			offerta_inserita = Float.parseFloat(new_off);
		} catch (NumberFormatException ne) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("i valori inseriti devono essere solo numeri");
			return;
		}

		try {
			parsedDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));

		} catch (ParseException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore a parsare la data");
			e.printStackTrace();
		}

		if (offerta_inserita <= offerta_max_database) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("offert too low");

			return;
		}
		if (offerta_inserita <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("offert cant be below 0");
			return;
		}
		if (offerta_inserita <= minimo_rialzo) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("offert cant be lower than the minimum upside");
			return;
		}

		try {
			o.createNewOffert(asta_id, new_off, username.getId_utente(), parsedDate);
		} catch (SQLException offertaEx) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore nell aggiornare il database");
			offertaEx.printStackTrace();
			System.out.println("errore madornale");
			return;
		}
		// o.updateMaxOffertaInAstaAperta(Float.parseFloat(new_off), asta_id);

		response.setStatus(HttpServletResponse.SC_OK);

	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
