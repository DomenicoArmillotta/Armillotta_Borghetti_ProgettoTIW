package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

import beans.Utente;
import misc.ConnectionHandler;

/**
 * Servlet implementation class GetHomepage
 */
@WebServlet("/GetHomepage")
public class GetHomepage extends HttpServlet {
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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHomepage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginpath = getServletContext().getContextPath() + "/index.html";
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		
		String timestamp = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		//si ringazia  michele per il suo operato caritatevole nello scoprire i metodi più oscuri di java.util.Date
		
		if (session.isNew() || session.getAttribute("utente") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		if(request.getSession().getAttribute("asta_id_for_offerta") != null)
			request.getSession().removeAttribute("asta_id_for_offerta");
		
		//user.setId_utente(session.getAttribute("username").toString());
		final WebContext ctw = new WebContext(request, response, getServletContext(),request.getLocale());
		ctw.setVariable("currdata", timestamp);
		String path = "/ThyMeLeafTemplate/Home.html";
		te.process(path, ctw ,response.getWriter());
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
