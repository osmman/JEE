package cz.cvut.fel.jee.utils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import org.infinispan.Cache;
import org.infinispan.io.GridFile;
import org.infinispan.io.GridFilesystem;
import org.infinispan.manager.CacheContainer;

/**
 * Created by Tomáš on 10.1.14.
 */
public class Resources {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

//    @Resource(lookup = "java:jboss/infinispan/container/video")
//    private CacheContainer container;
    
    @Resource(lookup="java:jboss/infinispan/cache/video/data")
    Cache<String, byte[]> data;
    @Resource(lookup="java:jboss/infinispan/cache/video/metadata")
    Cache<String, GridFile.Metadata> metadata;
    
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

    @Produces
    public GridFilesystem getVideoCache() {
        GridFilesystem fs = new GridFilesystem(data, metadata);
        return fs;
    }
}
