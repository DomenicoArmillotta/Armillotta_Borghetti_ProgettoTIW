package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.AstaChiusa;
import beans.Utente;
import dao.AstaChiusaDao;
import misc.ConnectionHandler;



@WebServlet("/GoToAstaChiusaDetails")
public class GoToAstaChiusaDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine te ;
  
    public GoToAstaChiusaDetails() {
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
		
		Integer asta_id = null;
		asta_id = Integer.parseInt(request.getParameter("asta_id"));
		HttpSession session = request.getSession();
		Utente user = (Utente) session.getAttribute("utente");
		AstaChiusaDao dao = new AstaChiusaDao(connection);
		AstaChiusa astaChiusa = null;
		try {
			astaChiusa=dao.getAstaById(asta_id);
			if(astaChiusa == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
				return;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to recover mission");
			return;
		}
		if(!astaChiusa.getId_utente().equals(user.getId_utente())){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not the owner");
			return;
		}
		
		//trovo la lista di tutte le aste dell utente
		//confronto e controllo che sia coerente
		
		
		
		
		//anche se utente modifica asta_id non fa niente
		//reinderizza alla pagina dettagli asta chiusa
		String path = "/ThyMeLeafTemplate/DettagliAstaChiusa.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("astaChiusa", astaChiusa);
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

















