package cz.cvut.fel.jee.client.maps;

import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tomáš on 2.2.14.
 */
public class MapsClient {

    private MapsApi api;

    public MapsClient(MapsApi api) {
        this.api = api;
    }

    public String getLocation(String address) {
        JsonObject response = api.getGeoCode(address, false);

        try {
            JsonObject location = response.getJsonArray("results")
                    .getJsonObject(0)
                    .getJsonObject("geometry")
                    .getJsonObject("location");

            return location.getJsonNumber("lat") + "," + location.getJsonNumber("lng");

        } catch (NullPointerException | IndexOutOfBoundsException e) {
            Logger.getLogger(MapsClient.class.getName()).log(Level.SEVERE, e.toString(), e);
            return null;
        }
    }
}
