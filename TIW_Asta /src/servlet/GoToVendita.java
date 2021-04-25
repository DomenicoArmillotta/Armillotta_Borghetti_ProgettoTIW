package servlet;
import java.io.IOException;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.UnavailableException;
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
import beans.AstaChiusa;
import beans.Offerta;
import beans.Utente;
import dao.AstaApertaDao;
import dao.AstaChiusaDao;
import dao.OffertaDao;
import misc.ConnectionHandler;


@WebServlet("/GoToVendita")
public class GoToVendita extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine te ;

	
	public GoToVendita() {
		super();
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
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		


		String path = "/ThyMeLeafTemplate/Vendita.html";
		//creo lista ASTE APERTE con la query del database grazie al dao
		AstaApertaDao astaDao = new AstaApertaDao(connection);
		List<AstaAperta> listaAsteAperte= new ArrayList<AstaAperta>();
		listaAsteAperte= astaDao.getAllAsteByUser(utente.getId_utente());
		
		
		
		
		
		//creo lista ASTE CHIUSE con la query del database grazie al dao
		AstaChiusaDao Chiusadao = new AstaChiusaDao(connection);
		List<AstaChiusa> listaAsteChiuse = new ArrayList<AstaChiusa>();
		listaAsteChiuse=Chiusadao.getAllAsteByUser(utente.getId_utente());
		
		
				
		//creo il webContext
		final WebContext ctx = new WebContext(req, res, getServletContext(),res.getLocale());
				
						
		//setto la variabile da ciclare nel html dinamico e lo processo
		ctx.setVariable("listaAsteAperte",listaAsteAperte);
		
	
		//setto la variabile da ciclare nel html dinamico e lo processo
		ctx.setVariable("listaAsteChiuse",listaAsteChiuse);
		if(listaAsteChiuse==null)
		{
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "lista nulla");
		}else {
			te.process(path, ctx ,res.getWriter());

		}
		
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
