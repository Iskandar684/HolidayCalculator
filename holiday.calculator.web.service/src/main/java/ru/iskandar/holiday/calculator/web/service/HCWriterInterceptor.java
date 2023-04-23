package ru.iskandar.holiday.calculator.web.service;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.jboss.resteasy.core.ResourceInvoker;

import lombok.extern.jbosslog.JBossLog;
import ru.iskandar.holiday.calculator.service.ejb.HCWebMethod;

@Provider
@JBossLog
public class HCWriterInterceptor implements WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext ctx)
            throws IOException, WebApplicationException {
        log.info("HCWriterInterceptor.aroundWriteTo");
        Object entity = ctx.getEntity();
        if (entity instanceof ErrorResponse) {
            ResourceInvoker invoker = (ResourceInvoker) ctx
                    .getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
            if (invoker != null) {
                Method m = invoker.getMethod();
                HCWebMethod webMethodAnnotation = m.getAnnotation(HCWebMethod.class);
                if (webMethodAnnotation != null) {
                    ((ErrorResponse) entity).setMessage(webMethodAnnotation.errMess());
                    ctx.setEntity(entity);
                }
            }
        }
        ctx.proceed();
        return;
    }
}
