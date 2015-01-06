package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.persistence.OrderMapper;
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
public class OrderBasePersistence
        extends AbstractBasePersistence<Order, Integer>
        implements OrderMapper {

    private Logger logger = LoggerFactory.getLogger(OrderBasePersistence.class);

    @Override
    public List<Order> getOrdersByUsername(@Nonnull final String username) {
        Query query = createNamedQuery("Order.getOrdersByUsername");
        query.setParameter("username", username);

        @SuppressWarnings("unchecked")
        List<Order> orders = query.getResultList();
        logger.info("Read [{}] order(s) for username[{}]", orders.size(), username);
        return orders;
    }

    @Override
    public Order getOrder(final int orderId) {
        return read(Integer.valueOf(orderId));
    }

    @Override
    public void insertOrder(@Nonnull final Order order) {
        create(order);
    }

    @Override
    public void insertOrderStatus(@Nonnull final Order order) {
        // no need to implement, order status is inserted with the order
    }
}
