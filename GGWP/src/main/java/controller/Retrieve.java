package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/Retrieve")
public class Retrieve extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Retrieve() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		if (u != null) {
			if (u.isAdmin()) {
				response.setContentType("application/json");
				DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
				JSONObject obj = new JSONObject();
				String id = request.getParameter("id");
				String par = request.getParameter("par");
				try {
					VideogameDAO vgDao = new VideogameDAO(ds);
					VgGenDAO vgGen=new VgGenDAO(ds);
					GenreDAO genreDao=new GenreDAO(ds);
					model.Videogame vg = vgDao.doRetrieveByKey(id);	//recupero vg da modificare
					
					ArrayList<VgGen> gen=vgGen.doRetrieveAll("");	//recupero generi
					ArrayList<String> generiVg=new ArrayList<String>();	//generi del videogioco
					ArrayList<String> generiNonVg=new ArrayList<String>();	//generi non del videogioco
					for(Genre g:genreDao.doRetrieveAll(""))
						generiNonVg.add(g.getType());
					for(VgGen g:gen)
						if(g.getVg().equals(vg.getTitle())) {
							generiVg.add(g.getGenere());
							generiNonVg.remove(g.getGenere());
						}
					
					obj.put("titolo", vg.getTitle());
					obj.put("desc", vg.getDescription());
					obj.put("prezzo", vg.getPrice());
					obj.put("img", vg.getImage());
					obj.put("video", vg.getVideo());
					obj.put("generiVg",generiVg);
					obj.put("generiNonVg", generiNonVg);
					
				} catch (SQLException e) {
					System.out.println(e);
				}
				response.getWriter().println(obj);
			} else
				response.sendError(403);
		} else
			response.sendError(403);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
