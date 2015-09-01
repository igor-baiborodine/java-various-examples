package com.kiroule.jpetstore.vaadinspring.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import com.kiroule.jpetstore.vaadinspring.ui.util.event.UINavigationEvent;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Theme("valo")
@Title("JPetstore 6 Demo Vaadin UI")
@SpringUI
public class MainUI extends UI {

  private static final Logger logger = LoggerFactory.getLogger(MainUI.class);

  private EventBus eventBus;

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    setupEventBus();
    setContent(new MainContent());
  }

  private void setupEventBus() {
    eventBus = new EventBus((throwable, subscriberExceptionContext) -> {
      logger.error("Subscription event error: ", throwable);
    });
    eventBus.register(this);
  }

  @Subscribe
  public void navigateTo(UINavigationEvent view) {
    getNavigator().navigateTo(view.getViewName());
  }

  public static MainUI getCurrent() {
    return (MainUI) UI.getCurrent();
  }

  public static EventBus getEventBus() {
    return getCurrent().eventBus;
  }
}
