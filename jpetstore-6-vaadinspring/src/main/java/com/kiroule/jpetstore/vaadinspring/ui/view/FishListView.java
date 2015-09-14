package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = FishListView.VIEW_NAME)
@ViewConfig(displayName = "Fish", productCategory = "FISH")
public class FishListView extends ProductListView {

  public static final String VIEW_NAME = "fish";
}