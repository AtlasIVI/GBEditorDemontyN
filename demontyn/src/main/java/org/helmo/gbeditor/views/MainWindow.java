package org.helmo.gbeditor.views;

import javafx.scene.Scene;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;

import java.util.HashMap;
import java.util.Map;

public class MainWindow {

    private Scene scene;


    private final Map<ViewType, ViewJavaFx> allViews;


    public MainWindow(ViewJavaFx... views) {
        this.allViews = new HashMap<>();

        for (var view : views) {
            this.allViews.put(view.getViewType(), view);
            view.setConsumer(this::goTo);
        }

        goTo(ViewType.LOGIN, null);
    }


    public Scene getScene() {
        return scene;
    }


    public void goTo(ViewType type, NavigationArg args) {
        var view = allViews.get(type);
        view.beforeShow(args);

        if (scene == null) {
            scene = new Scene(view.getRoot());
        } else {
            scene.setRoot(view.getRoot());
        }
    }
}
