package servlet;

import java.io.IOException;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
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


@WebServlet("/GoToAstaApertaDetails")
public class GoToAstaApertaDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine te ;
       
 
    public GoToAstaApertaDetails() {
        super();
        // TODO Auto-generated constructor stub
    }
    
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
		Integer asta_id = null;
		asta_id = Integer.parseInt(request.getParameter("asta_id"));
		HttpSession session = request.getSession();
		Utente user = (Utente) session.getAttribute("utente");
		AstaApertaDao dao = new AstaApertaDao(connection);
		AstaAperta astaAperta = null;
		String path = "/ThyMeLeafTemplate/DettagliAstaAperta.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		//tutte le offerte dell'asta
		OffertaDao offertaDao = new OffertaDao (connection);
		List<Offerta> listaOfferte= new ArrayList<Offerta>();
		listaOfferte = null;
		ctx.setVariable("listaOfferte",listaOfferte);

		try {
			listaOfferte = offertaDao.getAllOfferteByAstaId(asta_id);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db internal error");

		}
		
		//anche se utente modifica asta_id non è un problema
		
		
		//dettagli asta aperta
		try {
			astaAperta=dao.getAstaApertaById(asta_id);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db internal error");
			return;
		}
		
		
		if(astaAperta == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Resource not found");
			return;
		}
		if(!astaAperta.getId_utente().equals(user.getId_utente())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not the owner");
			return;
		}
		
		String immagine = astaAperta.getImg();
        //reinderizza alla pagina dettagli asta chiusa
        ctx.setVariable("astaAperta", astaAperta);
        ctx.setVariable("immagine", immagine);
        te.process(path, ctx, response.getWriter());	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
