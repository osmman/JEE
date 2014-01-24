package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.utils.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tomáš on 24.1.14.
 */
@RunWith(Arquillian.class)
public class UserServiceTest
{

    @Deployment
    public static WebArchive createDeployment()
    {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(UserService.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    private UserService userService;

    private User user;

    @Before
    public void init() throws NoSuchAlgorithmException
    {
        User user = new User();
        user.setEmail("aaa@ggg.com");
        user.setPassword("123456789");
        userService.create(user);
        this.user = user;
    }

    @After
    public void destroy(){
        userService.remove(user);
    }

    @Test
    public void createUser() throws NoSuchAlgorithmException
    {
        User user = new User();
        user.setEmail("bbb@ggg.com");
        user.setPassword("123456789");

        userService.create(user);

        Assert.assertNotNull(user.getId());
    }

    @Test
    public void findByEmail(){
        Assert.assertNotNull(userService.findByEmail("aaa@ggg.com"));
    }

    @Test(expected = EJBException.class)
    public void validation()
    {
        User user = new User();
        user.setEmail("aaaa");

        userService.create(user);

        Assert.assertNull(user.getId());
    }
}
