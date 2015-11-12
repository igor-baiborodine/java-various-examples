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
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
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

    final TextField searchTextField = new TextField();
    searchTextField.setImmediate(true);
    searchTextField.addShortcutListener(new ShortcutListener("enter-shortcut", ShortcutAction.KeyCode.ENTER, null) {
      @Override
      public void handleAction(Object sender, Object target) {
        searchProducts(((TextField) target).getValue());
      }
    });
    addComponent(searchTextField);

    addButton(null, getDisplayName(SearchView.class), event -> searchProducts(searchTextField.getValue()));
    addButton(SignInView.VIEW_NAME, getDisplayName(SignInView.class));
    addButton(CartView.VIEW_NAME, getDisplayName(CartView.class));
    addButton(HelpView.VIEW_NAME, getDisplayName(HelpView.class));
  }

  private void searchProducts(String keyword) {

    if (keyword.trim().length() < 3) {
      new Notification("Keyword length must be greater than 2", null, Notification.Type.WARNING_MESSAGE)
          .show(Page.getCurrent());
    } else {
      String uri = SearchView.VIEW_NAME + "/" + keyword.trim().toLowerCase().replaceAll("%", "");
      UIEventBus.post(new UINavigationEvent(uri));
    }
  }

  private Button addButton(String viewName, String displayName) {
    return addButton(viewName, displayName, null);
  }

  private Button addButton(String viewName, String displayName, Button.ClickListener clickListener) {

    String uri = viewName;
    if (clickListener == null) {
      clickListener = event -> UIEventBus.post(new UINavigationEvent(uri));
    }
    Button viewButton = new Button(displayName, clickListener);
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
