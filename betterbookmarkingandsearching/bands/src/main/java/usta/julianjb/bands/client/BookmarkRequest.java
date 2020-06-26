package usta.julianjb.bands.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import usta.julianjb.bands.client.api.client.BookmarkClient;
import usta.julianjb.bands.shared.domain.Bookmark;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The REST requests that are performed by the client.
 * The BookmarkRequest class is responsible for sending the
 * requests received from the bookmarking module.
 */
public class BookmarkRequest {

    // A logger to keep track of the requests handled by the request class
    private Logger logger;

    // An instance of the client interface to perform REST requests
    private BookmarkClient client;

    // An instance of the bookmarking module to access methods of the user interface
    private bands_bookmarking bookmarkingModule;

    public BookmarkRequest() {
    }

    // The constructor for the Bookmark Request class, receives the instance from
    // the bookmarking module.
    public BookmarkRequest(bands_bookmarking bookmarkingModule) {
        // Initialising the Logger for the request class
        logger = java.util.logging.Logger.getLogger("Bookmark Request Log");
        // Create the instance of the client interface to perform REST requests
        client = GWT.create(BookmarkClient.class);
        // Assign the instance of the bookmarking module received to manipulate it locally
        this.bookmarkingModule = bookmarkingModule;
    }

    /**
     * The REST requests methods
     */
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
        // Log the parameters values to the console
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
                // Logging the request response
                logger.log(Level.INFO, "The POST request was successfully handled");
                // The database supports multiple connections at a time but calls are asynchronous,
                // so the system should wait to dispatch a call first.
                // Proceed to retrieve the lists from the database
                getLists("bmMainPanel");
            }

            // If the POST request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // Logging the request response
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
                // Logging the request response
                logger.log(Level.INFO, "The GET request was successfully handled");
                // Retrieve all of the Bookmark objects and send them to display in the UI
                bookmarkingModule.showBookmarks(response);
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                // Logging the request response
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve a bookmark from the database using its URL value
    public void getBookmark(String locationAnchor) {
        // A variable to store the URL information about the bookmark
        String urlEncoded;
        // Get the actual value of the URL of the bookmark
        urlEncoded = locationAnchor;
        // Request the Bookmark object from the database
        client.getBookmark(urlEncoded, new MethodCallback<Bookmark>() {
            // If the GET request is handled correctly display the Bookmark object
            @Override
            public void onSuccess(Method method, Bookmark response) {
                // Logging the request response
                logger.log(Level.INFO, "The GET request was successfully handled");
                // Retrieve the Bookmark object and send them to display in the bookmarks editor
                bookmarkingModule.showBookmarksEditor(response);
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                // Logging the request response
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a DELETE request to the server to remove a bookmark from the database
    public void removeBookmark(String locationAnchor) {
        // A variable to store the URL information about the bookmark
        String urlEncoded;
        // Get the actual value of the URL of the bookmark
        urlEncoded = locationAnchor;
        // Request to remove a bookmark from the database
        client.removeBookmark(urlEncoded, new MethodCallback<Void>() {
            // If the DELETE request is handled correctly delete the bookmark from the database
            @Override
            public void onSuccess(Method method, Void response) {
                // Logging the request response
                logger.log(Level.INFO, "The DELETE request was successfully handled");
                // The database supports multiple connections at a time but calls are asynchronous,
                // so the system should wait to dispatch a call first.
                // Proceed to retrieve the bookmarks from the database
                getBookmarks();
            }

            // If the DELETE request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // Logging the request response
                logger.log(Level.SEVERE, "The DELETE request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a PUT request to the server to edit a bookmark in the database
    public void editBookmark(Bookmark returnedBookmark) {
        // A variable to store the URL information about the bookmark
        String urlEncoded;
        // Get the actual URL value of the bookmark parameters retrieved from the Bookmark object
        urlEncoded = returnedBookmark.getUrlEncoded();
        // A Bookmark object to sent within the request
        Bookmark bookmark;
        // Get the actual parameters values of the Bookmark object
        bookmark = returnedBookmark;
        // Request to edit a bookmark in the database
        client.editBookmark(urlEncoded, bookmark, new MethodCallback<Void>() {
            // If the PUT request is handled correctly update the bookmark in the database
            @Override
            public void onSuccess(Method method, Void response) {
                // Logging the request response
                logger.log(Level.INFO, "The PUT request was successfully handled");
                // The database supports multiple connections at a time but calls are asynchronous,
                // so the system should wait to dispatch a call first.
                // The lists could have been modified via the list manager
                // while the bookmarks editor window was active, so proceed
                // to request to retrieve the lists so that every UI element
                // in the Main Panel gets refreshed.
                getLists("bmMainPanel");
            }

            // If the PUT request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // Logging the request response
                logger.log(Level.SEVERE, "The PUT request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve all of the lists from the database
    public void getLists(String requestType) {
        // Request all of the lists from the database
        client.getLists(new MethodCallback<List<String>>() {
            // If the GET request is handled correctly display the lists
            @Override
            // Using List type: see notes on BookmarkClient interface
            public void onSuccess(Method method, List<String> response) {
                // Logging the request response
                logger.log(Level.INFO, "The GET request was successfully handled");
                // Identify the type of request sent within the call to perform
                // the respective action.
                if (requestType.equals("bmMainPanel")) {
                    // The request comes or handles the main panel of the bookmarking module
                    // Retrieve all of the lists and assign them to the List of String objects
                    bookmarkingModule.lists = response;
                    // Request to show the lists in the UI
                    bookmarkingModule.showLists();
                    // Proceed to retrieve the bookmarks from the database
                    getBookmarks();
                } else if (requestType.equals("bmWidget")) {
                    // The request comes or handles a widget of the bookmarking module
                    // Retrieve all of the lists and assign them to the List of String objects
                    bookmarkingModule.lists = response;
                }
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                // Logging the request response
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a GET request to the server to retrieve all of the bookmarks
    // from the database filtered from a list.
    public void filterBookmarks(String listName) {
        // A variable to store the List value
        String list;
        // Get the actual value of the list
        list = listName;
        // Request all of the Bookmark objects from the database using the list value
        client.filterBookmarks(list, new MethodCallback<List<Bookmark>>() {
            // If the GET request is handled correctly display the Bookmark objects
            @Override
            // Using List type: see notes on BookmarkClient interface
            public void onSuccess(Method method, List<Bookmark> response) {
                // Logging the request response
                logger.log(Level.INFO, "The GET request was successfully handled");
                // Retrieve the filtered Bookmark objects and send them to display in the UI
                bookmarkingModule.showBookmarks(response);
            }

            // If the GET request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable exception) {
                // Logging the request response
                logger.log(Level.SEVERE, "The GET request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a POST request to the server to create a list in the database
    public void createList(String listName) {
        // A variable to store the list value
        String list;
        // Get the actual value of the list
        // The "General" list is created by default and cannot be removed from the database
        list = listName;
        // Request to store a list in the database
        client.createList(list, new MethodCallback<Void>() {
            // If the POST request is handled correctly store the list in the database
            @Override
            public void onSuccess(Method method, Void response) {
                // Logging the request response
                logger.log(Level.INFO, "The POST request was successfully handled");
                // Request to update the lists retrieved from the database
                getLists("bmWidget");
            }

            // If the POST request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // Logging the request response
                logger.log(Level.SEVERE, "The POST request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }

    // Make a DELETE request to the server to remove a list from the database
    public void removeList(String listName) {
        // A variable to store the List value
        String list;
        // Get the actual value of the list
        // The "General" list is created by default and cannot be removed from the database
        list = listName;
        // Request to remove a list from the database
        client.removeList(list, new MethodCallback<Void>() {
            // If the DELETE request is handled correctly delete the list from the database
            @Override
            public void onSuccess(Method method, Void response) {
                // Logging the request response
                logger.log(Level.INFO, "The DELETE request was successfully handled");
                // Request to update the lists retrieved from the database
                getLists("bmWidget");
            }

            // If the DELETE request is not handled correctly throw an exception
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // Logging the request response
                logger.log(Level.SEVERE, "The DELETE request failed");
                throw new RuntimeException("Call failed");
            }
        });
    }
}