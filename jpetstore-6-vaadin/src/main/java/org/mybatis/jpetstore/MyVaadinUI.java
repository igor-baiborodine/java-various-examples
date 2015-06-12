package org.mybatis.jpetstore;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * @author Igor Baiborodine
 */
@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

  @Override
  protected void init(VaadinRequest request) {
    setContent(new Label("Test UI!"));
  }
}