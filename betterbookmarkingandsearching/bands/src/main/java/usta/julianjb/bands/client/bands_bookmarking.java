package usta.julianjb.bands.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import usta.julianjb.bands.client.api.client.BookmarkClient;
import usta.julianjb.bands.shared.domain.Bookmark;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EntryPoint of the Bookmarking module.
 * This code is displayed to the user's browser.
 */
public class bands_bookmarking implements EntryPoint {

    // A logger to keep track of the requests handled by the module
    private Logger logger;

    // An instance of the client interface to perform REST requests
    private BookmarkClient client;

    // A vertical panel widget to display elements into the root panel
    private VerticalPanel verticalPanel;

    // The code to be executed once the Bookmarking module is loaded
    public void onModuleLoad() {

        // DEBUG: Processing a bookmarking request
        // Initialising the Logger for the bookmarking module
        logger = java.util.logging.Logger.getLogger("Bookmarking Module Log");
        // Start the REST service of the Bookmarking module
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        // Create the instance of the client interface to perform REST requests
        client = GWT.create(BookmarkClient.class);
        // Checking if there is a query string with the Client request
        // Calls are asynchronous but the database only supports one connection at a time
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
        verticalPanel = new VerticalPanel();
        rootPanel.add(verticalPanel);

        // DEBUG: Testing displaying a simple label in the Bookmarking page
        final Label welcomeLabel = new Label("Welcome to your Bookmark Manager");
        verticalPanel.add(welcomeLabel);
    }

    // Make a POST request to the server to create a bookmark in the database
    public void createBookmark() {
        // The variables to store the information about the bookmark (Page Title and URL)
        String pageTitle, urlEncoded;
        // A Bookmark object to sent within the request
        Bookmark bookmark;
        // Get the actual values of the parameters sent in the query string of the request
        pageTitle = Window.Location.getParameter("title");
        urlEncoded = Window.Location.getParameter("url");
        // DEBUG: Log the parameters values to the console
        logger.log(Level.INFO, "Log<Page Title>: " + pageTitle);
        logger.log(Level.INFO, "Log<URL>: " + urlEncoded);
        // Create the Bookmark object from the parameters values
        bookmark = new Bookmark(pageTitle, urlEncoded);
        // Request to store a bookmark in the database
        client.createBookmark(bookmark, new MethodCallback<Void>() {
            // If the POST request is handled correctly store the bookmark in the database
            @Override
            public void onSuccess(Method method, Void response) {
                logger.log(Level.INFO, "The POST request was successfully handled");
                // Calls are asynchronous but the database only supports one connection at a time
                // Proceed to retrieve the bookmarks from the database
                getBookmarks();
            }

            // If the POST request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.log(Level.SEVERE, "The POST request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve all of the bookmarks from the database
    public void getBookmarks() {
        // Request all of the Bookmark objects from the database
        client.getBookmarks(new MethodCallback<List<Bookmark>>() {
            // If the GET request is handled correctly display the Bookmark objects
            @Override
            // Using List type: see notes on BookmarkClient interface
            public void onSuccess(Method method, List<Bookmark> response) {
                // Retrieve all of the Bookmark objects and prepare to display them
                for (Bookmark bookmark : response) {
                    // TODO: Populate the vertical panel with labels with the data of the Bookmark objects
                    //Label label = new Label("Bookmark: " + bookmark.getPageTitle());
                    //verticalPanel.add(label);
                    // TODO: Alternative way to display the list of Bookmark objects (preferred)
                    verticalPanel.add(new Label("Bookmark Title: " + bookmark.getPageTitle()
                            + " URL: " + bookmark.getUrlEncoded()));
                }
                logger.log(Level.INFO, "The GET request was successfully handled");
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }
}