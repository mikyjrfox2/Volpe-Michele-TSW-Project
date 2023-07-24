package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.*;
import model.Videogame;

@WebServlet("/Edit")
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 10485760, maxRequestSize = 52428800)
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Edit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(403);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		User u = (User) request.getSession().getAttribute("user");

		String type = (String) request.getParameter("type");

		if (type.equals("user") && u != null && u.isAdmin()) {
			String nick = request.getParameter("nick");
			String pass = request.getParameter("pass");
			if (!pass.equals(u.getPassword()))
				pass = Base64.getEncoder().encodeToString(pass.getBytes());
			String name = request.getParameter("nome");
			String bio = request.getParameter("bio");
			String img = request.getPart("immagine").getSubmittedFileName();

			if (!img.equals(u.getProfilePic()) && !img.equals("")) {
				String path = "C:\\Users\\Gdg\\git\\GGWP\\GGWP\\src\\main\\webapp\\profilepics";
				String picName = null;
				Part pic = request.getPart("immagine");
				if (pic != null) {
					picName = request.getPart("immagine").getSubmittedFileName();
					if (!picName.equals(""))
						pic.write(path + File.separator + picName);
				}
				u.setProfilePic(img);
			}

			u.setBio(bio);
			u.setName(name);
			u.setNickname(nick);
			u.setPassword(pass);

			request.getSession().setAttribute("user", u);

			UserDAO uDao = new UserDAO(ds);
			try {
				uDao.doUpdate(u);
				request.getSession().setAttribute("mess", "Informazioni account modificate correttamente!");
			} catch (SQLException e) {
				System.out.println(e);
				request.getSession().setAttribute("mess",
						"Errore interno. Informazioni account non modificate: " + e.getMessage());
			}
			response.sendRedirect(response.encodeRedirectURL("user.jsp"));
		} else {
			if (u != null && u.isAdmin()) {
				String title = request.getParameter("titolo");
				String desc = request.getParameter("desc");
				double prezzo = Double.parseDouble(request.getParameter("prezzo"));
				Part img = request.getPart("img");
				String video = request.getParameter("video");
				String generi[] = request.getParameter("gens").split("-");
				model.Videogame vg = null;
				try {
					VideogameDAO vgDao = new VideogameDAO(ds);
					vg = vgDao.doRetrieveByKey(title);
					vg.setTitle(title);
					vg.setDescription(desc);
					vg.setPrice(prezzo);
					String path = "C:\\Users\\Gdg\\git\\GGWP\\GGWP\\src\\main\\webapp\\img_exe_games";
					String picName = vg.getImage();
					Part pic = request.getPart("img");
					if (pic != null && !pic.getSubmittedFileName().equals(picName) && !request.getPart("img").getSubmittedFileName().equals("")) {
						picName = request.getPart("img").getSubmittedFileName();
						if (!picName.equals(""))
							pic.write(path + File.separator + picName);
						
					}
					vg.setImage(picName);
					vg.setVideo(video);

					vgDao.doUpdate(vg);
				} catch (SQLException e) {
					System.out.println(e);
					request.getSession().setAttribute("mess",
							"Errore interno. Informazioni videogioco non modificate: " + e.getMessage());
				}

				VgGenDAO vgGenDao = new VgGenDAO(ds);
				VgGen vgGen = new VgGen();
				try {
					ArrayList<VgGen> lista = vgGenDao.doRetrieveAll("");
					ArrayList<VgGen> generiVg = new ArrayList<VgGen>();

					for (VgGen gen : lista) // recupero tutti i generi del vg
						if (gen.getVg().equals(vg.getTitle()))
							generiVg.add(gen);

					for (int i = 0; i < generiVg.size(); i++) { // elimino ogni genere del vg non presente nei generi
																// passati dal form
						boolean trovato = false;
						for (String s : generi)
							if (s.equals(generiVg.get(i).getGenere()))
								trovato = true;
						if (!trovato)
							vgGenDao.doDelete(generiVg.get(i));
					}

					for (String g : generi) {
						boolean trovato = false;
						for (int i = 0; i < generiVg.size(); i++)
							if (g.equals(generiVg.get(i).getGenere()))
								trovato = true;
						if (!trovato) {
							vgGen.setGenere(g);
							vgGen.setVg(vg.getTitle());
							vgGenDao.doSave(vgGen);
						}
					}
					request.getSession().setAttribute("mess", "Informazioni videogioco modificate correttamente!");
				} catch (SQLException e) {
					System.out.println(e);
					request.getSession().setAttribute("mess",
							"Errore interno. Informazioni videogioco non modificate: " + e.getMessage());
				}
				response.sendRedirect(response.encodeRedirectURL("management.jsp"));
			} else
				response.sendError(403);
		}
	}

}
