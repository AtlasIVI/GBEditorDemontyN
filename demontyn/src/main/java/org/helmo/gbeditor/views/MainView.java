package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenters.Presenter;

public class MainView  {

    Presenter presenter;


    GridPane centerPane = new GridPane();
    {
        centerPane.setAlignment(Pos.CENTER);
        centerPane.add(new Label("Welcome to your GameBook"), 0, 0);
        Button goToLogin = new Button("S'authentifier");
        goToLogin.setOnAction(action -> presenter.goToLogin());
        centerPane.add(goToLogin, 0, 1);

    }

    public BorderPane mainPane = new BorderPane();
    {
        mainPane.setCenter(centerPane);
    }
    public Parent getRoot() {
        return mainPane;
    }
}
