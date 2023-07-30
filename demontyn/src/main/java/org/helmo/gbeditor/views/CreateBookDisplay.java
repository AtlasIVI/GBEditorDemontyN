package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.helmo.gbeditor.presenters.interfaceview.ICreateBookDisplay;
import org.helmo.gbeditor.views.interfacepresenter.ICreateBookPresenter;


public class CreateBookDisplay implements ICreateBookDisplay {



    GridPane topPane = new GridPane();
    {
        topPane.setAlignment(Pos.CENTER);
        topPane.add(new Label("GameBook Editor"), 0, 0);
    }

    public BorderPane mainPane = new BorderPane();
    {
        mainPane.setTop(topPane);
    }


    @Override
    public void setICreateBookPresenter(ICreateBookPresenter createBookPresenter) {

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(mainPane, 1200, 700));
        primaryStage.show();
    }

    @Override
    public void displayError(String message) {

    }
}
