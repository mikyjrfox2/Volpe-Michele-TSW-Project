package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;
import it.unisa.utils.*;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CarrelloServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
    	
    	ArrayList<Carrello> pb = new ArrayList<>();
    	CarrelloModelDS model = new CarrelloModelDS(ds);
    	String un=(String) request.getSession().getAttribute("username");
    	
    	Utility.print(un);

        try {
        	Collection<Carrello> al = model.doRetrieveAll(false);
        	for (Iterator<Carrello> i = al.iterator();i.hasNext();) {
        		Carrello elemento1 = (Carrello)i.next();
        		if(un.equals(elemento1.getUsername()))
        			pb.add(elemento1);
        	}


        	Utility.print(pb.toString());
        // Imposta i risultati come attributo della richiesta
        request.setAttribute("searchResults", pb);

        // Inoltra la richiesta e i risultati alla pagina JSP per la visualizzazione
        getServletContext().getRequestDispatcher(response.encodeRedirectURL("/user.jsp")).forward(request, response);
        }catch(SQLException e){
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
			}
	}

}
