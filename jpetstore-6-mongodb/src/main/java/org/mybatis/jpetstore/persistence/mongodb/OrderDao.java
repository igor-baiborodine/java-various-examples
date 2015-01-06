package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.persistence.LineItemMapper;
import org.mybatis.jpetstore.persistence.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

/**
 * @author Igor Baiborodine
 */
public class OrderDao
        extends AbstractBaseDao<Order, Integer>
        implements OrderMapper, LineItemMapper {

    private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    public OrderDao(@Nonnull final String collectionName) {
        super(collectionName);
    }

    public List<LineItem> getLineItemsByOrderId(final int orderId) {

        DBObject orderObj = collection.findOne(new BasicDBObject("_id", orderId));
        List<LineItem> lineItems = new ArrayList<>();

        if (orderObj != null) {
            Order order = Order.fromDBObject(orderObj);
            lineItems.addAll(order.getLineItems());
        }
        logger.info("Found for order id[{}] following line items{}", orderId, lineItems);

        return lineItems;
    }

    public void insertLineItem(@Nonnull final LineItem lineItem) {

        checkNotNull(lineItem, "Argument[lineItem] must not be null");
        checkNotNull(lineItem.getOrderId(), "Argument[lineItem] must not be null");

        DBObject query = new BasicDBObject("_id", lineItem.getOrderId());
        DBObject update = new BasicDBObject(
                "$push", new BasicDBObject("line_items", lineItem.toDBObject()));
        WriteResult result = collection.update(query, update);

        logger.info("[{}] order updated for line item[{}]", result.getN(), lineItem);
        // TODO: and handling for 0 orders updated
    }

    public List<Order> getOrdersByUsername(@Nonnull final String username) {

        checkNotNull(emptyToNull(username), "Argument[username] must not be empty or null");

        List<Order> orders = new ArrayList<>();
        DBObject query = new BasicDBObject("username", username);

        try (DBCursor cursor = collection.find(query).sort(new BasicDBObject("order_id", 1))) {
            while (cursor.hasNext()) {
                DBObject orderObj = cursor.next();
                orders.add(Order.fromDBObject(orderObj));
            }
        }
        logger.info("Found for username [{}] [{}] order(s)", username, orders.size());

        return orders;
    }

    public Order getOrder(final int orderId) {

        Order order = super.read(orderId);
        logger.info("Found for id[{}] following order[{}]", orderId, order);

        return order;
    }

    public void insertOrder(@Nonnull final Order order) {

        checkNotNull(order, "Argument[order] must not be null");

        super.create(order);
        logger.info("Inserted new order[{}]", order);
    }

    public void insertOrderStatus(@Nonnull final Order order) {

        checkNotNull(order, "Argument[order] must not be null");

        DBObject query = new BasicDBObject("_id", order.getOrderId());
        DBObject update = new BasicDBObject(
                "$set", new BasicDBObject("status", order.getStatus()));
        WriteResult result = collection.update(query, update);
        if (result.getN() == 0) {
            throw new RuntimeException("Cannot update non-existing document[" + query + "]");
        }
        logger.info("Updated status[{}] for order with id[{}]", order.getStatus(), order.getOrderId());
    }

}
