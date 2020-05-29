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
 * - The SELECT multiple rows statement.
 * - The INSERT statement.
 * - The SELECT statement.
 * - The DELETE statement.
 * - The UPDATE statement.
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

    // The SELECT multiple rows statement: Retrieves all of the
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
            statement = connection.prepareStatement("SELECT * from BOOKMARKS");
            // Perform the query to the database and store the results
            resultSet = statement.executeQuery();
            // For each of the bookmarks stored in the database,
            // an individual Bookmark object will be created
            // from the fields "TITLE", "DESCRIPTION", and "URL".
            while (resultSet.next()) {
                returnedBookmarks.add(new Bookmark(resultSet.getString("TITLE"),
                        resultSet.getString("DESCRIPTION"), resultSet.getString("URL")));
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

    // The INSERT statement: Stores a bookmark in the database
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
                    "(TITLE, DESCRIPTION, URL) VALUES (?, ?, ?)");
            // Obtain the value of the Page Title and add it to the INSERT statement
            statement.setString(1, bookmark.getPageTitle());
            // Obtain the value of the Page Description and add it to the INSERT statement
            statement.setString(2, bookmark.getPageDescription());
            // Obtain the value of the URL Encoded and add it to the INSERT statement
            statement.setString(3, bookmark.getUrlEncoded());
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

    // The SELECT statement: Retrieves a bookmark stored
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
            statement = connection.prepareStatement("SELECT * from BOOKMARKS WHERE URL = ?");
            // Add the value of the URL Encoded to the SELECT statement
            statement.setString(1, urlEncoded);
            // Perform the query to the database and store the results
            resultSet = statement.executeQuery();
            // An individual Bookmark object will be created
            // from the fields "TITLE", "DESCRIPTION", and "URL".
            returnedBookmark = new Bookmark(resultSet.getString("TITLE"),
                    resultSet.getString("DESCRIPTION"), resultSet.getString("URL"));
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

    // The DELETE statement: Removes a bookmark from the database
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

    // The UPDATE statement: Edits a bookmark in the database
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
                    "TITLE = ?, DESCRIPTION = ?, URL = ? WHERE URL = ?");
            // Obtain the value of the Page Title and add it to the UPDATE statement
            statement.setString(1, bookmark.getPageTitle());
            // Obtain the value of the Page Description and add it to the UPDATE statement
            statement.setString(2, bookmark.getPageDescription());
            // Obtain the value of the URL Encoded and add it to the UPDATE statement
            statement.setString(3, bookmark.getUrlEncoded());
            // Add the value of the URL Encoded to the UPDATE statement
            statement.setString(4, urlEncoded);
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