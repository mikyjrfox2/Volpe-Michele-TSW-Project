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

import org.json.simple.JSONObject;

import model.*;

@WebServlet("/Carrello")
public class Carrello extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Carrello() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		/*	nel caso di assenza di cookie
		 * DataSource ds = (DataSource) request.getServletContext().getAttribute("DataSource");
		UserDAO uDao=new UserDAO(ds);
		User u = null;
		try {
			u=(User) uDao.doRetrieveByKey(request.getParameter("user"));
		}catch(SQLException e) {
			System.out.println(e);
		}*/
		boolean flag;
		DataSource ds = (DataSource) request.getServletContext().getAttribute("DataSource");
		if (u != null) {
			String t = request.getParameter("title");
			if (t == null) {
				response.sendRedirect(response.encodeRedirectURL("catalogo.jsp"));
				return;
			}

			CartDAO cartDao = new CartDAO(ds);
			Cart c = new Cart();

			if (request.getParameter("del") == null) { //inserimento nel carrello
				JSONObject object = new JSONObject();

				ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");	//andrebbe recuperato dal db in caso di assenza di cookie

				VideogameDAO vgDao = new VideogameDAO(ds);
				try {
					model.Videogame vg = vgDao.doRetrieveByKey(t);
					c.setEmail(u.getEmail());
					c.setImg(vg.getImage());
					c.setVg(vg.getTitle());
					c.setCost(vg.getPrice());
					cartDao.doSave(c);
					cart.add(c);
					flag = true;
				} catch (SQLException e) {
					System.out.println(e);
					if(t!=null)
						response.sendRedirect(response.encodeRedirectURL("Catalogo"));
					flag = false;
				}
				
				object.put("flag", flag);
				object.put("numElements", cart.size()); // aggiorna il contatore del carrello
				response.setContentType("application/json");
				response.getWriter().println(object);
			} else {	//eliminazione dal carrello
				c.setEmail(u.getEmail());
				c.setVg(t);
				try {
					cartDao.doDelete(c); // cancella dal db
					ArrayList<Cart> cart=new ArrayList<Cart>();
					ArrayList<Cart> lista = cartDao.doRetrieveAll("");
					for (Cart cr : lista)
						if (cr.getEmail().equals(u.getEmail()))
							if (!cr.isPurchased())
								cart.add(cr);
					request.getSession().setAttribute("cart", cart); // aggiorna il carrello

					response.sendRedirect(response.encodeRedirectURL("carrello.jsp"));
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} else {
			request.setAttribute("error", "Accedi per poter inserire elementi o visualizzare il carrello");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
