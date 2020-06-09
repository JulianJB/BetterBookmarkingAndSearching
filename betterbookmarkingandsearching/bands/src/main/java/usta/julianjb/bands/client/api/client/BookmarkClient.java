package usta.julianjb.bands.client.api.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import usta.julianjb.bands.shared.domain.Bookmark;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * The REST requests that are supported by the server.
 * The BookmarkClient interface defines and maps the requests
 * between the client and the server of the Bookmarking module.
 */
@Path("/api/bookmarks")
public interface BookmarkClient extends RestService {

    /**
     * Note: Apparently, RestyGWT cannot define REST requests where
     * the MethodCallback object returned is of an ArrayList type
     * (which is serializable), so use List type instead for the client-side.
     * In the server-side is valid to use an ArrayList as a return type
     * for the request callback.
     */
    @GET
    // Retrieve all of the Bookmark objects
    public void getBookmarks(MethodCallback<List<Bookmark>> bookmarks);

    // Create a bookmark in the database
    @POST
    public void createBookmark(Bookmark bookmark, MethodCallback<Void> callback);

    // Retrieve a Bookmark object from the database using its URL value
    @GET
    // Map the request as of Bookmark Editor type (/bmEditor)
    @Path("/bmEditor/{url}")
    public void getBookmark(@PathParam("url") String urlEncoded, MethodCallback<Bookmark> bookmark);

    // Remove a bookmark from the database
    @DELETE
    // Map the request as of Bookmark Manager type (/bmManager)
    @Path("/bmManager/{url}")
    public void removeBookmark(@PathParam("url") String urlEncoded, MethodCallback<Void> callback);

    // Edit a bookmark in the database
    @PUT
    // Map the request as of Bookmark Editor type (/bmEditor)
    @Path("/bmEditor/{url}")
    public void editBookmark(@PathParam("url") String urlEncoded, Bookmark bookmark,
                             MethodCallback<Void> callback);

    // Retrieve all of the lists for filtering
    @GET
    // Map the request as of Bookmark Filter type (/bmFilter)
    @Path("/bmFilter")
    public void getLists(MethodCallback<List<String>> lists);

    // Retrieve all of the Bookmark objects filtered from a list
    @GET
    // Map the request as of Bookmark Filter type (/bmFilter)
    @Path("/bmFilter/{list}")
    public void filterBookmarks(@PathParam("list") String list, MethodCallback<List<Bookmark>> bookmarks);

    // Create a list in the database
    @POST
    // Map the request as of Bookmark Filter type (/bmFilter)
    @Path("/bmFilter/{list}")
    public void createList(@PathParam("list") String list, MethodCallback<Void> callback);

    // Remove a list from the database
    @DELETE
    // Map the request as of Bookmark Filter type (/bmFilter)
    @Path("/bmFilter/{list}")
    public void removeList(@PathParam("list") String list, MethodCallback<Void> callback);
}