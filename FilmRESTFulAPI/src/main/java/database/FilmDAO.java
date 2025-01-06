
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Film;

/**
 * This class is used to deal with any interactions with the database. All
 * Creation, updating, deleting, and searching of data in the database is done
 * from here.
 * 
 * @author Louis Alder
 *
 */

public class FilmDAO {

	Film oneFilm = null;
	Connection conn = null;
	Statement stmt = null;

	// username and password to mudfoot account
	String user = "REDACTED";
	String password = "REDACTED";
	String url = "REDACTED" + user;


	
	/**
	 * Constructor for DAO
	 * 
	 */
	public FilmDAO() {
	}

	
	/**
	 * Creates a connection to the SQL DB hosting the film DB
	 * 
	 * 
	 */
	
	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {
			// connection string for demos database, username demos, password demos
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	/**
	 * Tries to close connection to the SQL DB hosting the film DB
	 * 
	 * 
	 */

	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * A method to get the next film object
	 * 
	 * @param rs 
	 * @return the film object
	 * 
	 */

	private Film getNextFilm(ResultSet rs) {
		Film thisFilm = null;
		try {
			thisFilm = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("stars"), rs.getString("review"));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return thisFilm;
	}

	/**
	 * Retrieves all films from the film DB by opening the connection and then
	 * running a select all query
	 * 
	 * @return allFilms - array list of all of the films in the DB
	 * 
	 * 
	 */
	public ArrayList<Film> getAllFilms() {

		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "select * from films";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
				allFilms.add(oneFilm);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allFilms;
	}

	/**
	 * Retrieves film by ID number
	 * 
	 * @param id the ID of the film selected
	 * @return the object of the film
	 * 
	 */

	public Film getFilmByID(int id) {

		openConnection();
		oneFilm = null;
		// Create select statement and execute it
		try {
			String selectSQL = "select * from films where id=" + id;
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
			}

			stmt.close();// closes the SQL query
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return oneFilm;
	}

	/**
	 * Creates a new film entry in the DB using a prepared statement
	 * 
	 * @param f is the film film object to be added into the db
	 * @throws SQLException if connection is unsuccessful or query fails
	 * @return boolean depending on if the SQL query executed
	 */

	public boolean insertFilm(Film f) throws SQLException {
		String sql = "INSERT INTO films (id, title, year, director, stars, review) VALUES (?, ?, ?, ?, ?, ?)";

		openConnection();

		try {
			// SQL query which gets the highest ID in the DB.
			String highestIDNumber = "SELECT MAX(id) AS max_id FROM films";
			// adds the result of the query to the result set
			ResultSet rs = stmt.executeQuery(highestIDNumber);

			// adds one to the value which is returned to the result set
			int nextId = rs.next() ? rs.getInt("max_id") + 1 : 1;

			// initiates a new prepared statement using the INSERT sql query
			PreparedStatement ps = conn.prepareStatement(sql);

			// sets the ID of the new film to the last highest ID retrieved in the previous
			// query +1
			ps.setInt(1, nextId);
			ps.setString(2, f.getTitle());
			ps.setInt(3, f.getYear());
			ps.setString(4, f.getDirector());
			ps.setString(5, f.getStars());
			ps.setString(6, f.getReview());

			ps.executeUpdate(); // executes the prepared statement
			ps.close(); // closes the prepared statement

		} catch (SQLException se) {
			System.out.println(se);
			return false;// returns false if the query fails
		}

		closeConnection();// closes the DB connection
		return true; // returns true if SQL query does not return an error and film has been inserted
	}

	/**
	 * Deletes a film with the id using a prepared statement
	 * 
	 * @param f selected film
	 * @return number of rows deleted
	 * @throws SQLException if connection is unsuccessful or if query fails
	 * 
	 */

	public boolean deleteFilm(Film f) throws SQLException {

		String sql = "DELETE FROM films WHERE id = ?;";

		openConnection();

		try {

			PreparedStatement ps = conn.prepareStatement(sql);

			// gets the id from the film
			// adds the id integer into the prepared statement
			ps.setInt(1, f.getId());

			// number of rows deleted
			int rowsDeleted = ps.executeUpdate();

			ps.close();// closes the connection
			closeConnection();

			return rowsDeleted > 0; // return true if any rows were deleted

		} catch (SQLException se) {
			System.out.println(se);
			return false; // catch for a SQL exception if the above fails
		}
	}

	/**
	 * Updates / modifies a film with the id using a prepared statement
	 * 
	 * @param f film selected film
	 * @return number of rows updated in db
	 * @throws SQLException if connection is unsuccessful or if query fails
	 * 
	 */

	public boolean updateFilm(Film f) throws SQLException {

		// update sql string
		String sql = "UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? WHERE id = ?;";

		openConnection();

		try {

			// initiates prepared statrement
			PreparedStatement ps = conn.prepareStatement(sql);

			// adds values from the film object f, and assigns it
			ps.setString(1, f.getTitle());
			ps.setInt(2, f.getYear());
			ps.setString(3, f.getDirector());
			ps.setString(4, f.getStars());
			ps.setString(5, f.getReview());
			ps.setInt(6, f.getId());

			// execute the prepared statement
			int rowsUpdated = ps.executeUpdate();

			ps.close();
			closeConnection();

			return rowsUpdated > 0; // return true if any rows were updated

		} catch (SQLException se) {
			System.out.println(se);
			return false; // return false in case of any exception
		}
	}

	/**
	 * searches for a film with the title using a prepared statement
	 * 
	 * @param title this is the film the user is searching for
	 * @return searchFilms the returned films from the prepared statement
	 * 
	 */
	public ArrayList<Film> searchFilmsTitle(String title) {
		openConnection();

		// search SQL string is defined
		String searchSQL = "SELECT * FROM films WHERE title LIKE ?";

		// that are similar to the search input from the user
		ArrayList<Film> searchFilms = new ArrayList<Film>();
		try {
			PreparedStatement ps = conn.prepareStatement(searchSQL);

			ps.setString(1, "%" + title + "%");
			// uses the '%...% wildcard to search DB for title. This returns any strings
			// similar to the title
			// executes the query
			ResultSet rs = ps.executeQuery();

			// adds all films returned from the query into the results
			while (rs.next()) {
				Film oneFilm = getNextFilm(rs);
				searchFilms.add(oneFilm);
			}

			ps.close();
			// closes the prepared statement

			closeConnection();
			// closes the sql connection
		} catch (SQLException se) {
			System.out.println(se);
		}
		// returns the list of films pulled back from the sql query
		return searchFilms;
	}

	/**
	 * searches for a film with the year released using a prepared statement
	 * 
	 * @param year is the year of film the user has searched for
	 * @return searchFilms the returned films from the prepared statement
	 * 
	 */

	public ArrayList<Film> searchFilmsYear(int year) {
		// open connection to DB
		openConnection();

		String searchSQL = "SELECT * FROM films WHERE year LIKE ?";

		ArrayList<Film> searchFilms = new ArrayList<Film>();
		try {
			PreparedStatement ps = conn.prepareStatement(searchSQL);
			ps.setString(1, "%" + year + "%");
			// uses the '%...% wildcard to search DB for year. This returns any strings
			// similar to the title
			// executes the query

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				// adds all films to the result set
				Film oneFilm = getNextFilm(rs);
				searchFilms.add(oneFilm);
			}
			// closes the prepared statement
			ps.close();
			// closes the sql query
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		// returns the list of films pulled back from the sql query
		return searchFilms;
	}

}
