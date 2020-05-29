package usta.julianjb.bands.server;

import usta.julianjb.bands.shared.domain.Bookmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The connections to the database are handled by the SQLite connector for Java.
 * The database is stored locally in the server within the GWT application.
 * The requests (queries) supported by the connection are:
 * - The SELECT multiple rows (bookmarks) statement.
 * - The INSERT bookmark statement.
 * - The SELECT bookmark statement.
 * - The DELETE bookmark statement.
 * - The UPDATE bookmark statement.
 * - The SELECT multiple rows (lists) statement.
 * - The SELECT multiple rows (bookmarks) by filtering statement.
 * - The INSERT list statement.
 * - The DELETE list statement.
 * - The UPDATE list statement.
 */
public class SQLiteConnection {

    // A logger to keep track of the requests handled by the database connector
    private Logger logger;

    public SQLiteConnection() {
        // Initialising the Logger for the bookmarking server
        logger = java.util.logging.Logger.getLogger("Database Connector Log");
    }

    // Establish a connection with the local database of the Bookmarking module
    private Connection getConnection() throws Exception {
        // Load the SQLite JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        // Create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:BANDS_BOOKMARKS.db");
        // Logging the connection
        logger.log(Level.INFO, "SQLite database connected");
        return connection;
    }

    // The SELECT multiple rows (bookmarks) statement: Retrieves all of the
    // bookmarks stored in the database.
    public ArrayList<Bookmark> selectBookmarks() throws Exception {
        // An ArrayList for retrieving the Bookmarks stored in the database
        ArrayList<Bookmark> returnedBookmarks = new ArrayList<Bookmark>();
        // To establish a connection with the database
        Connection connection = null;
        // To create a SELECT statement to query the database
        PreparedStatement statement = null;
        // To store the results of the query performed
        ResultSet resultSet = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the SELECT statement for the query
            statement = connection.prepareStatement("SELECT * FROM BOOKMARKS");
            // Perform the query to the database and store the results
            resultSet = statement.executeQuery();
            // For each of the bookmarks stored in the database,
            // an individual Bookmark object will be created
            // from the fields "TITLE", "DESCRIPTION", "URL", and "LIST".
            while (resultSet.next()) {
                returnedBookmarks.add(new Bookmark(resultSet.getString("TITLE"),
                        resultSet.getString("DESCRIPTION"), resultSet.getString("URL"),
                        resultSet.getString("LIST")));
            }
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while reading the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            resultSet.close();
            statement.close();
            connection.close();
        }
        // Return the list of Bookmark objects retrieved
        return returnedBookmarks;
    }

    // The INSERT bookmark statement: Stores a bookmark in the database
    public void insertBookmark(Bookmark bookmark) throws Exception {
        // To establish a connection with the database
        Connection connection = null;
        // To create an INSERT statement to update the database
        PreparedStatement statement = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the INSERT statement for the update
            // To avoid duplication of bookmarks, the SQL statement INSERT IGNORE is used
            statement = connection.prepareStatement("INSERT OR IGNORE INTO BOOKMARKS " +
                    "(TITLE, DESCRIPTION, URL, LIST) VALUES (?, ?, ?, ?)");
            // Obtain the value of the Page Title and add it to the INSERT statement
            statement.setString(1, bookmark.getPageTitle());
            // Obtain the value of the Page Description and add it to the INSERT statement
            statement.setString(2, bookmark.getPageDescription());
            // Obtain the value of the URL Encoded and add it to the INSERT statement
            statement.setString(3, bookmark.getUrlEncoded());
            // Obtain the value of the List and add it to the INSERT statement
            statement.setString(4, bookmark.getList());
            // Perform the update to the database
            statement.executeUpdate();
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while writing to the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            statement.close();
            connection.close();
        }
    }

    // The SELECT bookmark statement: Retrieves a bookmark stored
    // in the database using its URL value.
    public Bookmark selectBookmark(String urlEncoded) throws Exception {
        // A Bookmark object for retrieving the bookmark information stored in the database
        Bookmark returnedBookmark = new Bookmark();
        // To establish a connection with the database
        Connection connection = null;
        // To create a SELECT statement to query the database
        PreparedStatement statement = null;
        // To store the results of the query performed
        ResultSet resultSet = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the SELECT statement for the query
            statement = connection.prepareStatement("SELECT * FROM BOOKMARKS WHERE URL = ?");
            // Add the value of the URL Encoded to the SELECT statement
            statement.setString(1, urlEncoded);
            // Perform the query to the database and store the results
            resultSet = statement.executeQuery();
            // An individual Bookmark object will be created
            // from the fields "TITLE", "DESCRIPTION", "URL", and "LIST".
            returnedBookmark = new Bookmark(resultSet.getString("TITLE"),
                    resultSet.getString("DESCRIPTION"), resultSet.getString("URL"),
                    resultSet.getString("LIST"));
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while reading the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            resultSet.close();
            statement.close();
            connection.close();
        }
        // Return the list of Bookmark objects retrieved
        return returnedBookmark;
    }

    // The DELETE bookmark statement: Removes a bookmark from the database
    public void deleteBookmark(String urlEncoded) throws Exception {
        // To establish a connection with the database
        Connection connection = null;
        // To create an DELETE statement to update the database
        PreparedStatement statement = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the DELETE statement for the update
            statement = connection.prepareStatement("DELETE FROM BOOKMARKS WHERE URL = ?");
            // Add the value of the URL Encoded to the DELETE statement
            statement.setString(1, urlEncoded);
            // Perform the update to the database
            statement.executeUpdate();
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while writing to the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            statement.close();
            connection.close();
        }
    }

    // The UPDATE bookmark statement: Edits a bookmark in the database
    public void updateBookmark(String urlEncoded, Bookmark bookmark) throws Exception {
        // To establish a connection with the database
        Connection connection = null;
        // To create an UPDATE statement to update the database
        PreparedStatement statement = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the UPDATE statement for the update
            statement = connection.prepareStatement("UPDATE BOOKMARKS SET " +
                    "TITLE = ?, DESCRIPTION = ?, URL = ?, LIST = ? WHERE URL = ?");
            // Obtain the value of the Page Title and add it to the UPDATE statement
            statement.setString(1, bookmark.getPageTitle());
            // Obtain the value of the Page Description and add it to the UPDATE statement
            statement.setString(2, bookmark.getPageDescription());
            // Obtain the value of the URL Encoded and add it to the UPDATE statement
            statement.setString(3, bookmark.getUrlEncoded());
            // Obtain the value of the List and add it to the UPDATE statement
            statement.setString(4, bookmark.getList());
            // Add the value of the URL Encoded to the UPDATE statement
            statement.setString(5, urlEncoded);
            // Perform the update to the database
            statement.executeUpdate();
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while writing to the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            statement.close();
            connection.close();
        }
    }

    // The SELECT multiple rows (lists) statement: Retrieves all of the
    // lists stored in the database.
    public ArrayList<String> selectLists() throws Exception {
        // An ArrayList for retrieving the lists stored in the database
        ArrayList<String> returnedLists = new ArrayList<String>();
        // To establish a connection with the database
        Connection connection = null;
        // To create a SELECT statement to query the database
        PreparedStatement statement = null;
        // To store the results of the query performed
        ResultSet resultSet = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the SELECT statement for the query
            statement = connection.prepareStatement("SELECT * FROM LISTS");
            // Perform the query to the database and store the results
            resultSet = statement.executeQuery();
            // For each of the lists stored in the database,
            // add it to the ArrayList of String objects
            // from the field "TITLE".
            while (resultSet.next()) {
                returnedLists.add(resultSet.getString("TITLE"));
            }
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while reading the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            resultSet.close();
            statement.close();
            connection.close();
        }
        // Return the ArrayList of lists retrieved
        return returnedLists;
    }

    // The SELECT multiple rows (bookmarks) by filtering statement: Retrieves all of the
    // bookmarks stored in the database filtered from a list.
    public ArrayList<Bookmark> filterBookmarks(String list) throws Exception {
        // An ArrayList for retrieving the Bookmarks stored in the database
        ArrayList<Bookmark> returnedBookmarks = new ArrayList<Bookmark>();
        // To establish a connection with the database
        Connection connection = null;
        // To create a SELECT statement to query the database
        PreparedStatement statement = null;
        // To store the results of the query performed
        ResultSet resultSet = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the SELECT statement for the query
            statement = connection.prepareStatement("SELECT * FROM BOOKMARKS WHERE LIST = ?");
            // Add the value of the List to the SELECT statement
            statement.setString(1, list);
            // Perform the query to the database and store the results
            resultSet = statement.executeQuery();
            // For each of the bookmarks stored in the database,
            // an individual Bookmark object will be created
            // from the fields "TITLE", "DESCRIPTION", "URL", and "LIST".
            while (resultSet.next()) {
                returnedBookmarks.add(new Bookmark(resultSet.getString("TITLE"),
                        resultSet.getString("DESCRIPTION"), resultSet.getString("URL"),
                        resultSet.getString("LIST")));
            }
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while reading the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            resultSet.close();
            statement.close();
            connection.close();
        }
        // Return the list of Bookmark objects retrieved
        return returnedBookmarks;
    }

    // The INSERT list statement: Stores a list in the database
    public void insertList(String list) throws Exception {
        // To establish a connection with the database
        Connection connection = null;
        // To create an INSERT statement to update the database
        PreparedStatement statement = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the INSERT statement for the update
            // To avoid duplication of lists, the SQL statement INSERT IGNORE is used
            statement = connection.prepareStatement("INSERT OR IGNORE INTO LISTS " +
                    "(TITLE) VALUES (?)");
            // Add the value of the List to the INSERT statement
            statement.setString(1, list);
            // Perform the update to the database
            statement.executeUpdate();
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while writing to the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            statement.close();
            connection.close();
        }
    }

    // The DELETE list statement: Removes a list from the database
    public void deleteList(String list) throws Exception {
        // To establish a connection with the database
        Connection connection = null;
        // To create an DELETE statement to update the database
        PreparedStatement statement = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the DELETE statement for the update
            statement = connection.prepareStatement("DELETE FROM LISTS WHERE TITLE = ?");
            // Add the value of the List to the DELETE statement
            statement.setString(1, list);
            // Perform the update to the database
            statement.executeUpdate();
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while writing to the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            statement.close();
            connection.close();
        }
    }

    // The UPDATE list statement: Updates the list of the bookmarks
    // in the database prior to a list removal.
    public void updateList(String list) throws Exception {
        // To establish a connection with the database
        Connection connection = null;
        // To create an UPDATE statement to update the database
        PreparedStatement statement = null;
        try {
            // Establish a connection with the database
            connection = getConnection();
            // Prepare the UPDATE statement for the update
            statement = connection.prepareStatement("UPDATE BOOKMARKS SET " +
                    "LIST = ? WHERE LIST = ?");
            // Add the value of the "General" List to the UPDATE statement (reset the List value)
            statement.setString(1, "General");
            // Add the value of the List to the UPDATE statement
            statement.setString(2, list);
            // Perform the update to the database
            statement.executeUpdate();
        } catch (SQLException sqle) {
            // Logging the exception
            logger.log(Level.SEVERE, "An error while writing to the database has occurred: "
                    + sqle);
        } finally {
            // Cleaning up the database connection and variables
            statement.close();
            connection.close();
        }
    }
}