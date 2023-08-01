package org.helmo.gbeditor.views;

import javafx.stage.Stage;
import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenters.CreateBookPresenter;
import org.helmo.gbeditor.presenters.ILoginPresenter;
import org.helmo.gbeditor.presenters.MainPresenter;
import org.helmo.gbeditor.repository.RepositoryInterface;

public class ViewsNavigator {
    private final RepositoryInterface repository;
    private Stage primaryStage;
    private LoginDisplay loginDisplay;
    private MainView mainView;
    private CreateBookDisplay createBookDisplay;
    private EditBookDisplay editBookDisplay;

    public ViewsNavigator(Stage primaryStage, RepositoryInterface repository) {

        this.primaryStage = primaryStage;
        this.repository = repository;
    }

    public void switchToHome() {
        if (this.mainView == null) {
            this.mainView = new MainView();
            new MainPresenter(this.mainView, this);
        }
        this.mainView.start(primaryStage);
    }

    public void switchToLoginDisplay() {
        if (this.loginDisplay == null) {
            this.loginDisplay = new LoginDisplay();
            new ILoginPresenter(this.loginDisplay, this, repository);
        }
        this.loginDisplay.start(primaryStage);
    }

    public void switchToCreateBookDisplay(Autor autor) {
        if (this.createBookDisplay == null) {
            this.createBookDisplay = new CreateBookDisplay();
            new CreateBookPresenter(this.createBookDisplay, this, repository, autor);
        }
        this.createBookDisplay.start(primaryStage);

    }

    public void switchToEditBookDisplay(String bookTitle, String bookResume, String isbn, Autor autor) {
        if (this.editBookDisplay == null) {
            this.editBookDisplay = new EditBookDisplay();
            new CreateBookPresenter(this.editBookDisplay, this, repository, autor, bookTitle, bookResume, isbn);
        }
        this.editBookDisplay.start(primaryStage);

    }

}
