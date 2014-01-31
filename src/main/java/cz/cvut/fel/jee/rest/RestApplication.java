package cz.cvut.fel.jee.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by Tomáš on 7.1.14.
 */
@ApplicationPath("/api")
public class RestApplication extends Application
{
    public static final String COMMENTS = "comments";
    public static final String VIDEOS = "videos";
    
    
}
