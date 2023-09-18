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

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
	  DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
      
	int code = Integer.parseInt(request.getParameter("code"));
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    int price = Integer.parseInt(request.getParameter("price"));

    ProductBean newProduct = new ProductBean();
    newProduct.setCode(code);
    newProduct.setName(name);
    newProduct.setDescription(description);
    newProduct.setPrice(price);


	ProductModelDS model = new ProductModelDS(ds);
    // Salva il nuovo prodotto nel database
    try {
		model.doSave(newProduct);
	} catch (SQLException e) {
		Utility.print(e);
		
		request.setAttribute("error", e.getMessage());
	}

    // Effettua il redirect alla pagina di visualizzazione dei prodotti
    response.sendRedirect("AdminDashboard.jsp");
  }
}
