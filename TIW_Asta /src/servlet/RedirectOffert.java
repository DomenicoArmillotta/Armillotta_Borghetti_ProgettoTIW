package servlet;

import java.io.IOException;

import java.sql.Connection;
 
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.Utente;
import dao.AstaApertaDao;
import dao.OffertaDao;
import misc.ConnectionHandler;


/**
 * Servlet implementation class RedirectOffer
 */

@WebServlet("/RedirectOffert")
public class RedirectOffert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private TemplateEngine te ;
	
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
    public RedirectOffert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    // se siamo arrivati a questo punto vuol dire che sappiamo gia che i valori sono validi
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		OffertaDao o = new OffertaDao(conn);
		AstaApertaDao astaApertaDao = new AstaApertaDao(conn);
		int asta_id = (int) session.getAttribute("asta_id_for_offerta");
		Utente username = (Utente)session.getAttribute("utente");
		String new_off = (String) session.getAttribute("offerta");
		SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedDate = null;
		
		
		try {
			parsedDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		

		o.createNewOffert(asta_id, new_off, username.getId_utente(), parsedDate);
		o.updateMaxOffertaInAstaAperta(Float.parseFloat(new_off), asta_id);

		response.sendRedirect(session.getServletContext().getContextPath()+"/GoToAstaApertaForOfferta");
	} 	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
