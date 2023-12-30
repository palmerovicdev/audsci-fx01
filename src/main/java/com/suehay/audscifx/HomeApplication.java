package com.suehay.audscifx;

import com.suehay.audscifx.utils.HomeInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

import static com.suehay.audscifx.utils.HomeInitializer.initCellsFactories;
import static com.suehay.audscifx.utils.HomeInitializer.initKeyCombination;

public class HomeApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home-view.fxml"));
        fxmlLoader.load();
        initCellsFactories(fxmlLoader);
        HomeInitializer.init();
        var scene = new Scene(fxmlLoader.getRoot(), 1080, 700);
        initKeyCombination(scene, fxmlLoader.getController());
        stage.setTitle("AUDSCI");
        stage.setScene(scene);
        stage.show();
    }
}