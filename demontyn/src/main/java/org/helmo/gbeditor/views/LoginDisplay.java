package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.helmo.gbeditor.views.interfacepresenter.ILoginPresenter;

public class LoginDisplay implements org.helmo.gbeditor.presenters.interfaceview.ILoginDisplay {

    private ILoginPresenter loginPresenter;

    private Label errorMessage = new Label();


    GridPane centerPane = new GridPane();
    {
        centerPane.setAlignment(Pos.CENTER);
        centerPane.add(new Label("Login panel"), 0, 0);

        Label name = new Label("Entrez votre nom");
        centerPane.add(name, 0, 2);
        TextField entryName = new TextField();
        centerPane.add(entryName, 1, 2);

        Label firstName = new Label("Entrez votre prénom");
        centerPane.add(firstName, 0, 3);
        TextField entryFirstName = new TextField();
        centerPane.add(entryFirstName, 1, 3);


        Button goToLogin = new Button("S'authentifier");
        goToLogin.setOnAction(action -> loginPresenter.login(entryFirstName.getText(),entryName.getText()));
        centerPane.add(goToLogin, 0, 4);
    }
    GridPane leftPane = new GridPane();
    {
        leftPane.setAlignment(Pos.CENTER);
        leftPane.add(new Label("Error Panel"), 0, 0);
        leftPane.add(errorMessage, 0, 1);
    }
    BorderPane mainPane = new BorderPane();
    {
        mainPane.setCenter(centerPane);
        mainPane.setLeft(leftPane);
    }

    @Override
    public void setLoginPresenterInterface(ILoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(mainPane, 1200, 700));
        primaryStage.show();
    }
    @Override
    public void displayError(String message) {
        this.errorMessage.setText(message);
    }


}
