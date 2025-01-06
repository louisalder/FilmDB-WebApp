
package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAO;
import models.Film;

/**
 * Servlet to handle any updates
 * 
 * @author Louis Alder
 * 
 * 
 */

@WebServlet("/update")
public class update extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * A method which handles the post request to update a film by ID
	 * 
	 * @throws SQLException
	 * 
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		FilmDAO dao = new FilmDAO();
		Film f = dao.getFilmByID(Integer.parseInt(id));

		// Prints all parameters which are sent in the request
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("title"));
		System.out.println(request.getParameter("director"));
		System.out.println(request.getParameter("year"));
		System.out.println(request.getParameter("stars"));
		System.out.println(request.getParameter("review"));

		// puts the parameters into variables which cam be used later
		String title = request.getParameter("title");
		String director = request.getParameter("director");
		int year = Integer.parseInt(request.getParameter("year"));
		String stars = request.getParameter("stars");
		String review = request.getParameter("review");

		// uses the setters to give the modified film new titles, years etc.
		f.setTitle(title);
		f.setYear(year);
		f.setDirector(director);
		f.setStars(stars);
		f.setReview(review);

		try {

			// calls the update film method, and inserts the film with the modified inputs
			dao.updateFilm(f);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// redirects user back to main page
		response.sendRedirect("./films");
	}

}
