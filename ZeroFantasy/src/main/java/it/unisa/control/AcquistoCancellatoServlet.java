package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;
import it.unisa.utils.Utility;

@WebServlet("/AcquistoCancellatoServlet")
public class AcquistoCancellatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcquistoCancellatoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility.print("Nome utente: "+(String) request.getSession().getAttribute("username"));
		Utility.print("Nome prodotto: "+(String) request.getParameter("name"));
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
	    Carrello carrelloCode = new Carrello();
	    carrelloCode.setName(request.getParameter("name"));
	    carrelloCode.setUsername((String)request.getSession().getAttribute("username"));
	    CarrelloModelDS model = new CarrelloModelDS(ds);

	    try {
	    	model.doDelete(carrelloCode);		 	
	    
	  }catch(SQLException e){
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
			}
	    response.sendRedirect("CarrelloServlet");
	}

}
