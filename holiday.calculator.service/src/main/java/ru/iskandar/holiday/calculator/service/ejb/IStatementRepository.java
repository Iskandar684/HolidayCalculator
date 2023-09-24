package ru.iskandar.holiday.calculator.service.ejb;

import java.util.Collection;
import java.util.EnumSet;

import ru.iskandar.holiday.calculator.service.model.StatementNotFoundException;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatement;
import ru.iskandar.holiday.calculator.service.model.statement.HolidayStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatement;
import ru.iskandar.holiday.calculator.service.model.statement.LeaveStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatement;
import ru.iskandar.holiday.calculator.service.model.statement.RecallStatementEntry;
import ru.iskandar.holiday.calculator.service.model.statement.Statement;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.service.model.statement.StatementStatus;
import ru.iskandar.holiday.calculator.service.model.statement.StatementType;
import ru.iskandar.holiday.calculator.user.service.api.User;

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
        // FIXME в аргументе перейти на UserId
	public Collection<Statement<?>> getStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
        // FIXME в аргументе перейти на UserId
	public Collection<HolidayStatement> getHolidayStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
        // FIXME в аргументе перейти на UserId
	public Collection<RecallStatement> getRecallStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления, автором которых является указанный пользователь
	 *
	 * @param aAuthor
	 *            пользователь
	 * @return заявления
	 */
        // FIXME в аргументе перейти на UserId
	public Collection<LeaveStatement> getLeaveStatementsByAuthor(User aAuthor);

	/**
	 * Возвращает заявления с указанными статусами
	 *
	 * @param aStatuses
	 *            статусы; не может быть {@code null} или пустым
	 * @return заявления с указанными статусами
	 */
	public Collection<Statement<?>> getStatementsByStatus(EnumSet<StatementStatus> aStatuses);

	/**
	 * Возвращает заявления на отгул с указанными статусами
	 *
	 * @param aStatuses
	 *            статусы; не может быть {@code null} или пустым
	 * @return заявления с указанными статусами
	 */
	public Collection<HolidayStatement> getHolidayStatementsByStatus(EnumSet<StatementStatus> aStatuses);

	/**
	 * Возвращает заявления на отпуск с указанными статусами
	 *
	 * @param aStatuses
	 *            статусы; не может быть {@code null} или пустым
	 * @return заявления с указанными статусами
	 */
	public Collection<LeaveStatement> getLeaveStatementsByStatus(EnumSet<StatementStatus> aStatuses);

	/**
	 * Возвращает заявления на отзыв с указанными статусами
	 *
	 * @param aStatuses
	 *            статусы; не может быть {@code null} или пустым
	 * @return заявления с указанными статусами
	 */
	public Collection<RecallStatement> getRecallStatementsByStatus(EnumSet<StatementStatus> aStatuses);

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
	public Statement<?> getStatement(StatementId aID, StatementType aType);

	/**
	 * Обновляет указанное заявление. Заявление должно уже создано заранее.
	 *
	 *
	 * @param aStatement
	 *            заявление
	 * @throws StatementNotFoundException
	 *             если указанное заявление не найдено
	 * @see #create(Statement)
	 */
	public void save(Statement<?> aStatement) throws StatementNotFoundException;

	/**
	 * Создает заявление на отгул
	 *
	 * @param aStatementEntry
	 *            содержание создаваемого заявления
	 * @throws NullPointerException
	 *             если aStatementEntry {@code null}
	 */
	public HolidayStatement createHolidayStatement(HolidayStatementEntry aStatementEntry);

	/**
	 * Создает заявление на отпуск
	 *
	 * @param aStatementEntry
	 *            содержание создаваемого заявления
	 * @throws NullPointerException
	 *             если aStatementEntry {@code null}
	 */
	public LeaveStatement createLeaveStatement(LeaveStatementEntry aStatementEntry);

	/**
	 * Создает заявление на отзыв
	 *
	 * @param aStatementEntry
	 *            содержание создаваемого заявления
	 * @throws NullPointerException
	 *             если aStatementEntry {@code null}
	 */
	public RecallStatement createRecallStatement(RecallStatementEntry aStatementEntry);

}
