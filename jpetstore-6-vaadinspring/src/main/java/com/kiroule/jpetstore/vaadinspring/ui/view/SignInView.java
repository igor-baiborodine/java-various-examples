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
@SpringView(name = SignInView.VIEW_NAME)
@ViewConfig(displayName = "Sign In")
public class SignInView extends MVerticalLayout implements View {

  public static final String VIEW_NAME = "sign-in";

  @PostConstruct
  void init() {
    addComponent(new Label("Sign In: not implemented"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}