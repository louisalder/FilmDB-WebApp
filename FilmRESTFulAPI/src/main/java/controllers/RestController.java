package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import database.FilmDAO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.Film;

/**
 * This controller handles all of the requests and responses in the API. It
 * includes methods to parse data into different formats, do GET, POST, PUT and
 * DELETE requests. The API plays a part in the middle of the client web
 * application (or postman) and the Database Access Object. The class calls the
 * DAO at many points to communicate with the DB.
 * 
 * @author Louis Alder
 *
 */

@WebServlet("/filmapi")
public class RestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * This method handles doGET requests. It requests the different headers, such
	 * as the Accept header. It then requests parameters such as year, title it use
	 * series of if statements to obtain the correct data format.
	 *
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String format = null;

		// gets headers and parameters
		String acceptHeader = request.getHeader("Accept");

		String title = request.getParameter("title");
		String id = request.getParameter("id");
		String year = request.getParameter("year");

		// sets the format to a simpler string
		if (acceptHeader.contains("text/plain")) {
			format = "text";
			// sets the response format to text/plain
			response.setContentType("text/plain");

		} else if (acceptHeader.contains("application/json")) {
			format = "json";
			// sets the response format to application/json
			response.setContentType("application/json");
		} else if (acceptHeader.contains("application/xml")) {
			format = "xml";
			// sets the response format to application/xml
			response.setContentType("application/xml");
		}

		if (title == null && id == null && year == null) {
			// if the parameters are all null, it will get all the films
			System.out.println("Searching all");

			// passes in the format
			getAll(format, request, response);
		} else if (title != null) {
			// if there's a title, it will run the search method
			System.out.println("Searching title");
			searchTitle(title, format, request, response);
		} else if (id != null) {
			// if there's an id number, it will run the get ID method
			System.out.println("Searching by ID");
			getWithID(id, format, request, response);
		} else if (year != null) {
			// if there's a year, it will run the search method
			System.out.println("Searching year");
			searchYear(year, format, request, response);
		}

	}

	/**
	 * A method to get the film by searching for similar titles. The title and
	 * format parameters are passed in
	 * 
	 * @param format is passed in to determine which format the film should be
	 *               returned in
	 * 
	 * @param title  is passed in to the SQL query for the searchTitle method on the
	 *               dao
	 * 
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * 
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 */

	private void searchTitle(String title, String format, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		PrintWriter out = response.getWriter();

		if (format != null) {
			if (format.equals("text")) {
				ArrayList<Film> searchFilms = dao.searchFilmsTitle(title);

				String txtoutput = searchFilms.toString();
				System.out.println(txtoutput);
				out.write(txtoutput);
			} else if (format.equals("json")) {
				Gson gson = new Gson();

				try {

					ArrayList<Film> searchFilms = dao.searchFilmsTitle(title);
					String json = gson.toJson(searchFilms);
					out.write(json);
				} catch (JsonParseException e) {
					e.printStackTrace();
				}
			} else if (format.equals("xml")) {
				try {
					ArrayList<Film> searchFilms = dao.searchFilmsTitle(title);
					FilmList fl = new FilmList(searchFilms);

					StringWriter sw = new StringWriter();
					JAXBContext context = JAXBContext.newInstance(FilmList.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					m.marshal(fl, sw);

					System.out.println(sw.toString());
					out.write(sw.toString());

				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			out.close();
		} else {

			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}

		/**
		 * A method to get the film by searching for similar titles. The title and
		 * format parameters are passed in
		 *
		 * @param format is passed in to determine format
		 *
		 * @param year   is passed in to the SQL query
		 *
		 * @throws ServletException throws error in case there is issues with the
		 *                          request
		 *
		 * @throws IOException      throws error in case there is a problem with any of
		 *                          the parameters
		 */

	}

	private void searchYear(String year, String format, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// creates a dao object
		FilmDAO dao = new FilmDAO();

		PrintWriter out = response.getWriter();

		// parses out the year string that is passed from the get request into an int
		int inputYear = Integer.parseInt(year);

		// if format is null, it will default to searching in text format
		// detects format and handles the search as required
		if (format != null) {
			if (format.equals("text")) {
				// creates an array list of all films
				// uses the searchFilmsYear method from the new dao object
				// inputs the year from the user
				// the searchFilmsYear returns the array list of films

				ArrayList<Film> searchFilms = dao.searchFilmsYear(inputYear);

				// creates a to string
				String txtoutput = searchFilms.toString();
				// prints the to string to the console on server
				System.out.println(txtoutput);

				out.write(txtoutput);
			} else if (format.equals("json")) {
				// creates a new gson object
				Gson gson = new Gson();

				try {
					// creates an array list of all films
					// uses the searchFilmsYear method from the new dao object
					// inputs the year from the user
					// the searchFilmsYear returns the array list of films

					ArrayList<Film> searchFilms = dao.searchFilmsYear(inputYear);
					// creates a json string using the toJSON method, called by gson
					String json = gson.toJson(searchFilms);
					// writes the string out
					out.write(json);
				} catch (JsonParseException e) {
					// prints json parse error if there's a problem
					e.printStackTrace();
				}

			} else if (format.equals("xml")) {
				try {
					// creates an array list of all films
					// uses the searchFilmsYear method from the new dao object
					// inputs the year from the user
					// the searchFilmsYear returns the array list of films
					ArrayList<Film> searchFilms = dao.searchFilmsYear(inputYear);
					// creates a film list and adds the films from the array list to it
					FilmList fl = new FilmList(searchFilms);

					// create a string writer
					StringWriter sw = new StringWriter();

					JAXBContext context = JAXBContext.newInstance(FilmList.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					m.marshal(fl, sw);

					System.out.println(sw.toString());
					out.write(sw.toString());

				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			out.close();
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}

	}

	/**
	 * A method to get the film by ID. The ID and format parameters are passed i
	 * Format is passed in to determine which format the film should be returned in
	 * id is passed in to determine which film should be returned
	 * 
	 * @param id, format are both parameters that are passed into the method.
	 * 
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 * 
	 */

	private void getWithID(String id, String format, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		PrintWriter out = response.getWriter();

		if (format != null) {

			if (format.equals("text")) {
				Film film = dao.getFilmByID(Integer.parseInt(id));
				String txtoutput = film.toString();
				System.out.println(txtoutput);
				out.write(txtoutput);
			} else if (format.equals("json")) {

				try {

					Gson gson = new Gson();
					Film film = dao.getFilmByID(Integer.parseInt(id));
					String json = gson.toJson(film);
					out.write(json);
				} catch (JsonParseException e) {
					e.printStackTrace();
				}

			} else if (format.equals("xml")) {
				try {
					Film film = dao.getFilmByID(Integer.parseInt(id));
					StringWriter sw = new StringWriter();
					JAXBContext context = JAXBContext.newInstance(Film.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					m.marshal(film, sw);

					System.out.println(sw.toString());
					out.write(sw.toString());
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			out.close();
		} else {

			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}

	}

	/**
	 * A method to get all the films Format is passed in to determine which format
	 * the film should be returned in.
	 * 
	 * @param format is the selected format the user wants to be returned
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 * 
	 */

	private void getAll(String format, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		PrintWriter out = response.getWriter();

		if (format != null) {
			if (format.equals("text")) {
				ArrayList<Film> allFilms = dao.getAllFilms();

				System.out.println("getting films in txt");

				String txtoutput = allFilms.toString();
				System.out.println(txtoutput);
				out.write(txtoutput);
			} else if (format.equals("json")) {

				System.out.println("getting films in json");

				try {
					Gson gson = new Gson();

					ArrayList<Film> allFilms = dao.getAllFilms();
					String json = gson.toJson(allFilms);
					out.write(json);
				} catch (JsonParseException e) {
					e.printStackTrace();
				}

			} else if (format.equals("xml")) {
				System.out.println("getting films in xml");
				try {
					ArrayList<Film> allFilms = dao.getAllFilms();
					FilmList fl = new FilmList(allFilms);

					StringWriter sw = new StringWriter();
					JAXBContext context = JAXBContext.newInstance(FilmList.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					m.marshal(fl, sw);

					System.out.println(sw.toString());
					out.write(sw.toString());

				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			out.close();
		} else {

			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}

	}

	/**
	 * A method to post a film received by the web application It uses a buffered
	 * reader and string builder to read in content from the body The method takes
	 * in content type and passes the string produced to the appropriate method i.e.
	 * if the content type is application/json, it passes data to the inputJSON()
	 * method.
	 * 
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 * 
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String contentType = request.getHeader("Content-Type");

		System.out.println("Content Type Recieved: " + contentType);

		BufferedReader reader = request.getReader();
		String line = null;

		StringBuilder sb = new StringBuilder();

		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String filmData = sb.toString();

		System.out.println("before:");
		System.out.println(filmData);
		System.out.println("after");
		Film f = new Film();

		if (contentType.equals("text/plain")) {
			f = inputText(filmData);
		} else if (contentType.equals("application/json")) {
			f = inputJSON(filmData);
		} else if (contentType.equals("application/xml")) {
			f = inputXML(filmData);
		} else {
			System.out.println("no content type detected");
		}

		FilmDAO dao = new FilmDAO();
		PrintWriter out = response.getWriter();

		try {

			dao.insertFilm(f);
			out.write("Film inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();

	}

	/**
	 * A method to modify / PUT a film received by the web application It uses a
	 * buffered reader and string builder to read in content from the body The
	 * method takes in content type and passes the string produced to the
	 * appropriate method i.e. if the content type is application/json, it passes
	 * data to the inputJSON() method.
	 * 
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 * 
	 */

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Film f = new Film();

		// gets the content type specified by the user
		String contentType = request.getHeader("Content-Type");

		// prints this for testing purposes
		System.out.println("Content Type Recieved: " + contentType);

		// creates a new buffered reader
		BufferedReader reader = request.getReader();
		String line = null;

		StringBuilder sb = new StringBuilder();

		// splits up the content of the string builder by line, until it stops. It
		// appends the information and adds it to the string builder
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		// sets the String filmdata equal to the toString of the string builder
		String filmData = sb.toString();

		// handles data formats
		if (contentType.equals("text/plain")) {
			f = inputText(filmData);
		} else if (contentType.equals("application/json")) {
			f = inputJSON(filmData);
		} else if (contentType.equals("application/xml")) {
			f = inputXML(filmData);
		} else {
			System.out.println("no content type detected");
		}

		// creates an instance of filmDAO
		FilmDAO dao = new FilmDAO();

		PrintWriter out = response.getWriter();

		try {

			// if successful, it sends 'Film updated' to the client
			dao.updateFilm(f);
			out.write("Film Updated");
		} catch (SQLException e) {

			// sends SQL exception to the console if it fails
			e.printStackTrace();
		}
		out.close();
	}

	/**
	 * A method to send a delete request to the DAO. It takes the ID parameter which
	 * is sent in the request
	 * 
	 * @throws ServletException throws error in case there is issues with the
	 *                          request
	 * @throws IOException      throws error in case there is a problem with any of
	 *                          the parameters
	 */

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Running Delete");

		// if statement checks if the id value is null
		// this was used as during testing I recieved a few null values.

		String id = request.getParameter("id");
		if (id == null) {

			// prints out the following if no id is found
			System.out.println("ID value is null");
		} else {

			// creates an instance of a film
			System.out.println(id);
			Film f = new Film();

			// sets the ID, using integer.value of to turn the string into an integer
			f.setId(Integer.valueOf(id));

			FilmDAO dao = new FilmDAO();

			PrintWriter out = response.getWriter();

			try {
				// sends the film to the deleteFilm method on the dao
				dao.deleteFilm(f);
				// prints if film was successfully deleted on client
				out.write("Film Deleted");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.close();
		}
	}

	/*
	 * This method takes in the xml data from the web application and puts it into
	 * an object using gson
	 * 
	 * @param filmData incoming film data, in string format
	 * 
	 * @return f film object
	 * 
	 */

	private Film inputXML(String filmData) {

		System.out.println("Processing XML Input");

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Film.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(filmData);

			Film f = (Film) jaxbUnmarshaller.unmarshal(reader);
			System.out.println("Testing toString: " + f.toString());
			return f;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * This method takes in the data from the web application and puts it into an
	 * object using string manipulation, splits and replaces
	 * 
	 * @param filmData incoming film data, in string format
	 * 
	 * @return f film object
	 * 
	 * 
	 */

	private Film inputText(String filmData) {

		System.out.println("Processing Txt Input");

		String film = filmData.replace("***", "");
		// removes "***" from the string

		String[] filmElements = film.split("%%");
		// splits up the elements of each film (i.e. title, director etc) using the
		// string "%%" and adds it to the string array

		// creates a new film object
		Film f = new Film();

		// adds the elements into variables
		// uses each value of the string array for this
		String id = filmElements[0].replace("ID: ", "");
		String title = filmElements[1].replace("Title: ", "");
		String year = filmElements[2].replace("Year: ", "");
		String director = filmElements[3].replace("Director: ", "");
		String stars = filmElements[4].replace("Stars: ", "");
		String review = filmElements[5].replace("Review: ", "");

		// adding the values to the film object using the setters
		f.setId(Integer.valueOf(id));
		f.setTitle(title);
		f.setYear(Integer.valueOf(year));
		f.setDirector(director);
		f.setStars(stars);
		f.setReview(review);

		// to string for testing purposes
		System.out.println("Testing toString: " + f.toString());

		// returns the film
		return f;

	}

	/**
	 * This method takes in the json data from the web application and puts it into
	 * an object
	 * 
	 * @param filmData incoming film data, in string format
	 * @return f film object
	 * 
	 */

	private Film inputJSON(String filmData) {

		// prints message to console for testing purposes
		System.out.println("Processing JSON Input");

		// creates new instances of a film and Gson to handle parsing
		Film f = new Film();
		Gson gson = new Gson();

		// adds data to the film object f
		f = gson.fromJson(filmData, Film.class);

		System.out.println(f.getId());
		System.out.println(f.getYear());

		// prints out a toString - this was used for testing purposes
		System.out.println("Testing toString: " + f.toString());

		return f;

	}
}