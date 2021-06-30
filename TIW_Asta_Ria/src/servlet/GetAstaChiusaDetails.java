package servlet;
import java.io.IOException;




import java.sql.Connection;
import java.sql.SQLException;

import java.lang.String;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.AstaChiusa;
import beans.Utente;
import dao.AstaChiusaDao;
import misc.ConnectionHandler;

@WebServlet("/GetAstaChiusaDetails")
public class GetAstaChiusaDetails extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	
	public GetAstaChiusaDetails() {
		super();
	}
	
	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer asta_id = null;
		//prendo parametro dalla richiesta
		try {
		asta_id = Integer.parseInt(request.getParameter("asta_id"));
		} catch (NumberFormatException | NullPointerException e) {
			// only for debugging e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Incorrect param values");
			return;
		}
		
		//prendo la sessione
		HttpSession session = request.getSession();
		Utente user = (Utente) session.getAttribute("utente");
		AstaChiusaDao dao = new AstaChiusaDao(connection);
		AstaChiusa astaChiusa = null;
		try {
			astaChiusa=dao.getAstaById(asta_id);
			if(astaChiusa == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("asta_id non trovato");
				return;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to recover mission");
			return;
		}
		if(!astaChiusa.getId_utente().equals(user.getId_utente())){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("non sei autorizzato");
			return;
		}
		

		Gson gson = new GsonBuilder()
				   .setDateFormat("yyyy-MM-dd hh:mm:ss a").create();
		String json = gson.toJson(astaChiusa);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
		
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
