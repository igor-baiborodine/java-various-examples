package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;

/**
 * @author Igor Baiborodine
 */
public class AccountDao
        extends AbstractBaseDao<Account, String>
        implements AccountMapper {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    public AccountDao(String collectionName) {

        super(collectionName);
    }

    public Account getAccountByUsername(final String username) {

        checkNotNull(emptyToNull(username), "Argument[username] must not be empty or null");

        DBObject accountObj = collection.findOne(new BasicDBObject("_id", username));
        Account account = (accountObj != null) ? Account.fromDBObject(accountObj) : null;
        logger.info("Found for username[{}] following account[{}]", username, account);

        return account;
    }

    public Account getAccountByUsernameAndPassword(final String username, final String password) {

        checkNotNull(emptyToNull(username), "Argument[username] must not be empty or null");
        checkNotNull(emptyToNull(password), "Argument[password] must not be empty or null");

        Account account = getAccountByUsername(username);
        return password.equals(account.getPassword()) ? account : null;
    }

    public void insertAccount(@Nonnull final Account account) {

        checkNotNull(account, "Argument[account] must not be null");

        super.create(account);
        logger.info("Inserted new account[{}]", account);
    }

    public void updateAccount(@Nonnull final Account account) {

        checkNotNull(account, "Argument[account] must not be null");

        super.update(account);
        logger.info("Updated account[{}]", account);
    }

    public void deleteAccount(@Nonnull final String username) {

        checkNotNull(nullToEmpty(username), "Argument[username] cannot be null or empty");

        super.delete(username);
        logger.info("Deleted account with id[{}]", username);
    }

    public void insertProfile(final Account account) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void insertSignon(final Account account) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateProfile(final Account account) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateSignon(final Account account) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
