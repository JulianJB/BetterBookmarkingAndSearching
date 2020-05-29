package usta.julianjb.bands.shared.domain;

import static org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.*;

public class Bookmark_Generated_JsonEncoderDecoder_ extends org.fusesource.restygwt.client.AbstractJsonEncoderDecoder<usta.julianjb.bands.shared.domain.Bookmark> {
  
  public static final Bookmark_Generated_JsonEncoderDecoder_ INSTANCE = new Bookmark_Generated_JsonEncoderDecoder_();
  
  public com.google.gwt.json.client.JSONValue encode(usta.julianjb.bands.shared.domain.Bookmark value) {
    if( value==null ) {
      return getNullType();
    }
      return encodeBookmark1475508727(value);
    }
    
    private com.google.gwt.json.client.JSONValue encodeBookmark1475508727( usta.julianjb.bands.shared.domain.Bookmark value) {
      com.google.gwt.json.client.JSONObject rc = new com.google.gwt.json.client.JSONObject();
      usta.julianjb.bands.shared.domain.Bookmark parseValue = (usta.julianjb.bands.shared.domain.Bookmark)value;
      isNotNullValuePut(STRING.encode(parseValue.getPageTitle()), rc, "pageTitle");
      isNotNullValuePut(STRING.encode(parseValue.getPageDescription()), rc, "pageDescription");
      isNotNullValuePut(STRING.encode(parseValue.getUrlEncoded()), rc, "urlEncoded");
      isNotNullValuePut(STRING.encode(parseValue.getList()), rc, "list");
      return rc;
    }
    
    public usta.julianjb.bands.shared.domain.Bookmark decode(com.google.gwt.json.client.JSONValue value) {
      if( value == null || value.isNull()!=null ) {
        return null;
      }
      com.google.gwt.json.client.JSONObject object = toObject(value);
        return decodeBookmark1475508727(object);
      }
      
      private usta.julianjb.bands.shared.domain.Bookmark decodeBookmark1475508727(com.google.gwt.json.client.JSONObject object) {
        // We found a creator so we use the annotated constructor
        usta.julianjb.bands.shared.domain.Bookmark rc = new usta.julianjb.bands.shared.domain.Bookmark(
          // The arguments are placed in the order they appear within the annotated constructor
          object.get("pageTitle") == null || object.get("pageTitle") instanceof com.google.gwt.json.client.JSONNull ? null : STRING.decode(object.get("pageTitle")), 
          object.get("pageDescription") == null || object.get("pageDescription") instanceof com.google.gwt.json.client.JSONNull ? null : STRING.decode(object.get("pageDescription")), 
          object.get("urlEncoded") == null || object.get("urlEncoded") instanceof com.google.gwt.json.client.JSONNull ? null : STRING.decode(object.get("urlEncoded")), 
          object.get("list") == null || object.get("list") instanceof com.google.gwt.json.client.JSONNull ? null : STRING.decode(object.get("list"))
        );
        return rc;
      }
      
    }
