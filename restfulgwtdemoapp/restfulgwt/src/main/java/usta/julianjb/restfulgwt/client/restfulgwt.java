package usta.julianjb.restfulgwt.client;

import java.util.List;

import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import usta.julianjb.restfulgwt.client.api.client.HelloClient;
import usta.julianjb.restfulgwt.shared.domain.Hello;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The EntryPoint of the GWT application.
 * This code is displayed to the user's browser.
 */
public class restfulgwt implements EntryPoint {

  // The code to be executed once the GWT module is loaded
  public void onModuleLoad() {

    Defaults.setServiceRoot(GWT.getHostPageBaseURL());

    // Map a div element of the HTML file as the Root Panel of the application
    final RootPanel rootPanel = RootPanel.get("resty");
    // Add a vertical panel to display the list of Hello objects
    final VerticalPanel verticalPanel = new VerticalPanel();
    rootPanel.add(verticalPanel);

    // Create a client to retrieve the Hello objects
    HelloClient client = GWT.create(HelloClient.class);

    // Access all of the Hello objects
    client.getAll(new MethodCallback<List<Hello>>() {

      // If the GET request is handled correctly display the Hello objects
      public void onSuccess(Method method, List<Hello> response) {
        for (Hello hello : response) {
          // Populate the vertical panel with labels with the data of the Hello objects
          Label label = new Label("Hello " + hello.getName());
          verticalPanel.add(label);
          // Alternative way to display the list of Hello objects
          //verticalPanel.add(new Label("Hello " + hello.getName()));
        }
      }

      // If the GET request is not handled correctly throw an exception
      public void onFailure(Method method, Throwable exception) {
        throw new RuntimeException("Call failed");
      }
    });
  }
}