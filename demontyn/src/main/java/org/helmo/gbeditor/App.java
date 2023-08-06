/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.helmo.gbeditor;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.helmo.gbeditor.infrastructures.DbInfrastructure;
import org.helmo.gbeditor.presenter.*;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.views.*;

public class App extends Application {


    public final static String DBURL = "jdbc:mysql://asterix-intra.cg.helmo.be:13306/Q210037";
    public final static String DBUSER = "Q210037";
    public final static String DBPASS = "0037";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {



        Repository repo = new DbInfrastructure(DRIVER, DBURL, DBUSER, DBPASS);

        MainWindow view = new MainWindow(
                new LoginView(new LoginPresenter(repo)),
                new AllBooksView(new AllBooksPresenter(repo)),
                new CreateBookView(new CreateBookPresenter(repo)),
                new EditBookView(new EditBookPresenter(repo)),
                new AllPageView(new AllPagePresenter(repo))
        );



        initStage(primaryStage);


        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }


    private void initStage(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setWidth(1500);
        primaryStage.setHeight(800);

    }
}
