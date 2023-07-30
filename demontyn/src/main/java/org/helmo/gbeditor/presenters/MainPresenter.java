package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.views.MainView;
import org.helmo.gbeditor.views.ViewsNavigator;

public class MainPresenter {
    private final ViewsNavigator viewsNavigator;

    public MainPresenter(MainView mainView, ViewsNavigator viewsNavigator) {
        this.viewsNavigator = viewsNavigator;
    }


}
