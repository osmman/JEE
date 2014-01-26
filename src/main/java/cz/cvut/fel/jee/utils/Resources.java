package cz.cvut.fel.jee.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Logger;
import javax.annotation.Resource;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import org.infinispan.Cache;
import org.infinispan.io.GridFile;
import org.infinispan.io.GridFilesystem;

/**
 * Created by Tomáš on 10.1.14.
 */
public class Resources {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;
    
    @Produces
    public EntityManager getEm() {
        return em;
    }

    @Produces
    public Logger getLogger(InjectionPoint ip) {
        String category = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(category);
    }

    @Produces
    @RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
