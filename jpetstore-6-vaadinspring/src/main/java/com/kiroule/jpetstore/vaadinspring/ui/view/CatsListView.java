package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = CatsListView.VIEW_NAME)
@ViewConfig(displayName = "Cats", productCategory = "CATS")
public class CatsListView extends ProductListView {

  public static final String VIEW_NAME = "cats";
}