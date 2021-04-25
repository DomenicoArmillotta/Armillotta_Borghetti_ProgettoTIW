package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

import beans.Offerta;
import dao.OffertaDao;
import misc.ConnectionHandler;

/**
 * Servlet implementation class GoToAstaApertaForAuction
 */
@WebServlet("/GoToAstaApertaForOfferta")
public class GoToAstaApertaForOfferta extends HttpServlet {
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
    public GoToAstaApertaForOfferta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int asta_id = 0;
		
		OffertaDao od = new OffertaDao(conn);
		
		//se carichi per la prima volta la pg allora peschi dalla request, else peschi asta_id dalla sessione
		if(request.getSession().getAttribute("asta_id_for_offerta") == null) {
			asta_id = Integer.parseInt(request.getParameter("asta_id"));
		}else
			asta_id = (int)request.getSession().getAttribute("asta_id_for_offerta");
		
		List<Offerta> o = od.getAllOfferteByAstaId(asta_id);
		
		request.getSession().setAttribute("asta_id_for_offerta", asta_id);
		request.getSession().setAttribute("offerta", request.getParameter("offerta"));
		
		
		
		String path = "/ThyMeLeafTemplate/OffertaPerAstaAperta.html";
		final WebContext ctw = new WebContext(request, response, getServletContext(),request.getLocale());
		ctw.setVariable("offerte", o);
		te.process(path, ctw,response.getWriter());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		//faccio redirect senza pietà
		String path = "/TIW_Asta/RedirectOffert";
		String offerta = request.getParameter("offerta");
		session.setAttribute("offerta", offerta);
		
		
		
		response.sendRedirect(path);
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//in asta aperta un campo asta aperta che contiene la max offerta, che aggiona il database
	
}
