package org.helmo.gbeditor.views;

import javafx.scene.Parent;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;

public interface IView {
    ViewType getViewType();

    Parent getRoot();

    void beforeShow(NavigationArg args);
}
