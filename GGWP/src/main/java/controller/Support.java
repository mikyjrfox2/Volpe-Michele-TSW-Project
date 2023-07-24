package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.*;

@WebServlet("/Support")
public class Support extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Support() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		boolean rem=true;
		if (user != null) {
			String title = (String) request.getParameter("title");
			if (title != null) {
				ArrayList<Cart> libr = (ArrayList<Cart>) request.getSession().getAttribute("libr");
				for (Cart c : libr)
					if (c.getVg().equals(title)) {
						Date today = new Date();
						long maxDays = 14 * 1000 * 60 * 60 * 24; // rimborso entro 14 giorni
						if ((today.getTime() - c.getPurchaseDate().getTime()) <= maxDays) {
							DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
							CartDAO cDao = new CartDAO(ds);
							UserDAO uDao = new UserDAO(ds);
							try {
								cDao.doDelete(c);
								user.setWallet(user.getWallet()+c.getActualCost());	//rimborsa i soldi spesi
								if(c.getActualCost()==c.getCost())	//se non è stato applicato sconto
									if(user.getPoints()-(int)c.getActualCost()<=0)	//per non scendere sotto 0
										user.setPoints(0);
									else
										user.setPoints(user.getPoints()-(int)c.getActualCost());	//tolgi punti guadagnati grazie all'acquisto
								uDao.doUpdate(user);
							} catch (SQLException e) {
								System.out.println(e);
								rem=false;
							}
							if(rem)
								request.getSession().setAttribute("mess", String.format("%.2f",c.getActualCost())+"\\u20AC rimborsati correttamente");
							response.sendRedirect((response.encodeRedirectURL("Libreria")));
						} else {
							request.setAttribute("error", "");
							request.getRequestDispatcher(response.encodeRedirectURL("Videogame?title=" + c.getVg())).forward(request, response);;
						}
					}
			} else {
				response.sendRedirect(response.encodeRedirectURL("catalogo.jsp"));
			}
		} else {
			request.setAttribute("error", "Accedi per richiedere supporto");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
