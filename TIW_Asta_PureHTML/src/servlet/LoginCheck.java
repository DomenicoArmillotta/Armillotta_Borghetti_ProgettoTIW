package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.Utente;
import dao.UtenteDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private TemplateEngine te ;
       
    /**
     * @return 
     * @throws ServletException 
     * @see HttpServlet#HttpServlet()
     */
	
	public void init() throws ServletException {
		conn = ConnectionHandler.getConnection(getServletContext());
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.te = new TemplateEngine();
		this.te.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
		
	}
	
    public LoginCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = "";
		String psw = "";
		String path = null;
				
		
		//parsiamo la form e estraiamo le cose che ci servono
		try {
			username = (String) request.getParameter("username");
			psw = (String) request.getParameter("password");
		}catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		//query al database
		UtenteDao ud = new UtenteDao(conn);
		Utente utente = new Utente();
		utente = null;
		
		try {
			utente = ud.getUtente(username);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db internal error");
			return;
		}
		
		if(utente == null) {
			//l'utente non è registrato opppure ha messo credenziali sbagliateù
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "errore di formattazione credenziali");
			return;
		}
		else if(utente.getPassword().equals( psw)) {
			
			//utente è correttamente registrato
			Date timestampLogin = null;
			timestampLogin =  Calendar.getInstance().getTime();
			request.getSession().setAttribute("timestampLogin",timestampLogin);
			request.getSession().setAttribute("utente", utente);
	
			path = getServletContext().getContextPath() + "/GetHomepage";
			response.sendRedirect(path);
			}	
		
			else if(!utente.getPassword().equals( psw)){
				final WebContext ctw = new WebContext(request, response, getServletContext(),request.getLocale());
				ctw.setVariable("wrongCredential", "Utente riconosciuto ma la password inserita è sbagliata, redirect alla pagina di login");
				//response.sendRedirect(getServletContext().getContextPath()+"/index.html");
				te.process("/index.html", ctw ,response.getWriter());
			}
		}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * do post , riceve un form.
	 * se le credenziali sono corrette, fa redirect alla homepage html scritta con template tymeleaf
	 * se scorrette ritorna in dientro mandando un mex di errore all'utente
	 * 
	 * uso UtenteDao
	 * 
	 * salvare l'utente nella sessione 
	 * 
	 */
}
