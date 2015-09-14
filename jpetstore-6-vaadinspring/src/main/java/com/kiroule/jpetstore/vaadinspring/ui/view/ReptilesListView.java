package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = ReptilesListView.VIEW_NAME)
@ViewConfig(displayName = "Reptiles", productCategory = "REPTILES")
public class ReptilesListView extends ProductListView {

  public static final String VIEW_NAME = "reptiles";
}