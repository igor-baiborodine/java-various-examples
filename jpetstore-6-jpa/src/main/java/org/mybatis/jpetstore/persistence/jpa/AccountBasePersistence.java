package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Igor Baiborodine
 */
@Repository
public class AccountBasePersistence
        extends AbstractBasePersistence<Account, String>
        implements AccountMapper {
    private static final Logger logger = LoggerFactory.getLogger(AccountBasePersistence.class);

    public Account getAccountByUsername(@Nonnull final String username) {
        Account result = null;
        Query query = createNamedQuery("Account.getAccountByUsername");
        query.setParameter("username", username);

        @SuppressWarnings("unchecked")
        List<Account> accounts = (List<Account>) query.getResultList();
        checkState(accounts.size() <= 1, String.format("Query[Account.getAccountByUsername]" +
                " with parameter[%s] returned [%d] results", username, accounts.size()));
        if (accounts.size() == 1) {
            result = accounts.get(0);
        }
        logger.info("Read account[{}]", result);
        return result;
    }

    public Account getAccountByUsernameAndPassword(@Nonnull final String username,
            @Nonnull final String password) {
        Account result = null;
        Query query = createNamedQuery("Account.getAccountByUsernameAndPassword");
        query.setParameter("username", username);
        query.setParameter("password", password);

        @SuppressWarnings("unchecked")
        List<Account> accounts = (List<Account>) query.getResultList();
        checkState(accounts.size() <= 1, String.format("Query[Account" +
                ".getAccountByUsernameAndPassword] with parameters[%s,%s] returned [%d] results",
                username, password, accounts.size()));
        if (accounts.size() == 1) {
            result = accounts.get(0);
        }
        logger.info("Read account[{}]", result);
        return result;
    }

    public Account getAccount(@Nonnull String userId) {
        return read(userId);
    }

    @Override
    public void insertAccount(@Nonnull final Account account) {
        create(account);
    }

    @Override
    public void updateAccount(@Nonnull final Account account) {
        update(account);
    }

    @Override
    public void insertProfile(@Nonnull final Account account) {
        //no need to implement since profile info is stored in secondary table 'profile'
    }

    @Override
    public void insertSignon(@Nonnull final Account account) {
        //no need to implement since signon info is stored in secondary table 'signon'
    }

    @Override
    public void updateProfile(@Nonnull final Account account) {
        //no need to implement since profile info is stored in secondary table 'profile'
    }

    public void updateSignon(@Nonnull final Account account) {
        //no need to implement since signon info is stored in secondary table 'signon'
    }
}
