package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tomáš on 25.1.14.
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceTest
{
    public static final String WEBAPP_SRC = "src/main/webapp";

    private WebTarget target;

    @ArquillianResource
    URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment()
    {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addClasses(UserService.class, AbstractFacade.class)
                .addPackages(true, UserResource.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/ejb-jar.xml"), "ejb-jar.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
        return webArchive;

    }

    @Before
    public void setupClass() throws MalformedURLException
    {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "api/user").toExternalForm()));
    }

    @Test
    public void test1Post() throws NoSuchAlgorithmException
    {
        User user = new User();
        user.setEmail("test3@email.cz");
        user.setPassword("123456");

        Response response = target.request()
                .post(Entity.xml(user));
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }


    @Test
    public void test2Put() throws NoSuchAlgorithmException
    {
        User user = new User();
        user.setId(1L);
        user.setEmail("test3@email.cz");
        user.setPassword("654321");
        Response response = target.path("{id}")
                .resolveTemplate("id", "1")
                .request()
                .put(Entity.xml(user));

        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

    }

    @Test
    public void test3Get()
    {
        User user = target.path("{id}")
                .resolveTemplate("id", "1")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get(User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals("test3@email.cz", user.getEmail());
    }

    @Test
    public void test4GetAll()
    {
        User[] list = target.request()
                .accept(MediaType.APPLICATION_XML)
                .get(User[].class);
        Assert.assertEquals(1, list.length);
    }

    @Test
    public void test5Delete()
    {
        Response response = target.path("{id}")
                .resolveTemplate("id", "1")
                .request()
                .delete();

        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        User[] list = target.request()
                .accept(MediaType.APPLICATION_XML)
                .get(User[].class);
        Assert.assertEquals(0, list.length);
    }

    @Test
    public void test6Invalid()
    {
        User user = new User();
        user.setEmail("test");

        Response response = target.request()
                .accept(MediaType.APPLICATION_XML)
                .post(Entity.xml(user));
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        response.close();

        User[] list = target.request()
                .accept(MediaType.APPLICATION_XML)
                .get(User[].class);
        Assert.assertEquals(0, list.length);
    }
}
