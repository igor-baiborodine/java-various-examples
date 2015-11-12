package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Category;
import com.kiroule.jpetstore.vaadinspring.persistence.CategoryMapper;
import com.kiroule.jpetstore.vaadinspring.persistence.ProductMapper;
import com.kiroule.jpetstore.vaadinspring.ui.component.ProductListTable;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@UIScope
@SpringView(name = ProductListView.VIEW_NAME)
@ViewConfig(displayName = "Product")
public class ProductListView extends AbstractView {

  public static final String VIEW_NAME = "product-list";

  @Autowired
  private ProductMapper productRepository;

  @Autowired
  private CategoryMapper categoryRepository;

  @Autowired
  private ProductListTable productListTable;

  @PostConstruct
  public void init() {

    addComponent(getTitle());
    addComponent(productListTable);
    setSizeFull();
    expand(productListTable);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    Category category = categoryRepository.getCategory(event.getParameters());
    title.setValue(category.getName());
    productListTable.setBeans(productRepository.getProductListByCategory(category.getCategoryId()));
  }
}