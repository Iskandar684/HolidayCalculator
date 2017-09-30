package ru.iskandar.holiday.calculator.web.service;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ru.iskandar.holiday.calculator.service.model.user.User;
import ru.iskandar.holiday.calculator.service.model.user.UserFactory;

@Path("/")
public class HolidayCalculatorWebService {

	private class CurrentUserInfoFactory extends UserFactory {

		@Override
		protected UUID getUUID() {
			return UUID.randomUUID();
		}

		@Override
		protected String getFirstName() {
			return "Олег";
		}

		@Override
		protected String getLastName() {
			return "Сидоров";
		}

		@Override
		protected String getPatronymic() {
			return "Михайлович";
		}

		@Override
		protected Date getEmploymentDate() {
			return new Date();
		}

		@Override
		protected String getLogin() {
			return "user1";
		}

	}

	@GET
	@Path("/user")
	@Produces({ "application/json" })
	public User getUser() {
		return new CurrentUserInfoFactory().create();
	}

}
