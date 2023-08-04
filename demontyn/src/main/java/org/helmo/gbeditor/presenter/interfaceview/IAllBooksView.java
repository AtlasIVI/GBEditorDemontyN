package org.helmo.gbeditor.presenter.interfaceview;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.presenter.arg.NavigationArg;

import java.util.List;

public interface IAllBooksView {


    void goTo(ViewType type, NavigationArg args);

    void throwAlert(String message);
}
