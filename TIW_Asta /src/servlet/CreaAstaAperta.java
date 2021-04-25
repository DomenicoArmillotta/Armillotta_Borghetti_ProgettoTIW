package servlet;

import java.io.IOException;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.AstaChiusa;
import beans.Utente;
import dao.AstaApertaDao;
import dao.AstaChiusaDao;
import misc.ConnectionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CreaAstaAperta")
public class CreaAstaAperta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine te ;
    
    public CreaAstaAperta() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
	
		boolean isBadRequest = false;
		String titolo = null;
		String descrizione = null;
		float prezzo_iniziale = 0;
		float minimo_rialzo = 0;
		Date data_apertura = null;
		Date data_scadenza = null;
		
		//prendo i parametri dalla post della pagine html
		try {
			titolo = StringEscapeUtils.escapeJava(request.getParameter("nome"));
			descrizione = StringEscapeUtils.escapeJava(request.getParameter("descrizione"));
			prezzo_iniziale=Float.parseFloat(request.getParameter("prezzo_iniziale"));
			minimo_rialzo=Float.parseFloat(request.getParameter("minimo_rialzo"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			data_apertura = (Date) sdf.parse(request.getParameter("data_apertura"));
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			data_scadenza = (Date) sdf2.parse(request.getParameter("data_scadenza"));
		} catch (NumberFormatException | NullPointerException e) {
			isBadRequest = true;
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isBadRequest) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect or missing param values");
			return;
		}
		
		//creo l'asta del Database		
		// recupero utete dalla sessione ma non va      =====        Utente utente = (Utente) session.getAttribute("Utente");
		// Integer.parseInt(utente.getId_utente())   === da mettere nel costruttore di aste aperte
		AstaApertaDao dao = new AstaApertaDao(connection);
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		
			dao.creaAsta(titolo, descrizione, prezzo_iniziale, minimo_rialzo , utente.getId_utente() ,data_apertura,data_scadenza);
		
			
		
		
		//ritorno la pagine dove è la lista con la servlet
		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/GoToVendita";
		response.sendRedirect(path);
		
		
		
		
	}
	
	
	
	
	//chiude la connessione
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
