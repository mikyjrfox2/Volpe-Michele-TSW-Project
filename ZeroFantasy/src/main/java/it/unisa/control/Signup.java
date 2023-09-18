package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;


@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=(DataSource) getServletContext().getAttribute("DataSource");
		UserModelDS udao=new UserModelDS(ds);
		
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String rPass=request.getParameter("psw-repeat");
		
		 // Interrompi l'esecuzione del codice se l'username non è valido
		if (!isAlphanumeric(username)) {
		    request.setAttribute("error", "L'username deve contenere solo caratteri alfanumerici!");
		    request.getRequestDispatcher("/signup.jsp").forward(request, response);
		    return;
		}
		
		User u = new User();
		boolean aExists=false;
		
		if(rPass.equals(password)) {
			u.setUsername(username);
			u.setPassword(password);
			u.setTipo("user");
			u.setPortafoglio(0);
			try {
				udao.doSave(u);
				request.setAttribute("reg", true);
			}catch(SQLIntegrityConstraintViolationException ie) {
				request.setAttribute("error", "Esiste gia' un account collegato a questo Username!");
				aExists =true;
				System.out.println(ie);
			}catch (SQLException e) {
				request.setAttribute("error", e.getMessage());
				System.out.println(e);
			}
				if(aExists) {
					request.getRequestDispatcher("/signup.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("/AdminLogin.jsp").forward(request, response);
				}
			}else {
			request.setAttribute("error", "Le password non corrispondono");
			request.getRequestDispatcher("/signup.jsp").forward(request, response);
		}
	}

	private boolean isAlphanumeric(String str) {
	    return str.matches("^[a-zA-Z0-9]+$");
	}

}
