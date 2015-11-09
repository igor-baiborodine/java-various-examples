package com.kiroule.jpetstore.vaadinspring.ui.util;

import com.vaadin.navigator.View;

/**
 * @author Igor Baiborodine
 */
public class ViewConfigUtil {

  public static String getDisplayName(Class<? extends View> clazz) {
    ViewConfig viewConfig = clazz.getAnnotation(ViewConfig.class);
    return (viewConfig != null) ? viewConfig.displayName() : null;
  }
}
