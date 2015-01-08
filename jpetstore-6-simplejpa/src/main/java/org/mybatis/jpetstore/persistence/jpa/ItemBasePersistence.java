package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.INCREMENT;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.ITEM_ID;

/**
 * @author Igor Baiborodine
 */
@Repository
public class ItemBasePersistence
        extends AbstractBasePersistence<Item, String>
        implements ItemMapper {

    private Logger logger = LoggerFactory.getLogger(ItemBasePersistence.class);

    @Autowired
    private ProductBasePersistence productBasePersistence;

    @Override
    public void updateInventoryQuantity(@Nonnull final Map<String, Object> param) {

        checkNotNull(param, "Argument[param] must not be null");
        checkState(param.size() == 2,
                String.format("Argument[param] has illegal state: size[%d]", param.size()));
        String itemId = (String) param.get(ITEM_ID.toString());
        Integer increment = (Integer) param.get(INCREMENT.toString());

        Item item = getItem(itemId);
        Integer itemQuantity = item.getQuantity();
        item.setQuantity(itemQuantity - increment);

        update(item);

        logger.info("Updated inventory quantity from [{}] to [{}] for item with id[{}]",
                itemQuantity, item.getQuantity(), itemId);
    }

    @Override
    public int getInventoryQuantity(@Nonnull final String itemId) {

        int result = -1;
        Item item = getItem(itemId);

        if (item != null) {
            result = item.getQuantity();
        }
        logger.info("Read quantity[{}] for item[{}]", result, itemId);

        return result;
    }

    @Override
    public List<Item> getItemListByProduct(@Nonnull final String productId) {

        // Named queries not supported by SimpleJPA
        // Query query = createNamedQuery("Item.getItemListByProduct");

        Query query = getEntityManager().createQuery("select i from Item i where i.productId = :productId");
        query.setParameter("productId", productId);

        @SuppressWarnings("unchecked")
        List<Item> items = (List<Item>) query.getResultList();
        closeEntityManager();
        for (Item item : items) {
            Product product = productBasePersistence.getProduct(item.getProductId());
            item.setProduct(product);
        }
        logger.info("Read [{}] item(s) with product id[{}]", items.size(), productId);

        return items;
    }

    @Override
    public Item getItem(@Nonnull final String itemId) {

        Item item = read(itemId);
        if (item != null) {
            Product product = productBasePersistence.getProduct(item.getProductId());
            item.setProduct(product);
        }

        return item;
    }

}
