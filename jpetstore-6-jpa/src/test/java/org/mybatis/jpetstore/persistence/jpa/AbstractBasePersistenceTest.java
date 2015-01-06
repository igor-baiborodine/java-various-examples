package org.mybatis.jpetstore.persistence.jpa;

import org.junit.runner.RunWith;
import org.mybatis.jpetstore.persistence.helper.TablesToClean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/persistence-context-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public abstract class AbstractBasePersistenceTest {

    protected void cleanDatabase(EntityManager em) {
        for (TablesToClean table : TablesToClean.values()) {
            String tableName = table.toString().toLowerCase();
            em.createQuery("delete from " + tableName).executeUpdate();
            Long count = (Long) em.createQuery("select count(*) from " + tableName)
                    .getSingleResult();
            assertThat(count, equalTo(0L));
        }
    }
}
