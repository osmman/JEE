/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.rest.entity.Login;
import cz.cvut.fel.jee.security.Authenticator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author saljack
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Stateless
@Path("/")
public class LoginResource {
    
    @Inject
    private Authenticator auth;
    
    
    @Path("login")
    @POST
    public Response login(Login login, @Context HttpServletRequest request){
        if(login.getEmail() != null && login.getPassword() != null){
            auth.setEmail(login.getEmail());
            auth.setPassword(login.getPassword());
            if(auth.login(request)){
                return Response.ok("logged").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Bad login or password").build();
    }
    
    @Path("logout")
    @GET
    public Response logout(@Context HttpServletRequest request){
        auth.logout(request);
        return Response.ok("logouted").build();
    }
    
}
