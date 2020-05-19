package usta.julianjb.bands.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The EntryPoint of the Searching module.
 * This code is displayed to the user's browser.
 */
public class bands_searching implements EntryPoint {

    // The code to be executed once the GWT module is loaded
    public void onModuleLoad() {

        // Map a div element of the HTML file as the Root Panel of the application
        final RootPanel rootPanel = RootPanel.get("searchingPanel");
        // Add a vertical panel to display the list of Bookmark objects
        final VerticalPanel verticalPanel = new VerticalPanel();
        rootPanel.add(verticalPanel);

        // DEBUG: Testing displaying a simple label in the Index page
        final Label welcomeLabel = new Label("Welcome to the BANDS Search Engine");
        verticalPanel.add(welcomeLabel);
    }
}