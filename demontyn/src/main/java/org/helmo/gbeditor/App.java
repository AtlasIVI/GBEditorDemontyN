/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.helmo.gbeditor;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.helmo.gbeditor.infrastructures.DbInfrastructure;
import org.helmo.gbeditor.presenters.Presenter;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.views.MainView;

public class App extends Application {

    public final static String DBURL = "jdbc:mysql://asterix-intra.cg.helmo.be:13306/Q210037";
    public final static String DBUSER = "Q210037";
    public final static String DBPASS = "0037";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        RepositoryInterface repository = new DbInfrastructure(DRIVER, DBURL, DBUSER, DBPASS);
        MainView mainView = new MainView();

        Presenter p = new Presenter(mainView,repository);

        Parent root = mainView.getRoot();
        Scene scene = new Scene(root, 1200, 700);

        primaryStage.setTitle("GameBook");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
