package com.suehay.audscifx;

import com.suehay.audscifx.config.GuideConfig;
import com.suehay.audscifx.model.AreaEntity;
import com.suehay.audscifx.repository.AreaRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
        stage.setTitle("AUDSCI");
        stage.setScene(scene);
        stage.show();
    }
    public void init(){
        GuideConfig guideConfig=new GuideConfig();
        try {
            guideConfig.saveTestTemplates();
            guideConfig.chargeTestTemplates();
            AreaRepository areaRepository=new AreaRepository();
            areaRepository.save(new AreaEntity(3,"Area 3"));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}