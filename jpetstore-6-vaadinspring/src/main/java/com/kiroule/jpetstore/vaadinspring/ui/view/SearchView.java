package com.kiroule.jpetstore.vaadinspring.ui.view;

import com.kiroule.jpetstore.vaadinspring.ui.util.ViewConfig;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@SpringView(name = SearchView.VIEW_NAME)
@ViewConfig(displayName = "Search")
public class SearchView extends AbstractView {

  public static final String VIEW_NAME = "search";

  @PostConstruct
  void init() {
    Label label = new Label("NOT IMPLEMENTED");
    addComponents(getTitle(), label);
    setSizeFull();
    expand(label);
  }
}