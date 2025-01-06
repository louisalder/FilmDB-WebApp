package controllers;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Film;
import database.FilmDAO;

/**
 * A controller to handle doGET and do post methods for adding new films and
 * getting films to add to the table in the jsp
 * 
 * @author Louis Alder
 * 
 */

@WebServlet("/films")

public class filmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * A class which does the doGET. It grabs all the films and sends them to the
	 * films.jsp in the table
	 * 
	 *
	 * @throws ServletException, IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// new dao object created
		FilmDAO dao = new FilmDAO();

		// creates an arraylist, and adds all the films in the db via the dao
		ArrayList<Film> allFilms = dao.getAllFilms();
		request.setAttribute("films", allFilms);
		RequestDispatcher rd = request.getRequestDispatcher("films.jsp");

		rd.include(request, response);
		response.sendRedirect("./films");

	}

	/**
	 * A class which does the doPOST. It handles new films which are added into the
	 * DB by calling the insertFilm method from the DAO
	 * 
	 *
	 * @throws ServletException, IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// gets all the parameters and puts them into variables
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String stars = request.getParameter("stars");
		String review = request.getParameter("review");

		// creates a new dao
		FilmDAO dao = new FilmDAO();
		// creates a new film
		Film f = new Film();

		// uses setters to set film title, year, director etc. Uses the variables above
		f.setTitle(title);
		f.setYear(Integer.parseInt(year));
		f.setDirector(director);
		f.setStars(stars);
		f.setReview(review);

		try {
			//calls the insert film method, and sends the film f which was created above
			dao.insertFilm(f);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
