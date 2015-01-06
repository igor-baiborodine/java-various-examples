package org.mybatis.jpetstore.domain.builder;

import org.mybatis.jpetstore.domain.CartItem;
import org.mybatis.jpetstore.domain.LineItem;

import javax.annotation.Nonnull;

/**
 * @author Igor Baiborodine
 */
public class LineItemBuilder
        implements Builder<LineItem> {

    private Integer orderId;
    private Integer lineNumber;
    private CartItem cartItem;

    public LineItemBuilder(@Nonnull final Integer lineNumber, @Nonnull final CartItem cartItem) {

        // non-null validation is done in LineItem(lineNumber, cartItem) constructor
        this.lineNumber = lineNumber;
        this.cartItem = cartItem;
    }

    public LineItemBuilder orderId(@Nonnull final Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public LineItem build() {

        LineItem lineItem = new LineItem(lineNumber, cartItem);
        lineItem.setOrderId(orderId);
        return lineItem;
    }

}
