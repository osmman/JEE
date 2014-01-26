package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.utils.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tomáš on 24.1.14.
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class UserServiceTest
{

    @Deployment
    public static WebArchive createDeployment()
    {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml")
                .addPackage(UserService.class.getPackage());
    }

    @Inject
    private UserService userService;

    @Test
    @UsingDataSet("datasets/users/users.yml")
    public void createUser() throws NoSuchAlgorithmException
    {
        User user = new User();
        user.setEmail("bbb@ggg.com");
        user.setPassword("123456789");

        userService.create(user);

        Assert.assertNotNull(user.getId());
    }

    @Test(expected = EJBException.class)
    @UsingDataSet("datasets/users/users.yml")
    public void createExistedEmail() throws NoSuchAlgorithmException
    {
        User user = new User();
        user.setEmail("test@email.cz");
        user.setPassword("123456789");
        userService.create(user);
    }

    @Test
    @UsingDataSet("datasets/users/users.yml")
    public void findByEmail()
    {
        Assert.assertNotNull(userService.findByEmail("test@email.cz"));
    }

    @Test(expected = EJBException.class)
    public void validation()
    {
        User user = new User();
        user.setEmail("aaaa");

        userService.create(user);

        Assert.assertNull(user.getId());
    }

    @Test
    @UsingDataSet("datasets/users/users.yml")
    public void findAll()
    {
        Assert.assertEquals(2, userService.findAll().size());
    }
}
