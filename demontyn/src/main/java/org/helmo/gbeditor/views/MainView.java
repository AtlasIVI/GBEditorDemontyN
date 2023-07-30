package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.helmo.gbeditor.presenters.MainPresenter;

public class MainView {
    MainPresenter mainPresenter;
    GridPane centerPane = new GridPane();
    {
        centerPane.setAlignment(Pos.CENTER);
        centerPane.add(new Label("Welcome to your GameBook"), 0, 0);
        Button goToLogin = new Button("S'authentifier");
        //goToLogin.setOnAction(action -> presenter.goToLogin());
        centerPane.add(goToLogin, 0, 1);
    }
    public BorderPane mainPane = new BorderPane();
    {
        mainPane.setCenter(centerPane);
    }
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(mainPane, 1200, 700));
        primaryStage.show();
    }
}