package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mybatis.jpetstore.domain.Domain.ACCOUNT;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;

/**
 * @author Igor Baiborodine
 */
public class AccountBasePersistenceTest
        extends AbstractBasePersistenceTest {

    @Autowired
    private AccountBasePersistence persistence;

    private Account existingAccount;

    @Before
    public void setUp() {

        cleanDomain(ACCOUNT);

        existingAccount = createAccountBuilderWithAllFields().build();
    }

    @After
    public void tearDown() {

        existingAccount = null;
    }

    @Test
    public void insertAccount_shouldInsertNewAccount() {

        Account newAccount = createAccountBuilderWithAllFields().build();
        persistence.insertAccount(newAccount);

        Account persistedAccount = persistence.read(newAccount.getUsername());
        assertHaveSamePersistentFields(newAccount, persistedAccount);
    }

    @Test
    public void getAccountByUsername_shouldFindExistingAccount() {

        insertExistingAccount();

        Account account = persistence.getAccountByUsername(existingAccount.getUsername());
        assertHaveSamePersistentFields(existingAccount, account);
    }

    @Test
    public void getAccountByUsername_shouldReturnNullForNonExistingAccount() {

        Account account = persistence.getAccountByUsername("non_existing_username");
        assertThat(account, nullValue());
    }

    @Test
    public void getAccountByUsernameAndPassword_shouldFindExistingAccount() {

        insertExistingAccount();

        Account account = persistence.getAccountByUsernameAndPassword(
                existingAccount.getUsername(), existingAccount.getPassword());
        assertHaveSamePersistentFields(existingAccount, account);
    }

    @Test
    public void getAccountByUsernameAndPassword_shouldReturnNullForExistingAccountAndInvalidPassword() {

        insertExistingAccount();

        Account account = persistence.getAccountByUsernameAndPassword(existingAccount.getUsername(), "invalid_password");
        assertThat(account, nullValue());
    }

    @Test
    public void updateAccount_shouldUpdateExistingAccount() {

        existingAccount = createAccountBuilderWithBaseFields(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME).build();
        insertExistingAccount();

        Account accountToUpdate = createAccountBuilderWithAllFields(existingAccount).build();
        assertThat(accountToUpdate.getUsername(), is(existingAccount.getUsername()));
        persistence.updateAccount(accountToUpdate);

        Account updatedAccount = persistence.read(accountToUpdate.getUsername());
        assertHaveSamePersistentFields(updatedAccount, accountToUpdate, "CGLIB$BOUND", "CGLIB$CALLBACK_0");
    }

    @Test
    public void deleteAccount_shouldDeleteExistingAccount() throws Exception {

        insertExistingAccount();

        persistence.delete(existingAccount.getUsername());
        Thread.sleep(1000);

        assertCount(ACCOUNT, 0);
    }

    private void insertExistingAccount() {

        persistence.create(existingAccount);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertCount(ACCOUNT, 1);
    }

}
