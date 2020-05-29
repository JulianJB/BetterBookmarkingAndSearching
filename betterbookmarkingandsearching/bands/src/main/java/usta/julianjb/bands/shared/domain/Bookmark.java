package usta.julianjb.bands.shared.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The components that are shared between the client and server classes.
 * The specification of a Bookmark object is defined here so that both the client
 * and the server make a reference to the same object data type.
 */
public class Bookmark {

    // The attributes of the Bookmark object
    public String pageTitle;
    public String pageDescription;
    public String urlEncoded;
    public String list;

    public Bookmark() {
    }

    // The constructor for the Bookmark object, defines a Java object as a JSON object
    @JsonCreator
    public Bookmark(@JsonProperty("pageTitle") String pageTitle,
                    @JsonProperty("pageDescription") String pageDescription,
                    @JsonProperty("urlEncoded") String urlEncoded,
                    @JsonProperty("list") String list) {
        this.pageTitle = pageTitle;
        this.pageDescription = pageDescription;
        this.urlEncoded = urlEncoded;
        this.list = list;
    }

    // A method to query the Bookmark object by Page Title
    public String getPageTitle() {
        return pageTitle;
    }

    // A method to set the Page Title value of the Bookmark object
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    // A method to query the Bookmark object by Page Description
    public String getPageDescription() {
        return pageDescription;
    }

    // A method to set the Page Description value of the Bookmark object
    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    // A method to query the Bookmark object by URL Encoded
    public String getUrlEncoded() {
        return urlEncoded;
    }

    // A method to set the URL Encoded value of the Bookmark object
    public void setUrlEncoded(String urlEncoded) {
        this.urlEncoded = urlEncoded;
    }

    // A method to query the Bookmark object by List
    public String getList() {
        return list;
    }

    // A method to set the List value of the Bookmark object
    public void setList(String list) {
        this.list = list;
    }
}