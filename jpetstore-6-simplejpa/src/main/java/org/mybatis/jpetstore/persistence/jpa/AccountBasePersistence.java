package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;

/**
 * @author Igor Baiborodine
 */
@Repository
public class AccountBasePersistence
        extends AbstractBasePersistence<Account, String>
        implements AccountMapper {

    private static final Logger logger = LoggerFactory.getLogger(AccountBasePersistence.class);

    public void deleteAccount(@Nonnull final String username) {

        checkNotNull(nullToEmpty(username), "Argument[username] cannot be null or empty");

        super.delete(username);
        logger.info("Deleted account with id[{}]", username);
    }

    public Account getAccountByUsername(@Nonnull final String username) {

        checkNotNull(emptyToNull(username), "Argument[username] must not be empty or null");

        Account result = null;
        // Named queries not supported by SimpleJPA
        // Query query = createNamedQuery("Account.getAccountByUsername");

        Query query = getEntityManager().createQuery("select a from Account a where a.username = :username");
        query.setParameter("username", username);

        @SuppressWarnings("unchecked")
        List<Account> accounts = (List<Account>) query.getResultList();
        closeEntityManager();

        if (accounts.size() == 1) {
            result = accounts.get(0);
        }
        logger.info("Read account[{}]", result);

        return result;
    }

    public Account getAccountByUsernameAndPassword(@Nonnull final String username,
            @Nonnull final String password) {

        checkNotNull(emptyToNull(username), "Argument[username] must not be empty or null");
        checkNotNull(emptyToNull(password), "Argument[password] must not be empty or null");

        Account result = null;
        // Named queries not supported by SimpleJPA
        // Query query = createNamedQuery("Account.getAccountByUsernameAndPassword");

        Query query = getEntityManager().createQuery(
                "select a from Account a where a.username = :username and a.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        @SuppressWarnings("unchecked")
        List<Account> accounts = (List<Account>) query.getResultList();
        closeEntityManager();

        if (accounts.size() == 1) {
            result = accounts.get(0);
        }
        logger.info("Read account[{}]", result);

        return result;
    }

    public Account getAccount(@Nonnull String username) {

        checkNotNull(emptyToNull(username), "Argument[username] must not be empty or null");

        return read(username);
    }

    @Override
    public void insertAccount(@Nonnull final Account account) {

        checkNotNull(account, "Argument[account] must not be null");

        create(account);
    }

    @Override
    public void updateAccount(@Nonnull final Account account) {

        checkNotNull(account, "Argument[account] must not be null");

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
