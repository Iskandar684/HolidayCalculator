package ru.iskandar.holiday.calculator.web.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class HolidayCalculatorWebService {

	@GET
	@Path("/user")
	@Produces({ "application/json" })
	public String getUser() {
		// TODO
		return "{\"result\":\"" + "Алексей" + "\"}";
	}

}
