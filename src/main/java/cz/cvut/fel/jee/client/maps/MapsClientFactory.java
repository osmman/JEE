package cz.cvut.fel.jee.client.maps;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.inject.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by Tomáš on 2.2.14.
 */
public class MapsClientFactory {

    private static final String RESOURCE = "http://maps.googleapis.com/maps/api";

    @Produces
    public static MapsClient create(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(RESOURCE);
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;

        return new MapsClient(rtarget.proxy(MapsApi.class));
    }
}
