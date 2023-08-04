package org.helmo.gbeditor.views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenter.LoginPresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ILoginView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;

public class LoginView extends ViewJavaFx implements ILoginView {

    private BorderPane root;

    private LoginPresenter presenter;

    GridPane centerPane = new GridPane();{
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
        goToLogin.setOnAction(action -> presenter.login(entryFirstName.getText().trim(),entryName.getText().trim()));
        centerPane.add(goToLogin, 0, 4);
    }

    public LoginView(LoginPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.LOGIN;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void beforeShow(NavigationArg args) {
        root.setCenter(centerPane);
    }



}
