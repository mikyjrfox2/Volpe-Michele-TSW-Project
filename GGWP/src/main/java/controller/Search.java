package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.*;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public Search() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		VideogameDAO vgDao=new VideogameDAO(ds);
		String search=(String)request.getParameter("search");
		try {
			ArrayList<model.Videogame> lista=vgDao.doRetrieveAll("");
			ArrayList<model.Videogame> vg=new ArrayList<model.Videogame>();
			for(model.Videogame e:lista) 
				if(e.getTitle().toLowerCase().contains(search.toLowerCase()) || e.getTitle().equalsIgnoreCase(search))
					vg.add(e);
			
			JSONArray json=new JSONArray();
			for(model.Videogame v:vg) 
				json.add(v.getTitle());
			response.getWriter().println(json);
		}catch(SQLException e) {
			System.out.println(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
