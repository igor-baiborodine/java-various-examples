package org.mybatis.jpetstore.persistence;

import java.util.List;

import org.mybatis.jpetstore.domain.LineItem;

public interface LineItemMapper {

  List<LineItem> getLineItemsByOrderId(String orderId);

  void insertLineItem(LineItem lineItem);

}
