package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;


import beans.Utente;
import dao.UtenteDao;
import misc.ConnectionHandler;


@WebServlet("/LoginCheck")
@MultipartConfig

public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
       
   
	
	public void init() throws ServletException {
		conn = ConnectionHandler.getConnection(getServletContext());
		
		
	}
	
    public LoginCheck() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String username =null;
		String password = null;
		
		username = StringEscapeUtils.escapeJava(request.getParameter("username"));
		password = StringEscapeUtils.escapeJava(request.getParameter("password"));
		
		if (username == null || password == null || username.isEmpty() || password.isEmpty() ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.getWriter().println("Credentials must be not null");
			return;
		}
		
		//query al database
		UtenteDao utenteDao = new UtenteDao(conn);
		Utente utente = null;
		utente = utenteDao.getUtente(username);	
		
		if(utente == null) {
			//l'utente non è registrato opppure ha messo credenziali sbagliateù
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "errore di formattazione credenziali");
			
		}
		else if(utente.getPassword().equals( password)) {
			
			//utente è correttamente registrato
			Date timestampLogin = null;
			timestampLogin =  Calendar.getInstance().getTime();
			request.getSession().setAttribute("timestampLogin",timestampLogin);
			request.getSession().setAttribute("utente", utente);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println(username);
		}	
		
			else if(!utente.getPassword().equals( password)){
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().println("Incorrect credentials");
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
