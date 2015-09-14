package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringView(name = DogsListView.VIEW_NAME)
@ViewConfig(displayName = "Dogs", productCategory = "DOGS")
public class DogsListView extends ProductListView {

  public static final String VIEW_NAME = "dogs";
}