package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class DialogController {
    @FXML
    public Label message;
    @FXML
    public AnchorPane root=new AnchorPane();
    @FXML
    private JFXButton okButton;
    @FXML
    private JFXButton cancelButton;
    private Optional<Boolean> result;

    public void setOnOkAction(MouseEvent mouseEvent) {
        result = Optional.of(true);
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void setOnCancelAction(MouseEvent mouseEvent) {
        result = Optional.of(false);
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}