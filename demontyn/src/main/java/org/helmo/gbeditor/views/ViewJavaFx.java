package org.helmo.gbeditor.views;

import javafx.scene.control.Alert;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.views.event.IGoToEvent;

public abstract class ViewJavaFx implements IView {


    private IGoToEvent goTo;
    public void setConsumer(IGoToEvent consumer) {
        this.goTo = consumer;
    }

    public void goTo(ViewType type, NavigationArg args) {
        if(goTo == null)
            throw  new RuntimeException("GotoEvent is null maybe you forgot to set it");

        goTo.gotoView(type, args);
    }



    public void throwAlert(String message) {
        Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
        messageAlert.setContentText(message);
        messageAlert.showAndWait();
    }
}
