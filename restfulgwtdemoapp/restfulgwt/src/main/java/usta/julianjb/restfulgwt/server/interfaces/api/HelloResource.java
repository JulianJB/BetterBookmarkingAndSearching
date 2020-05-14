package usta.julianjb.restfulgwt.server.interfaces.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import usta.julianjb.restfulgwt.shared.domain.Hello;

@Path("hellos")
public class HelloResource {

    // A database to store the Hello objects
    Map<String, Hello> database;

    // Populate the database of Hello objects with their respective fields
    public HelloResource() {
        database = new HashMap<String, Hello>();
        // A Hello object is made from an "id" field and a "Name" field
        Hello hello1 = new Hello("1", "Aline");
        Hello hello2 = new Hello("2", "Aleksandra");

        database.put(hello1.getId(), hello1);
        database.put(hello2.getId(), hello2);
    }

    // Retrieve all of the Hello objects
    @GET
    @Produces("application/json")
    public Collection<Hello> get() {
        return database.values();
    }

    // Retrieve a Hello object by its id
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Hello getHello(@PathParam("id") String id) {
        return database.get(id);
    }
}