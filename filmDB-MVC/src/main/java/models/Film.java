package models;

/**
 * Creates a class for film and identifies its parameters Contains toString,
 * getters and setters
 * 
 * @author Louis Alder
 * 
 * @param id - id of film in DB
 * @param title - the name of the film
 * @param year - the year the film was created 
 * @param director - people involved in directing the films
 * @param stars - actors and actresses involved in film
 * @paranm review - the review of the film
 *
 */

public class Film {
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
	 * @param id - id of film in DB
	 */

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * get the title of a film
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of a film
	 * 
	 * @param title - the title of the film
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
	 * @param year - the year the film was released
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
	 * @param director - the directors who were involved in the film
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
	 * @param stars - the stars who were involved in the film
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
	 * Creates a toString of all films
	 * 
	 */
	
	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", year=" + year + ", director=" + director + ", stars=" + stars
				+ ", review=" + review + "]";
	}
}
