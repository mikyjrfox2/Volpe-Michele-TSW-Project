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


@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Delete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u=(User)request.getSession().getAttribute("user");
		if(u!=null && u.isAdmin()) {
			String par=(String)request.getParameter("par");
			String id=(String)request.getParameter("id");
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			switch(par) {
			case "vg":
				VideogameDAO vgDao=new VideogameDAO(ds);
				model.Videogame v=new model.Videogame();
				v.setTitle(id);
				try {
					vgDao.doDelete(v);
					request.getSession().setAttribute("mess", "Videogioco eliminato correttamente");
					response.sendRedirect(response.encodeRedirectURL("management.jsp"));
				}catch(SQLException e) {
					System.out.println(e);
					request.getSession().setAttribute("mess", "Errore durante l'eliminazione del videogioco: "+e.getMessage());
					response.sendRedirect(response.encodeRedirectURL("management.jsp"));
				}
				break;
			case "user":
				UserDAO uDao=new UserDAO(ds);
				User us=new User();
				us.setEmail(id);
				if(!u.getEmail().equals(us.getEmail())) {
					try {
						uDao.doDelete(us);
						request.getSession().setAttribute("mess", "Utente eliminato correttamente");
						response.sendRedirect(response.encodeRedirectURL("management.jsp"));
					}catch(SQLException e) {
						System.out.println(e);
						request.getSession().setAttribute("mess", "Errore durante l'eliminazione dell'utente: "+e.getMessage());
						response.sendRedirect(response.encodeRedirectURL("management.jsp"));
					}
				}else {
					request.getSession().setAttribute("mess", "Impossibile eliminare l'account attuale");
					response.sendRedirect(response.encodeRedirectURL("management.jsp"));
				}
				break;
			}
		}else {
			response.sendError(403);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
