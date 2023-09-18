package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;
import it.unisa.utils.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/AddCarrelloServlet")
public class AddCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCarrelloServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		String name = request.getParameter("name");
		String username = (String)request.getSession().getAttribute("username");
		int price = Integer.parseInt(request.getParameter("price"));
		
		Carrello newCar = new Carrello();
		newCar.setAcquistato(false);
		newCar.setData(LocalDate.now().toString());
		newCar.setName(name);
		newCar.setUsername(username);
		newCar.setPrice(price);
		

		CarrelloModelDS cm = new CarrelloModelDS(ds);
		
		
		Utility.print("Nuovo carrello: "+ newCar.toString());
		
		CarrelloModelDS model = new CarrelloModelDS(ds);
		
		try {
			model.doSave(newCar);
		} catch (SQLException e) {
			Utility.print(e);
			
			request.setAttribute("error", e.getMessage());
		}

		request.getRequestDispatcher("SearchServlet").forward(request, response);
		
	}

}
