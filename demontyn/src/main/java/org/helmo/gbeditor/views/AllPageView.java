package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.helmo.gbeditor.presenter.AllPagePresenter;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IAllPageView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.viewmodels.PageVM;


import java.util.List;

public class AllPageView extends ViewJavaFx implements IAllPageView {
    private BorderPane root;
    private AllPagePresenter presenter;

    private GridPane centerCreatePagePane;

    //<editor-fold defaultstate="collapsed" desc="attribut pour le tableau">
    private TableView<PageVM> table;
    private TableColumn<PageVM, String> textCol;
    private TableColumn<PageVM, String> numberCol;

    //</editor-fold>


    private GridPane topPane = new GridPane();{
        topPane.setAlignment(Pos.TOP_CENTER);
        topPane.add(new javafx.scene.control.Label("Create Page"), 0, 0);
    }
    private GridPane centerPane = new GridPane();{
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
        var pageVMList = presenter.getAllPages(args.getBook());

        initTable(pageVMList,args);
        initCreatePageForm(args);
        root.setRight(centerCreatePagePane);
        root.setTop(topPane);
        root.setCenter(table);
    }

    private void initCreatePageForm(NavigationArg args) {
        centerCreatePagePane = new GridPane();
        //Padding and gap
        centerCreatePagePane.setHgap(10);
        centerCreatePagePane.setVgap(10);
        centerCreatePagePane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));


        centerCreatePagePane.setAlignment(Pos.CENTER);

        centerCreatePagePane.add(new Label("Donnez un titre à votre livre"), 1, 0);
        var entryTitle = new javafx.scene.control.TextField();
        centerCreatePagePane.add(entryTitle, 1, 1);

        centerCreatePagePane.add(new Label("Entrez votre matricule"), 1, 2);
        var matricule = new javafx.scene.control.TextField();
        centerCreatePagePane.add(matricule, 1, 3);

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
}
