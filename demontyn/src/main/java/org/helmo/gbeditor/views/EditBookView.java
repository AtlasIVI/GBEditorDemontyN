package org.helmo.gbeditor.views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.helmo.gbeditor.presenter.EditBookPresenter;
import org.helmo.gbeditor.presenter.LoginPresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IEditBookView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;

public class EditBookView extends ViewJavaFx implements IEditBookView {
    private BorderPane root;
    private EditBookPresenter presenter;
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

        var allBookBtn = new Button("Voir tous les livres");
        allBookBtn.setOnAction(action -> this.goTo(ViewType.SEE_ALL_BOOKS,new NavigationArg(null)));
        root.setCenter(allBookBtn);
    }
}
