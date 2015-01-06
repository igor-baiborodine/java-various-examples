package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.ITEM_ID;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.INCREMENT;

/**
 * @author Igor Baiborodine
 */
@Repository
public class ItemBasePersistence
        extends AbstractBasePersistence<Item, String>
        implements ItemMapper {

    private Logger logger = LoggerFactory.getLogger(ItemBasePersistence.class);

    @Override
    public void updateInventoryQuantity(@Nonnull final Map<String, Object> param) {
        checkNotNull(param, "Argument[param] must not be null");
        checkState(param.size() == 2,
                String.format("Argument[param] has illegal state: size[%d]", param.size()));
        String itemId = (String) param.get(ITEM_ID.toString());
        Integer increment = (Integer) param.get(INCREMENT.toString());

        Query query = createNamedQuery("Item.updateInventoryQuantity");
        query.setParameter(ITEM_ID.toString(), itemId);
        query.setParameter(INCREMENT.toString(), increment);
        int count = query.executeUpdate();
        logger.info("Query[Item.updateInventoryQuantity] with parameters[itemId[{}], " +
                "quantity[{}]] updated with count[{}]", itemId, increment, count);
    }

    @Override
    public int getInventoryQuantity(@Nonnull final String itemId) {
        int result = -1;
        Query query = createNamedQuery("Item.getInventoryQuantity");
        query.setParameter("itemId", itemId);

        @SuppressWarnings("unchecked")
        List<Integer> quantities = (List<Integer>) query.getResultList();
        checkState(quantities.size() <= 1, String.format("Query[Item.getInventoryQuantity]" +
                "with parameter[%s] returned [%d] results", itemId, quantities.size()));
        if (quantities.size() == 1) {
            result = quantities.get(0);
        }
        logger.info("Read quantity[{}] for item[{}]", result, itemId);
        return result;
    }

    @Override
    public List<Item> getItemListByProduct(@Nonnull final String productId) {
        Product product = entityManager.find(Product.class, productId);
        Query query = createNamedQuery("Item.getItemListByProduct");
        query.setParameter("product", product);

        @SuppressWarnings("unchecked")
        List<Item> items = (List<Item>) query.getResultList();
        logger.info("Read [{}] item(s) for product[{}]", items.size(), productId);
        return items;
    }

    @Override
    public Item getItem(@Nonnull final String itemId) {
        return read(itemId);
    }
}
