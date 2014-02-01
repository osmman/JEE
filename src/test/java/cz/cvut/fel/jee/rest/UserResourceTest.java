package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.AbstractFacadeImpl;
import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.ejb.CommentServiceImpl;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.ejb.UserServiceImpl;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.endpoints.websocket.CommentMessage;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.rest.adapters.UserAdapter;
import cz.cvut.fel.jee.rest.entity.EntityXml;
import cz.cvut.fel.jee.rest.entity.UserXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import cz.cvut.fel.jee.rest.mock.VideoServiceMock;
import cz.cvut.fel.jee.utils.Resources;
import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.infinispan.io.GridFilesystem;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
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

/**
 * Created by Tomáš on 25.1.14.
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceTest {

    public static final String WEBAPP_SRC = "src/main/webapp";

    private WebTarget target;

    @ArquillianResource
    URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addClasses(CommentMessage.class,
                        AbstractFacade.class,
                        AbstractFacadeImpl.class,
                        UserService.class,
                        UserServiceImpl.class,
                        CommentService.class,
                        CommentServiceImpl.class,
                        VideoService.class
                        )
                .addClass(VideoServiceMock.class)
                .addPackages(true, UserResource.class.getPackage())
                .addPackages(true, UserAdapter.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/ejb-jar.xml"), "ejb-jar.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
        return webArchive;

    }

    @Before
    public void setupClass() throws MalformedURLException {
        
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "api/user").toExternalForm()));
        System.out.println(target.getUri().toString());
    }

    @Test
    public void test1Post() throws Exception {
        User user = new User();
        user.setEmail("test3@email.cz");
        user.setPassword("123456");
//        UserXml marshal = new UserAdapter().marshal(user);
//        Response response = target.request()
//                .post(Entity.json(marshal));
//        
        Response response = target.request()
                .post(EntityJSON(user));
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void test2Put() throws NoSuchAlgorithmException {
        User user = new User();
        user.setId(1L);
        user.setEmail("test3@email.cz");
        user.setPassword("654321");
        WebTarget resolveTemplate = target.path("{id}")
                .resolveTemplate("id", "1");
        System.out.println(resolveTemplate.getUri().toString());
        Response response = resolveTemplate
                .request()
                .put(EntityJSON(user));
        

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }

    @Test
    public void test3Get() {
        User user = target.path("{id}")
                .resolveTemplate("id", "1")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals("test3@email.cz", user.getEmail());
    }

    @Test
    public void test4GetAll() {
        User[] list = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(User[].class);
        Assert.assertEquals(1, list.length);
    }

    @Test
    public void test5Delete() {
        Response response = target.path("{id}")
                .resolveTemplate("id", "1")
                .request()
                .delete();

        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        User[] list = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(User[].class);
        Assert.assertEquals(0, list.length);
    }

    @Test
    public void test6Invalid() {
        User user = new User();
        user.setEmail("test");

        Response response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(user));
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        response.close();

        User[] list = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .get(User[].class);
        Assert.assertEquals(0, list.length);
    }
    
    
    static Entity<String> EntityJSON(User u){
        StringBuilder sb = new StringBuilder("{");
 
        if(u.getId() != null){
            sb.append("\"id\":");
            sb.append(u.getId().toString());
            sb.append("");
        }
        
        if(u.getEmail() != null){
            if(sb.length() > 1){
                sb.append(", ");
            }
            sb.append("\"email\":\"");
            sb.append(u.getEmail());
            sb.append("\"");
        }
        
        if(u.getPassword() != null){
            if(sb.length() > 1){
                sb.append(", ");
            }
            sb.append("\"password\":\"");
            sb.append(u.getPassword());
            sb.append("\"");
        }
        sb.append("}");
        System.out.println(sb.toString());
        return Entity.entity(sb.toString(), MediaType.APPLICATION_JSON);
    }
}
