package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.component.VerticalSpacedLayout;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = ReptilesListView.VIEW_NAME)
@ViewConfig(displayName = "Reptiles")
public class ReptilesListView extends VerticalSpacedLayout implements View {

  public static final String VIEW_NAME = "reptiles";

  @PostConstruct
  void init() {
    addComponent(new Label("Reptiles view: not implemented"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}