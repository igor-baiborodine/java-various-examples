package com.kiroule.jpetstore.vaadinspring.ui.util;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;

/**
 * @author Igor Baiborodine
 */
public class PageTitleUpdater implements ViewChangeListener {

  @Override
  public boolean beforeViewChange(ViewChangeEvent event) {
    return true;
  }

  @Override
  public void afterViewChange(ViewChangeEvent event) {
    String title = ViewConfigUtil.getDisplayName(event.getNewView().getClass());
    if (title.isEmpty()) {
      title = "Home";
    }
    if (!isNullOrEmpty(event.getParameters())) {
      title += " | " + event.getParameters();
    }
    Page.getCurrent().setTitle(title);
  }
}
