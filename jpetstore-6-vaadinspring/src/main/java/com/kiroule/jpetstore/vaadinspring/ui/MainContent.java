package com.kiroule.jpetstore.vaadinspring.ui;

import com.kiroule.jpetstore.vaadinspring.ui.menu.LeftMenu;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Igor Baiborodine
 */
public class MainContent extends VerticalLayout {

  private LeftMenu leftMenu;
  private Panel content;
  private Navigator navigator;

  public MainContent() {

    addStyleName(JPetStoreTheme.MAIN_LAYOUT);
    setSizeFull();
    initLayouts();
    initNavigator();
  }

  private void initLayouts() {

    leftMenu = new LeftMenu();
    content = new Panel();
    content.setSizeFull();
    content.addStyleName(JPetStoreTheme.PANEL_BORDERLESS);

    addComponents(leftMenu, content);
    setExpandRatio(content, 1);
  }

  private void initNavigator() {

    navigator = new Navigator(MainUI.getCurrent(), content);
    navigator.addProvider(MainUI.getCurrent().getViewProvider());

    // Add view change listeners so we can do things like select the correct menu item and update the page title
    navigator.addViewChangeListener(leftMenu);
    //navigator.addViewChangeListener(new PageTitleUpdater());
  }
}
