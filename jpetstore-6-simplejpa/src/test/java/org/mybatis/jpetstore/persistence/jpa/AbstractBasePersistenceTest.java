package org.mybatis.jpetstore.persistence.jpa;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.*;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.domain.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/persistence-context-test.xml")
public abstract class AbstractBasePersistenceTest {

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Autowired
    protected AmazonSimpleDB amazonSimpleDB;

    @Value("${persistenceUnitName}")
    protected String persistenceUnitName;

    protected void cleanDatabase() {

        for (Domain domain : Domain.values()) {
            /*
            String tableName = table.toString().toLowerCase();
            em.createQuery("delete from " + tableName).executeUpdate();
            Long count = (Long) em.createQuery("select count(*) from " + tableName)
                    .getSingleResult();
            assertThat(count, is(0L));
            */
            // Since SimpleJPA does not support DELETE queries,
            // use AmazonSimpleDB client to clean the DB by deleting and re-creating domains
            cleanDomain(domain);
        }
    }

    protected void cleanDomain(@Nonnull final Domain domain) {

        String fullDomainName = getFullDomainName(domain);

        DeleteDomainRequest deleteRequest = new DeleteDomainRequest(fullDomainName);
        amazonSimpleDB.deleteDomain(deleteRequest);

        CreateDomainRequest createRequest = new CreateDomainRequest(fullDomainName);
        amazonSimpleDB.createDomain(createRequest);

        assertCount(domain, 0);
    }

    protected void assertCount(@Nonnull final Domain domain, int expectedCount) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore
        }
        String selectExpression = "select count(*) from `" + getFullDomainName(domain) + "`";
        SelectRequest selectRequest = new SelectRequest(selectExpression);

        List<Item> items = amazonSimpleDB.select(selectRequest).getItems();
        assertThat(items.size(), is(1));

        List<Attribute> attributes = items.get(0).getAttributes();
        assertThat(items.get(0).getAttributes().size(), is(1));
        assertThat(attributes.get(0).getName(), is("Count"));
        assertThat(attributes.get(0).getValue(), is(String.valueOf(expectedCount)));
    }

    protected String getFullDomainName(Domain domain) {

        return persistenceUnitName + "-" + domain.toString();
    }

}
