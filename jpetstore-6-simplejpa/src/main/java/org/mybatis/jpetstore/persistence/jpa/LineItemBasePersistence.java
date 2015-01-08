package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.persistence.LineItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
@Repository
public class LineItemBasePersistence
        extends AbstractBasePersistence<LineItem, String>
        implements LineItemMapper {

    private Logger logger = LoggerFactory.getLogger(LineItemBasePersistence.class);

    @Override
    public List<LineItem> getLineItemsByOrderId(@Nonnull final String orderId) {

        // Named queries not supported by SimpleJPA
        // Query query = createNamedQuery("LineItem.getLineItemsByOrderId");

        Query query = getEntityManager().createQuery("select li from LineItem li where li.orderId = :orderId");
        query.setParameter("orderId", orderId);

        @SuppressWarnings("unchecked")
        List<LineItem> lineItems = (List<LineItem>) query.getResultList();
        closeEntityManager();
        logger.info("Read [{}] line item(s) for order id[{}]", lineItems.size(), orderId);

        return lineItems;
    }

    @Override
    public void insertLineItem(@Nonnull final LineItem lineItem) {
        create(lineItem);
    }
}
