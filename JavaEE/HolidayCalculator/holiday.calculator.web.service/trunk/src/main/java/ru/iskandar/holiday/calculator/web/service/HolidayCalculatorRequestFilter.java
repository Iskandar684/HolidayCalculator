package ru.iskandar.holiday.calculator.web.service;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class HolidayCalculatorRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext aContext) throws IOException {
		System.out.println("filter context " + aContext);
		System.out.println("filter context UserPrincipal=" + aContext.getSecurityContext().getUserPrincipal());
	}

}
