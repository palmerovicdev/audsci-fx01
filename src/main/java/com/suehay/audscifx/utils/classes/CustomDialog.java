package com.suehay.audscifx.utils.classes;

import com.suehay.audscifx.HomeApplication;
import com.suehay.audscifx.controller.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomDialog extends Stage {
    // ...
    private final DialogController controller;

    public CustomDialog() {
        FXMLLoader loader = new FXMLLoader(HomeApplication.class.getResource("custom-dialog.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
    }

    public void showDialog(String type, String message) {
        switch (type) {
            case "ERROR":
                setTitle("Error");
                controller.getRoot().setStyle("-fx-background-color: rgba(255,230,230,0.80);");
                break;
            case "CONFIRMATION":
                setTitle("Confirmation");
                controller.getRoot().setStyle("-fx-background-color: rgba(232,248,255,0.70);");
                break;
            case "SUCCESS":
                setTitle("Success");
                controller.getRoot().setStyle("-fx-background-color: rgba(222,255,222,0.8);");
                break;
            case "WARNING":
                setTitle("Warning");
                controller.getRoot().setStyle("-fx-background-color: rgba(255,253,228,0.8);");
                break;
        }
        controller.getMessage().setText(message);
        showAndWait();
    }

    public Boolean getResult() {
        return controller.getResult().orElse(false);
    }
}