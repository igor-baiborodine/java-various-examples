package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.persistence.CategoryMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;

import org.vaadin.viritin.fields.MTable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Igor Baiborodine
 */
@UIScope
@SpringView(name = ProductListView.VIEW_NAME)
@ViewConfig(displayName = "Product")
public class ProductListView extends AbstractView {

  public static final String VIEW_NAME = "product-list";

  @Resource
  private ProductMapper productRepository;

  @Resource
  private CategoryMapper categoryRepository;

  private MTable<Product> productList;

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

    addComponent(getTitle());
    addComponent(productList);
    setSizeFull();
    expand(productList);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    Category category = categoryRepository.getCategory(event.getParameters());
    title.setValue(category.getName());
    productList.setBeans(productRepository.getProductListByCategory(category.getCategoryId()));
  }
}