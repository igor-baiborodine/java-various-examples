package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.domain.Product;
import com.kiroule.jpetstore.vaadinspring.ui.component.VerticalSpacedLayout;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;

import org.vaadin.viritin.fields.MTable;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = FishListView.VIEW_NAME)
@ViewConfig(displayName = "Fish")
public class FishListView extends VerticalSpacedLayout implements View {

  public static final String VIEW_NAME = "fish";

  private MTable<Product> list = new MTable<>(Product.class)
      .withProperties("productId", "name", "description")
      .withColumnHeaders("ID", "Name", "Description")
      .setSortableProperties("productId", "name")
      .withFullWidth();

  @PostConstruct
  void init() {
    addComponent(new Label("Fish view: not implemented"));


  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}