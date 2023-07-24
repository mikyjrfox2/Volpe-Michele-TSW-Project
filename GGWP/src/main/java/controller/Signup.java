package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.User;
import model.UserDAO;


@WebServlet("/Signup")
@MultipartConfig(fileSizeThreshold=0, maxFileSize = 10485760, maxRequestSize = 52428800)
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Signup() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=(DataSource) getServletContext().getAttribute("DataSource");
		UserDAO udao=new UserDAO(ds);
		
		String path = "C:\\Users\\Gdg\\git\\GGWP\\GGWP\\src\\main\\webapp\\profilepics";
		String picName=null;
		Part pic=request.getPart("profilepic");
		if(pic!=null) {
			picName=request.getPart("profilepic").getSubmittedFileName();
			if(!picName.equals("")) 
				pic.write(path + File.separator + picName);
		}
		
		String nick=request.getParameter("nickname");
		String email=request.getParameter("email");
		String pass=request.getParameter("psw");
		String rPass=request.getParameter("psw-repeat");
		String name=request.getParameter("name");
		
		boolean em=email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
		boolean pss=pass.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");
		boolean pssR=rPass.equals(pass);
		boolean nm=name.matches("^[a-zA-Z\\s]+");
		
		User u;
		if(em && pss && pssR && nm) {
			u = new User();
			u.setBio("");
			u.setEmail(email);
			u.setName(name);
			u.setNickname(nick);
			pass=Base64.getEncoder().encodeToString(pass.getBytes());	//codifica password
			u.setPassword(pass);
			u.setPoints(0);
			u.setRole("user");
			u.setWallet(0);
			if(!picName.equals(""))
				u.setProfilePic(picName);
			else
				u.setProfilePic("user.png");
			try {
				udao.doSave(u);
				request.setAttribute("reg", true);
			}catch(SQLIntegrityConstraintViolationException ie) {
				request.setAttribute("error", "Esiste gia' un account collegato a questa e-mail!");
				System.out.println(ie);
			}catch (SQLException e) {
				request.setAttribute("error", e.getMessage());
				System.out.println(e);
			}
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			request.setAttribute("error", "Parametri errati");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

}
