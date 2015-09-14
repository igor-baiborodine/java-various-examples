package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = BirdsListView.VIEW_NAME)
@ViewConfig(displayName = "Birds", productCategory = "BIRDS")
public class BirdsListView extends ProductListView {

  public static final String VIEW_NAME = "birds";
}