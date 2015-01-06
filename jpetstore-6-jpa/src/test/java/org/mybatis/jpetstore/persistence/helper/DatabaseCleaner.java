package org.mybatis.jpetstore.persistence.helper;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Reference: https://github.com/npryce/goos-code-examples
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/persistence-context-test.xml")
public class DatabaseCleaner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseCleaner.class);
    private EntityManager entityManager;

    public DatabaseCleaner(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void clean() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        for (TablesToClean table : TablesToClean.values()) {
            deleteEntities(table);
        }
        transaction.commit();

        for (TablesToClean table : TablesToClean.values()) {
            assertThat(count(table), equalTo(0L));
        }
        logger.info("cleaned tables{}", Arrays.asList(TablesToClean.values()));
    }

    private void deleteEntities(TablesToClean table) {
        entityManager.createQuery("delete from " + table.toString().toLowerCase()).executeUpdate();
    }

    private Long count(TablesToClean table) {
        return (Long) entityManager.createQuery("select count(*) from "
                + table.toString().toLowerCase()).getSingleResult();
    }
}
