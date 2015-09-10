package com.kiroule.jpetstore.vaadinspring.ui.menu;

import com.google.common.collect.Maps;

import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.kiroule.jpetstore.vaadinspring.ui.view.BirdsListView;
import com.kiroule.jpetstore.vaadinspring.ui.view.CatsListView;
import com.kiroule.jpetstore.vaadinspring.ui.view.DogsListView;
import com.kiroule.jpetstore.vaadinspring.ui.view.FishListView;
import com.kiroule.jpetstore.vaadinspring.ui.view.ReptilesListView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import java.util.Map;

/**
 * @author Igor Baiborodine
 */
public class LeftNavBar extends CssLayout implements ViewChangeListener {

  private Map<String, Button> uriToButtonMap = Maps.newHashMap();

  public LeftNavBar() {

    setHeight("100%");
    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.LEFT_MENU);

    Label logo = new Label("<strong>JPetStore 6 Demo Vaadin</strong>", ContentMode.HTML);
    logo.addStyleName(JPetStoreTheme.MENU_TITLE);
    addComponent(logo);
    addView(FishListView.VIEW_NAME, getDisplayName(FishListView.class));
    addView(DogsListView.VIEW_NAME, getDisplayName(DogsListView.class));
    addView(CatsListView.VIEW_NAME, getDisplayName(CatsListView.class));
    addView(ReptilesListView.VIEW_NAME, getDisplayName(ReptilesListView.class));
    addView(BirdsListView.VIEW_NAME, getDisplayName(BirdsListView.class));
  }

  private String getDisplayName(Class<?> listViewClass) {

    ViewConfig viewConfig = listViewClass.getAnnotation(ViewConfig.class);

    if (viewConfig != null) {
      return viewConfig.displayName();
    }
    return "Not Available";
  }

  public void addView(String uri, String displayName) {

    Button viewButton = new Button(displayName, click -> UIEventBus.post(new UINavigationEvent(uri)));
    uriToButtonMap.put(uri, viewButton);

    viewButton.addStyleName(JPetStoreTheme.MENU_ITEM);
    viewButton.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
    addComponent(viewButton);
  }

  @Override
  public boolean beforeViewChange(ViewChangeEvent event) {
    return true; // false blocks navigation, always return true here
  }

  @Override
  public void afterViewChange(ViewChangeEvent event) {

    uriToButtonMap.values().forEach(button -> button.removeStyleName(JPetStoreTheme.SELECTED));
    Button button = uriToButtonMap.get(event.getViewName());
    if (button != null) {
      button.addStyleName(JPetStoreTheme.SELECTED);
    }
  }

}
