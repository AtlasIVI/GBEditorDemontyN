package org.helmo.gbeditor.presenters.interfaceview;

import javafx.stage.Stage;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.views.interfacepresenter.ISeeAllBooksPresenter;

import java.util.List;

public interface ISeeAllBooksDisplay {

    void setSeeAllBooksPresenterInterface(ISeeAllBooksPresenter seeAllBooksPresenter);

    void start(Stage primaryStage);

    void displayError(String message);

    void displayBooks(List<Book> books);


}
