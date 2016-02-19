package de.gedoplan.jaxgui.system.security;

import javax.ejb.EJBAccessException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessExceptionHandler implements ExceptionMapper<Exception>{

    @Override
    public Response toResponse(Exception exception) {
        return Response.status(455).type("text/html").build();
    }
}
