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

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Home() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		VideogameDAO vDao=new VideogameDAO(ds);
		
		try {
			ArrayList<model.Videogame> piucomprati=vDao.doRetrieveAll("nAcq DESC");
			ArrayList<model.Videogame> recenti=vDao.doRetrieveAll("data_agg DESC");

			try {
				while(true) {
					piucomprati.remove(6);
					recenti.remove(6);
				}
			}catch(IndexOutOfBoundsException ie) {}
			
			request.setAttribute("comprati", piucomprati);
			request.setAttribute("recenti", recenti);
			
		}
		catch(SQLException e) {
			System.out.println(e);
			response.sendError(0);
		}
		request.getRequestDispatcher(response.encodeRedirectURL("home.jsp")).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
