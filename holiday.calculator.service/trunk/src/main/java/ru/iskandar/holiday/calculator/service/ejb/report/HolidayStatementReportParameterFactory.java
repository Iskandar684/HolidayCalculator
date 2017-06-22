package ru.iskandar.holiday.calculator.service.ejb.report;

import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 *
 */
public abstract class HolidayStatementReportParameterFactory {
	
	public HolidayStatementReportParameter create (){
		User author = getAuthor() ;
		if (author==null){
			throw new IllegalStateException("Не указан автор");
		}
		HolidayStatementReportParameter  param = new HolidayStatementReportParameter  (author) ;
		return param ;
	}
	
	protected abstract User getAuthor();

}
