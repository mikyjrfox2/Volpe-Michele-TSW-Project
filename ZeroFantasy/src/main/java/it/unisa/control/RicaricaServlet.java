package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;
import it.unisa.utils.Utility;

@WebServlet("/RicaricaServlet")
public class RicaricaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RicaricaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
	    UserModelDS model = new UserModelDS(ds);

	    User newWallet = new User();
	    int nw = (int) request.getSession().getAttribute("wallet");
	    newWallet.setPortafoglio(Integer.parseInt(request.getParameter("ricarica")));
	    newWallet.setUsername((String)request.getSession().getAttribute("username"));
	    
	    try {
	    	model.doUpdate(newWallet);		 	
	    
	  }catch(SQLException e){
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
			}
	    

		request.getSession().setAttribute("wallet", nw + newWallet.getPortafoglio());
	   
		request.getRequestDispatcher("CarrelloServlet").forward(request, response);
	}

}
