package com.suehay.audscifx.utils.classes;

import com.suehay.audscifx.controller.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CustomDialog extends Stage {
    // ...
    private final DialogController controller;

    public CustomDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyDialog.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = new DialogController(this);
        loader.setController(controller);

        Scene scene = new Scene(root);
        setScene(scene);
    }

    public Optional<Boolean> showDialog(String type, String message) {
        // ...
        switch (type) {
            case "ERROR":
                setTitle("Error");
                controller.getRoot().setStyle("-fx-background-color: #ffb9b9;");
                break;
            case "CONFIRMATION":
                setTitle("Confirmation");
                controller.getRoot().setStyle("-fx-background-color: #b9f2ff;");
                break;
            case "SUCCESS":
                setTitle("Success");
                controller.getRoot().setStyle("-fx-background-color: #b9ffb9;");
                break;
            case "WARNING":
                setTitle("Warning");
                controller.getRoot().setStyle("-fx-background-color: #fffcb9;");
                break;
        }
        controller.setMessage(new Label(message));
        showAndWait();
        return controller.getResult();
    }

    public Boolean getResult() {
        return controller.getResult().orElse(false);
    }
}