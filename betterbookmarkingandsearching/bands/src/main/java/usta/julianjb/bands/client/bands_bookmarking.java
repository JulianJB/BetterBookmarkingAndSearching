package usta.julianjb.bands.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EntryPoint of the Bookmarking module.
 * This code is displayed to the user's browser.
 */
public class bands_bookmarking implements EntryPoint {

    // A logger to keep track of the requests handled by the module
    private static Logger logger;

    // The code to be executed once the GWT module is loaded
    public void onModuleLoad() {

        // DEBUG: Processing a bookmarking request
        // Initialising the Logger for the bookmarking module
        logger = java.util.logging.Logger.getLogger("Bookmarking Module Log");
        // Checking if there is a query string with the Client request
        if (!Window.Location.getQueryString().isEmpty()) {
            // There is a query string within the request, proceed to create the bookmark
            createBookmark();
        } else {
            // There is not a query string within the request, proceed to retrieve the bookmarks
            getBookmarks();
        }

        // Map a div element of the HTML file as the Root Panel of the application
        final RootPanel rootPanel = RootPanel.get("bookmarkingPanel");
        // Add a vertical panel to display the list of Bookmark objects
        final VerticalPanel verticalPanel = new VerticalPanel();
        rootPanel.add(verticalPanel);

        // DEBUG: Testing displaying a simple label in the Bookmarking page
        final Label welcomeLabel = new Label("Welcome to your Bookmark Manager");
        verticalPanel.add(welcomeLabel);
    }

    public static void createBookmark() {
        // The variables to store the information about the bookmark (Page Title and URL)
        String pageTitle, urlEncoded;
        // Get the actual values of the parameters sent in the query string of the request
        pageTitle = Window.Location.getParameter("title");
        urlEncoded = Window.Location.getParameter("url");
        // DEBUG: Log the parameters values to the console
        logger.log(Level.INFO, "Log<Page Title>: " + pageTitle);
        logger.log(Level.INFO, "Log<URL>: " + urlEncoded);
        // TODO: Open database connection and store Bookmark String values
    }

    public static void getBookmarks() {
        //TODO
    }
}