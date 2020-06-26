package usta.julianjb.bands.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Defaults;
import usta.julianjb.bands.shared.domain.Bookmark;

import java.util.List;

/**
 * The EntryPoint of the Bookmarking module.
 * This code is displayed to the user's browser.
 */
public class bands_bookmarking implements EntryPoint {

    // An instance of the Bookmark Request class to perform REST requests
    private BookmarkRequest bmRequest;

    // A horizontal panel widget to display the filtering elements into the main panel
    private HorizontalPanel bmFilterPanel;

    // A horizontal panel widget to display the list filtering elements
    private HorizontalPanel bmListPanel;

    // A vertical panel widget to display the bookmarks into the main panel
    protected VerticalPanel bmBookmarksPanel;

    // A List of String objects containing the retrieved lists from the database
    // Required to be a global variable since it is accessed from different parts of the module
    protected List<String> lists;

    // A label widget to display the tag used for filtering
    protected Label tagFilterLabel;

    // The code to be executed once the Bookmarking module is loaded
    public void onModuleLoad() {
        // Start the REST service of the Bookmarking module
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        // Map a div element of the HTML file as the Root Panel of the application
        final RootPanel rootPanel = RootPanel.get("bookmarkingPanel");
        // A vertical panel widget to display elements into the root panel
        VerticalPanel bmMainPanel;
        // An horizontal panel widget to display the title elements into the main panel
        HorizontalPanel bmTitlePanel;
        // Initialise the main user interface (UI) elements of the bookmarking module
        // Instance a vertical panel to act as a container of all of the main UI elements
        bmMainPanel = new VerticalPanel();
        // Instance a horizontal panel to act as a container of all of the title elements
        bmTitlePanel = new HorizontalPanel();
        // Instance a horizontal panel to act as a container of all of the filtering elements
        bmFilterPanel = new HorizontalPanel();
        // Set the CSS style to the filtering panel
        bmFilterPanel.setStyleName("bmFilterPanel");
        // Instance a vertical panel to act as a container of all of the bookmarks elements
        bmBookmarksPanel = new VerticalPanel();
        // Instance a label for the title of the bookmarking module
        final Label titleLabel = new Label("My bookmarks");
        // Set the CSS style to the title label
        titleLabel.setStyleName("titleLabel");
        // Add the title label to the title panel
        bmTitlePanel.add(titleLabel);
        // Instance a button for the help window of the bookmarking module
        final Button helpButton = new Button();
        // Set the tooltip text for the help button
        helpButton.setTitle("Help");
        // Set the CSS style to the help button
        helpButton.addStyleName("helpButton");
        // Adding the click handler method for the help button
        helpButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Show the help window of the bookmarking system
                showHelp();
            }
        });
        // Add the help button to the title panel
        bmTitlePanel.add(helpButton);
        // Add the title panel to the main panel
        bmMainPanel.add(bmTitlePanel);
        // Add the filtering panel to the main panel
        bmMainPanel.add(bmFilterPanel);
        // Add the bookmarks panel to the main panel
        bmMainPanel.add(bmBookmarksPanel);
        // Add the main panel to the root panel of the module
        rootPanel.add(bmMainPanel);
        // Proceed to show the themes available for the bookmarking module
        showThemes();
        // Create the instance of the Bookmark Request class to perform REST requests
        bmRequest = new BookmarkRequest(this);
        // Checking if there is a query string with the Client request
        // The database supports multiple connections at a time but calls are asynchronous,
        // so the system should wait to dispatch a call first.
        if (!Window.Location.getQueryString().isEmpty()) {
            // There is a query string within the request, proceed to create the bookmark
            bmRequest.createBookmark();
        } else {
            // There is not a query string within the request, proceed to retrieve the lists
            bmRequest.getLists("bmMainPanel");
        }
    }

    /**
     * The Graphic User Interface (GUI) methods
     */
    // Show all of the bookmarks retrieved from the database
    protected void showBookmarks(List<Bookmark> bookmarks) {
        // A flex table widget to display the bookmarks
        FlexTable bookmarksTable;
        // The UI elements of the bookmarks table
        FlowPanel containerPanel;
        Label nameLabel;
        Label descriptionLabel;
        Anchor locationAnchor;
        Anchor tagsAnchor;
        Button editButton;
        Button removeButton;
        // Constants for defining the columns of the content
        // Column 0: Bookmark information
        // Column 1: Bookmark manager
        final int BOOKMARK_COLUMN = 0;
        final int MANAGER_COLUMN = 1;
        // A constant for indexing the Anchor element within the container panel
        // There are three bookmark information UI elements within the container panel:
        // Index 0: Page Title (Label)
        // Index 1: Page Description (Label)
        // Index 2: URL Encoded (Anchor)
        // Index 3: Tags (Label)
        final int ANCHOR_INDEX = 2;
        // Instance a flex table to act as a container of the Bookmark objects
        bookmarksTable = new FlexTable();
        // Set the CSS style to the bookmarks table
        bookmarksTable.setStyleName("bookmarksTable");
        // Retrieve all of the Bookmark objects and prepare to display them
        for (Bookmark bookmark : bookmarks) {
            // A variable to control the creation of new entries in the bookmarks table
            final int row = bookmarksTable.getRowCount();
            // Initialise the UI elements for the bookmark information
            nameLabel = new Label(bookmark.getPageTitle());
            // Set the CSS style to the name label
            nameLabel.setStyleName("nameLabel");
            descriptionLabel = new Label(bookmark.getPageDescription());
            locationAnchor = new Anchor(bookmark.getUrlEncoded());
            // Set the CSS style to the location anchor
            locationAnchor.setStyleName("locationAnchor");
            // Adding the click handler method for the anchor element
            locationAnchor.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    // Open a new tab in the web browser with the url of the anchor element
                    Window.open(bookmark.getUrlEncoded(), "_blank", "");
                }
            });
            // Initialise a flow panel to act as a container of all of the bookmark information UI elements
            containerPanel = new FlowPanel();
            // Set the CSS style to the bookmarks panel
            containerPanel.setStyleName("bookmarksPanel");
            // Add the bookmark information UI elements to the container panel
            containerPanel.add(nameLabel);
            containerPanel.add(descriptionLabel);
            containerPanel.add(locationAnchor);
            // Obtain all the tags from the bookmark and separate them individually
            String[] tagsArray = bookmark.getTags().trim().split("\\s+");
            // Iterate for each tag in the bookmark
            for (String tag : tagsArray) {
                tagsAnchor = new Anchor(tag);
                // Adding the click handler method for the anchor element
                tagsAnchor.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        // Clear all the UI elements in the bookmarks panel so that
                        // it can be refreshed with the updated bookmarks
                        bmBookmarksPanel.clear();
                        // Proceed to request the bookmarks from the database with the given tag filter
                        bmRequest.filterBookmarksByTag(tag);
                    }
                });
                containerPanel.add(tagsAnchor);
            }
            // Add the container panel to the bookmarks table in the corresponding column
            // Create a new entry in the flex table
            bookmarksTable.setWidget(row, BOOKMARK_COLUMN, containerPanel);
            // Set the CSS style to the bookmarks cells of the bookmarks table
            // Apply a cell formatter in the bookmarks column to define a fixed
            // column size that ignores the width of the content of the bookmark.
            bookmarksTable.getCellFormatter().addStyleName(row, BOOKMARK_COLUMN,
                    "bookmarksCell");
            // Initialise the UI elements for the bookmark manager
            editButton = new Button();
            removeButton = new Button();
            // Set the tooltip text for the edit button
            editButton.setTitle("Edit");
            // Set the tooltip text for the delete button
            removeButton.setTitle("Delete");
            // Set the CSS style to the edit button
            editButton.addStyleName("editButton");
            // Set the CSS style to the delete button
            removeButton.addStyleName("removeButton");
            // Adding the click handler methods for the buttons
            // The click handler method for the "Edit Bookmark" button
            editButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    // The UI elements used by the click handler
                    // The flow panel and the anchor element have to be declared again
                    // as their previous instances cannot be accessed within inner classes.
                    FlowPanel containerPanel;
                    Anchor locationAnchor;
                    // Obtain the container panel from the Bookmark information column
                    containerPanel = (FlowPanel) bookmarksTable.getWidget(row, BOOKMARK_COLUMN);
                    // Obtain the anchor element from the container panel
                    locationAnchor = (Anchor) containerPanel.getWidget(ANCHOR_INDEX);
                    // Proceed to request the retrieval of the bookmark information from the database
                    bmRequest.getBookmark(locationAnchor.getText());
                }
            });
            // The click handler method for the "Delete Bookmark" button
            removeButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    // The UI elements used by the click handler
                    // The flow panel and the anchor element have to be declared again
                    // as their previous instances cannot be accessed within inner classes.
                    FlowPanel containerPanel;
                    Anchor locationAnchor;
                    // Obtain the container panel from the Bookmark information column
                    containerPanel = (FlowPanel) bookmarksTable.getWidget(row, BOOKMARK_COLUMN);
                    // Obtain the anchor element from the container panel
                    locationAnchor = (Anchor) containerPanel.getWidget(ANCHOR_INDEX);
                    // Confirm if the user wants to delete the selected bookmark
                    if (Window.confirm("Are you sure you want to permanently delete this bookmark?")) {
                        // The user clicked on the "OK" button
                        // Clear all the UI elements in the bookmarks panel so that
                        // it can be refreshed with the updated bookmarks.
                        bmBookmarksPanel.clear();
                        // Proceed to request the removal of the bookmark information from the database
                        bmRequest.removeBookmark(locationAnchor.getText());
                    } else {
                        // The user clicked on the "Cancel" button
                        // Dismiss the confirmation message and go back to the bookmarks manager
                    }
                }
            });
            // Initialise a flow panel to act as a container of all of the bookmark manager UI elements
            containerPanel = new FlowPanel();
            // Add the bookmark manager UI elements to the container panel
            containerPanel.add(editButton);
            containerPanel.add(removeButton);
            // Set the CSS style to the container panel for the buttons
            // of the bookmarks table.
            containerPanel.setStyleName("buttonsPanel");
            // Add the container panel to the bookmarks table in the corresponding column
            // Create a new entry in the flex table
            bookmarksTable.setWidget(row, MANAGER_COLUMN, containerPanel);
        }
        // Add the bookmarks table to the bookmarks panel
        bmBookmarksPanel.add(bookmarksTable);
        // Display the credits information of the bookmarking system
        showCredits();
    }

    // Show the credits information of the bookmarking system
    private void showCredits() {
        // The UI elements of the credits section
        final HorizontalPanel creditsPanel = new HorizontalPanel();
        final Label creditsLabel = new Label("BANDS: Better bookmarking and searching");
        final Anchor homeAnchor = new Anchor("Home");
        final Anchor helpAnchor = new Anchor("Help");
        // Set the CSS style for the anchor elements of the credits panel
        homeAnchor.setStyleName("creditsAnchor");
        helpAnchor.setStyleName("creditsAnchor");
        // Adding the click handler method for the Home anchor element
        homeAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Open a new tab in the web browser with the url of homepage
                Window.open("http://127.0.0.1:8888/index.html", "_blank", "");
            }
        });
        // Adding the click handler method for the Help anchor element
        helpAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Show the help window of the bookmarking system
                showHelp();
            }
        });
        // Add the credits information elements to the credits panel
        creditsPanel.add(creditsLabel);
        creditsPanel.add(homeAnchor);
        creditsPanel.add(helpAnchor);
        // Set the CSS style of the credits panel
        creditsPanel.setStyleName("creditsPanel");
        // Add the credits panel to the bookmarks panel
        bmBookmarksPanel.add(creditsPanel);
    }

    // Show a help window with information on how to use the bookmarking system
    private void showHelp() {
        // The UI elements of the help window
        DialogBox helpWindow;
        FlowPanel containerPanel;
        HorizontalPanel instructionsPanel;
        HorizontalPanel buttonsPanel;
        Label instructionsTitleLabel;
        Label instructionsContentLabel;
        Label instructionsHighlightLabel;
        Image instructionsImage;
        Button closeButton;
        // Initialise the UI elements for the help window
        // Instance a dialog box to act as a pop-up window and set its display properties
        helpWindow = new DialogBox();
        // Disable the background by blurring it with a dim color (requires CSS styling)
        helpWindow.setGlassEnabled(true);
        // Enable animation features for the widget
        helpWindow.setAnimationEnabled(true);
        // Set a title for the help window
        helpWindow.setText("How to use the bookmarking system");
        // Instance the UI elements of the help window with their default values
        containerPanel = new FlowPanel();
        instructionsPanel = new HorizontalPanel();
        buttonsPanel = new HorizontalPanel();
        // Initialise the title label, set its CSS style, and add it
        // to the container panel.
        instructionsTitleLabel = new Label("Editing a bookmark");
        instructionsTitleLabel.setStyleName("instructionsTitleLabel");
        containerPanel.add(instructionsTitleLabel);
        // Initialise the image widget, set its size, and retrieve
        // the corresponding image from the URL.
        instructionsImage = new Image();
        instructionsImage.setWidth("36px");
        instructionsImage.setUrl("../resources/images/edit.png");
        // Add the image to the instructions panel
        instructionsPanel.add(instructionsImage);
        // Initialise the content label, set its CSS style, and add it
        // to the instructions panel.
        instructionsContentLabel = new Label("Click this button to edit a bookmark");
        instructionsContentLabel.setStyleName("instructionsContentLabel");
        instructionsPanel.add(instructionsContentLabel);
        // Set the CSS style for the instructions panel of the help window
        instructionsPanel.setStyleName("instructionsPanel");
        // Add the instructions panel to the container panel
        containerPanel.add(instructionsPanel);
        // Instance the UI elements of the help window with their default values
        instructionsPanel = new HorizontalPanel();
        // Initialise the title label, set its CSS style, and add it
        // to the container panel.
        instructionsTitleLabel = new Label("Deleting a bookmark");
        instructionsTitleLabel.setStyleName("instructionsTitleLabel");
        containerPanel.add(instructionsTitleLabel);
        // Initialise the image widget, set its size, and retrieve
        // the corresponding image from the URL.
        instructionsImage = new Image();
        instructionsImage.setWidth("36px");
        instructionsImage.setUrl("../resources/images/delete.png");
        // Add the image to the instructions panel
        instructionsPanel.add(instructionsImage);
        // Initialise the content label, set its CSS style, and add it
        // to the instructions panel.
        instructionsContentLabel = new Label("Click this button to delete a bookmark");
        instructionsContentLabel.setStyleName("instructionsContentLabel");
        instructionsPanel.add(instructionsContentLabel);
        // Set the CSS style for the instructions panel of the help window
        instructionsPanel.setStyleName("instructionsPanel");
        // Add the instructions panel to the container panel
        containerPanel.add(instructionsPanel);
        // Instance the UI elements of the help window with their default values
        instructionsPanel = new HorizontalPanel();
        // Initialise the title label, set its CSS style, and add it
        // to the container panel.
        instructionsTitleLabel = new Label("Creating a list");
        instructionsTitleLabel.setStyleName("instructionsTitleLabel");
        containerPanel.add(instructionsTitleLabel);
        // Initialise the content label, and add it to the instructions panel
        instructionsContentLabel = new Label("Open the Bookmarks Editor " +
                "and click on the List item box to select the option");
        instructionsPanel.add(instructionsContentLabel);
        // Initialise the highlighted label, set its CSS style, and add it
        // to the instructions panel.
        instructionsHighlightLabel = new Label("New list...");
        instructionsHighlightLabel.setStyleName("instructionsHighlightLabel");
        instructionsPanel.add(instructionsHighlightLabel);
        // Set the CSS style for the instructions panel of the help window
        instructionsPanel.setStyleName("instructionsPanel");
        // Add the instructions panel to the container panel
        containerPanel.add(instructionsPanel);
        // Instance the UI elements of the help window with their default values
        instructionsPanel = new HorizontalPanel();
        // Initialise the title label, set its CSS style, and add it
        // to the container panel.
        instructionsTitleLabel = new Label("Deleting a list");
        instructionsTitleLabel.setStyleName("instructionsTitleLabel");
        containerPanel.add(instructionsTitleLabel);
        // Initialise the content label, and add it to the instructions panel
        instructionsContentLabel = new Label("Click on the Filter by List " +
                "item box to select the option");
        instructionsPanel.add(instructionsContentLabel);
        // Initialise the highlighted label, set its CSS style, and add it
        // to the instructions panel.
        instructionsHighlightLabel = new Label("Manage lists...");
        instructionsHighlightLabel.setStyleName("instructionsHighlightLabel");
        instructionsPanel.add(instructionsHighlightLabel);
        // Set the CSS style for the instructions panel of the help window
        instructionsPanel.setStyleName("instructionsPanel");
        // Add the instructions panel to the container panel
        containerPanel.add(instructionsPanel);
        // Initialise the button for the help window
        closeButton = new Button();
        closeButton.setText("Close");
        // Set the CSS style to the close button
        closeButton.setStyleName("closeButton");
        // Adding the click handler method for the button
        // The click handler method for the "Close" button
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Close the help window
                helpWindow.hide();
            }
        });
        // Adding the remaining UI elements of the help window hierarchically
        // Add the close button to the buttons panel
        buttonsPanel.add(closeButton);
        // Set the CSS style to the buttons panel of the help window
        buttonsPanel.setStyleName("buttonsPanel");
        // Add a break line in HTML to separate the widgets
        containerPanel.add(new HTML("&nbsp;"));
        // Add the buttons panel to the container panel
        containerPanel.add(buttonsPanel);
        // Set the CSS style to the container panel of the help window
        containerPanel.setStyleName("widgetPanel");
        // Add the container panel to the help window
        helpWindow.add(containerPanel);
        // Position the help window at the center of the screen
        helpWindow.center();
        // Show the help window
        helpWindow.show();
    }

    // Show all of the themes available for the bookmarking module
    private void showThemes() {
        // A horizontal panel widget to display the themes
        HorizontalPanel bmThemesPanel;
        // The UI elements of the theme selector functionality
        Label themeSelectorLabel;
        ListBox themeSelector;
        // Instance a horizontal panel to act as a container of the UI elements
        // of the theme selector functionality.
        bmThemesPanel = new HorizontalPanel();
        // Set the CSS style to the themes panel
        bmThemesPanel.setStyleName("bmThemesPanel");
        // Initialise the UI elements for the theme selector functionality
        themeSelectorLabel = new Label("Theme: ");
        themeSelector = new ListBox();
        // Set the CSS style to the theme selector label
        themeSelectorLabel.setStyleName("themeSelectorLabel");
        // Add the theme selector functionality UI elements to the themes panel
        bmThemesPanel.add(themeSelectorLabel);
        bmThemesPanel.add(themeSelector);
        // Add all of the theme items to the theme selector element
        themeSelector.addItem("Light");
        themeSelector.addItem("Sepia");
        themeSelector.addItem("Gray");
        // Adding the change handler method for the theme items of the theme selector
        themeSelector.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                // Obtain the text value of the selected theme item
                final String themeName = themeSelector.getSelectedItemText();
                // If the theme item selected is the "Light" element, set the background
                // color to white.
                if (themeName.equals("Light")) {
                    Document.get().getBody().getStyle().setBackgroundColor("#fff");
                } else if (themeName.equals("Sepia")) {
                    // If the theme item selected is the "Sepia" element,
                    // set the background color to sepia.
                    Document.get().getBody().getStyle().setBackgroundColor("#f8f2e3");
                } else if (themeName.equals("Gray")) {
                    // If the theme item selected is the "Gray" element,
                    // set the background color to gray.
                    Document.get().getBody().getStyle().setBackgroundColor("#f0f0f0");
                }
            }
        });
        // Add the themes panel to the filtering panel
        bmFilterPanel.add(bmThemesPanel);
    }

    // Show all of the lists retrieved from the database
    protected void showLists() {
        // The UI elements of the list filtering functionality
        Label listFilterLabel;
        ListBox listSelector;
        // Instance a horizontal panel to act as a container of the UI elements
        // of the list filtering functionality.
        bmListPanel = new HorizontalPanel();
        // Set the CSS style to the list panel
        bmListPanel.setStyleName("bmListPanel");
        // Initialise the UI elements for the list filtering functionality
        listFilterLabel = new Label("Filter by List:");
        listSelector = new ListBox();
        // Set the CSS style to the list filter label
        listFilterLabel.setStyleName("listFilterLabel");
        // Add the list filtering functionality UI elements to the list panel
        bmListPanel.add(listFilterLabel);
        bmListPanel.add(listSelector);
        // A constant for the index of the default list item ("All")
        final int DEFAULT_LIST = 0;
        // Retrieve all of the lists and prepare to display them
        for (String list : lists) {
            // The order hierarchy of the lists begins from the "All" list
            // for displaying the bookmarks without any list filter,
            // then proceeds in ascending alphabetical order (A-Z).
            // Add a new list item to the list selector element
            listSelector.addItem(list);
        }
        // Add the "Manage lists..." list item at the end of the list selector element
        listSelector.addItem("Manage lists...");
        // Adding the change handler method for the list items of the list selector
        listSelector.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                // Obtain the text value of the selected list item
                final String listName = listSelector.getSelectedItemText();
                // If the list item selected is the "All" element, retrieve the bookmarks
                // without any filter.
                if (listName.equals("All")) {
                    // Clear all the UI elements in the bookmarks panel so that
                    // it can be refreshed with the updated bookmarks
                    bmBookmarksPanel.clear();
                    // Proceed to retrieve all of the bookmarks from the database
                    bmRequest.getBookmarks();
                } else if (listName.equals("Manage lists...")) {
                    // If the list item selected is the "Manage lists..." element
                    // call the method to manage the lists from the database.
                    // Request the list manager window to manage the lists
                    showListManager();
                } else { // Retrieve the bookmarks with the corresponding list filter
                    // Clear all the UI elements in the bookmarks panel so that
                    // it can be refreshed with the updated bookmarks
                    bmBookmarksPanel.clear();
                    // Proceed to request the bookmarks from the database with the given list filter
                    bmRequest.filterBookmarks(listName);
                }
            }
        });
        // Set the default list item of the list selector as the "All" list
        listSelector.setSelectedIndex(DEFAULT_LIST);
        // Add the list panel to the filtering panel
        bmFilterPanel.add(bmListPanel);
    }

    // Show the bookmarks editor window to edit a bookmark from the database
    protected void showBookmarksEditor(Bookmark bookmark) {
        // The UI elements of the bookmarks editor
        DialogBox bookmarksEditor;
        FlowPanel containerPanel;
        HorizontalPanel buttonsPanel;
        Label nameLabel;
        Label descriptionLabel;
        Label locationLabel;
        Label listLabel;
        Label tagsLabel;
        TextBox nameTextBox;
        TextArea descriptionTextArea;
        TextBox locationTextBox;
        ListBox listSelector;
        TextBox tagsTextBox;
        Button confirmButton;
        Button cancelButton;
        // Initialise the UI elements for the bookmarks editor
        // Instance a dialog box to act as a pop-up window and set its display properties
        bookmarksEditor = new DialogBox();
        // Disable the background by blurring it with a dim color (requires CSS styling)
        bookmarksEditor.setGlassEnabled(true);
        // Enable animation features for the widget
        bookmarksEditor.setAnimationEnabled(true);
        // Set a title for the bookmarks editor window
        bookmarksEditor.setText("Edit Bookmark");
        // Instance the UI elements of the bookmarks editor with their default values
        containerPanel = new FlowPanel();
        buttonsPanel = new HorizontalPanel();
        // Initialise the labels for the bookmarks editor
        nameLabel = new Label("Name:");
        descriptionLabel = new Label("Description:");
        locationLabel = new Label("Location:");
        listLabel = new Label("List:");
        tagsLabel = new Label("Tags (separated by a whitespace):");
        // Initialise the text areas for the bookmarks editor with the bookmark information
        nameTextBox = new TextBox();
        // Set a placeholder text for the Name text box
        nameTextBox.getElement().setPropertyString("placeholder",
                "Name (Optional)");
        nameTextBox.setText(bookmark.getPageTitle());
        descriptionTextArea = new TextArea();
        // Set a placeholder text for the Description text area
        descriptionTextArea.getElement().setPropertyString("placeholder",
                "Description (Optional)");
        descriptionTextArea.setText(bookmark.getPageDescription());
        locationTextBox = new TextBox();
        locationTextBox.setText(bookmark.getUrlEncoded());
        locationTextBox.setEnabled(false);
        tagsTextBox = new TextBox();
        // Set a placeholder text for the Tags text box
        tagsTextBox.getElement().setPropertyString("placeholder",
                "Tags (Optional)");
        tagsTextBox.setText(bookmark.getTags());
        // Initialise the list selector for the bookmarks editor
        listSelector = new ListBox();
        // Request to update the lists retrieved from the database
        bmRequest.getLists("bmWidget");
        // A variable to store the index value of the list item obtained from the bookmark
        int listIndex = 0;
        // Retrieve all of the lists and prepare to display them
        for (String list : lists) {
            // If the list item is the same as the list value of the bookmark
            if (list.equals(bookmark.getList())) {
                // Retrieve the index value of the list item
                // Minus 1 because the first element of the lists is removed, so the
                // indices position is changed one position.
                listIndex = lists.indexOf(list) - 1;
            }
            // If the list item is the "All" element ignore it, as a bookmark
            // cannot be directly associated with this list element.
            if (list.equals("All")) {
                // Skip adding the "All" element to the list selector
                // as it is not an actual list.
                continue;
            }
            // Add a new list item to the list selector element
            listSelector.addItem(list);
        }
        // Add the "New list..." list item at the end of the list selector element
        listSelector.addItem("New list...");
        // Adding the change handler method for the list items of the list selector
        listSelector.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                // Obtain the text value of the selected list item
                final String listName = listSelector.getSelectedItemText();
                // If the list item selected is the "New list..." element
                // call the method to create a new list.
                if (listName.equals("New list...")) {
                    // Create a Bookmark object to temporarily store the bookmark
                    // information values from the bookmarks editor.
                    Bookmark temporaryBookmark;
                    temporaryBookmark = new Bookmark(nameTextBox.getText(),
                            descriptionTextArea.getText(), locationTextBox.getText(),
                            "General", tagsTextBox.getText());
                    // Close the bookmarks editor window
                    bookmarksEditor.hide();
                    // Request the list creator window to create a new list
                    showListCreator(temporaryBookmark);
                } else {
                    // Do not perform any action
                }
            }
        });
        // Set the default list item of the list selector as the list value of the bookmark
        listSelector.setSelectedIndex(listIndex);
        // Initialise the buttons for the bookmarks editor
        confirmButton = new Button();
        confirmButton.setText("Confirm");
        cancelButton = new Button();
        cancelButton.setText("Cancel");
        // Set the CSS style to the confirm button
        confirmButton.setStyleName("confirmButton");
        // Set the CSS style to the cancel button
        cancelButton.setStyleName("cancelButton");
        // Adding the click handler methods for the buttons
        // The click handler method for the "Confirm" button
        confirmButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // The variables to store the information about the bookmark
                // (Page Title, Page Description, URL, List, and Tags).
                String pageTitle, pageDescription, urlEncoded, list, tags;
                // Get the updated values of the bookmark parameters retrieved from the
                // text areas of the bookmarks editor.
                pageTitle = nameTextBox.getText();
                pageDescription = descriptionTextArea.getText();
                urlEncoded = locationTextBox.getText();
                // Get the text value of the selected list item
                list = listSelector.getSelectedItemText();
                tags = tagsTextBox.getText().toLowerCase();
                // If the list item selected is the "New list..." element
                // reset the list value to the "General" list item.
                if (list.equals("New list...")) {
                    // Skip the "New list..." element of the list selector
                    // as it is not an actual list.
                    list = "General";
                }
                // A new Bookmark object has to be created to be sent within the request
                // because the parameters values are going to be modified by the user
                // while interacting with the Bookmark Editor.
                Bookmark editedBookmark;
                // Create the Bookmark object from the parameters values
                editedBookmark = new Bookmark(pageTitle, pageDescription, urlEncoded, list, tags);
                // Close the bookmarks editor window
                bookmarksEditor.hide();
                // Clear all the UI elements in the list panel and in the bookmarks
                // panel so that the UI can be refreshed with the updated content.
                bmListPanel.clear();
                bmBookmarksPanel.clear();
                // Proceed to request the modification of the bookmark information in the database
                bmRequest.editBookmark(editedBookmark);
            }
        });
        // The click handler method for the "Cancel" button
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Close the bookmarks editor window
                bookmarksEditor.hide();
                // Clear all the UI elements in the list panel and in the bookmarks
                // panel so that the UI can be refreshed with the updated content.
                bmListPanel.clear();
                bmBookmarksPanel.clear();
                // The lists could have been modified via the list manager
                // while the bookmarks editor window was active, so proceed
                // to request to retrieve the lists so that every UI element
                // in the Main Panel gets refreshed.
                bmRequest.getLists("bmMainPanel");
            }
        });
        // Adding the UI elements of the bookmarks editor hierarchically
        // Add the bookmark name UI elements to the container panel
        containerPanel.add(nameLabel);
        containerPanel.add(nameTextBox);
        // Add the bookmark description UI elements to the container panel
        containerPanel.add(descriptionLabel);
        containerPanel.add(descriptionTextArea);
        // Add the bookmark location UI elements to the container panel
        containerPanel.add(locationLabel);
        containerPanel.add(locationTextBox);
        // Add the bookmark list UI elements to the container panel
        containerPanel.add(listLabel);
        containerPanel.add(listSelector);
        // Add the bookmark tags UI elements to the container panel
        containerPanel.add(tagsLabel);
        containerPanel.add(tagsTextBox);
        // Add a break line in HTML to separate the widgets
        containerPanel.add(new HTML("&nbsp;"));
        // Add the bookmarks editor buttons to the buttons panel
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        // Set the CSS style to the buttons panel of the bookmarks editor
        buttonsPanel.setStyleName("buttonsPanel");
        // Add the buttons panel to the container panel
        containerPanel.add(buttonsPanel);
        // Set the CSS style to the container panel of the bookmarks editor
        containerPanel.setStyleName("widgetPanel");
        // Add the container panel to the bookmarks editor window
        bookmarksEditor.add(containerPanel);
        // Position the bookmarks editor at the center of the screen
        bookmarksEditor.center();
        // Show the bookmarks editor window
        bookmarksEditor.show();
    }

    // Show the list creator window to create a new list in the database
    private void showListCreator(Bookmark temporaryBookmark) {
        // The UI elements of the list creator
        DialogBox listCreator;
        FlowPanel containerPanel;
        HorizontalPanel buttonsPanel;
        Label listNameLabel;
        TextBox listNameTextBox;
        Button confirmButton;
        Button cancelButton;
        // Initialise the UI elements for the list creator
        // Instance a dialog box to act as a pop-up window and set its display properties
        listCreator = new DialogBox();
        // Disable the background by blurring it with a dim color (requires CSS styling)
        listCreator.setGlassEnabled(true);
        // Enable animation features for the widget
        listCreator.setAnimationEnabled(true);
        // Set a title for the list creator window
        listCreator.setText("Create New List");
        // Instance the UI elements of the list creator with their default values
        containerPanel = new FlowPanel();
        buttonsPanel = new HorizontalPanel();
        // Initialise the label for the list creator
        listNameLabel = new Label("List name:");
        // Initialise the text box for the list creator
        listNameTextBox = new TextBox();
        // Initialise the buttons for the list creator
        confirmButton = new Button();
        confirmButton.setText("Confirm");
        cancelButton = new Button();
        cancelButton.setText("Cancel");
        // Set the CSS style to the confirm button
        confirmButton.setStyleName("confirmButton");
        // Set the CSS style to the cancel button
        cancelButton.setStyleName("cancelButton");
        // Adding the click handler methods for the buttons
        // The click handler method for the "Confirm" button
        confirmButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // A variable to store the name of the list
                String listName;
                // Get the updated value of the list name retrieved from the
                // text box of the list creator.
                listName = listNameTextBox.getText();
                // Proceed to request the creation of a new list in the database
                bmRequest.createList(listName);
                // Close the list creator window
                listCreator.hide();
                // A constant for setting a fixed delay time in milliseconds
                final int FIXED_DELAY = 500;
                // Schedule a fixed delay of 500 milliseconds before invoking the bookmarks
                // editor window so that the lists can be updated first.
                // Using the Timer class instead of the Scheduler class because it performs
                // better in this scenario (provides a smoother execution).
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        // Invoke the bookmarks editor window and return the bookmark
                        // information through the temporary Bookmark object.
                        showBookmarksEditor(temporaryBookmark);
                    }
                };
                // Adding the fixed delay to the timer object
                timer.schedule(FIXED_DELAY);
            }
        });
        // The click handler method for the "Cancel" button
        cancelButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Close the list creator window
                listCreator.hide();
                // Invoke the bookmarks editor window and return the bookmark information
                // through the temporary Bookmark object.
                showBookmarksEditor(temporaryBookmark);
            }
        });
        // Adding the UI elements of the list creator hierarchically
        // Add the list name UI elements to the container panel
        containerPanel.add(listNameLabel);
        containerPanel.add(listNameTextBox);
        // Add a break line in HTML to separate the widgets
        containerPanel.add(new HTML("&nbsp;"));
        // Add the list creator buttons to the buttons panel
        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        // Set the CSS style to the buttons panel of the list creator
        buttonsPanel.setStyleName("buttonsPanel");
        // Add the buttons panel to the container panel
        containerPanel.add(buttonsPanel);
        // Set the CSS style to the container panel of the list creator
        containerPanel.setStyleName("widgetPanel");
        // Add the container panel to the list creator window
        listCreator.add(containerPanel);
        // Position the list creator at the center of the screen
        listCreator.center();
        // Show the list creator window
        listCreator.show();
    }

    // Show the list manager window to manage the lists in the database
    private void showListManager() {
        // The UI elements of the list manager
        DialogBox listManager;
        FlowPanel containerPanel;
        HorizontalPanel buttonsPanel;
        Button removeButton;
        Button closeButton;
        // A flex table widget to display the lists
        FlexTable listsTable;
        // Constants for defining the columns of the content
        // Column 0: List name
        // Column 1: List manager
        final int LIST_COLUMN = 0;
        final int MANAGER_COLUMN = 1;
        // Initialise the UI elements for the list manager
        // Instance a dialog box to act as a pop-up window and set its display properties
        listManager = new DialogBox();
        // Disable the background by blurring it with a dim color (requires CSS styling)
        listManager.setGlassEnabled(true);
        // Enable animation features for the widget
        listManager.setAnimationEnabled(true);
        // Set a title for the list manager window
        listManager.setText("Manage My Lists");
        // Instance a flex table to act as a container of the lists
        listsTable = new FlexTable();
        // Set the CSS style to the lists table
        listsTable.setStyleName("listsTable");
        // Instance the UI elements of the list manager with their default values
        containerPanel = new FlowPanel();
        buttonsPanel = new HorizontalPanel();
        // Request to update the lists retrieved from the database
        bmRequest.getLists("bmWidget");
        // Retrieve all of the lists and prepare to display them
        for (String list : lists) {
            // If the list item is the "All" element ignore it, as it
            // cannot be directly managed from the list manager.
            if (list.equals("All")) {
                // Skip adding the "All" element to the lists table
                // as it is not an actual list.
                continue;
            }
            // A variable to control the creation of new entries in the lists table
            final int row = listsTable.getRowCount();
            // Add the list to the lists table in the corresponding column
            // Create a new entry in the flex table
            listsTable.setText(row, LIST_COLUMN, list);
            // Initialise the UI elements for the list manager
            removeButton = new Button();
            removeButton.setText("Delete");
            // The "General" list is created by default and cannot be removed from the database
            if (list.equals("General")) {
                // Disable the "Delete" button of the lists table
                // as it is the default list and cannot be removed.
                removeButton.setEnabled(false);
            }
            // Adding the click handler method for the button
            // The click handler method for the "Delete List" button
            removeButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    // A variable to store the value of the name of the list
                    String listName;
                    // Obtain the value of the list name field from the lists table
                    listName = listsTable.getText(row, LIST_COLUMN);
                    // Confirm if the user wants to delete the selected list
                    if (Window.confirm("Are you sure you want to permanently delete this list?")) {
                        // The user clicked on the "OK" button
                        // Proceed to request the removal of a list in the database
                        bmRequest.removeList(listName);
                        // Close the list manager window
                        listManager.hide();
                        // A constant for setting a fixed delay time in milliseconds
                        final int FIXED_DELAY = 500;
                        // Schedule a fixed delay of 500 milliseconds before invoking the list
                        // manager window so that the lists can be updated first.
                        // Using the Timer class instead of the Scheduler class because
                        // it performs better in this scenario (provides a smoother execution).
                        Timer timer = new Timer() {
                            @Override
                            public void run() {
                                // Invoke the list manager window so that the lists table
                                // gets refreshed.
                                showListManager();
                            }
                        };
                        // Adding the fixed delay to the timer object
                        timer.schedule(FIXED_DELAY);
                    } else {
                        // The user clicked on the "Cancel" button
                        // Dismiss the confirmation message and go back to the list manager window
                    }
                }
            });
            // Add the remove button to the lists table in the corresponding column
            // Create a new entry in the flex table
            listsTable.setWidget(row, MANAGER_COLUMN, removeButton);
        }
        // Initialise the remaining button for the list manager
        closeButton = new Button();
        closeButton.setText("Close");
        // Set the CSS style to the close button
        closeButton.setStyleName("closeButton");
        // Adding the click handler method for the button
        // The click handler method for the "Close" button
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                // Close the list manager window
                listManager.hide();
                // Clear all the UI elements in the list panel and in the bookmarks
                // panel so that the UI can be refreshed with the updated content.
                bmListPanel.clear();
                bmBookmarksPanel.clear();
                // The lists could have been modified while the list manager window
                // was active, so proceed to request to retrieve the lists so that
                // every UI element in the Main Panel gets refreshed.
                bmRequest.getLists("bmMainPanel");
            }
        });
        // Adding the UI elements of the list manager hierarchically
        // Add the lists table to the container panel
        containerPanel.add(listsTable);
        // Add the close button to the buttons panel
        buttonsPanel.add(closeButton);
        // Set the CSS style to the buttons panel of the list manager
        buttonsPanel.setStyleName("buttonsPanel");
        // Add the buttons panel to the container panel
        containerPanel.add(buttonsPanel);
        // Set the CSS style to the container panel of the list manager
        containerPanel.setStyleName("widgetPanel");
        // Add the container panel to the list manager window
        listManager.add(containerPanel);
        // Position the list manager at the center of the screen
        listManager.center();
        // Show the list manager window
        listManager.show();
    }
}