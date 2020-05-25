/**
 * Bookmarklet Functionality (Redirect)
 * Production version, no comments included
 * @JulianJb
 *
 * This JS file contains the Bookmarklet functionality for the
 * Bookmarklet script. The script (compressed version) is in
 * another file (BookmarkletScript.js).
 *
 * Version 1.3 / Date: May 25, 2020
 */

const url = "http://127.0.0.1:8888/bookmarking.html";

var pageTitle = encodeURIComponent(document.title);
var pageDescription = "";
var urlEncoded = encodeURIComponent(location.href);

var metaTags = document.getElementsByTagName("meta");
for (var i in metaTags) {
    if (typeof (metaTags[i].name) != "undefined" && metaTags[i].name.toLowerCase() == "description") {
        pageDescription = encodeURIComponent(metaTags[i].content);
    }
}

var queryString = "?title=" + pageTitle + "&description=" + pageDescription + "&url=" + urlEncoded;
var redirectUrl = url + queryString;

console.log("RedirectURL: ", redirectUrl);

alert("Bookmark created successfully" +
    "\n\nYou are going to be redirected to your Bookmarks page");

window.location.replace(redirectUrl);