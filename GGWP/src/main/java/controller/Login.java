package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Cart;
import model.CartDAO;
import model.User;
import model.UserDAO;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		String un= request.getParameter("email");
		String pw= request.getParameter("password");
		if(un!=null && pw!=null && !un.equals("") && !pw.equals("")) {

			UserDAO user = new UserDAO(ds);
			User u = null;
			try {
				u = user.doRetrieveByKey(un);
				
				byte[] pass=Base64.getDecoder().decode(u.getPassword().getBytes());	//decodifica password
				String encPass=new String(pass);
				
				if (encPass.equals(pw)) {
					request.getSession().setAttribute("user", u);
					request.getSession().setAttribute("primo", true);
					ArrayList<Cart> cart = new ArrayList<Cart>();
					ArrayList<Cart> libreria=new ArrayList<Cart>();
	
					String userEmail = u.getEmail();
					CartDAO dao = new CartDAO(ds);
	
					try {
						ArrayList<Cart> lista = dao.doRetrieveAll("");
						for (Cart c : lista)
							if (c.getEmail().equals(userEmail)) {
								if (!c.isPurchased())
									cart.add(c);
								else
									libreria.add(c);
							}
						request.getSession().setAttribute("cart", cart);
						request.getSession().setAttribute("libr", libreria);
						response.sendRedirect(response.encodeRedirectURL("Home"));
						return;
					} catch (SQLException e) {
						System.out.println(e);
					}
				} else
					request.setAttribute("error", "Password errata. Si prega di riprovare.");
	
			} catch (SQLException e) {
				request.setAttribute("error", "Utente non trovato. Si prega di riprovare.");
			}
		}else 
			request.setAttribute("error", "Inserire parametri");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
