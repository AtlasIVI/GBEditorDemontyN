package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenter.CreateBookPresenter;
import org.helmo.gbeditor.presenter.LoginPresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ICreateBookView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;

public class CreateBookView extends ViewJavaFx implements ICreateBookView {
    private BorderPane root;
    private CreateBookPresenter presenter;
    private Label lAutorName = new Label("Autor :");

    GridPane topPane = new GridPane();

    {
        topPane.setAlignment(Pos.TOP_CENTER);
        var lMainTitle = new Label("GameBook Editor");
        topPane.add(lMainTitle, 5, 0);

    }

    GridPane leftPane = new GridPane();{
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.add(lAutorName, 0, 0);
    }




    public CreateBookView(CreateBookPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.CREATE_BOOK;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void beforeShow(NavigationArg args) {

        GridPane centerCreateBookPane = new GridPane();

        {
            centerCreateBookPane.setAlignment(Pos.CENTER);

            centerCreateBookPane.add(new Label("Donnez un titre à votre livre"), 1, 0);
            TextField entryTitle = new TextField();
            centerCreateBookPane.add(entryTitle, 1, 1);

            centerCreateBookPane.add(new Label("Entrez votre matricule"), 1, 2);
            TextField matricule = new TextField();
            centerCreateBookPane.add(matricule, 1, 3);


            centerCreateBookPane.add(new Label("Entrez le résumé du livre"), 1, 4);
            TextArea resume = new TextArea();
            centerCreateBookPane.add(resume, 1, 5);

            Button valid = new Button("Valider");
            valid.setOnAction(action -> presenter.createBook(args, entryTitle.getText(), resume.getText(), matricule.getText().strip()));
            centerCreateBookPane.add(valid, 1, 6);

        }

        root.setTop(topPane);
        root.setLeft(leftPane);
        root.setCenter(centerCreateBookPane);
    }
}
