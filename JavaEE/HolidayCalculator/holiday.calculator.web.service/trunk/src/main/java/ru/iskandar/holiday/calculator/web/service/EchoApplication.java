package ru.iskandar.holiday.calculator.web.service;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class EchoApplication extends Application {

    public EchoApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[] {"http"});
        beanConfig.setTitle("My echo API");
        beanConfig.setHost("localhost:8080");
        // beanConfig.setBasePath("/swageasy");
        beanConfig.setBasePath("/holiday-calculator-web-service");
        beanConfig.setResourcePackage("ru.iskandar.holiday.calculator.web.service");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(HolidayCalculatorWebService.class);
        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return set;
    }
}
