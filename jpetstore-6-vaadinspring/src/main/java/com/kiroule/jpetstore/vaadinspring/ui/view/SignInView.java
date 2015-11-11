package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = SignInView.VIEW_NAME)
@ViewConfig(displayName = "Sign In")
public class SignInView extends AbstractView {

  public static final String VIEW_NAME = "sign-in";

  @PostConstruct
  void init() {
    Label label = new Label("NOT IMPLEMENTED");
    addComponents(getTitle(), label);
    setSizeFull();
    expand(label);
  }
}