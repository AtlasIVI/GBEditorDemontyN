package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
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
import javafx.scene.layout.HBox;
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

    //<editor-fold defaultstate="collapsed" desc="attribut pour le tableau">
    private TableView<BookVM> table;
    private TableColumn<BookVM, String> titleCol;
    private TableColumn<BookVM, String> isbnCol;
    private TableColumn<BookVM, String> autorCol;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Menu latéral du livre sélectionné">
    private Label titleLabel = new Label();
    private Label resumeLabel = new Label();
    private Button publishBtn = new Button();
    private Label isbnLabel = new Label();
    private Label nbrPage = new Label();
    private Button editBtn = new Button("Edit");
    private Button deleteBtn = new Button("Delete");
    private Button addPageBtn = new Button("All Pages");
    //</editor-fold>
    private GridPane topPane = new GridPane();{
        topPane.setAlignment(Pos.TOP_CENTER);
        topPane.add(new Label("All books"), 0, 0);
    }

    private GridPane leftTopPane = new GridPane();{
        leftTopPane.setAlignment(Pos.TOP_LEFT);
        leftTopPane.setPadding(new Insets(10, 10, 10, 10));
        leftTopPane.add(autorLabel, 0, 0);


    }

    GridPane rightPane = new GridPane();{
        rightPane.setAlignment(Pos.CENTER_LEFT);
        rightPane.setPadding(new Insets(10, 10, 10, 10));

        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(200);
        resumeLabel.setWrapText(true);
        resumeLabel.setMaxWidth(200);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(5);
        buttonBox.getChildren().addAll(editBtn, deleteBtn, addPageBtn);
        rightPane.add(titleLabel, 0, 0);
        rightPane.add(resumeLabel, 0, 1);
        rightPane.add(isbnLabel, 0, 2);
        rightPane.add(nbrPage,0, 3);
        rightPane.add(buttonBox, 0, 4);
        rightPane.add(publishBtn,1,0);
        rightPane.setVisible(false);


    }


    public AllBooksView(AllBooksPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();
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
        initTab(args);

        GridPane bottomPane = new GridPane();
        {
            bottomPane.setAlignment(Pos.TOP_RIGHT);
            bottomPane.setPadding(new Insets(10, 10, 10, 10));
            var createBookButton = new Button("Create book");
            createBookButton.setOnAction(action -> this.goTo(ViewType.CREATE_BOOK, args));
            bottomPane.add(createBookButton, 0, 0);
        }
        root.setLeft(leftTopPane);
        root.setTop(topPane);
        root.setRight(rightPane);
        root.setBottom(bottomPane);
        root.setCenter(table);


    }
    //<editor-fold defaultstate="collapsed" desc="Tableau">

    /**
     * Initialise le tableau
     * @param args les arguments de navigation
     */
    private void initTab(NavigationArg args) {
        table = new TableView<>();
        titleCol = new TableColumn<>("Title");
        titleCol.setPrefWidth(300);
        isbnCol = new TableColumn<>("ISBN");
        isbnCol.setPrefWidth(300);
        autorCol = new TableColumn<>("Author");
        autorCol.setPrefWidth(300);
        table.getColumns().addAll(titleCol, isbnCol, autorCol);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        table.setItems(FXCollections.observableArrayList(booksVM));

        table.setOnMouseClicked(event -> {
            var book = table.getSelectionModel().getSelectedItem();

            if (book != null) {
                book.setPages(presenter.getPages(args, book.getBook()));
                if(book.getPages() != null){
                    if(presenter.isBookPublished(book.getBook())){
                        publishBtn.setText("Unpublish");
                        publishBtn.setOnMouseClicked(event1 -> {
                            presenter.unPublishBook(args, book.getBook());
                        });
                        editBtn.setVisible(false);
                        deleteBtn.setVisible(false);
                        addPageBtn.setVisible(false);
                    }
                    else  {
                        publishBtn.setText("Publish");
                        publishBtn.setOnMouseClicked(event1 -> {
                            presenter.publishBook(args, book.getBook());
                        });
                        editBtn.setVisible(true);
                        deleteBtn.setVisible(true);
                        addPageBtn.setVisible(true);
                    }
                }

                rightPane.setVisible(true);
                titleLabel.setText("Title : " + book.getTitle());
                resumeLabel.setText("Resume : " + book.getResume());
                isbnLabel.setText("ISBN : " + book.getIsbn());
                editBtn.setOnAction(action -> this.goTo(ViewType.EDIT_BOOK, new NavigationArg(args.getAutor(), book.getBook(), null)));
                addPageBtn.setOnAction(action -> this.goTo(ViewType.All_PAGES, new NavigationArg(args.getAutor(), book.getBook(), null)));
                deleteBtn.setOnAction(action -> {
                    presenter.deleteBook(args, book.getBook());
                });


            } else {
                titleLabel.setText("");
                resumeLabel.setText("");
                isbnLabel.setText("");
            }
        });
    }
    //</editor-fold>


}
