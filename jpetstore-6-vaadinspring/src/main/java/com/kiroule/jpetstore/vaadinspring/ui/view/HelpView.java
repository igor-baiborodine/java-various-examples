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
@SpringView(name = HelpView.VIEW_NAME)
@ViewConfig(displayName = "Help")
public class HelpView extends MVerticalLayout implements View {

  public static final String VIEW_NAME = "help";

  @PostConstruct
  void init() {
    addComponent(new Label("Help: not implemented"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}