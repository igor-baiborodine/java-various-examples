package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = HomeView.VIEW_NAME)
@ViewConfig(displayName = "Home")
public class HomeView extends MVerticalLayout implements View {

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