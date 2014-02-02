package cz.cvut.fel.jee.client.maps;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by Tomáš on 2.2.14.
 */
public interface MapsApi {

    @GET
    @Path("/geocode/json")
    @Consumes(MediaType.APPLICATION_JSON)
    JsonObject getGeoCode(@QueryParam("address") String address, @QueryParam("sensor") Boolean sensor);
}
