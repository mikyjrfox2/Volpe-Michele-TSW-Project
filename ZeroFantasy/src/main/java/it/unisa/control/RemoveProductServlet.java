package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.ProductModelDS;
import it.unisa.utils.Utility;

@WebServlet("/RemoveProductServlet")
public class RemoveProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
	
    int productCode = Integer.parseInt(request.getParameter("productCode"));
    ProductModelDS model = new ProductModelDS(ds);

    try {
    	model.doDelete(productCode);		 	
    
  }catch(SQLException e){
		Utility.print(e);
		request.setAttribute("error", e.getMessage());
		}
    response.sendRedirect("./AdminDashboard.jsp");
} }