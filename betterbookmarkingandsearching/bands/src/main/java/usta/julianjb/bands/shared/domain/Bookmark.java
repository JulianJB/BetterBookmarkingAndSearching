package usta.julianjb.bands.shared.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The components that are shared between the client and server classes.
 * The specification of a Bookmark object is defined here so that both the client
 * and the server make a reference to the same data type.
 */
public class Bookmark {

    // The attributes of the Bookmark object
    public String pageTitle;
    public String urlEncoded;

    public Bookmark() {
    }

    // The constructor for the Bookmark object, defines a Java object as a JSON object
    @JsonCreator
    public Bookmark(@JsonProperty("pageTitle") String pageTitle, @JsonProperty("urlEncoded") String urlEncoded) {
        this.pageTitle = pageTitle;
        this.urlEncoded = urlEncoded;
    }

    // A method to query the Bookmark object by Page Title
    public String getPageTitle() {
        return pageTitle;
    }

    // A method to set the Page Title value of the Bookmark object
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    // A method to query the Bookmark object by URL Encoded
    public String getUrlEncoded() {
        return urlEncoded;
    }

    // A method to set the URL Encoded value of the Bookmark object
    public void setUrlEncoded(String urlEncoded) {
        this.urlEncoded = urlEncoded;
    }
}