/**
 * The script in JavaScript of the Bookmarklet.
 * This JavaScript file contains the code of the Bookmarklet functionality
 * for the Bookmarklet script. The actual script (compressed version)
 * is in the file "BookmarkletScript.js".
 */

// A constant for the base url of the bookmarking module of the server
const url = "http://127.0.0.1:8888/bookmarking.html";

// Define the variables to send the information about the website
var pageTitle = encodeURIComponent(document.title);
var pageDescription = "";
var urlEncoded = encodeURIComponent(location.href);

/**
 * This code snippet is taken from the following source:
 * Title: A Javascript function used to extract meta description from an HTML document
 * Author: Maloney, N.
 * Date: 2011
 * Availability: https://gist.github.com/ngmaloney/1049839
 */
// Get the description about the website
var metaTags = document.getElementsByTagName("meta");
// Look in the meta tags of the website for information about the website
for (var i in metaTags) {
    // If one of the meta tags is labelled as "Description" obtain the contents of the meta tag
    if (typeof (metaTags[i].name) != "undefined" && metaTags[i].name.toLowerCase() == "description") {
        pageDescription = encodeURIComponent(metaTags[i].content);
    } // A "Description" meta tag is not compulsory, so send an empty description instead
}

// Define the query string and store into a variable
// The format of the query string as required by GWT is:
// ?title={pageTitle}&description={pageDescription}&url={urlEncoded}
var queryString = "?title=" + pageTitle + "&description=" + pageDescription + "&url=" + urlEncoded;

// A variable for the HTTP redirection request (the server URL with parameters)
var redirectUrl = url + queryString;

// DEBUG: Verifying that the url is valid
console.log("RedirectURL: ", redirectUrl);

// Pop up window confirming the bookmark creation and
// notifying the user that the web page is going to be redirected
alert("Bookmark created successfully" +
    "\n\nYou are going to be redirected to your Bookmarks page");

// Redirection to the GWT bookmarking server
window.location.replace(redirectUrl);