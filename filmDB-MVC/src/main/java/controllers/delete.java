package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Film;
import database.FilmDAO;

/**
 * A controller to handle requests to delete data
 * 
 * @author Louis Alder
 */
@WebServlet("/delete")
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * A method which handles the post request to delete a film by ID
	 * 
	 * @throws SQLException
	 * 
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// parses string into int
		int filmID = Integer.parseInt(request.getParameter("id"));

		// creates new dao instance
		FilmDAO dao = new FilmDAO();

		// creates new film
		Film f = new Film();
		f.setId(filmID);

		try {
			// calls deleteFilm method
			dao.deleteFilm(f);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// redirects user back to main page
		response.sendRedirect("./films");
	}

}
