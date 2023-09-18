package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.ProductBean;
import it.unisa.model.ProductModelDS;
import it.unisa.utils.Utility;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public UpdateProductServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	      
		int code = Integer.parseInt(request.getParameter("code"));
	    String name = request.getParameter("name");
	    String description = request.getParameter("description");
	    int price = Integer.parseInt(request.getParameter("price"));

	    ProductBean newProduct = new ProductBean();
	    int minus = -1;
	    newProduct.setName("");
	    newProduct.setDescription("");
	    newProduct.setPrice(minus);
	    newProduct.setCode(code);
	    
	    Utility.print(newProduct.toString());
	    
	    if(name!=null)
	    	newProduct.setName(name);
	    if(description!=null)
	    	newProduct.setDescription(description);
	    if(price!=minus)
	    	newProduct.setPrice(price);


		ProductModelDS model = new ProductModelDS(ds);
	    // Aggiorna il nuovo prodotto nel database
	    try {
			model.doUpdate(newProduct);
		} catch (SQLException e) {
			Utility.print(e);
			
			request.setAttribute("error", e.getMessage());
		}

	    // Effettua il redirect alla pagina di visualizzazione dei prodotti
	    response.sendRedirect("AdminDashboard.jsp");
	  }
		
	

}
