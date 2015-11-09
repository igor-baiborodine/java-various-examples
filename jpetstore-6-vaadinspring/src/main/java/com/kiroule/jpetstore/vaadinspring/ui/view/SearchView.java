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
@SpringView(name = SearchView.VIEW_NAME)
@ViewConfig(displayName = "Search")
public class SearchView extends MVerticalLayout implements View {

  public static final String VIEW_NAME = "search";

  @PostConstruct
  void init() {
    addComponent(new Label("Search: not implemented"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}