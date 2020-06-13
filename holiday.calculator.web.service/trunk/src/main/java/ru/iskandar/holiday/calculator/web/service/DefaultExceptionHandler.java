package ru.iskandar.holiday.calculator.web.service;

import javax.ejb.EJBAccessException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.jbosslog.JBossLog;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadyConsideredException;
import ru.iskandar.holiday.calculator.service.model.StatementAlreadySendedException;
import ru.iskandar.holiday.calculator.service.model.StatementNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserByIdNotFoundException;
import ru.iskandar.holiday.calculator.service.model.user.UserByLoginNotFoundException;

@Provider
@JBossLog
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        log.error(e.getMessage(), e);
        Throwable cause = getFirstCause(e);
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
        ErrorResponse errResp = ErrorResponse.builder().message("Ошибка")
                .code(status.getStatusCode()).description(cause.getMessage()).build();
        return Response.status(status).entity(errResp).type(MediaType.APPLICATION_JSON).build();
    }

    private Throwable getFirstCause(Exception aException) {
        Throwable cause = aException;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

}
