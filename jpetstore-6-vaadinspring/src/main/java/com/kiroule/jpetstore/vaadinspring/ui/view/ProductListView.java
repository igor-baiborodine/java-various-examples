package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;

import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public abstract class ProductListView extends MVerticalLayout implements View {

  protected MTable<Product> productList;

  @Resource
  protected ProductMapper productRepository;

  @PostConstruct
  public void init() {
    productList = new MTable<>(Product.class)
        .withProperties("productId", "name")
        .withColumnHeaders("Product ID", "Name")
        .setSortableProperties("productId", "name")
        .withFullWidth();
    productList.setBeans(productRepository.getProductListByCategory(getViewConfig().productCategory()));

    addComponent(getHeader());
    addComponent(productList);
    this.setSizeFull();
    this.expand(productList);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }

  private ViewConfig getViewConfig() {
    return this.getClass().getAnnotation(ViewConfig.class);
  }

  private Label getHeader() {
    Label header = new Label(getViewConfig().displayName());
    header.addStyleName(JPetStoreTheme.LABEL_H2);
    header.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return header;
  }
}