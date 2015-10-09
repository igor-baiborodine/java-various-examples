package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.persistence.CategoryMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;

import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Igor Baiborodine
 */
@UIScope
@SpringView(name = ProductListView.VIEW_NAME)
@ViewConfig(displayName = "Product")
public class ProductListView extends MVerticalLayout implements View {

  public static final String VIEW_NAME = "product-list";

  @Resource
  private ProductMapper productRepository;

  @Resource
  private CategoryMapper categoryRepository;

  private MTable<Product> productList;
  private Label header;

  @PostConstruct
  public void init() {

    productList = new MTable<>(Product.class)
        .withProperties("productId", "name")
        .withColumnHeaders("Product ID", "Name")
        .setSortableProperties("productId", "name")
        .withGeneratedColumn("productId", entity -> {
          String uri = ItemListView.VIEW_NAME + "/" + entity.getProductId();
          Button inventoryButton = new Button(entity.getProductId(),
              click -> UIEventBus.post(new UINavigationEvent(uri)));
          inventoryButton.setData(entity.getProductId());
          inventoryButton.addStyleName("link");
          return inventoryButton;
        })
        .withFullWidth();

    addComponent(getHeader());
    addComponent(productList);
    setSizeFull();
    expand(productList);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    Category category = categoryRepository.getCategory(event.getParameters());
    header.setValue(category.getName());
    productList.setBeans(productRepository.getProductListByCategory(category.getCategoryId()));
  }

  private Label getHeader() {
    header = new Label("Category Name Header");
    header.addStyleName(JPetStoreTheme.LABEL_H2);
    header.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return header;
  }
}