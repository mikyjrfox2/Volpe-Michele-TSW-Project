package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.ProductModelDS;
import it.unisa.utils.Utility;

@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
				
				ProductModelDS model = new ProductModelDS(ds);
				
				try {
					request.setAttribute("product", model.doRetrieveAll());
					
					RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/home.jsp");
					dispatcher.forward(request, response);
				} catch(SQLException e) {
					Utility.print(e);
					
					request.setAttribute("error", e.getMessage());
				}
				
			}

}
