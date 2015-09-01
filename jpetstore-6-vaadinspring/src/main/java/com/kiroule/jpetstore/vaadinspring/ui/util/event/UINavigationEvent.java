package com.kiroule.jpetstore.vaadinspring.ui.util.event;

/**
 * @author Igor Baiborodine
 */
public class UINavigationEvent {

    private String viewName;

    public UINavigationEvent(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
