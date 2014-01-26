package cz.cvut.fel.jee.utils;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import org.infinispan.Cache;
import org.infinispan.io.GridFile;
import org.infinispan.io.GridFilesystem;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;

/**
 * Created by Tomáš on 26.1.14.
 */
public class VideoResources
{
    @Resource(lookup="java:jboss/infinispan/cache/video/data")
    Cache<String, byte[]> data;

    @Resource(lookup="java:jboss/infinispan/cache/video/metadata")
    Cache<String, GridFile.Metadata> metadata;

    @Produces
    @VideoFilesystem
    public GridFilesystem getVideoCache() {
        GridFilesystem fs = new GridFilesystem(data, metadata);
        return fs;
    }
}
