package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit based unit tests for the {@link AccountDao} class.
 *
 * @author Igor Baiborodine
 */
public class AccountDaoTest extends AbstractBaseDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Override
    public String getCollectionName() {

        return accountDao.getCollectionName();
    }

    @Test(expected = NullPointerException.class)
    public void insertAccount_shouldThrowNPEForNullAccountArgument() {

        accountDao.insertAccount(null);
    }

    @Test
    public void insertAccount_shouldInsertNewAccountIntoCollection() {

        Account newAccount = createAccountBuilderWithAllFields().build();
        accountDao.insertAccount(newAccount);

        DBObject accountObj = collection.findOne(new BasicDBObject("_id", newAccount.getUsername()));
        assertThat("Cannot find account with username [" + newAccount.getUsername() + "]", accountObj, notNullValue());

        Account persistedAccount = Account.fromDBObject(accountObj);
        assertReflectionEquals(newAccount, persistedAccount);
    }

    @Test(expected = MongoException.DuplicateKey.class)
    public void insertAccount_shouldThrowDuplicateKeyExceptionForAccountWithDuplicateUsername() {

        Account existingAccount = createAccountBuilderWithAllFields().build();
        insertAccount(existingAccount);

        Account accountWithDuplicateUsername = createAccountBuilderWithAllFields().build();
        accountDao.insertAccount(accountWithDuplicateUsername);
    }

    @Test(expected = NullPointerException.class)
    public void getAccountByUsername_shouldThrowNPEForNullUsernameArgument() {

        accountDao.getAccountByUsername(null);
    }

    @Test(expected = NullPointerException.class)
    public void getAccountByUsername_shouldThrowNPEForEmptyUsernameArgument() {

        accountDao.getAccountByUsername("");
    }

    @Test
    public void getAccountByUsername_shouldFindExistingAccount() {

        Account existingAccount = createAccountBuilderWithAllFields().build();
        insertAccount(existingAccount);

        Account account = accountDao.getAccountByUsername(existingAccount.getUsername());
        assertReflectionEquals(existingAccount, account);
    }

    @Test
    public void getAccountByUsername_shouldReturnNullForNonExistingAccount() {

        Account account = accountDao.getAccountByUsername("non_existing_username");
        assertThat(account, nullValue());
    }

    @Test(expected = NullPointerException.class)
    public void getAccountByUsernameAndPassword_shouldThrowNPEForNullUsernameArgument() {

        accountDao.getAccountByUsernameAndPassword(null, "password");
    }

    @Test(expected = NullPointerException.class)
    public void getAccountByUsernameAndPassword_shouldThrowNPEForEmptyUsernameArgument() {

        accountDao.getAccountByUsernameAndPassword("", "password");
    }

    @Test(expected = NullPointerException.class)
    public void getAccountByUsernameAndPassword_shouldThrowNPEForNullPasswordArgument() {

        accountDao.getAccountByUsernameAndPassword("username", null);
    }

    @Test(expected = NullPointerException.class)
    public void getAccountByUsernameAndPassword_shouldThrowNPEForEmptyPasswordArgument() {

        accountDao.getAccountByUsernameAndPassword("username", "");
    }

    @Test
    public void getAccountByUsernameAndPassword_shouldFindExistingAccount() {

        Account existingAccount = createAccountBuilderWithAllFields().build();
        insertAccount(existingAccount);

        Account account = accountDao.getAccountByUsernameAndPassword(
                existingAccount.getUsername(), existingAccount.getPassword());
        assertReflectionEquals(existingAccount, account);
    }

    @Test
    public void getAccountByUsernameAndPassword_shouldReturnNullForExistingAccountAndInvalidPassword() {

        Account existingAccount = createAccountBuilderWithAllFields().build();
        insertAccount(existingAccount);

        Account account = accountDao.getAccountByUsernameAndPassword(existingAccount.getUsername(), "invalid_password");
        assertThat(account, nullValue());
    }

    @Test
    public void updateAccount_shouldUpdateExistingAccount() {

        Account existingAccount = createAccountBuilderWithBaseFields(USERNAME, null, null, null).build();
        insertAccount(existingAccount);

        Account accountToUpdate = createAccountBuilderWithBaseFields(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME).build();
        accountDao.updateAccount(accountToUpdate);

        DBObject accountObj = collection.findOne(new BasicDBObject("_id", accountToUpdate.getUsername()));
        assertReflectionEquals(accountToUpdate, Account.fromDBObject(accountObj));
    }

    @Test(expected = RuntimeException.class)
    public void updateAccount_shouldThrowRuntimeExceptionForNonExistingAccount() {

        Account nonExistingAccount = createAccountBuilderWithBaseFields(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME).build();
        accountDao.updateAccount(nonExistingAccount);
    }

    @Test
    public void deleteAccount_shouldDeleteExistingAccount() {

        Account existingAccount = createAccountBuilderWithBaseFields().build();
        insertAccount(existingAccount);

        accountDao.delete(existingAccount.getUsername());
        DBObject accountObj = collection.findOne(new BasicDBObject("_id", existingAccount.getUsername()));
        assertThat(accountObj, nullValue());
    }

    @Test(expected = RuntimeException.class)
    public void deleteAccount_shouldThrowRuntimeExceptionForNonExistingAccount() {

        DBObject accountObj = collection.findOne(new BasicDBObject("_id", USERNAME));
        assertThat(accountObj, nullValue());

        accountDao.delete(USERNAME);
    }

    private void insertAccount(final Account account) {

        collection.insert(account.toDBObject());

        DBObject accountObj = collection.findOne(new BasicDBObject("_id", account.getUsername()));
        assertThat("Cannot find account with username [" + account.getUsername() + "]", accountObj, nullValue());
    }

}
