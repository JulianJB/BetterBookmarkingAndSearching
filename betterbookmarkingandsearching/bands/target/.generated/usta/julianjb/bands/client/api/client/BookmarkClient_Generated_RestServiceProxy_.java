package usta.julianjb.bands.client.api.client;

import static org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.*;

public class BookmarkClient_Generated_RestServiceProxy_ implements usta.julianjb.bands.client.api.client.BookmarkClient, org.fusesource.restygwt.client.RestServiceProxy {
  private org.fusesource.restygwt.client.Resource resource = null;
  
  public void setResource(org.fusesource.restygwt.client.Resource resource) {
    this.resource = resource;
  }
  public org.fusesource.restygwt.client.Resource getResource() {
    if (this.resource == null) {
      String serviceRoot = org.fusesource.restygwt.client.Defaults.getServiceRoot();
      this.resource = new org.fusesource.restygwt.client.Resource(serviceRoot).resolve("/api/bookmarks");
    }
    return this.resource;
  }
  private org.fusesource.restygwt.client.Dispatcher dispatcher = null;
  
  public void setDispatcher(org.fusesource.restygwt.client.Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }
  
  public org.fusesource.restygwt.client.Dispatcher getDispatcher() {
    return this.dispatcher;
  }
  public void createBookmark(usta.julianjb.bands.shared.domain.Bookmark bookmark, org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback) {
    final usta.julianjb.bands.shared.domain.Bookmark final_bookmark = bookmark;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .post();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    __method.json(usta.julianjb.bands.shared.domain.Bookmark_Generated_JsonEncoderDecoder_.INSTANCE.encode(bookmark));
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.lang.Void>(__method, callback) {
        protected java.lang.Void parseResult() throws Exception {
          return (java.lang.Void) null;
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void editBookmark(java.lang.String urlEncoded, usta.julianjb.bands.shared.domain.Bookmark bookmark, org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback) {
    final java.lang.String final_urlEncoded = urlEncoded;
    final usta.julianjb.bands.shared.domain.Bookmark final_bookmark = bookmark;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/bmEditor/"+(urlEncoded== null? null : com.google.gwt.http.client.URL.encodePathSegment(urlEncoded))+"")
    .put();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    __method.json(usta.julianjb.bands.shared.domain.Bookmark_Generated_JsonEncoderDecoder_.INSTANCE.encode(bookmark));
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.lang.Void>(__method, callback) {
        protected java.lang.Void parseResult() throws Exception {
          return (java.lang.Void) null;
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
  public void getBookmark(java.lang.String urlEncoded, org.fusesource.restygwt.client.MethodCallback<usta.julianjb.bands.shared.domain.Bookmark> bookmark) {
    final java.lang.String final_urlEncoded = urlEncoded;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/bmEditor/"+(urlEncoded== null? null : com.google.gwt.http.client.URL.encodePathSegment(urlEncoded))+"")
    .get();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<usta.julianjb.bands.shared.domain.Bookmark>(__method, bookmark) {
        protected usta.julianjb.bands.shared.domain.Bookmark parseResult() throws Exception {
          try {
            return usta.julianjb.bands.shared.domain.Bookmark_Generated_JsonEncoderDecoder_.INSTANCE.decode(com.google.gwt.json.client.JSONParser.parse(__method.getResponse().getText()));
          } catch (Throwable __e) {
            throw new org.fusesource.restygwt.client.ResponseFormatException("Response was NOT a valid JSON document", __e);
          }
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      bookmark.onFailure(__method,__e);
    }
  }
  public void getBookmarks(org.fusesource.restygwt.client.MethodCallback<java.util.List<usta.julianjb.bands.shared.domain.Bookmark>> bookmarks) {
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .get();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.util.List<usta.julianjb.bands.shared.domain.Bookmark>>(__method, bookmarks) {
        protected java.util.List<usta.julianjb.bands.shared.domain.Bookmark> parseResult() throws Exception {
          try {
            return toList(com.google.gwt.json.client.JSONParser.parse(__method.getResponse().getText()), usta.julianjb.bands.shared.domain.Bookmark_Generated_JsonEncoderDecoder_.INSTANCE);
          } catch (Throwable __e) {
            throw new org.fusesource.restygwt.client.ResponseFormatException("Response was NOT a valid JSON document", __e);
          }
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      bookmarks.onFailure(__method,__e);
    }
  }
  public void removeBookmark(java.lang.String urlEncoded, org.fusesource.restygwt.client.MethodCallback<java.lang.Void> callback) {
    final java.lang.String final_urlEncoded = urlEncoded;
    final org.fusesource.restygwt.client.Method __method =
    getResource()
    .resolve("/bmManager/"+(urlEncoded== null? null : com.google.gwt.http.client.URL.encodePathSegment(urlEncoded))+"")
    .delete();
    __method.setDispatcher(this.dispatcher);
    __method.header(org.fusesource.restygwt.client.Resource.HEADER_ACCEPT, org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON);
    try {
      __method.send(new org.fusesource.restygwt.client.AbstractRequestCallback<java.lang.Void>(__method, callback) {
        protected java.lang.Void parseResult() throws Exception {
          return (java.lang.Void) null;
        }
      });
    } catch (com.google.gwt.http.client.RequestException __e) {
      callback.onFailure(__method,__e);
    }
  }
}
