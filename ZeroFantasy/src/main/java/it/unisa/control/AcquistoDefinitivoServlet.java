package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.*;
import it.unisa.utils.Utility;

@WebServlet("/AcquistoDefinitivoServlet")
public class AcquistoDefinitivoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcquistoDefinitivoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility.print("Sto comprando per: " + (String) request.getSession().getAttribute("username"));
		Utility.print(request.getParameter("name"));
		Utility.print(request.getParameter("price"));
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		int prezzo = Integer.parseInt(request.getParameter("price"));
		int portafoglio = (int)request.getSession().getAttribute("wallet");
		Utility.print("prezzo:" + prezzo +", portafoglio:"+portafoglio);
		String currentDate = LocalDate.now().toString();
	    Carrello carrelloCode = new Carrello();
	    carrelloCode.setName(request.getParameter("name"));
	    carrelloCode.setUsername((String)request.getSession().getAttribute("username"));
	    carrelloCode.setAcquistato(true);
	    carrelloCode.setData(currentDate);
	    CarrelloModelDS model = new CarrelloModelDS(ds);

	    UserModelDS model2 = new UserModelDS(ds);

	    User newWallet = new User();
	    int nw = (int) request.getSession().getAttribute("wallet");
	    newWallet.setPortafoglio(portafoglio-prezzo);
	    newWallet.setUsername((String)request.getSession().getAttribute("username"));
	    try {
	    if(prezzo<=portafoglio) {
	    	model.doUpdate(carrelloCode);

	    	model2.doUpdate(newWallet);	
	    	
	    }

	    
	  }catch(SQLException e){
			Utility.print(e);
			request.getSession().setAttribute("error", e.getMessage());
			}

	    if(prezzo<=portafoglio) {
			request.getSession().setAttribute("wallet", newWallet.getPortafoglio());
	    	response.sendRedirect("Libreria");}
	    else {

	    	request.setAttribute("error", "Effettua una ricarica");
	    	request.getRequestDispatcher("CarrelloServlet").forward(request, response);}
	}

}
