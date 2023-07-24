package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.*;

@WebServlet("/Add")
@MultipartConfig(fileSizeThreshold=0, maxFileSize = 10485760, maxRequestSize = 52428800)
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Add() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(403);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		String tipo=request.getParameter("type");
		if (tipo!=null) {	//controllo se è un'aggiunta di un vg o di un genere
			model.Videogame vg = new model.Videogame();

			vg.setDescription(request.getParameter("desc"));
			vg.setDeveloper(request.getParameter("dev"));
			Part img=request.getPart("img");
			String path = "C:\\Users\\Gdg\\git\\GGWP\\GGWP\\src\\main\\webapp\\img_exe_games";
			if(!img.getSubmittedFileName().equals(""))
				img.write(path + File.separator + img.getSubmittedFileName());
			vg.setImage(img.getSubmittedFileName());
			vg.setPrice(Double.parseDouble(request.getParameter("prezzo")));
			vg.setPublisher(request.getParameter("pub"));
			Date pubDate = new Date(0);
			String data[] = request.getParameter("pubDate").split("-");
			pubDate.setYear(Integer.parseInt(data[0]) - 1900);
			pubDate.setMonth(Integer.parseInt(data[1]) - 1);
			pubDate.setDate(Integer.parseInt(data[2]));
			vg.setReleaseDate(pubDate);
			vg.setTitle(request.getParameter("titolo"));
			vg.setVideo(request.getParameter("video"));

			VideogameDAO vgDao = new VideogameDAO(ds);
			VgGenDAO vgGenDao = new VgGenDAO(ds);
			VgGen vgGen = new VgGen();
			vgGen.setVg(vg.getTitle());
			String generi[] = request.getParameter("gens").split("-");

			try {
				vgDao.doSave(vg);
				for (String s : generi) {
					vgGen.setGenere(s);
					vgGenDao.doSave(vgGen);
				}
				request.getSession().setAttribute("mess", "Videogioco aggiunto correttamente");
			} catch (SQLException e) {
				System.out.println(e);
				request.getSession().setAttribute("mess", "Errore durante l'aggiunta del videogioco: "+e.getMessage());
				
			}
		}else {
			GenreDAO genre=new GenreDAO(ds);
			try {
				Genre g=new Genre();
				g.setType(request.getParameter("genere"));
				genre.doSave(g);
				request.getSession().setAttribute("mess", "Genere aggiunto correttamente");
			}catch(SQLException e) {
				System.out.println(e);
				request.getSession().setAttribute("mess", "Errore durante l'aggiunta del genere: "+e.getMessage());
			}
		}
		response.sendRedirect(response.encodeRedirectURL("management.jsp"));
	}

}
