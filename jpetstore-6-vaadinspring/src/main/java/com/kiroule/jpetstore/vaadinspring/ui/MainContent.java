package com.kiroule.jpetstore.vaadinspring.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Igor Baiborodine
 */
public class MainContent extends VerticalLayout {

  public MainContent() {
    Label label = new Label("JPetStore 6 1/2 Demo Spring Vaadin");
    addComponent(label);
  }
}
