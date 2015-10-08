package com.kiroule.jpetstore.vaadinspring.ui.view;

import static java.lang.String.format;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.persistence.ItemMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Igor Baiborodine
 */
@UIScope
@SpringView(name = ItemListView.VIEW_NAME)
@ViewConfig(displayName = "Item")
public class ItemListView extends MVerticalLayout implements View {

  public static final String VIEW_NAME = "item-list";

  @Resource
  private ProductMapper productRepository;

  @Resource
  private ItemMapper itemRepository;

  private MTable<Item> itemList;
  private Label header;
  private Product product;

  @PostConstruct
  public void init() {

    itemList = new MTable<>(Item.class)
        .withProperties("itemId", "product", "listPrice", "attribute5")
        .withColumnHeaders("Item ID", "Description", "List Price", "")
        .setSortableProperties("itemId", "product", "listPrice")
        .withGeneratedColumn("itemId", entity -> {
          Button inventoryButton = new Button(entity.getItemId(),
              click -> Notification.show("Item Form: not implemented", Notification.Type.WARNING_MESSAGE));
          inventoryButton.setData(entity.getProductId());
          inventoryButton.addStyleName("link");
          return inventoryButton;
        })
        .withGeneratedColumn("product", entity -> entity.getAttribute1() + " " + product.getName())
        .withGeneratedColumn("attribute5", entity -> {
          //String uri = ShoppingCartView.VIEW_NAME + "/" + entity.getItemId;
          Button addToCartButton = new Button("Add to Cart",
              //click -> UIEventBus.post(new UINavigationEvent(uri)));
              click -> Notification.show("Shopping Cart View: not implemented", Notification.Type.WARNING_MESSAGE));
          addToCartButton.setData(entity.getItemId());
          return addToCartButton;
        })
        .withFullWidth();

    addComponent(getHeader());
    addComponent(itemList);
    this.setSizeFull();
    this.expand(itemList);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    product = productRepository.getProduct(event.getParameters());
    header.setValue(format("%s (%s)", product.getName(), product.getProductId()));
    itemList.setBeans(itemRepository.getItemListByProduct(product.getProductId()));
  }

  private Label getHeader() {
    header = new Label("Product Name Header");
    header.addStyleName(JPetStoreTheme.LABEL_H2);
    header.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return header;
  }
}