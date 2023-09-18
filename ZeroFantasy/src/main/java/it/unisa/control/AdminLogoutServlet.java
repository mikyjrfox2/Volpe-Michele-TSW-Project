package it.unisa.control; // Assicurati che il package sia corretto

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name="AdminLogoutServlet",urlPatterns= {"/AdminLogoutServlet"})
public class AdminLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        // Effettua il logout dell'amministratore
        HttpSession session = request.getSession();
        session.invalidate();

        // Reindirizza alla pagina di login dell'amministratore
        response.sendRedirect("home.jsp");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			doPost(request, response);
		}

	
}
