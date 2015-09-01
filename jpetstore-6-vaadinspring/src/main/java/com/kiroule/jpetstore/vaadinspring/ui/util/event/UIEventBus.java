package com.kiroule.jpetstore.vaadinspring.ui.util.event;

import com.kiroule.jpetstore.vaadinspring.ui.MainUI;

/**
 * @author Igor Baiborodine
 */
public class UIEventBus {

  public static void register(final Object listener) {
    MainUI.getEventBus().register(listener);
  }

  public static void unregister(final Object listener) {
    MainUI.getEventBus().unregister(listener);
  }

  public static void post(final Object event) {
    MainUI.getEventBus().post(event);
  }
}
