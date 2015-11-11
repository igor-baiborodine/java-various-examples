package com.kiroule.jpetstore.vaadinspring.ui.menu;

import static com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfigUtil.getDisplayName;

import com.kiroule.jpetstore.vaadinspring.ui.MainUI;
import com.kiroule.jpetstore.vaadinspring.ui.event.UIEventBus;
import com.kiroule.jpetstore.vaadinspring.ui.event.UINavigationEvent;
import com.kiroule.jpetstore.vaadinspring.ui.theme.JPetStoreTheme;
import com.kiroule.jpetstore.vaadinspring.ui.view.CartView;
import com.kiroule.jpetstore.vaadinspring.ui.view.HelpView;
import com.kiroule.jpetstore.vaadinspring.ui.view.SearchView;
import com.kiroule.jpetstore.vaadinspring.ui.view.SignInView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;

/**
 * @author Igor Baiborodine
 */
public class TopNavBar extends CssLayout implements ViewChangeListener {

  public TopNavBar() {

    setWidth(100f, Unit.PERCENTAGE);
    setHeight(44f, Unit.PIXELS);
    addStyleName(JPetStoreTheme.MENU_ROOT);
    addStyleName(JPetStoreTheme.TOP_MENU);

    TextField searchTextField = new TextField();
    addComponent(searchTextField);

    addButton(SearchView.VIEW_NAME, getDisplayName(SearchView.class));
    addButton(SignInView.VIEW_NAME, getDisplayName(SignInView.class));
    addButton(CartView.VIEW_NAME, getDisplayName(CartView.class));
    addButton(HelpView.VIEW_NAME, getDisplayName(HelpView.class));
  }

  private Button addButton(String viewName, String displayName) {

    String uri = viewName;
    Button viewButton = new Button(displayName, click -> UIEventBus.post(new UINavigationEvent(uri)));
    MainUI.getUriToButtonMap().put(uri, viewButton);

    viewButton.addStyleName(JPetStoreTheme.MENU_ITEM);
    viewButton.addStyleName(JPetStoreTheme.BUTTON_BORDERLESS);
    addComponent(viewButton);
    return viewButton;
  }

  @Override
  public boolean beforeViewChange(ViewChangeEvent viewChangeEvent) {
    return true; // false blocks navigation, always return true here
  }

  @Override
  public void afterViewChange(ViewChangeEvent event) {

    MainUI.getUriToButtonMap().values().forEach(button -> button.removeStyleName(JPetStoreTheme.SELECTED));
    Button button = MainUI.getUriToButtonMap().get(event.getViewName());
    if (button != null) {
      button.addStyleName(JPetStoreTheme.SELECTED);
    }
  }
}
