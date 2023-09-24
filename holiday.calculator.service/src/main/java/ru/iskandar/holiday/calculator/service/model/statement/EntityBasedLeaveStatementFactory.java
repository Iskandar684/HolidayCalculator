package ru.iskandar.holiday.calculator.service.model.statement;

import java.util.Date;
import java.util.Set;
import java.util.function.Function;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.iskandar.holiday.calculator.service.entities.LeaveStatementEntity;
import ru.iskandar.holiday.calculator.user.service.api.User;
import ru.iskandar.holiday.calculator.user.service.api.UserId;

/**
 *
 */
@RequiredArgsConstructor
public class EntityBasedLeaveStatementFactory extends LeaveStatementFactory {

    @NonNull
    private final LeaveStatementEntity _entity;

    @NonNull
    private final Function<UserId, User> _userProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<Date> getDays() {
        return _entity.getDays();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StatementId getId() {
        return StatementId.from(_entity.getUuid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected StatementStatus getStatus() {
        return _entity.getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected User getAuthor() {
        return _userProvider.apply(_entity.getAuthorId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Date getCreateDate() {
        return _entity.getCreateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected User getConsider() {
        UserId considerId = _entity.getConsiderId();
        if (considerId == null) {
            return null;
        }
        return _userProvider.apply(considerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Date getConsiderDate() {
        return _entity.getConsiderDate();
    }

}
