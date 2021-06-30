package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.AstaAperta;
import dao.AstaApertaDao;
import misc.ConnectionHandler;


@WebServlet("/GetAstaApertaDetails")
public class GetAstaApertaDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	
	public GetAstaApertaDetails() {
		super();
	}
	
	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		

		AstaApertaDao dao = new AstaApertaDao(connection);
		AstaAperta astaAperta = null;

		
		try {
			astaAperta=dao.getAstaApertaById(asta_id);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("errore interno al database");
			return;
		}
		if(astaAperta == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("non ci sono aste per questo id");
			return;
		}
		
		response.setStatus(HttpServletResponse.SC_OK);

		Gson gson = new GsonBuilder()
				   .setDateFormat("yyyy-MM-dd hh:mm:ss a").create();
		String json = gson.toJson(astaAperta);
		
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
