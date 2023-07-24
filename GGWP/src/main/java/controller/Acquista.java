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

@WebServlet("/Acquista")
public class Acquista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Acquista() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("carrello.jsp"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		if (u != null) {
			String price = (String) request.getParameter("price");
			double costo = 0;
			if (price != null) {
				costo = Double.parseDouble(price.substring(0, price.length() - 1));
				if (costo >= 0) {
					if (u.getWallet() >= costo) {
						DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
						CartDAO cartDao = new CartDAO(ds);
						UserDAO uDao = new UserDAO(ds);
						ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
						double sconto=0;
						if (request.getParameter("pts") != null)
							while ((sconto + 0.25) < costo && u.getPoints() > 0) {	//per ogni punto togli 25 cent
								sconto += 0.25;
								u.setPoints(u.getPoints() - 1);
							}
						else 
							u.setPoints(u.getPoints() + (int)costo);
						
						u.setWallet(u.getWallet() - costo + sconto);
						try {
							uDao.doUpdate(u);
							VideogameDAO vgDao=new VideogameDAO(ds);
							for (Cart c : cart) {
								if (sconto>0)
									c.setActualCost(c.getCost() - (sconto/cart.size()));
								else
									c.setActualCost(c.getCost());
								cartDao.doUpdate(c);
								model.Videogame vg=vgDao.doRetrieveByKey(c.getVg());
								vg.setnAcq(vg.getnAcq()+1);
								vgDao.doUpdate(vg);
							}
							
							cart.removeAll(cart);
							request.getSession().setAttribute("mess", "Goditi i tuoi nuovi acquisti!");
							response.sendRedirect((response.encodeRedirectURL("Libreria")));
						} catch (SQLException e) {
							System.out.println(e);
							request.getSession().setAttribute("mess", "Errore durante l'acquisto");
							response.sendRedirect((response.encodeRedirectURL("carrello.jsp")));
						}
					} else {
						request.setAttribute("wallet", "Il tuo portafoglio non ha abbastanza fondi. Qui puoi aggiungerne quanti ne vuoi!");
						request.getRequestDispatcher(response.encodeRedirectURL("ricarica.jsp")).forward(request,
								response);
					}
				} else {
					response.sendRedirect(response.encodeRedirectURL("carrello.jsp"));
				}
			} else {
				response.sendRedirect(response.encodeRedirectURL("carrello.jsp"));
			}
		} else {
			request.setAttribute("error", "Accedi per effettuare acquisti");
			request.getRequestDispatcher(response.encodeRedirectURL("login.jsp")).forward(request, response);
		}
	}

}
