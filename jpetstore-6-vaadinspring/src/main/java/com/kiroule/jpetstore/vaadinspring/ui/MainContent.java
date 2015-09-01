package com.kiroule.jpetstore.vaadinspring.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Igor Baiborodine
 */
public class MainContent extends VerticalLayout {

  public MainContent() {

    Label header = new Label("<strong>JPetStore 6 Demo Vaadin</strong>", ContentMode.HTML);
    addComponent(header);
  }
}
