/**
 * Bookmarklet Functionality
 * @JulianJb
 *
 * This JS file contains the Bookmarklet functionality for the
 * Bookmarklet script. The script (compressed version) is in
 * another file.
 *
 * Version 1.0 / Date: February 21st, 2020
 */

// Inside a function for future wrapping
(function () {

    // A constant for the base url of the server
    // TODO replace with actual server url (GWT-devmode)
    //const url = "https://reqres.in/api/users"; // DEBUG: use this url for testing @POST requests
    // const url = "https://httpbin.org/redirect/1"; // DEBUG: use this url for testing @GET requests (redirect)

    const url = "http://127.0.0.1:8888"; // DEBUG: use this url for testing @POST requests

    // Define the variables to send the information about the website
    var pageTitle = encodeURIComponent(document.title);
    var urlEncoded = encodeURIComponent(location.href);

    // A variable to store the redirect URL
    var redirectUrl;

    // The HTTP Request function used for handling the HTTP requests
    // Currently only @GET and @POST supported
    // Using the Fetch API for Pure JS
    const sendHttpRequest = (method, url, data) => {
        return fetch(url, {
            // The HTTP request type
            method: method,
            // The data to be sent (the page title and the url encoded)
            body: JSON.stringify(data),
            // The headers for handling the HTTP request
            // If there is data in the request then send the appropriate header, else is not needed
            headers: data ? {"Content-Type": "application/json"} : {}
        }).then(response => {
            // DEBUG: Checking if the response sent by the server is a redirection (experimental)
            if (response.redirected) {
                // Grab the url of the response which is going to be used for the redirection
                redirectUrl = response.url;
                // DEBUG: Verifying that the url is valid
                console.log("RedirectURL: ", redirectUrl);
            }
            if (response.status >= 400) { // If the response from the server was an error, also: "!response.ok"
                // Retrieve the error response from the server in a valid JSON format
                return response.json().then(errorResponseData => {
                    const error = new Error("An error occurred while processing the request");
                    error.data = errorResponseData;
                    throw error;
                });
            }
            // Return the response from the server
            return response.json();
        });
    };

    // @GET request
    const getData = () => {
        // Call the HTTPRequest method and perform a @GET request
        sendHttpRequest("GET", redirectUrl) // The redirect url should be sent to the server
            .then(responseData => {
                console.log(responseData); // DEBUG: log the server response for the GET request
            });
    };

    // @POST request
    const sendData = () => {
        // Call the HTTPRequest method and perform a @POST request
        // DEBUG: use this instruction for debugging redirection otherwise the response will fail
        //  (server not ready to receive data on redirection)
        // sendHttpRequest('GET', url, {
        // Use this instruction to perform a @POST in a server that supports it
        sendHttpRequest("POST", url, {
            pageTitle: pageTitle, // TODO must be changed to variables names of the GWT server app
            urlEncoded: urlEncoded, // TODO must be changed to variables names of the GWT server app
        })
            .then(responseData => {
                console.log(responseData); // DEBUG: log the server response for the POST request
                // Pop up window confirming the bookmark creation
                alert("Bookmark created successfully");
                if (confirm("Do you want to edit this bookmark?")) {
                    // The user wants to edit this bookmark now
                    // Pop up window notifying the user that the webpage is going to be redirected
                    alert("You are going to be redirected to your Bookmarks page");
                    // Call the getData method to perform a @GET request
                    getData();
                } else {
                    // The user does not want to edit this bookmark now
                    // Dismiss the pop up window and stay on the webpage
                }
            })
            .catch(error => {
                console.log(error, error.data); // DEBUG: log the server error for the POST request
            });
    };

    // Make an automatic call to the sendData method to perform a @POST request
    sendData();

})();