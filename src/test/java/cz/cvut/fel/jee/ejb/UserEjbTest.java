package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.utils.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

/**
 * Created by Tomáš on 24.1.14.
 */
@RunWith(Arquillian.class)
public class UserEjbTest
{

    @Deployment
    public static WebArchive createDeployment()
    {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addPackage(UserEjb.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    private UserEjb userEjb;

    @Test
    public void createUser()
    {
        User user = new User();
        user.setEmail("aaa@ggg.com");
        user.setPassword("123456789");

        userEjb.create(user);

        Assert.assertNotNull(user.getId());
    }

    @Test(expected = EJBException.class)
    public void validation()
    {
        User user = new User();
        user.setEmail("aaaa");

        userEjb.create(user);

        Assert.assertNull(user.getId());
    }
}
