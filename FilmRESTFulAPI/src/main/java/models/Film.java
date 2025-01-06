package models;

import java.io.StringWriter;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Creates a class for film and identifies its parameters Contains toString,
 * getters and setters
 * 
 * @author Louis Alder
 *
 */
@XmlRootElement(name = "film")

public class Film {

	/**
	 * Method that defines a film
	 * 
	 * @param id       - id of film in DB
	 * @param title    - the name of the film
	 * @param year     - the year the film was created
	 * @param director - people involved in directing the films
	 * @param stars    - actors and actresses involved in film
	 * @param review   - the review of the film
	 * 
	 * @author Louis Alder
	 * 
	 */
	public Film(int id, String title, int year, String director, String stars, String review) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
	}

	/**
	 * constructor for the class film
	 * 
	 */
	public Film() {
		super();
	}

	int id;
	String title;
	int year;
	String director;
	String stars;
	String review;

	// getters and setters

	/**
	 * Gets the id of a film
	 * 
	 * @return id
	 */

	public int getId() {
		return id;
	}

	/**
	 * Sets the id of a film
	 * 
	 * @param id - id of the film within the DB
	 */

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * gets the title of a film
	 * 
	 * @return title
	 */

	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of a film
	 * 
	 * @param title - name of the film
	 */

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * gets the year of a film
	 * 
	 * @return year
	 */

	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of a film
	 * 
	 * @param year - year the film was released
	 */

	public void setYear(int year) {
		this.year = year;

	}

	/**
	 * returns the director of a film
	 * 
	 * @return director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Sets the director of a film
	 * 
	 * @param director - director involved in film
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * gets the stars of a film
	 * 
	 * @return stars
	 */

	public String getStars() {
		return stars;
	}

	/**
	 * Sets the stars of a film
	 * 
	 * @param stars - actors and actresses in the film
	 */

	public void setStars(String stars) {
		this.stars = stars;
	}

	/**
	 * gets the review of a film
	 * 
	 * @return review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Sets the review of a film
	 * 
	 * @param review - review of the film
	 */

	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Creates a toString of all films using %% to split up elements and *** to
	 * split up different films
	 * 
	 * 
	 */

	@Override
	public String toString() {
		return "ID: " + id + "%%\nTitle: " + title + "%%\nYear: " + year + "%%\nDirector: " + director + "%%\nStars: "
				+ stars + "%%\nReview: " + review + "\n***";
	}

	/**
	 * 
	 * Adds films into XML format by using JAXB
	 * 
	 * @param films an array list of the films
	 * @return sw.toString returns the list of films in xml format
	 */
	public static String toXml(ArrayList<Film> films) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Film.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		marshaller.marshal(new FilmList(films), sw);
		return sw.toString();
	}

	/**
	 * 
	 * A class to create an array list of films
	 * 
	 * @author Louis Alder
	 *
	 */
	private static class FilmList {
		@XmlElement(name = "film")
		private ArrayList<Film> films;

		/**
		 * Allows users to create a films list
		 * 
		 * @param films
		 */

		public FilmList(ArrayList<Film> films) {
			this.films = films;
		}

	}
}
