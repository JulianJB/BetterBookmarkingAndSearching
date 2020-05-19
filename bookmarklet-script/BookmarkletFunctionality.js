/**
 * Bookmarklet Functionality (Redirect)
 * Production version, no comments included
 * @JulianJb
 *
 * This JS file contains the Bookmarklet functionality for the
 * Bookmarklet script. The script (compressed version) is in
 * another file (BookmarkletScript.js).
 *
 * Version 1.2 / Date: May 18, 2020
 */

const url = "http://127.0.0.1:8888/bookmarking.html";

var pageTitle = encodeURIComponent(document.title);
var urlEncoded = encodeURIComponent(location.href);
var queryString = "?title=" + pageTitle + "&url=" + urlEncoded;
var redirectUrl = url + queryString;

console.log("RedirectURL: ", redirectUrl);

alert("Bookmark created successfully" +
    "\n\nYou are going to be redirected to your Bookmarks page");

window.location.replace(redirectUrl);