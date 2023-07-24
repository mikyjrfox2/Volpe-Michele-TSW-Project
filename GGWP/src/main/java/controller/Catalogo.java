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

@WebServlet("/Catalogo")
public class Catalogo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Catalogo() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

		String search = (String) request.getParameter("search");
		String gen = (String) request.getParameter("gen");
		VideogameDAO vgDao = new VideogameDAO(ds);
		GenreDAO gnr = new GenreDAO(ds);

		try {
			if (gen != null) {
				VgGenDAO vggenDao=new VgGenDAO(ds);
				ArrayList<VgGen> lista= vggenDao.doRetrieveAll(gen);
				ArrayList<model.Videogame> vg = new ArrayList<model.Videogame>();
				
				for(VgGen vgg:lista) 
					vg.add(vgDao.doRetrieveByKey(vgg.getVg()));
				
				request.setAttribute("videogames", vg);
			} else if (search == null)
				request.setAttribute("videogames", vgDao.doRetrieveAll(""));
			else {
				ArrayList<model.Videogame> lista = vgDao.doRetrieveAll("");
				ArrayList<model.Videogame> vg = new ArrayList<model.Videogame>();
				for (model.Videogame e : lista)
					if (e.getTitle().toLowerCase().contains(search.toLowerCase()) || e.getTitle().equalsIgnoreCase(search))
						vg.add(e);
				request.setAttribute("videogames", vg);
			}
			request.setAttribute("generi", gnr.doRetrieveAll(""));
		} catch (SQLException e) {
			System.out.println(e);
			response.sendError(500);
		}
		request.getRequestDispatcher(response.encodeRedirectURL("catalogo.jsp")).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
