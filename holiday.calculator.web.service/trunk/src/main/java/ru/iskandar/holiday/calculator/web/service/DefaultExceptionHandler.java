package ru.iskandar.holiday.calculator.web.service;

import javax.ejb.EJBAccessException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ru.iskandar.holiday.calculator.service.model.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.StatementNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserByIdNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        StringBuilder response = new StringBuilder("<response>");
        response.append("<status>ERROR</status>");
        response.append("<message>" + e.getMessage() + "</message>");
        response.append("</response>");
        return Response.status(toStatus(e)).entity(response.toString())
                .type(MediaType.APPLICATION_XML).build();
    }

    private Response.Status toStatus(Exception aException) {
        Throwable cause = getFirstCause(aException);
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (cause instanceof EJBAccessException) {
            status = Response.Status.FORBIDDEN;
        } else if ((cause instanceof StatementNotFoundException)
                || (cause instanceof UserByIdNotFoundException)) {
            status = Response.Status.NOT_FOUND;
        } else if ((cause instanceof StatementAlreadyConsideredException)
                || (cause instanceof StatementAlreadySendedException)) {
            status = Response.Status.CONFLICT;
        } else if (cause instanceof UserByLoginNotFoundException) {
            status = Response.Status.UNAUTHORIZED;
        }
        return status;
    }

    private Throwable getFirstCause(Exception aException) {
        Throwable cause = aException;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

}
