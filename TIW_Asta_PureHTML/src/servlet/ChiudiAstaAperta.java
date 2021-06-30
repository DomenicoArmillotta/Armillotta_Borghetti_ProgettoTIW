package servlet;

import java.io.IOException;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.AstaAperta;
import beans.Utente;
import dao.AstaApertaDao;
import misc.ConnectionHandler;



@WebServlet("/ChiudiAstaAperta")
public class ChiudiAstaAperta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine te ;
 
    public ChiudiAstaAperta() {
        super();
        // TODO Auto-generated constructor stub
    }
    
  //inizializza e si connette al Datavase
public void init() throws ServletException {
		
		connection = ConnectionHandler.getConnection(getServletContext());
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.te = new TemplateEngine();
		this.te.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get and check params	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer asta_id = null;
		try {
			asta_id = Integer.parseInt(request.getParameter("asta_id"));
		} catch (NumberFormatException | NullPointerException e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
			return;
		}
		
		AstaApertaDao dao = new AstaApertaDao(connection);
		AstaAperta astaAperta = null;
		
		//get asteAperte
		try {
			astaAperta = dao.getAstaApertaById(asta_id);
		} catch (SQLException e1) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "internal db error ");
			return;
		}
		
		if (astaAperta == null) {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "non esiste asta con questo id");
			return;
		}
		//controllo se lìasta è sua dato che puo modificare dall'url
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if(!astaAperta.getId_utente().equals( utente .getId_utente() ))
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "you can not close this auction");
			return;
		}
		/*
		if(astaAperta.getScadenza().before((Date) request.getAttribute("timestampLogin")))
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "you can not close this auction before the scadenza");
			return;
		} */
		
		//chiude l'asta selezionata e la posiziona nelle aste Chiuse eliminandola dalle asta aperta
		try {
			dao.closeAsta(asta_id,utente.getIndirizzo_spedizione());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return view
		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/GoToVendita";
		response.sendRedirect(path);
	}
	
	
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
