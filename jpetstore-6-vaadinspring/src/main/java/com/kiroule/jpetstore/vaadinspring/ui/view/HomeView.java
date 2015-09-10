package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.component.VerticalSpacedLayout;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

@SpringView(name = HomeView.VIEW_NAME)
@ViewConfig(displayName = "Home")
public class HomeView extends VerticalSpacedLayout implements View {

  public static final String VIEW_NAME = "";

  @PostConstruct
  void init() {
    addComponent(new Label("Home view: to implement"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}