package it.unisa.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/UsernameValidationServlet")
public class UsernameValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UsernameValidationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        boolean isValid = isUsernameValid(username);

        // Creare una risposta JSON
        JSONObject jsonResponse = new JSONObject();
        try {
			jsonResponse.put("isValid", isValid);
		} catch (JSONException e) {
			e.printStackTrace();
		}

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    private boolean isUsernameValid(String username) {
         return username.matches("^[a-zA-Z0-9]+$");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
