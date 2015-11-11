package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;

import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author Igor Baiborodine
 */
public abstract class AbstractView extends MVerticalLayout implements View {

  protected Label title;

  protected Label getTitle() {
    title = new Label("Abstract Title");
    title.addStyleName(JPetStoreTheme.LABEL_H2);
    title.addStyleName(JPetStoreTheme.LABEL_BOLD);
    return title;
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    title.setValue(ViewConfigUtil.getDisplayName(this.getClass()));
  }
}