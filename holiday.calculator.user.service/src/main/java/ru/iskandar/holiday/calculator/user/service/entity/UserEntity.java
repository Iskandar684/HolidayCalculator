package ru.iskandar.holiday.calculator.user.service.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import ru.iskandar.holiday.calculator.user.service.api.UserConstants;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 * Сущность пользователя
 */
@Entity
@Table(name = "ru_iskandar_holiday_calculator_user")
public class UserEntity implements Serializable {

	/**
	 * Идентфикатор для сериализации
	 */
	private static final long serialVersionUID = -7202845772535656629L;

	/** Глобальный идентификатор */
	private UUID _uuid;

	/** Имя */
	private String _firstName;

	/** Фамилия */
	private String _lastName;

	/** Отчество */
	private String _patronymic;

	/** Логин пользователя */
	private String _login;

	/** Дата приема на работу */
	private Date _employmentDate;

	/** Примечание */
	private String _note;

	/**
	 * @return the uuid
	 */
	@Id
	@GeneratedValue
	@Column(name = "uuid")
	public UUID getUuid() {
		return _uuid;
	}

	/**
	 * @param aUuid
	 *            the uuid to set
	 */
	public void setUuid(UUID aUuid) {
		_uuid = aUuid;
	}

	/**
	 * @return the firstName
	 */
	@Column(name = "firstname")
	@NotNull(message = "Не указано имя")
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * @param aFirstName
	 *            the firstName to set
	 */
	public void setFirstName(String aFirstName) {
		_firstName = aFirstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(name = "lastname")
	@NotNull(message = "Не указана фамилия")
	public String getLastName() {
		return _lastName;
	}

	/**
	 * @param aLastName
	 *            the lastName to set
	 */
	public void setLastName(String aLastName) {
		_lastName = aLastName;
	}

	/**
	 * @return the patronymic
	 */
	@Column(name = "patronymic")
	@NotNull(message = "Не указано отчество")
	public String getPatronymic() {
		return _patronymic;
	}

	/**
	 * @param aPatronymic
	 *            the patronymic to set
	 */
	public void setPatronymic(String aPatronymic) {
		_patronymic = aPatronymic;
	}

	/**
	 * @return the login
	 */
	@Column(name = "login", unique = true)
	@NotNull(message = "Не указан логин")
	public String getLogin() {
		return _login;
	}

	/**
	 * @param aLogin
	 *            the login to set
	 */
	public void setLogin(String aLogin) {
		_login = aLogin;
	}

	/**
	 * @return the employmentDate
	 */
	@Column(name = "employmentdate")
	@NotNull(message = "Не указан логин")
	public Date getEmploymentDate() {
		return _employmentDate;
	}

	/**
	 * @param aEmploymentDate
	 *            the employmentDate to set
	 */
	public void setEmploymentDate(Date aEmploymentDate) {
		_employmentDate = aEmploymentDate;
	}

	@Column(name = "note", length = UserConstants.NOTE_LENGHT)
	public String getNote() {
		return _note;
	}

	public void setNote(String aNote) {
		_note = aNote;
	}

        @Transient
        public UserId getId() {
            return _uuid == null ? null : UserId.fromString(_uuid);
        }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((_uuid == null) ? 0 : _uuid.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserEntity other = (UserEntity) obj;
		if (_uuid == null) {
			if (other._uuid != null) {
				return false;
			}
		} else if (!_uuid.equals(other._uuid)) {
			return false;
		}
		return true;
	}

}
