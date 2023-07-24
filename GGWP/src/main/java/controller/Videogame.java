package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.*;

@WebServlet("/Videogame")
public class Videogame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Videogame() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		VideogameDAO vg = new VideogameDAO(ds);
		CartDAO dao=new CartDAO(ds);
		
		User u=(User)request.getSession().getAttribute("user");
		request.setAttribute("purchased", false);
		
		String t=(String) request.getParameter("title").split(";")[0];
		
		if(t!=null) {
			try {
				request.setAttribute("videogame", vg.doRetrieveByKey(t));
				if(u!=null) {
					ArrayList<Cart> cart=dao.doRetrieveAll("");
					for(Cart c:cart)
						if(c.getEmail().equals(u.getEmail()) && c.getVg().equals(t) && c.isPurchased())
							request.setAttribute("purchased", true);
				}
				VgGenDAO gen=new VgGenDAO(ds);
				ArrayList<VgGen> gnr=gen.doRetrieveAll("");
				ArrayList<String> generi=new ArrayList<String>();
				for(VgGen g:gnr)
					if(g.getVg().equals(t))
						generi.add(g.getGenere());
				request.setAttribute("generi", generi);
			} catch (SQLException e) {
				request.setAttribute("error", e.getMessage());
			}
			getServletContext().getRequestDispatcher(response.encodeRedirectURL("/videogame.jsp")).forward(request, response);
		}else {
			response.sendRedirect(response.encodeRedirectURL("Catalogo"));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
