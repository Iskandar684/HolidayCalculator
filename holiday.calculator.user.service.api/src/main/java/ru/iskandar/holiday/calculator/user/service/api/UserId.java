package ru.iskandar.holiday.calculator.user.service.api;

import java.io.Serializable;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Идентификатор пользователя
 */
@Accessors(prefix = "_")
@RequiredArgsConstructor
@EqualsAndHashCode(of = "_UUID")
public final class UserId implements Serializable {

    /**
     * Идентификатор для сериализации
     */
    private static final long serialVersionUID = -1773334763165903581L;

    /** Идентификатор пользователя */
    @NonNull
    @Getter
    private final UUID _UUID;

    /**
     * Создает идентификатор пользователя по UUID
     *
     * @param aUserUUID UUID пользователя
     * @return идентификатор пользователя
     * @throws NullPointerException если {@code aUserUUID} {@code null}
     */
    // для WEB-API метод должен называться valueOf или fromString
    public static UserId fromString(@NonNull UUID aUserUUID) {
        return new UserId(aUserUUID);
    }

}
