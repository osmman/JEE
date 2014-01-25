package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.utils.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by Tomáš on 25.1.14.
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceTest
{
    private static WebTarget target;

    @ArquillianResource
    URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment()
    {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(UserService.class.getPackage())
                .addPackage(UserResource.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Before
    public void setupClass() throws MalformedURLException
    {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "api/user").toExternalForm()));
    }

    @Test
    public void test1Post()
    {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("email", "test3@email.cz");
        map.add("password", "123456");
        Response response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(map));
        Assert.assertEquals(Response.Status.CREATED, response.getStatus());

        map = new MultivaluedHashMap<>();
        map.add("email", "test4@email.cz");
        map.add("password", "123456");
        response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(map));
        Assert.assertEquals(Response.Status.CREATED, response.getStatus());
    }

    @Test
    public void test2Put()
    {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("email", "test5@email.cz");
        map.add("id", "2");
        Response response = target.path("{id}")
                .resolveTemplate("id", "2")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.json(map));

        Assert.assertEquals(Response.Status.NO_CONTENT, response.getStatus());

    }

    @Test
    public void test3Get()
    {
        User user = target.path("{id}")
                .resolveTemplate("id", "1")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals("test3@email.cz", user.getEmail());
    }

    @Test
    public void test4GetAll()
    {
        GenericType<Collection<User>> listm = new GenericType<Collection<User>>() {};
        Collection<User> list = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(listm);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void test5Delete()
    {

    }
}
