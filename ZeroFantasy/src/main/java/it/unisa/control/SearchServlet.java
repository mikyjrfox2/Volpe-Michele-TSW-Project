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

import it.unisa.model.ProductBean;
import it.unisa.model.ProductModelDS;
import it.unisa.utils.*;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
// Ottieni il testo di ricerca dalla query della richiesta
    	
    	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        String query = request.getParameter("query");
        if(query==null)
        	query="";

		 Utility.print(query);
        ArrayList<ProductBean> pb = new ArrayList<>();
        ProductModelDS model = new ProductModelDS(ds);

        try {
        	Collection<ProductBean> al = model.doRetrieveAll();
        	for (Iterator<ProductBean> i = al.iterator();i.hasNext();) {
        		ProductBean elemento1 = (ProductBean)i.next();
        		if(query=="") {
        			pb.add(elemento1);
        		}else if(elemento1.getName().toLowerCase().equals(query.toLowerCase())) {
					 pb.add(elemento1);
			 	}
        	}

        // Imposta i risultati come attributo della richiesta
        request.setAttribute("searchResults", pb);

        // Inoltra la richiesta e i risultati alla pagina JSP per la visualizzazione
        getServletContext().getRequestDispatcher(response.encodeRedirectURL("/ProductView.jsp")).forward(request, response);
        }catch(SQLException e){
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
			}
	}
}


