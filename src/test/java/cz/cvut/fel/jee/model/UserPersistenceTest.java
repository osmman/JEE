package cz.cvut.fel.jee.model;

import cz.cvut.fel.jee.utils.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by Tomáš on 10.1.14.
 */
@RunWith(Arquillian.class)
public class UserPersistenceTest
{

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Resources.class)
                .addPackage(User.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Test
    public void findAll() {
        List<User> users = em.createQuery("select u from User u order by u.id", User.class).getResultList();
        Assert.assertEquals(0,users.size());
    }
}
