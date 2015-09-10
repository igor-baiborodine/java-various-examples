package com.kiroule.jpetstore.vaadinspring.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.menu.LeftNavBar;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.PageTitleUpdater;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Title("JPetStore 6 Demo Spring Vaadin")
@Theme("jpetstoretheme")
@SpringUI
public class MainUI extends UI {

  private final static Logger logger = LoggerFactory.getLogger(MainUI.class);

  @Autowired
  private SpringViewProvider viewProvider;

  private EventBus eventBus;

  public static MainUI getCurrent() {
    return (MainUI) UI.getCurrent();
  }

  public static EventBus getEventBus() {
    return getCurrent().eventBus;
  }

  @Override
  protected void init(VaadinRequest request) {
    initEventBus();
    initMainContent();
    logger.info("Finished initialization of main UI");
  }

  private void initEventBus() {
    eventBus = new EventBus((throwable, subscriberExceptionContext) -> {
      logger.error("Subscriber event error: ", throwable);
    });
    eventBus.register(this);
  }

  private void initMainContent() {

    HorizontalLayout mainContent = new HorizontalLayout();
    mainContent.setSizeFull();
    setContent(mainContent);

    LeftNavBar leftNavBar = new LeftNavBar();
    mainContent.addComponent(leftNavBar);

    Panel viewContainer = new Panel();
    viewContainer.setSizeFull();
    viewContainer.addStyleName(JPetStoreTheme.PANEL_BORDERLESS);
    mainContent.addComponent(viewContainer);
    mainContent.setExpandRatio(viewContainer, 1.0f);

    Navigator navigator = new Navigator(this, viewContainer);
    navigator.addProvider(viewProvider);
    navigator.addViewChangeListener(leftNavBar);
    navigator.addViewChangeListener(new PageTitleUpdater());
  }

  @Subscribe
  public void navigateTo(UINavigationEvent view) {
    getNavigator().navigateTo(view.getViewName());
  }
}
