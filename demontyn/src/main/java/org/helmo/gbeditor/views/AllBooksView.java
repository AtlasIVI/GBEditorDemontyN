package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenter.AllBooksPresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IAllBooksView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.viewmodels.BookVM;

import java.util.List;

public class AllBooksView extends ViewJavaFx implements IAllBooksView {
    private BorderPane root;
    private AllBooksPresenter presenter;

    private Label autorLabel = new Label("");
    private List<BookVM> booksVM;
    private TableView<BookVM> table;
    private TableColumn<BookVM, String> titleCol;
    private TableColumn<BookVM, String> isbnCol;
    private TableColumn<BookVM, String> autorCol;


    private GridPane topPane = new GridPane();

    {
        topPane.setAlignment(Pos.TOP_CENTER);
        topPane.add(new Label("All books"), 0, 0);
    }

    private GridPane leftTopPane = new GridPane();

    {
        leftTopPane.setAlignment(Pos.TOP_LEFT);
        leftTopPane.setPadding(new Insets(10, 10, 10, 10));
        leftTopPane.add(autorLabel, 0, 0);
    }



    public AllBooksView(AllBooksPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();


        table = new TableView<>();
        titleCol = new TableColumn<>("Title");
        isbnCol = new TableColumn<>("ISBN");
        autorCol = new TableColumn<>("Author");
        table.getColumns().addAll(titleCol, isbnCol, autorCol);

    }

    @Override
    public ViewType getViewType() {
        return ViewType.SEE_ALL_BOOKS;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void beforeShow(NavigationArg args) {
        this.booksVM = presenter.getBooksData(args);
        this.autorLabel.setText("Autor : " + args.getAutor().getCompletName());

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));


        table.setItems(FXCollections.observableArrayList(booksVM));




         GridPane rigthTopPan = new GridPane();
        {
            rigthTopPan.setAlignment(Pos.TOP_RIGHT);
            rigthTopPan.setPadding(new Insets(10, 10, 10, 10));
            var createBookButton = new Button("Create book");
            createBookButton.setOnAction(action -> this.goTo(ViewType.CREATE_BOOK, args));
            rigthTopPan.add(createBookButton, 0, 0);
        }
        root.setLeft(leftTopPane);
        root.setTop(topPane);
        root.setRight(rigthTopPan);
        root.setCenter(table);


    }


}
