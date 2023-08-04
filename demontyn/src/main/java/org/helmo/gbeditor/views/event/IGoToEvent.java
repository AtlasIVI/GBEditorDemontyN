package org.helmo.gbeditor.views.event;

import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;

@FunctionalInterface
public interface IGoToEvent {
    void gotoView(ViewType type, NavigationArg args);
}
