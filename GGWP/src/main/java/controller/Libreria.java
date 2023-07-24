package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.*;

@WebServlet("/Libreria")
public class Libreria extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public Libreria() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		User u=(User)request.getSession().getAttribute("user");
		if(u==null) {
			response.sendError(403);
			return;
		}
		String userEmail=u.getEmail();
		CartDAO dao=new CartDAO(ds);
		ArrayList<Cart> libreria=new ArrayList<Cart>();
		
		try {
			ArrayList<Cart> cart=dao.doRetrieveAll("");
			
			for(Cart c:cart)
				if(c.getEmail().equals(userEmail))
						if(c.isPurchased())
							libreria.add(c);
			request.getSession().setAttribute("libr", libreria);
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		request.getRequestDispatcher("/libreria.jsp").forward(request, response);
	}

}