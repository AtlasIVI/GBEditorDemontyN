package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenter.AllPagePresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IAllPageView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.viewmodels.BookVM;
import org.helmo.gbeditor.viewmodels.PageVM;


import java.util.List;

public class AllPageView extends ViewJavaFx implements IAllPageView {
    private BorderPane root;
    private AllPagePresenter presenter;

    private GridPane rigthCreatePagePane;

    //<editor-fold defaultstate="collapsed" desc="attribut pour le tableau">
    private TableView<PageVM> table;
    private List<PageVM> pageVMList;
    private TableColumn<PageVM, String> textCol;
    private TableColumn<PageVM, String> numberCol;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="attribut pour le formulaire de création et de modification">
    private Button updatePageBtn;
    private Label labContent;
    private Button manageLinkBtn;
    private TextArea entryContent;

    //</editor-fold>
    private GridPane topPane;
    private GridPane leftPane;

    private GridPane centerPane = new GridPane();


    {
        centerPane.setAlignment(Pos.CENTER);
        centerPane.add(new Label("Ajouter le texte de votre page"),0,0);
        var textEntry = new TextArea();
        centerPane.add(textEntry,0,1);
    }



    public AllPageView(AllPagePresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();
    }
    public ViewType getViewType() {
        return ViewType.All_PAGES;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void beforeShow(NavigationArg args) {

        pageVMList = presenter.getAllPages(args.getBook());
        updatePageBtn = new Button("Update");
        manageLinkBtn = new Button("Manage Links");
        labContent = new Label("Content");
        entryContent = new TextArea();
        initLeftPane(args);
        initTable(pageVMList,args);
        initCreatePageForm(args);
        root.setLeft(leftPane);
        root.setRight(rigthCreatePagePane);
        root.setTop(topPane);
        root.setCenter(table);
    }

    private void initLeftPane(NavigationArg args) {


        {
            leftPane = new GridPane();
            leftPane.setAlignment(Pos.TOP_CENTER);
            leftPane.setHgap(10);
            leftPane.setVgap(10);
            leftPane.setPadding(new Insets(10, 10, 10, 10));
            var backBtn = new Button("Home");
            backBtn.setOnAction(event -> this.goTo(ViewType.SEE_ALL_BOOKS, args));
            leftPane.add(backBtn, 0, 0);
            leftPane.add(new Label("Autor : " + args.getAutor().getCompletName()), 0, 1);
            leftPane.add(new Label("Book : " + args.getBook().getTitle()), 0, 2);
            var addPageBtn = new Button("Add Page");
            addPageBtn.setOnAction(event -> initCreatePage(args));
            leftPane.add(addPageBtn, 0, 4);

        }

    }

    private void initCreatePageForm(NavigationArg args) {
        rigthCreatePagePane = new GridPane();


        updatePageBtn.setVisible(false);
        manageLinkBtn.setVisible(false);
        rigthCreatePagePane.setHgap(10);
        rigthCreatePagePane.setVgap(10);
        rigthCreatePagePane.setPadding(new Insets(10, 10, 10, 10));
        rigthCreatePagePane.setAlignment(Pos.CENTER);
        labContent.setVisible(false);
        rigthCreatePagePane.add(labContent, 1, 2);
        entryContent.setVisible(false);
        rigthCreatePagePane.add(entryContent, 1, 3);
        rigthCreatePagePane.add(updatePageBtn, 1, 5);
        rigthCreatePagePane.add(manageLinkBtn, 1, 6);


        table.setOnMouseClicked(event -> {
            var pageVM = table.getSelectionModel().getSelectedItem();
            if (pageVM != null) {
                labContent.setVisible(true);
                entryContent.setVisible(true);
                updatePageBtn.setVisible(true);
                updatePageBtn.setText("Update Page");
                manageLinkBtn.setVisible(true);
                entryContent.setText(pageVM.getTextPage());
                updatePageBtn.setOnAction(event1 -> {
                    presenter.updatePage(pageVM,entryContent.getText());
                    pageVM.setTextPage(entryContent.getText());
                    table.refresh();
                });


                //manageLinkBtn.setOnAction(action -> this.goTo(ViewType.All_LINKS, new NavigationArg(args.getBook(), page, null)));
            } else {
                labContent.setVisible(false);
                entryContent.setVisible(false);
            }
        });

    }

    private void initTable(List<PageVM> pageVMList, NavigationArg args) {
        table = new TableView<>();
        textCol = new TableColumn<>("Texte");
        textCol.setPrefWidth(300);
        numberCol = new TableColumn<>("Numéro de page");
        numberCol.setPrefWidth(300);
        table.getColumns().addAll(textCol, numberCol);

        textCol.setCellValueFactory(new PropertyValueFactory<>("textPage"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("numberPage"));
        table.setItems(FXCollections.observableArrayList(pageVMList));
    }

    public void initCreatePage(NavigationArg args){
        entryContent.setText("");
        entryContent.setVisible(true);
        updatePageBtn.setVisible(true);
        updatePageBtn.setText("Create Page");
        updatePageBtn.setOnAction(event -> {
            presenter.createPage(entryContent.getText());
            var page = presenter.getLastPage();
            pageVMList.add(page);
            table.setItems(FXCollections.observableArrayList(pageVMList));
        });


    }

}
