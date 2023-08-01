package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.helmo.gbeditor.presenters.interfaceview.ICreateBookDisplay;
import org.helmo.gbeditor.views.interfacepresenter.ICreateBookPresenter;


public class CreateBookDisplay implements ICreateBookDisplay {

    private ICreateBookPresenter createBookPresenter;

    private Label lAutorName = new Label("Autor :");

    GridPane topPane = new GridPane();
    {
        topPane.setAlignment(Pos.TOP_CENTER);
        var lMainTitle = new Label("GameBook Editor");
        topPane.add(lMainTitle,5,0);

    }
    GridPane leftPane = new GridPane();
    {
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.add(lAutorName,0,0);
    }
    GridPane centerCreateBookPane = new GridPane();
    {
        centerCreateBookPane.setAlignment(Pos.CENTER);

        centerCreateBookPane.add(new Label("Donnez un titre à votre livre"), 1, 0);
        TextField entryTitle = new TextField();
        centerCreateBookPane.add(entryTitle, 1, 1);

        centerCreateBookPane.add(new Label("Entrez votre matricule"), 1, 2);
        TextArea matricule = new TextArea();
        centerCreateBookPane.add(matricule, 1, 3);


        centerCreateBookPane.add(new Label("Entrez le résumé du livre"), 1, 4);

        TextArea resume = new TextArea();
        centerCreateBookPane.add(resume, 1, 5);

        Button valid = new Button("Valider");
        valid.setOnAction(action -> createBookPresenter.switchToEditBookView(entryTitle.getText().strip(), resume.getText().strip(), matricule.getText().strip()));
        centerCreateBookPane.add(valid, 1, 6);
    }



    public BorderPane mainPane = new BorderPane();
    {
        mainPane.setTop(topPane);
        mainPane.setLeft(leftPane);
        mainPane.setCenter(centerCreateBookPane);
    }


    @Override
    public void setICreateBookPresenter(ICreateBookPresenter createBookPresenter) {
        this.createBookPresenter = createBookPresenter;
        this.lAutorName.setText("Autor : " + createBookPresenter.getAutorName());

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
