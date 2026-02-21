package com.david.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @ConfigProperty(name = "quarkus.profile", defaultValue = "dev")
    String profile;

    @Override
    public Response toResponse(Exception exception) {
        Map<String, String> error = new HashMap<>();
        
        if (exception instanceof ConstraintViolationException) {
            error.put("error", "Validation failed");
            error.put("message", "Los datos proporcionados no son validos");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
        
        error.put("error", "Internal Server Error");
        error.put("message", "Ha ocurrido un error interno");
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
