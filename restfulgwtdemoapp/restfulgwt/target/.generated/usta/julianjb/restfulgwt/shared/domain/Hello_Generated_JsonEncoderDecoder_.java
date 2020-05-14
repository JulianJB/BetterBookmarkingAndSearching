package usta.julianjb.restfulgwt.shared.domain;

public class Hello_Generated_JsonEncoderDecoder_ extends org.fusesource.restygwt.client.AbstractJsonEncoderDecoder<usta.julianjb.restfulgwt.shared.domain.Hello> {
  
  public static final Hello_Generated_JsonEncoderDecoder_ INSTANCE = new Hello_Generated_JsonEncoderDecoder_();
  
  public com.google.gwt.json.client.JSONValue encode(usta.julianjb.restfulgwt.shared.domain.Hello value) {
    if( value==null ) {
      return getNullType();
    }
    com.google.gwt.json.client.JSONObject rc = new com.google.gwt.json.client.JSONObject();
    usta.julianjb.restfulgwt.shared.domain.Hello parseValue = (usta.julianjb.restfulgwt.shared.domain.Hello)value;
    isNotNullValuePut(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.encode(parseValue.getId()), rc, "id");
    isNotNullValuePut(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.encode(parseValue.getName()), rc, "name");
    return rc;
  }
  
  public usta.julianjb.restfulgwt.shared.domain.Hello decode(com.google.gwt.json.client.JSONValue value) {
    if( value == null || value.isNull()!=null ) {
      return null;
    }
    com.google.gwt.json.client.JSONObject object = toObject(value);
    usta.julianjb.restfulgwt.shared.domain.Hello rc = new usta.julianjb.restfulgwt.shared.domain.Hello();
    rc.id= getValueToSet(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.decode(object.get("id")),null);
    rc.name= getValueToSet(org.fusesource.restygwt.client.AbstractJsonEncoderDecoder.STRING.decode(object.get("name")),null);
    return rc;
  }
  
}
