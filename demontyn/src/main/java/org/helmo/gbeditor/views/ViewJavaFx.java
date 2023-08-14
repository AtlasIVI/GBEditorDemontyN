package org.helmo.gbeditor.views;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.views.event.IGoToEvent;

import java.util.Optional;

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

    public boolean throwAlertWithConfirm(String content) {
        Alert confirmBox = new Alert(Alert.AlertType.CONFIRMATION);
        confirmBox.setTitle("SUPPRESSION");
        confirmBox.setHeaderText("Confirmation");
        confirmBox.setContentText(content);
        Optional<ButtonType> option = confirmBox.showAndWait();

        return option.get() == ButtonType.OK;
    }

}
