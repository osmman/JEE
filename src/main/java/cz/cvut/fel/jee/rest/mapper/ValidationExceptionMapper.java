package cz.cvut.fel.jee.rest.mapper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements
        ExceptionMapper<ConstraintViolationException>
{

    public Response toResponse(ConstraintViolationException exception)
    {

        return Response.status(Status.BAD_REQUEST)
                .entity("Validation error").build();
    }
}
