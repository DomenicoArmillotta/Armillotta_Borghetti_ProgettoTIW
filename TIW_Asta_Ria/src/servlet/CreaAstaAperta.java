package servlet;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Base64;
import java.util.Date;

import java.lang.String;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;

import org.apache.commons.lang.StringEscapeUtils;

import beans.Utente;
import dao.AstaApertaDao;

import misc.ConnectionHandler;

@MultipartConfig
@WebServlet("/CreaAstaAperta")
public class CreaAstaAperta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public CreaAstaAperta() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	String generateBase64Image(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String b64Image = null;
		Part part = request.getPart("immagine");
		try(InputStream fileContent = part.getInputStream();) {
			byte[] a = Base64.getEncoder().encode(fileContent.readAllBytes());
			b64Image = new String(a);
		}
		return b64Image;
	}

	// DA MODIFICARE ATTENZIONE VERSIONE NON JAVASCRIPT
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String titolo = null;
		String descrizione = null;
		float prezzo_iniziale = 0;
		float minimo_rialzo = 0;
		Date data_apertura = null;
		Date data_scadenza = null;
		// immagine da aggiustare
		String immagine = null;
		try {
			immagine = generateBase64Image(request, response);
		} catch (IOException | ServletException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore nel parsing dell'immagine");
			return;
		}

		// prendo i parametri dalla post della pagine html
		// try {
		titolo = StringEscapeUtils.escapeJava(request.getParameter("nome"));
		descrizione = StringEscapeUtils.escapeJava(request.getParameter("descrizione"));

		try {
			prezzo_iniziale = Float.parseFloat(request.getParameter("prezzo_iniziale").toString());
		} catch (NumberFormatException ne) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("prezzo iniziale non e' un numero");
			return;
		}

		try {
			minimo_rialzo = Float.parseFloat((String) request.getParameter("minimo_rialzo"));
		} catch (NumberFormatException ne) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("minimo rialzo deve essere un numero ");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			data_apertura = (Date) sdf.parse(request.getParameter("data_apertura"));
		} catch (ParseException e1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("data apertura nel formato non corretto ");
			return;
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			data_scadenza = (Date) sdf2.parse(request.getParameter("data_scadenza"));
		} catch (ParseException e1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("data chiusura non corretta");
			return;
		}

		if (prezzo_iniziale < 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect  param values prezzo iniziale can not be < 0 ");
			return;
		}
		if (minimo_rialzo < 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect  param values minimo rialzo can not be < 0 ");
			return;
		}
		if (data_scadenza.before(data_apertura)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect  param date data scadenza can not be before data apertura ");
			return;
		}
		if (data_apertura.before((Date) request.getSession().getAttribute("timestampLogin"))) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect  param date data apertura can not be before login");
			return;
		}

		// creo l'asta del Database
		// recupero utete dalla sessione ma non va ===== Utente utente = (Utente)
		
		// aste aperte
		AstaApertaDao dao = new AstaApertaDao(connection);
		Utente utente = (Utente) request.getSession().getAttribute("utente");

		try {
			dao.creaAsta(titolo, descrizione, prezzo_iniziale, minimo_rialzo, utente.getId_utente(), data_apertura,
					data_scadenza, immagine);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("non riesco a creare l'asta");
			return;
		} catch (ParseException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore durante il parsing della data");
			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

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
