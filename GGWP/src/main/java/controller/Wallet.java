package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.*;

@WebServlet("/Wallet")
public class Wallet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Wallet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=(User)request.getSession().getAttribute("user");
		if(user!=null) {
			String im=(String)request.getParameter("importo");
			if(!im.equals("")) {
				double importo;
				try {
					importo=Double.parseDouble(im);
				}catch(NumberFormatException e) {
					response.sendRedirect(response.encodeRedirectURL("ricarica.jsp"));
					return;
				}
				if(importo>0) {
					DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
					UserDAO dao=new UserDAO(ds);
					user.setWallet(user.getWallet()+importo);
					try {
						dao.doUpdate(user);
					}catch(SQLException e) {
						System.out.println(e);
						response.sendError(500);
					}
					request.getSession().setAttribute("user", user);
					request.getSession().setAttribute("mess", "Ricarica avvenuta con successo");
					response.sendRedirect(response.encodeRedirectURL("carrello.jsp"));
					return;
				}
			}
			request.getSession().setAttribute("mess", "Importo errato");
			response.sendRedirect(response.encodeRedirectURL("ricarica.jsp"));
		}else {
			request.setAttribute("error", "Accedi per aggiungere fondi al tuo portafoglio");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
