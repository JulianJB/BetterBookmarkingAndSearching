package usta.julianjb.restfulgwt.shared.domain;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The components that are shared between the client and server classes.
 * The specification of a Hello object is defined here so that both the client
 * and the server make a reference to the same data type.
 */
public class Hello {

    // The attributes of the Hello object
    public String id;
    public String name;

    public Hello() {
    }

    // The constructor for the Hello object, defines a Java object as a JSON object
    @JsonCreator
    public Hello(@JsonProperty("id") String id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    // A method to query the object by id
    public String getId() {
        return id;
    }

    // A method to query the object by name
    public String getName() {
        return name;
    }
}