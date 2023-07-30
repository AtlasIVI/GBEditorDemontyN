package org.helmo.gbeditor.presenters.interfaceview;

import javafx.stage.Stage;
import org.helmo.gbeditor.views.interfacepresenter.ICreateBookPresenter;

public interface ICreateBookDisplay {
    void setICreateBookPresenter(ICreateBookPresenter createBookPresenter);
    void start(Stage primaryStage);
    void displayError(String message);

}
