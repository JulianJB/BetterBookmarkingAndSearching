package usta.julianjb.bands.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class NativeScript_default_InlineClientBundleGenerator implements usta.julianjb.bands.client.resources.NativeScript {
  private static NativeScript_default_InlineClientBundleGenerator _instance0 = new NativeScript_default_InlineClientBundleGenerator();
  private void bookmarkletScriptInitializer() {
    bookmarkletScript = new com.google.gwt.resources.client.TextResource() {
      // file:/home/jjb21/APPDATA/IdeaProjects/bands/bands/src/main/java/usta/julianjb/bands/client/resources/javascript/BookmarkletScript.js
      public String getText() {
        return "javascript:(function()%7Bconst%20url%20%3D%20%22http%3A%2F%2F127.0.0.1%3A8888%2Fbookmarking.html%22%3Bvar%20pageTitle%20%3D%20encodeURIComponent(document.title)%3Bvar%20pageDescription%20%3D%20%22%22%3Bvar%20urlEncoded%20%3D%20encodeURIComponent(location.href)%3Bvar%20metaTags%20%3D%20document.getElementsByTagName(%22meta%22)%3Bfor%20(var%20i%20in%20metaTags)%20%7Bif%20(typeof%20(metaTags%5Bi%5D.name)%20!%3D%20%22undefined%22%20%26%26%20metaTags%5Bi%5D.name.toLowerCase()%20%3D%3D%20%22description%22)%20%7BpageDescription%20%3D%20encodeURIComponent(metaTags%5Bi%5D.content)%3B%7D%7Dvar%20queryString%20%3D%20%22%3Ftitle%3D%22%20%2B%20pageTitle%20%2B%20%22%26description%3D%22%20%2B%20pageDescription%20%2B%20%22%26url%3D%22%20%2B%20urlEncoded%3Bvar%20redirectUrl%20%3D%20url%20%2B%20queryString%3Bconsole.log(%22RedirectURL%3A%20%22%2C%20redirectUrl)%3Balert(%22Bookmark%20created%20successfully%22%20%2B%22%5Cn%5CnYou%20are%20going%20to%20be%20redirected%20to%20your%20Bookmarks%20page%22)%3Bwindow.location.replace(redirectUrl)%7D)()";
      }
      public String getName() {
        return "bookmarkletScript";
      }
    }
    ;
  }
  private static class bookmarkletScriptInitializer {
    static {
      _instance0.bookmarkletScriptInitializer();
    }
    static com.google.gwt.resources.client.TextResource get() {
      return bookmarkletScript;
    }
  }
  public com.google.gwt.resources.client.TextResource bookmarkletScript() {
    return bookmarkletScriptInitializer.get();
  }
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.google.gwt.resources.client.TextResource bookmarkletScript;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      bookmarkletScript(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("bookmarkletScript", bookmarkletScript());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'bookmarkletScript': return this.@usta.julianjb.bands.client.resources.NativeScript::bookmarkletScript()();
    }
    return null;
  }-*/;
}
