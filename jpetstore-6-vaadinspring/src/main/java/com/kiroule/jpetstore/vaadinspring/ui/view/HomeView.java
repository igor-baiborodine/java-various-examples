package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = HomeView.VIEW_NAME)
@ViewConfig(displayName = "")
public class HomeView extends AbstractView {

  public static final String VIEW_NAME = "";

  @PostConstruct
  void init() {

    setStyleName("home-view");
    Panel contentPanel = new Panel(new Image(null, new ThemeResource("img/splash.gif")));
    contentPanel.setSizeUndefined();
    contentPanel.setStyleName(JPetStoreTheme.PANEL_BORDERLESS);

    addComponents(getTitle(), contentPanel);
    setComponentAlignment(contentPanel, Alignment.MIDDLE_CENTER);
    expand(contentPanel);
    setSizeFull();
  }
}