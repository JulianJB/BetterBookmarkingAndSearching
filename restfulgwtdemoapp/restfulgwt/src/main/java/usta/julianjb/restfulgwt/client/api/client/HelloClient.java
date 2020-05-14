package usta.julianjb.restfulgwt.client.api.client;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import usta.julianjb.restfulgwt.shared.domain.Hello;

@Path("/api/hellos")
public interface HelloClient extends RestService {

    // Retrieve all of the Hello objects
    @GET
    public void getAll(MethodCallback<List<Hello>> hellos);

    // Retrieve a Hello object by its id
    @GET
    @Path("/{id}")
    public void getHello(@PathParam("id") String id, MethodCallback<List<Hello>> hello);
}