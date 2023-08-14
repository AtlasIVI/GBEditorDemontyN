package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenter.ManageLinkPresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IManageLinkView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.viewmodels.LinkVM;
import org.helmo.gbeditor.viewmodels.PageVM;

import java.util.List;

public class ManageLinkView extends ViewJavaFx implements IManageLinkView {
    private BorderPane root;
    private ManageLinkPresenter presenter;

    //<editor-fold defaultstate="collapsed" desc="attribut pour le tableau des liens crée">
    private TableView<LinkVM> tableOfLink;
    private List<LinkVM> linkVMList;
    private TableColumn<LinkVM, String> currentPageCol;
    private TableColumn<LinkVM, String> nextPageCol;
    private TableColumn<LinkVM, String> linkContentCol;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="attribut pour le tableau">
    private TableView<PageVM> table;
    private List<PageVM> pageVMList;

    private TableColumn<PageVM, String> textCol;
    private TableColumn<PageVM, String> numberCol;

    //</editor-fold>


    public ManageLinkView(ManageLinkPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        root = new BorderPane();
    }

    @Override
    public ViewType getViewType() {
        return ViewType.MANAGE_LINK;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public void beforeShow(NavigationArg args) {
        pageVMList = presenter.getAllPagesExceptArg(args.getBook(), args.getPage());
        linkVMList = presenter.getAllLinkOfPage(args.getBook(), args.getPage());
        initLeftPane(args);
        initTopPane();
        initTableOfLink(pageVMList, args);
        initTableOfCreateLink(args);
        initRigthPane(args);

    }

    private void initTableOfCreateLink( NavigationArg args) {


        var botPane = new GridPane();
        botPane.setAlignment(Pos.CENTER);
        tableOfLink = new TableView<>();
        currentPageCol = new TableColumn<>("Page actuelle");
        currentPageCol.setPrefWidth(300);
        nextPageCol = new TableColumn<>("Page suivante");
        nextPageCol.setPrefWidth(300);
        linkContentCol = new TableColumn<>("Texte du lien");
        linkContentCol.setPrefWidth(300);
        tableOfLink.getColumns().addAll(currentPageCol, nextPageCol, linkContentCol);

        currentPageCol.setCellValueFactory(new PropertyValueFactory<>("idCurrentPage"));
        nextPageCol.setCellValueFactory(new PropertyValueFactory<>("idNextPage"));
        linkContentCol.setCellValueFactory(new PropertyValueFactory<>("linkContent"));
        tableOfLink.setItems(FXCollections.observableArrayList(linkVMList));

        tableOfLink.setOnMouseClicked(event -> {
            var linkVM = tableOfLink.getSelectionModel().getSelectedItem();
            if (linkVM != null) {
                var deleteBtn = new Button("Delete link");
                botPane.add(deleteBtn, 1, 1);
                deleteBtn.setOnAction(action -> {
                   if(throwAlertWithConfirm("Voulez vous vraiment supprimer ce lien ?")){;
                    linkVMList.remove(linkVM);
                    presenter.updateAllLink(linkVMList, args);
                    tableOfLink.setItems(FXCollections.observableArrayList(linkVMList));
                    tableOfLink.refresh();
                    pageVMList.clear();
                    pageVMList.addAll(presenter.getAllPagesExceptArg(args.getBook(), args.getPage()));
                    table.setItems(FXCollections.observableArrayList(pageVMList));
                    table.refresh();
                   }

                });


            }
        });

        //ajouter un padding all de 15

        botPane.add(new Label("Lien existant entre cette page et les autres"),0,0);
        botPane.add(tableOfLink, 0, 1);
        root.setBottom(botPane);
    }
    private void initRigthPane(NavigationArg args) {
        var rightPane = new GridPane();
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(10));
        table.setOnMouseClicked(event -> {
            var pageVM = table.getSelectionModel().getSelectedItem();
            if (pageVM != null) {
                var label = new Label();
                label.setText("Lien entre la page : " + args.getPage().getNumberPage() + " et la page : " + pageVM.getNumberPage());
                var textEntry = new TextField();
                var addLinkBtn = new Button("Ajouter le lien");
                rightPane.add(label, 0, 0);
                rightPane.add(textEntry, 0, 1);
                rightPane.add(addLinkBtn, 0, 2);

                addLinkBtn.setOnAction(action -> {
                    try {
                        presenter.addNewLink(args.getPage(), pageVM, textEntry.getText(),args.getBook());
                        pageVMList.clear();
                        pageVMList.addAll(presenter.getAllPagesExceptArg(args.getBook(), args.getPage()));
                        table.setItems(FXCollections.observableArrayList(pageVMList));
                        table.refresh();
                        linkVMList.clear();
                        linkVMList.addAll(presenter.getAllLinkOfPage(args.getBook(), args.getPage()));
                        tableOfLink.setItems(FXCollections.observableArrayList(linkVMList));
                        tableOfLink.refresh();
                    } catch (Exception e) {
                        throwAlert("Impossible d'ajouter le lien");
                    }

                });


            }

            root.setRight(rightPane);

        });
    }

    private void initTableOfLink(List<PageVM> pageVMList, NavigationArg args) {
        table = new TableView<>();
        textCol = new TableColumn<>("Texte");
        textCol.setPrefWidth(300);
        numberCol = new TableColumn<>("Numéro de page");
        numberCol.setPrefWidth(300);
        table.getColumns().addAll(textCol, numberCol);

        //ajouter un padding all de 15
        table.setPadding(new Insets(15));


        textCol.setCellValueFactory(new PropertyValueFactory<>("textPage"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("numberPage"));
        table.setItems(FXCollections.observableArrayList(pageVMList));


        root.setCenter(table);
    }

    private void initTopPane() {
        //TItre de la page avec des padding de 10
        var topPane = new GridPane();
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(10));
        var title = new Label("Gestion des liens");
        title.setPadding(new Insets(10));
        topPane.add(title, 0, 0);
        root.setTop(topPane);


    }

    private void initLeftPane(NavigationArg args) {
        var leftPane = new GridPane();
        leftPane.setAlignment(Pos.CENTER);
        var autorLabel = new Label("Auteur : ");
        var allPageBtn = new Button("Toutes les pages");
        allPageBtn.setOnAction(action -> presenter.onAllPageBtnClicked(args));

        leftPane.add(autorLabel, 0, 0);
        leftPane.add(allPageBtn, 0, 1);
        root.setLeft(leftPane);
    }
}
