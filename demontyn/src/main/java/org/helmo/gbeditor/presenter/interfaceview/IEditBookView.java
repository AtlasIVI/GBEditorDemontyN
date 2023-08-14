package org.helmo.gbeditor.presenter.interfaceview;

import org.helmo.gbeditor.presenter.arg.NavigationArg;

public interface IEditBookView {
    void goTo(ViewType type, NavigationArg args);

    void throwAlert(String message);
    boolean throwAlertWithConfirm(String content);

}
