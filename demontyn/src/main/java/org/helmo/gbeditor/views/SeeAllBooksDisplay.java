package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.presenters.interfaceview.ILoginDisplay;
import org.helmo.gbeditor.presenters.interfaceview.ISeeAllBooksDisplay;
import org.helmo.gbeditor.views.interfacepresenter.ISeeAllBooksPresenter;

import java.text.BreakIterator;
import java.util.List;

public class SeeAllBooksDisplay implements ISeeAllBooksDisplay {


    private ISeeAllBooksPresenter seeAllBooksPresenter;
    private Autor autor;
    private Label lErrorMessage = new Label();
    private Label lAutorName = new Label("Autor :");


    private GridPane leftPane;
    {
        leftPane = new GridPane();
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.add(lAutorName, 0, 0);
        leftPane.add(lErrorMessage, 0, 1);
    }



    private GridPane topPane;
    {
        topPane = new GridPane();
        topPane.setAlignment(Pos.TOP_CENTER);
        var lMainTitle = new Label("Books display");
        topPane.add(lMainTitle,5,0);
    }
    private GridPane displayBooksPane;

    private BorderPane mainPane;
    {
        mainPane = new BorderPane();
        mainPane.setLeft(leftPane);
        mainPane.setTop(topPane);
        mainPane.setCenter(displayBooksPane);
    }


    @Override
    public void setSeeAllBooksPresenterInterface(ISeeAllBooksPresenter seeAllBooksPresenter) {
        this.seeAllBooksPresenter = seeAllBooksPresenter;
        this.lAutorName.setText("Autor : " + seeAllBooksPresenter.getAutorName());


    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(mainPane, 1200, 700));
        primaryStage.show();
    }

    @Override
    public void displayError(String message) {
        lErrorMessage.setText(message);
    }

    @Override
    public void displayBooks(List<Book> books) {
        displayBooksPane = new GridPane();
        displayBooksPane.setAlignment(Pos.CENTER);
        int i = 0;
        for (Book book : books) {
            displayBooksPane.add(new Label(book.getTitle()), 0, i);
            displayBooksPane.add(new Label(book.getResume()), 1, i);
            i++;
        }
        mainPane.setCenter(displayBooksPane);
    }

}
