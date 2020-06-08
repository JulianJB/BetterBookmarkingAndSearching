package usta.julianjb.bands.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * The NativeScript interface defines and maps text resources
 * between the client and external source files (JavaScript files)
 * for accessing their content easily from within the application.
 */
public interface NativeScript extends ClientBundle {

    // Associate the bookmarklet script JavaScript file as a resource file
    // by specifying the script location in the resources package.
    @Source("javascript/BookmarkletScript.js")
    TextResource bookmarkletScript();
}