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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.*;
import it.unisa.utils.Utility;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			doPost(request, response);
		}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
    
    	request.getSession().setAttribute("isAdminUser", false);
    	String username = request.getParameter("username");
        String password = request.getParameter("password");

        ArrayList<User> pb = new ArrayList<User>();
        UserModelDS model = new UserModelDS(ds);
        boolean il = false;
        int wallet=0;

        boolean ammin = false;
        boolean wrongPsw=false;
        try {
        	Collection<User> al = model.doRetrieveAll();
        	for (Iterator<User> i = al.iterator();i.hasNext();) {
        		User elemento1 = (User)i.next();
				 if(elemento1.getUsername().equals(username)) {
					 if(elemento1.getPassword().equals(password)) {
			        		request.setAttribute("isLogged", true);
			        		il = true;
			        		wallet = elemento1.getPortafoglio();
				        	if(elemento1.getTipo().equals("admin")) {
				        		ammin = true;
				        	}
		            }else {
		            	wrongPsw=true;
		            }
				 }
			 }// Redirect all'area amministratore
        	if(ammin) {
        		request.getSession().setAttribute("isAdminUser", true);
        	}

        	if(il) {
        		request.getSession().setAttribute("username", username);
        		request.getSession().setAttribute("wallet", wallet);
        		request.getRequestDispatcher("/home.jsp").forward(request, response);
        	}else if (wrongPsw){
    			request.setAttribute("error", "Password errata!");
    			request.getRequestDispatcher("/AdminLogin.jsp").forward(request, response);
        	}else {
    			request.setAttribute("error", "Questo account non esiste");
    			request.getRequestDispatcher("/AdminLogin.jsp").forward(request, response);
        	}
        	
        }catch(SQLException e){
			Utility.print(e);
			response.sendRedirect("./AdminLogin.jsp?error=invalid");
        }
    }

}

