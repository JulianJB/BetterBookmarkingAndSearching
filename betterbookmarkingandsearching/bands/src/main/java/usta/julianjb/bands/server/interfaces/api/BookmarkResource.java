package usta.julianjb.bands.server.interfaces.api;

import usta.julianjb.bands.server.SQLiteConnection;
import usta.julianjb.bands.shared.domain.Bookmark;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The REST requests that are handled by the server.
 * The BookmarkResource class is responsible for processing the
 * requests received from the client.
 */
@Path("bookmarks")
public class BookmarkResource {

    // A logger to keep track of the requests handled by the module
    private Logger logger;

    // An instance of the Database connector class
    private SQLiteConnection dbConnection;

    public BookmarkResource() {
        // Initialising the Logger for the bookmarking server
        logger = java.util.logging.Logger.getLogger("Bookmarking Server Log");
    }

    // Retrieve all of the Bookmark objects
    @GET
    @Produces("application/json")
    public ArrayList<Bookmark> getBookmarks() throws Exception {
        // Logging the request
        logger.log(Level.INFO, "Processing a GET request from the client");
        // An ArrayList for the Bookmark objects retrieved from the database
        ArrayList<Bookmark> returnedBookmarks;
        // Starting the connection with the database
        dbConnection = new SQLiteConnection();
        // Retrieve all of the bookmarks from the database
        returnedBookmarks = dbConnection.selectBookmarks();
        // Return the ArrayList of the Bookmark objects to the client
        return returnedBookmarks;
    }

    // Create a bookmark in the database
    @POST
    @Produces("application/json")
    public void createBookmark(Bookmark bookmark) throws Exception {
        // Logging the request
        logger.log(Level.INFO, "Processing a POST request from the client");
        // Starting the connection with the database
        dbConnection = new SQLiteConnection();
        // Create a bookmark in the database from a Bookmark object
        dbConnection.insertBookmark(bookmark);
    }
}