package org.mybatis.jpetstore.util;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.*;
import org.mybatis.jpetstore.domain.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Igor Baiborodine
 */
@Component
public class DatabaseUtils {

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Autowired
    protected AmazonSimpleDB amazonSimpleDB;

    @Value("${persistenceUnitName}")
    protected String persistenceUnitName;

    public void cleanDatabase() {

        for (Domain domain : Domain.values()) {

            cleanDomain(domain);
        }
    }

    public void cleanDomain(@Nonnull final Domain domain) {

        String fullDomainName = getFullDomainName(domain);

        DeleteDomainRequest deleteRequest = new DeleteDomainRequest(fullDomainName);
        amazonSimpleDB.deleteDomain(deleteRequest);

        CreateDomainRequest createRequest = new CreateDomainRequest(fullDomainName);
        amazonSimpleDB.createDomain(createRequest);

        assertCount(domain, 0);
    }

    public void assertCount(@Nonnull final Domain domain, int expectedCount) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore
        }
        String selectExpression = "select count(*) from `" + getFullDomainName(domain) + "`";
        SelectRequest selectRequest = new SelectRequest(selectExpression);

        List<Item> items = amazonSimpleDB.select(selectRequest).getItems();
        assertThat(items.size(), equalTo(1));

        List<Attribute> attributes = items.get(0).getAttributes();
        assertThat(items.get(0).getAttributes().size(), equalTo(1));
        assertThat(attributes.get(0).getName(), equalTo("Count"));
        assertThat(attributes.get(0).getValue(), equalTo(String.valueOf(expectedCount)));
    }

    public String getFullDomainName(Domain domain) {

        return persistenceUnitName + "-" + domain.toString();
    }

}
