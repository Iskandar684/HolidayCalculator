package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.EnumSet;

import ru.iskandar.holiday.calculator.service.model.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.Statement;
import ru.iskandar.holiday.calculator.service.model.StatementId;
import ru.iskandar.holiday.calculator.service.model.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.StatementType;
import ru.iskandar.holiday.calculator.service.model.user.User;

/**
 * Репозиторий заявлений
 */
public interface IStatementRepository {

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
	public Collection<Statement> getStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
	public Collection<HolidayStatement> getHolidayStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
	public Collection<RecallStatement> getRecallStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
	public Collection<LeaveStatement> getLeaveStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления с указанными статусами
	 *
	 * @param aStatuses
	 *            статусы; не может быть {@code null} или пустым
	 * @return заявления с указанными статусами
	 */
	public Collection<Statement> getStatementsByStatus(EnumSet<StatementStatus> aStatuses);

	/**
	 * Возвращает заявления на отгул по идентификатору заявления
	 *
	 * @param aId
	 *            UUID заявления
	 * @return заявление или {@code null}, если заявление с указанным UUID не
	 *         найдено
	 */
	public HolidayStatement getHolidayStatement(StatementId aId);

	/**
	 * Возвращает заявления на отпуск по идентификатору заявления
	 *
	 * @param aId
	 *            UUID заявления
	 * @return заявление или {@code null}, если заявление с указанным UUID не
	 *         найдено
	 */
	public LeaveStatement getLeaveStatement(StatementId aId);

	/**
	 * Возвращает заявления на отзыв по идентификатору заявления
	 *
	 * @param aId
	 *            UUID заявления
	 * @return заявление или {@code null}, если заявление с указанным UUID не
	 *         найдено
	 */
	public RecallStatement getRecallStatement(StatementId aId);

	/**
	 * Возвращает заявление по UUID
	 *
	 * @param aID
	 *            UUID
	 * @param aType
	 *            тип заявления
	 * @return заявление или {@code null}, если заявление не найдено
	 */
	public Statement getStatement(StatementId aID, StatementType aType);

	/**
	 * Сохраняет указанное заявление
	 *
	 * @param aStatement
	 *            заявление
	 */
	public void save(Statement aStatement);

}
