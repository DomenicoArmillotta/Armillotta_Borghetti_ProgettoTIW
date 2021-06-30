package servlet;

import java.io.IOException;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.AstaAperta;
import beans.Offerta;
import beans.Utente;
import dao.AstaApertaDao;
import dao.OffertaDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class GoToAstaApertaForAuction
 */
@WebServlet("/GoToAstaApertaForOfferta")
public class GoToAstaApertaForOfferta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private TemplateEngine te;

	public void init() throws ServletException {
		conn = ConnectionHandler.getConnection(getServletContext());
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.te = new TemplateEngine();
		this.te.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoToAstaApertaForOfferta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int asta_id = 0;

		OffertaDao od = new OffertaDao(conn);
		AstaApertaDao ad = new AstaApertaDao(conn);

		// se carichi per la prima volta la pg allora peschi dalla request, else peschi
		// asta_id dalla sessione
		asta_id = Integer.parseInt(request.getParameter("asta_id"));
		
		//controllo che l'id dell asta sia un id valido passato per attributo
		AstaAperta asta = null;
		
		try {
			asta = ad.getAstaApertaById(asta_id);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db internal error");
			return;

		}
		
		if(asta==null)
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"id_asta non esistente");
		
	
		List<Offerta> o = null;
		
		try {
			o = od.getAllOfferteByAstaId(asta_id);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"internal db error");
			return;
		}

		String path = "/ThyMeLeafTemplate/OffertaPerAstaAperta.html";
		final WebContext ctw = new WebContext(request, response, getServletContext(), request.getLocale());
		ctw.setVariable("offerte", o);
		ctw.setVariable("astaid", asta_id);
		te.process(path, ctw, response.getWriter());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		/*
		 * //faccio redirect senza pietà String path = "/TIW_Asta/RedirectOffert";
		 * String offerta = request.getParameter("offerta"); String l =
		 * request.getParameter("asta_id"); session.setAttribute("offerta", offerta);
		 */
		OffertaDao o = new OffertaDao(conn);
		AstaApertaDao astaApertaDao = new AstaApertaDao(conn);
		int asta_id = Integer.parseInt(request.getParameter("asta_id"));
		Utente username = (Utente) session.getAttribute("utente");
		String new_off = (String) request.getParameter("offerta");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedDate = null;

		AstaAperta a = null;
		
		try {
			a = astaApertaDao.getAstaApertaById(asta_id);
		} catch (SQLException e1) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"internal db error");
			return;
		}

		
		float offerta_inserita = Float.parseFloat(new_off);
		float offerta_max_database = a.getMax_offerta();
		float minimo_rialzo = a.getMinimo_rialzo();

		try {
			parsedDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (offerta_inserita <= offerta_max_database) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, " offert too low ");
			return;
		}
		if (offerta_inserita <= 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, " offert can not be <= 0 ");
			return;
		}
		
		if(offerta_inserita < minimo_rialzo) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, " offert cant be lower than the minimum upside ");

		}

		try {
			o.createNewOffert(asta_id, new_off, username.getId_utente(), parsedDate);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, " errore nella creazione dell'offerta ");
			return;
		}
	
		response.sendRedirect(
				session.getServletContext().getContextPath() + "/GoToAstaApertaForOfferta?asta_id=" + asta_id + "");
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// in asta aperta un campo asta aperta che contiene la max offerta, che aggiona
	// il database

}
