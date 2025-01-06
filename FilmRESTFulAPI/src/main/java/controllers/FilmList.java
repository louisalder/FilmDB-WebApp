package controllers;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import models.Film;


/**
 * 
 * Handles the creation of lists that contain all films
 * 
 * @author Louis Alder
 *
 */


@XmlAccessorType(XmlAccessType.FIELD)
//the top level element in the xml file is called films
@XmlRootElement(name = "films")


public class FilmList {
	// each child instance in the xml file will be called film
	@XmlElement(name = "film")
	private List<Film> FilmList;

	/**
	 * Constructor for FilmList
	 * 
	 */

	public FilmList() {
	}

	/**
	 * 
	 * Acts as a way to set the film list
	 * 
	 * @param FilmsList used to set the film list
	 */

	public FilmList(List<Film> FilmsList) {
		this.FilmList = FilmsList;
	}



	/*
	 * Getter to get the films list
	 * 
	 * @return FilmList retrieves the film list
	 * 
	 */

	public List<Film> getFilmList() {
		return FilmList;
	}

	/*
	 * Setter method to add a list of films to the FilmList
	 * 
	 * @param FilmsList a list of all films
	 * 
	 */

	public void setFilmList(List<Film> FilmsList) {
		this.FilmList = FilmsList;
	}
}