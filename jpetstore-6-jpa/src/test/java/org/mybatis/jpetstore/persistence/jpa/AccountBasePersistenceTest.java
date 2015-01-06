package org.mybatis.jpetstore.persistence.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.persistence.PersistenceException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createAccountBuilderWithAllFieldsWithBannerData;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createAccountBuilderWithAllFieldsWithoutBannerData;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * @author Igor Baiborodine
 */
public class AccountBasePersistenceTest
        extends AbstractBasePersistenceTest {

    public static final String FIRST_NAME_UPDATE = "Philip";
    public static final String LAST_NAME_UPDATE = "Fry";
    @Autowired
    private AccountBasePersistence persistence;

    @Before
    public void setUp() throws Exception {
        persistence.clearContext();
        cleanDatabase(persistence.getEntityManager());
    }

    @Test
    public void insertsANewAccountIntoTheDatabase() {
        Account newAccount = createAccountBuilderWithAllFieldsWithBannerData().build();
        persistence.insertAccount(newAccount);
        assertThat(newAccount.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(newAccount);
        assertFalse(persistence.contextContains(newAccount));

        Account persistedAccount = persistence.getEntityManager().find(Account.class,
                newAccount.getUserId());
        assertReflectionEquals(newAccount, persistedAccount);
    }

    @Test(expected = PersistenceException.class)
    @Rollback(true)
    public void throwsPersistenceExceptionWhenInsertingANewAccountWithDuplicateUsername() {
        Account existingAccount = createAccountBuilderWithAllFieldsWithoutBannerData().build();
        persistence.insertAccount(existingAccount);
        persistence.flushContext();
        persistence.detachEntity(existingAccount);

        Account accountWithDuplicateUsername =
                createAccountBuilderWithAllFieldsWithoutBannerData().build();
        persistence.insertAccount(accountWithDuplicateUsername);
        persistence.flushContext();
    }

    @Test
    public void getsAnExistingAccount() {
        Account persistedAccount = createAccountBuilderWithAllFieldsWithBannerData().build();
        persistence.getEntityManager().persist(persistedAccount);
        assertThat(persistedAccount.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(persistedAccount);
        assertFalse(persistence.contextContains(persistedAccount));

        Account existingAccount = persistence.getAccount(persistedAccount.getUserId());
        assertReflectionEquals(persistedAccount, existingAccount);
    }

    @Test
    public void updatesAnExistingAccount() {
        Account existingAccount = createAccountBuilderWithAllFieldsWithBannerData().build();
        persistence.getEntityManager().persist(existingAccount);
        assertThat(existingAccount.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(existingAccount);
        assertFalse(persistence.contextContains(existingAccount));

        existingAccount.setFirstName(FIRST_NAME_UPDATE);
        existingAccount.setLastName(LAST_NAME_UPDATE);
        persistence.updateAccount(existingAccount);
        persistence.flushContext();
        persistence.detachEntity(existingAccount);

        Account updatedAccount = persistence.getAccount(existingAccount.getUserId());
        assertThat(updatedAccount.getFirstName(), equalTo(FIRST_NAME_UPDATE));
        assertThat(updatedAccount.getLastName(), equalTo(LAST_NAME_UPDATE));
        assertThat(updatedAccount.getVersion(), equalTo(existingAccount.getVersion() + 1L));
    }

    @Test
    public void getsAnExistingAccountByUsername() {
        Account persistedAccount = createAccountBuilderWithAllFieldsWithBannerData().build();
        String username = persistedAccount.getUsername();
        persistence.getEntityManager().persist(persistedAccount);
        assertThat(persistedAccount.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(persistedAccount);
        assertFalse(persistence.contextContains(persistedAccount));

        Account existingAccount = persistence.getAccountByUsername(username);
        assertReflectionEquals(persistedAccount, existingAccount);
    }

    @Test
    public void getsAnExistingAccountByUsernameAndPassword() {
        Account persistedAccount = createAccountBuilderWithAllFieldsWithBannerData().build();
        String username = persistedAccount.getUsername();
        String password = persistedAccount.getPassword();
        persistence.getEntityManager().persist(persistedAccount);
        assertThat(persistedAccount.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(persistedAccount);
        assertFalse(persistence.contextContains(persistedAccount));

        Account existingAccount = persistence.getAccountByUsernameAndPassword(username, password);
        assertReflectionEquals(persistedAccount, existingAccount);
    }
}
