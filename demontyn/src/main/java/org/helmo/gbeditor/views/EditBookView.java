package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenter.EditBookPresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IEditBookView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.viewmodels.AutorVM;
import org.helmo.gbeditor.viewmodels.BookVM;

public class EditBookView extends ViewJavaFx implements IEditBookView {
    private BorderPane root;
    private EditBookPresenter presenter;

    private BookVM bookVM;
    private AutorVM autorVM;

    public EditBookView(EditBookPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.EDIT_BOOK;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void beforeShow(NavigationArg args) {
        this.bookVM = new BookVM(args.getBook());
        this.autorVM = new AutorVM(args.getAutor());

        GridPane leftPane = new GridPane();
        {
            leftPane.setAlignment(Pos.TOP_LEFT);
            leftPane.add(new Label("Autor : " + autorVM.getCompletName()), 0, 0);
        }
        GridPane centerPane = new GridPane();
        {
            centerPane.setAlignment(Pos.CENTER);

            centerPane.add(new Label("Donnez un titre à votre livre"), 1, 0);
            TextField entryTitle = new TextField();
            entryTitle.setText(bookVM.getTitle());
            centerPane.add(entryTitle, 1, 1);

            centerPane.add(new Label("Entrez le résumé du livre"), 1, 4);
            TextArea resume = new TextArea();
            resume.setWrapText(true);


            resume.setText(bookVM.getResume());
            centerPane.add(resume, 1, 5);

            Button valid = new Button("Valider");
            valid.setOnAction(action -> presenter.editBook(entryTitle.getText(),resume.getText(),args));
            centerPane.add(valid, 1, 6);


        }

        var allBookBtn = new Button("Voir tous les livres");
        allBookBtn.setOnAction(action -> this.goTo(ViewType.SEE_ALL_BOOKS, new NavigationArg(args.getAutor(), args.getBook(), null)));


        root.setCenter(centerPane);
        root.setLeft(leftPane);
    }
}
