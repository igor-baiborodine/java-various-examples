package com.kiroule.jpetstore.vaadinspring.ui.menu;

import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.view.DogsListView;
import com.kiroule.jpetstore.vaadinspring.ui.view.FishListView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import java.util.HashMap;
import java.util.Map;

public class LeftMenu extends CssLayout implements ViewChangeListener {

  private Map<String, Button> uriToButtonMap = new HashMap<>();

  public LeftMenu() {

    setHeight("100%");
    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.LEFT_MENU);

    Label logo = new Label("<strong>JPetStore 6 Demo</strong>App", ContentMode.HTML);
    logo.addStyleName(JPetStoreTheme.MENU_TITLE);
    addComponent(logo);
    addView(FishListView.VIEW_NAME, "Fish");
    addView(DogsListView.VIEW_NAME, "Dogs");
  }

  public void addView(String uri, String displayName) {

    Button viewButton = new Button(displayName, click -> UIEventBus.post(new UINavigationEvent(uri)));
    viewButton.addStyleName(JPetStoreTheme.MENU_ITEM);
    viewButton.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
    uriToButtonMap.put(uri, viewButton);

    addComponent(viewButton, components.size() - 1);
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
