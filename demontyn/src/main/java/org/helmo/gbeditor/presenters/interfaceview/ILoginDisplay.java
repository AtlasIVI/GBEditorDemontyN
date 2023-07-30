package org.helmo.gbeditor.presenters.interfaceview;

import javafx.stage.Stage;
import org.helmo.gbeditor.views.interfacepresenter.LoginPresenterInterface;

public interface ILoginDisplay {
        void setLoginPresenterInterface(LoginPresenterInterface loginPresenter);
        void start(Stage primaryStage);
        void displayError(String message);
}
