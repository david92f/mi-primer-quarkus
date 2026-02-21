package com.david.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Validation failed");
        error.put("message", "Los datos proporcionados no son validos");
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
