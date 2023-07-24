package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONArray;

import model.*;

@WebServlet("/Management")
public class Management extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public Management() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user=(User)request.getSession().getAttribute("user");
		
		if(user!=null) {
			if(user.isUser())
				response.sendError(403);
			else {
				response.setContentType("application/json");
				DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
				String manage=(String)request.getParameter("manage");
				JSONArray lista=new JSONArray();
				
				try {
					switch(manage) {
					case "user":
						UserDAO uDao=new UserDAO(ds);
						for(User u:uDao.doRetrieveAll(""))
							lista.add(UserJSON.map(u));
						response.getWriter().println(lista);
						break;
					case "vg":
						VideogameDAO vgDao=new VideogameDAO(ds);						
						for(model.Videogame v:vgDao.doRetrieveAll("")) 
							lista.add(VideogameJSON.map(v));
						response.getWriter().println(lista);
						break;
					case "generi":
						GenreDAO genre=new GenreDAO(ds);
						for(Genre g:genre.doRetrieveAll("")) 
							lista.add(GenreJSON.map(g));
						response.getWriter().println(lista);
						break;
					default: response.sendRedirect(response.encodeRedirectURL("management.jsp"));
					}
				}catch(SQLException e) {
					System.out.println(e);
					response.sendError(500);
				}
			}		
		}else {
			response.sendError(403);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
