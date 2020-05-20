package usta.julianjb.bands.client.api.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import usta.julianjb.bands.shared.domain.Bookmark;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * The REST requests that are supported by the server.
 * The BookmarkClient interface defines and maps the requests
 * between the client and the server of the Bookmarking module.
 */
@Path("/api/bookmarks")
public interface BookmarkClient extends RestService {

    // Retrieve all of the Bookmark objects
    @GET
    /**
     * Known bug: Apparently, for some reason, RestyGWT cannot define REST
     * requests where the MethodCallback object returned is of an ArrayList
     * type (which is serializable), so use List type instead for the client-side.
     * In the server-side is valid to use an ArrayList as a return type for the
     * request callback.
     */
    public void getBookmarks(MethodCallback<List<Bookmark>> bookmarks);

    // Create a bookmark in the database
    @POST
    public void createBookmark(Bookmark bookmark, MethodCallback<Void> callback);
}