package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    public AnchorPane root;
    @FXML
    private Label messageLabel;
    @FXML
    private JFXButton okButton;
    @FXML
    private JFXButton cancelButton;
    @Getter
    private Optional<Boolean> result = Optional.empty();
    private final Stage stage;

    public DialogController(Stage stage) {
        this.stage = stage;
    }

    public void setOnOkAction(EventHandler<ActionEvent> handler) {
        okButton.setOnAction(event -> {
            handler.handle(event);
            result = Optional.of(true);
            stage.close();
        });
    }

    public void setOnCancelAction(EventHandler<ActionEvent> handler) {
        cancelButton.setOnAction(event -> {
            handler.handle(event);
            result = Optional.of(false);
            stage.close();
        });
    }
}