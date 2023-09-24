package ru.iskandar.holiday.calculator.user.service.api;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Пользователь
 */
@Getter
@Builder
@Accessors(prefix = "_")
@EqualsAndHashCode(of = "_uuid")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
//констуктор без аргументов для WEB-API
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public final class User implements Serializable {

	/**
	 * Уникальный идентификатор класса для сериализации
	 */
	private static final long serialVersionUID = 7332495096773696543L;

	/** Имя */
	@NonNull
	@XmlElement(name = "firstName")
	private String _firstName;

	/** Фамилия */
	@NonNull
	@XmlElement(name = "lastName")
	private String _lastName;

	/** Отчество */
	@NonNull
	@XmlElement(name = "patronymic")
	private String _patronymic;

	/** Идентификатор */
	@NonNull
	@XmlElement(name = "uuid")
	private UUID _uuid;

	/** Дата приема на работу */
	@NonNull
	@XmlElement(name = "employmentDate")
	private Date _employmentDate;

	/** Логин */
	@NonNull
	@XmlElement(name = "login")
	private String _login;

	/** Примечание */
	// FIXME убрать публичный сеттер
	@Setter
	@XmlElement(name = "note")
	private String _note;

	public String getFIO() {
		return String.format("%s %s %s", _lastName, _firstName, _patronymic);
	}

	/**
	 * @return the uuid
	 */
	public UserId getId() {
		return UserId.fromString(_uuid);
	}

}
