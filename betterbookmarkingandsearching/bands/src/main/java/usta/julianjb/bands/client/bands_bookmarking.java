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

        // DEBUG: Testing a GET and a UPDATE request
        // getBookmark();
        // DEBUG: Testing a DELETE request
        // removeBookmark();
    }

    // Make a POST request to the server to create a bookmark in the database
    public void createBookmark() {
        // The variables to store the information about the bookmark (Page Title, Page Description, URL, and List)
        String pageTitle, pageDescription, urlEncoded, list;
        // A Bookmark object to sent within the request
        Bookmark bookmark;
        // Get the actual values of the parameters sent in the query string of the request
        pageTitle = Window.Location.getParameter("title");
        pageDescription = Window.Location.getParameter("description");
        // If the Page Description is empty put a placeholder value instead
        if (pageDescription.isEmpty()) {
            pageDescription = "Bookmarked with BANDS";
        }
        urlEncoded = Window.Location.getParameter("url");
        // By default, at creation all of the bookmarks are assigned to the "General" list
        list = "General";
        // DEBUG: Log the parameters values to the console
        logger.log(Level.INFO, "Log<Page Title>: " + pageTitle);
        logger.log(Level.INFO, "Log<Page Description>: " + pageDescription);
        logger.log(Level.INFO, "Log<URL>: " + urlEncoded);
        logger.log(Level.INFO, "Log<List>: " + list);
        // Create the Bookmark object from the parameters values
        bookmark = new Bookmark(pageTitle, pageDescription, urlEncoded, list);
        // Request to store a bookmark in the database
        client.createBookmark(bookmark, new MethodCallback<Void>() {
            // If the POST request is handled correctly store the bookmark in the database
            @Override
            public void onSuccess(Method method, Void response) {
                logger.log(Level.INFO, "The POST request was successfully handled");
                // Calls are asynchronous but the database only supports one connection at a time
                // Proceed to retrieve the bookmarks from the database
                getBookmarks();
                // DEBUG: Testing a GET and an UPDATE request
                // getBookmark();
                // DEBUG: Testing a DELETE request
                // removeBookmark();
                // DEBUG: Testing a GET lists and a GET bookmarks by filtering request
                // getLists();
                // filterBookmarks();
                // DEBUG: Testing a POST list and a DELETE list request
                // createList();
                // removeList();
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
                            + " Description: " + bookmark.getPageDescription()
                            + " URL: " + bookmark.getUrlEncoded()));
                }
                logger.log(Level.INFO, "The GET request was successfully handled");
                // DEBUG: Testing a GET lists and a GET bookmarks by filtering request
                // getLists();
                // filterBookmarks();
                // DEBUG: Testing a POST list and a DELETE list request
                createList();
                // removeList();
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve a bookmark from the database using its URL value
    public void getBookmark() {
        // A variable to store the URL information about the bookmark
        String urlEncoded;
        // Get the actual value of the URL of the bookmark
        // TODO: Replace with the corresponding GUI method
        urlEncoded = "https://en.wikipedia.org/wiki/Main_Page";
        // Request the Bookmark object from the database
        client.getBookmark(urlEncoded ,new MethodCallback<Bookmark>() {
            // If the GET request is handled correctly display the Bookmark object
            @Override
            public void onSuccess(Method method, Bookmark response) {
                // Retrieve the Bookmark object and prepare to display it
                // TODO: Populate the vertical panel with labels with the data of the Bookmark object
                //Label label = new Label("Bookmark: " + bookmark.getPageTitle());
                //verticalPanel.add(label);
                // TODO: Alternative way to display the Bookmark object (preferred)
                verticalPanel.add(new Label("Bookmark Title: " + response.getPageTitle()
                        + " Description: " + response.getPageDescription()
                        + " URL: " + response.getUrlEncoded()));
                logger.log(Level.INFO, "The GET request was successfully handled");
                // Calls are asynchronous but the database only supports one connection at a time
                // Proceed to retrieve the bookmark information to the Bookmark Editor
                editBookmark(response);
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a DELETE request to the server to remove a bookmark from the database
    public void removeBookmark() {
        // A variable to store the URL information about the bookmark
        String urlEncoded;
        // Get the actual value of the URL of the bookmark
        // TODO: Replace with the corresponding GUI method
        urlEncoded = "https://en.wikipedia.org/wiki/Main_Page";
        // Request to remove a bookmark from the database
        client.removeBookmark(urlEncoded, new MethodCallback<Void>() {
            // If the DELETE request is handled correctly delete the bookmark from the database
            @Override
            public void onSuccess(Method method, Void response) {
                logger.log(Level.INFO, "The DELETE request was successfully handled");
                // Calls are asynchronous but the database only supports one connection at a time
                // Proceed to retrieve the bookmarks from the database
                getBookmarks();
            }

            // If the DELETE request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.log(Level.SEVERE, "The DELETE request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a PUT request to the server to edit a bookmark in the database
    public void editBookmark(Bookmark returnedBookmark) {
        // The variables to store the information about the bookmark (Page Title, Page Description, URL, and List)
        String pageTitle, pageDescription, urlEncoded, list;
        // TODO: Populate the Bookmark Editor with the information from the Bookmark object
        // Get the actual values of the bookmark parameters retrieved from the Bookmark object
        pageTitle = returnedBookmark.getPageTitle();
        // pageDescription = returnedBookmark.getPageDescription();
        // DEBUG: Testing the UPDATE request
        pageDescription = "Wikipedia is part of the Wikimedia Foundation, Inc.";
        urlEncoded = returnedBookmark.getUrlEncoded();
        list = returnedBookmark.getList();
        /**
         * DEBUG: A new Bookmark object has to be created because the parameters values
         * are going to be modified by the user while interacting with the Bookmark Editor
         */
        // A Bookmark object to sent within the request
        Bookmark bookmark;
        // Create the Bookmark object from the parameters values
        bookmark = new Bookmark(pageTitle, pageDescription, urlEncoded, list);
        // Request to edit a bookmark in the database
        client.editBookmark(urlEncoded, bookmark, new MethodCallback<Void>() {
            // If the PUT request is handled correctly update the bookmark in the database
            @Override
            public void onSuccess(Method method, Void response) {
                logger.log(Level.INFO, "The PUT request was successfully handled");
                // Calls are asynchronous but the database only supports one connection at a time
                // Proceed to retrieve the bookmarks from the database
                getBookmarks();
            }

            // If the PUT request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.log(Level.SEVERE, "The PUT request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve all of the lists from the database
    public void getLists() {
        // Request all of the lists from the database
        client.getLists(new MethodCallback<List<String>>() {
            // If the GET request is handled correctly display the lists
            @Override
            // Using List type: see notes on BookmarkClient interface
            public void onSuccess(Method method, List<String> response) {
                // TODO: Replace with the corresponding GUI code (combo box)
                verticalPanel.add(new Label("List Title: " + "General"));
                // Retrieve all of the lists and prepare to display them
                for (String list : response) {
                    // TODO: Populate the Combo box of the GUI with the lists titles
                    // The order hierarchy of the lists begins from the "General" list,
                    // then proceeds in ascending alphabetical order (A-Z), and ends
                    // with the element "All" for displaying without any list filter.
                    if (list.equals("General")) {
                        // Skips the "General" list value as it is already added to the combo box
                        continue;
                    }
                    // TODO: Alternative way to display the lists (preferred)
                    verticalPanel.add(new Label("List Title: " + list));
                }
                // TODO: Replace with the corresponding GUI code (combo box)
                verticalPanel.add(new Label("List Title: " + "All"));
                logger.log(Level.INFO, "The GET request was successfully handled");
                // DEBUG: Testing a GET lists and a GET bookmarks by filtering request
                // getLists();
                // filterBookmarks();
                // DEBUG: Testing a POST list and a DELETE list request
                // createList();
                removeList();
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve all of the bookmarks
    // from the database filtered from a list.
    public void filterBookmarks() {
        // DEBUG: The List value can be sent as a parameter (either from the Combo box) or
        // from external methods (such as the removeList() method)
        // A variable to store the List value
        String list;
        // Get the actual value of the list
        // TODO: Replace with the corresponding GUI method
        list = "General";
        // Request all of the Bookmark objects from the database using the list value
        client.filterBookmarks(list, new MethodCallback<List<Bookmark>>() {
            // If the GET request is handled correctly display the Bookmark objects
            @Override
            // Using List type: see notes on BookmarkClient interface
            public void onSuccess(Method method, List<Bookmark> response) {
                // Retrieve all of the Bookmark objects and prepare to display them
                for (Bookmark bookmark : response) {
                    // TODO: Populate the vertical panel with labels with the data of the Bookmark objects
                    // TODO: Alternative way to display the list of Bookmark objects (preferred)
                    verticalPanel.add(new Label("Bookmark Title: " + bookmark.getPageTitle()
                            + " Description: " + bookmark.getPageDescription()
                            + " URL: " + bookmark.getUrlEncoded()
                            + " List: " + bookmark.getList()));
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

    // Make a POST request to the server to create a list in the database
    public void createList() {
        // A variable to store the List value
        String list;
        // Get the actual value of the list
        // The "General" list is created by default and cannot be removed from the database
        // TODO: Replace with the corresponding GUI method (Text box)
        list = "Readings";
        // Request to store a list in the database
        client.createList(list, new MethodCallback<Void>() {
            // If the POST request is handled correctly store the list in the database
            @Override
            public void onSuccess(Method method, Void response) {
                logger.log(Level.INFO, "The POST request was successfully handled");
                // DEBUG: The call to create a list is contained within the Bookmark Editor widget,
                // so it is not necessary an additional call to display the bookmarks.
                // DEBUG: Testing a GET lists and a GET bookmarks by filtering request
                getLists();
                // filterBookmarks();
            }

            // If the POST request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.log(Level.SEVERE, "The POST request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a DELETE request to the server to remove a list from the database
    public void removeList() {
        // A variable to store the List value
        String list;
        // Get the actual value of the list
        // The "General" list is created by default and cannot be removed from the database
        // TODO: Replace with the corresponding GUI method (Combo box)
        list = "Readings";
        // Request to remove a list from the database
        client.removeList(list, new MethodCallback<Void>() {
            // If the DELETE request is handled correctly delete the list from the database
            @Override
            public void onSuccess(Method method, Void response) {
                logger.log(Level.INFO, "The DELETE request was successfully handled");
                // DEBUG: A call to filter the bookmarks by the "General" list is possible
                // by sending the required parameter (List value)
                // Calls are asynchronous but the database only supports one connection at a time
                // Proceed to retrieve the bookmarks from the database
                // getBookmarks();
            }

            // If the DELETE request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.log(Level.SEVERE, "The DELETE request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }
}