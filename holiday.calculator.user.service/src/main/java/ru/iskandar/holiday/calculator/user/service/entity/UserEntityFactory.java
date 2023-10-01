package ru.iskandar.holiday.calculator.user.service.entity;

import java.util.Date;
import java.util.Objects;

import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Абстрактная фабрика сущности нового пользователя.
 */
public abstract class UserEntityFactory {

    /**
     * Создаёт сущность пользователя.
     *
     * @return сущность пользователя
     */
    public UserEntity create() {
        UserId id = getId();
        String firstName = getFirstName();
        Objects.requireNonNull(firstName);
        String lastName = getLastName();
        Objects.requireNonNull(lastName);

        String patronymic = getPatronymic();
        Objects.requireNonNull(patronymic);

        String login = getLogin();
        Objects.requireNonNull(login);

        Date employmentDate = getEmploymentDate();
        Objects.requireNonNull(employmentDate);

        UserEntity entity = new UserEntity();
        entity.setEmploymentDate(employmentDate);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setLogin(login);
        entity.setPatronymic(patronymic);
        entity.setNote(getNote());
        // У создаваемого адреса id = null
        entity.setUuid((id == null) ? null : id.getUUID());
        return entity;
    }

    /**
     * Возвращает примечание.
     *
     * @return примечание
     */
    protected abstract String getNote();

    /**
     * Возвращает идентификатор.
     *
     * @return идентификатор
     */
    protected abstract UserId getId();

    /**
     * Возвращает имя.
     *
     * @return имя
     */
    protected abstract String getFirstName();

    /**
     * Возвращает фамилию.
     *
     * @return фамилию
     */
    protected abstract String getLastName();

    /**
     * Возвращает отчество.
     *
     * @return отчество
     */
    protected abstract String getPatronymic();

    /**
     * Возвращает логин.
     *
     * @return логин
     */
    protected abstract String getLogin();

    /**
     * Возвращает дату принятия на работу.
     *
     * @return дату принятия на работу
     */
    protected abstract Date getEmploymentDate();

}
