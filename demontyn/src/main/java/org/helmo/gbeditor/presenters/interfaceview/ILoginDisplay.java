package org.helmo.gbeditor.presenters.interfaceview;

import javafx.stage.Stage;
import org.helmo.gbeditor.views.interfacepresenter.ILoginPresenter;

public interface ILoginDisplay {
        void setLoginPresenterInterface(ILoginPresenter loginPresenter);
        void start(Stage primaryStage);
        void displayError(String message);
}
