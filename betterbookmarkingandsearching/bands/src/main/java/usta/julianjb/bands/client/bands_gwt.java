package usta.julianjb.bands.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import usta.julianjb.bands.client.resources.NativeScript;

/**
 * The EntryPoint of the GWT application.
 * This code is displayed to the user's browser.
 */
public class bands_gwt implements EntryPoint {

  // A vertical panel widget to display the UI elements into the welcome page
  private VerticalPanel welcomePagePanel;

  // The code to be executed once the GWT module is loaded
  public void onModuleLoad() {
    // Map a div element of the HTML file as the Root Panel of the application
    final RootPanel rootPanel = RootPanel.get("indexPanel");
    // Initialise the main user interface (UI) elements of the welcome page
    // Instance a vertical panel to act as a container of all of the main UI elements
    welcomePagePanel = new VerticalPanel();
    // Vertically center the UI elements of the welcome page by setting a default height value
    welcomePagePanel.setHeight("720px");
    welcomePagePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    // Add the main vertical panel to the root panel of the module
    rootPanel.add(welcomePagePanel);
    // Display the welcome page
    showWelcomePage();
  }

  private void showWelcomePage() {
    // The UI elements of the welcome page
    VerticalPanel containerPanel;
    Label titleLabel;
    Label bookmarkletLabel;
    Label bookmarkletDescriptionLabel;
    Label bookmarkletInstructionsLabel;
    Label bookmarksLabel;
    Label bookmarksDescriptionLabel;
    Label bookmarksInstructionsLabel;
    Anchor bookmarkletAnchor;
    Anchor bookmarksAnchor;
    // Initialise the UI elements for the welcome page
    // Initialise the labels for the welcome page
    titleLabel = new Label("Better Bookmarking and Searching");
    // Set the CSS style to the title label
    titleLabel.setStyleName("titleLabel");
    bookmarkletLabel = new Label("Start bookmarking from your computer");
    // Set the CSS style to the bookmarklet label
    bookmarkletLabel.setStyleName("featureLabel");
    bookmarkletDescriptionLabel = new Label("Install the Bookmarklet to " +
            "save any page in your browser with a single click");
    // Set the CSS style to the bookmarklet description label
    bookmarkletDescriptionLabel.setStyleName("descriptionLabel");
    bookmarkletInstructionsLabel = new Label("Drag this button to your Bookmarks Bar");
    // Set the CSS style to the bookmarklet instructions label
    bookmarkletInstructionsLabel.setStyleName("instructionsLabel");
    bookmarksLabel = new Label("View your bookmarks from any web browser");
    // Set the CSS style to the bookmarks label
    bookmarksLabel.setStyleName("featureLabel");
    bookmarksDescriptionLabel = new Label("Access your stored bookmarks from " +
            "your favorite browser at any time");
    // Set the CSS style to the bookmarks description label
    bookmarksDescriptionLabel.setStyleName("descriptionLabel");
    bookmarksInstructionsLabel = new Label("Click this button to go to your " +
            "Bookmarks Manager");
    // Set the CSS style to the bookmarks instructions label
    bookmarksInstructionsLabel.setStyleName("instructionsLabel");
    // Initialise the anchors for the welcome page
    bookmarkletAnchor = new Anchor("+ Bookmark");
    bookmarksAnchor = new Anchor("My Bookmarks");
    // Set the CSS style to the bookmarklet anchor element
    bookmarkletAnchor.setStyleName("bookmarkletAnchor");
    // Set the CSS style to the bookmarks anchor element
    bookmarksAnchor.setStyleName("bookmarksAnchor");
    // Create an instance of the native script interface to load a text resource
    // from an external JavaScript file.
    NativeScript scriptLoader = GWT.create(NativeScript.class);
    // Prepare to load the content of a native script in JavaScript via a text resource
    // A constant for the script in JavaScript of the bookmarklet
    final String BOOKMARKLET_SCRIPT = scriptLoader.bookmarkletScript().getText();
    // A constant for the url of the bookmarking module
    final String BOOKMARKS_URL = "http://127.0.0.1:8888/bookmarking.html";
    // Adding an HREF attribute to the bookmarklet anchor element
    bookmarkletAnchor.setHref(BOOKMARKLET_SCRIPT);
    // Adding the click handler method for the bookmarks anchor element
    bookmarksAnchor.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        // Open a new tab in the web browser with the url of the bookmarking module
        Window.open(BOOKMARKS_URL, "_blank", "");
      }
    });
    // Initialise a vertical panel to act as a container of all of the welcome page UI elements
    containerPanel = new VerticalPanel();
    // Add the bookmarklet UI elements to the container panel
    containerPanel.add(bookmarkletLabel);
    containerPanel.add(bookmarkletAnchor);
    containerPanel.add(bookmarkletInstructionsLabel);
    containerPanel.add(bookmarkletDescriptionLabel);
    // Add the bookmarks manager UI elements to the container panel
    containerPanel.add(bookmarksLabel);
    containerPanel.add(bookmarksAnchor);
    containerPanel.add(bookmarksInstructionsLabel);
    containerPanel.add(bookmarksDescriptionLabel);
    // Add the container panel to the main panel
    welcomePagePanel.add(containerPanel);
  }
}