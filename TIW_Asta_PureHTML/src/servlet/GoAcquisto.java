package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.AstaAperta;
import dao.AstaApertaDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class GoAcquisto
 */
@WebServlet("/GoAcquisto")
public class GoAcquisto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private TemplateEngine te ;
	
	
	public void init() {
		try {
			conn = ConnectionHandler.getConnection(getServletContext());
		} catch (UnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.te = new TemplateEngine();
		this.te.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoAcquisto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final WebContext ctw = new WebContext(request, response, getServletContext(),request.getLocale());
		String path = "/ThyMeLeafTemplate/Acquisto.html";
		te.process(path, ctw ,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//qui immetto la parola chiave
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String keyword = (String) request.getParameter("keyword");
		
		List<AstaAperta> a = new ArrayList<AstaAperta>();
		
		
		AstaApertaDao apertaDao = new AstaApertaDao(conn);
		try {
			a = apertaDao.astaPerKeyword(keyword);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "internal db error");
		}
		final WebContext ctw = new WebContext(request, response, getServletContext(),request.getLocale());
		String path = "/ThyMeLeafTemplate/Acquisto.html";
		
		if(a.size() == 0) {
				ctw.setVariable("NoAsteMsg", "per \""+keyword+"\" non ci sono aste aperte");
		}else{
			//vuol dire che la servlet ha dentro 
			ctw.setVariable("aste", a);
		}
		
		te.process(path, ctw,response.getWriter());
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(conn);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
