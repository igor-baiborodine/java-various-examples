package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.domain.builder.Builder;
import org.mybatis.jpetstore.persistence.helper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.idOf;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;

/**
 * Reference: https://github.com/npryce/goos-code-examples
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/persistence-context-test.xml")
public class PersistabilityTest {

    private static Logger logger = LoggerFactory.getLogger(PersistabilityTest.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JPATransactor transactor;

    @SuppressWarnings("unchecked")
    private final List<? extends Builder<?>> persistentObjectBuilders = Arrays.asList(
            createAccountBuilderWithAllFieldsWithBannerData(),
            createAccountBuilderWithAllFieldsWithoutBannerData(),
            createCategoryBuilderWithAllFields(),
            createProductBuilderWithAllFields(),
            createSupplierBuilderWithAllFields(),
            createItemBuilderWithAllFields().supplier(null),
            createLineItemBuilderWithAllFields()
    );

    @Before
    public void cleanDatabase() throws Exception {
        assertThat(entityManagerFactory, notNullValue());

        entityManager = entityManagerFactory.createEntityManager();
        assertThat(entityManager, notNullValue());

        transactor = new JPATransactor(entityManager);
        DatabaseCleaner cleaner = new DatabaseCleaner(entityManager);
        cleaner.clean();
    }

    @After
    public void tearDown() {
        entityManager.close();
        entityManager = null;
        transactor = null;
    }

    @Test
    public void canRoundTripPersistentObjects() throws Exception {
        for (Builder<?> builder : persistentObjectBuilders) {
            assertCanBePersisted(builder);
        }
    }

    private void assertCanBePersisted(@Nonnull final Builder<?> builder) throws Exception {
        try {
            assertReloadsWithSameStateAs(persistedObjectFrom(builder));
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new PersistenceException("could not round-trip " + typeFor(builder), e);
        }
    }

    private String typeFor(@Nonnull final Builder<?> builder) {
        return builder.getClass().getSimpleName().replace("Builder", "");
    }

    private Object persistedObjectFrom(@Nonnull final Builder<?> builder) throws Exception {
        return transactor.performQuery(new QueryUnitOfWork<Object>() {
            public Object query() throws Exception {
                // Build the saved object in the transaction so any sub-builders
                // can do database activity if necessary
                Object saved = builder.build();
                entityManager.persist(saved);
                return saved;
            }
        });
    }

    private void assertReloadsWithSameStateAs(@Nonnull final Object original) throws Exception {
        transactor.perform(new UnitOfWork() {
            public void work() throws Exception {
                Object loaded = entityManager.find(original.getClass(), idOf(original));

                assertNotNull("should have found saved "
                        + original.getClass().getSimpleName(), loaded);
                assertHaveSamePersistentFields(loaded, original);
            }
        });
    }
}
