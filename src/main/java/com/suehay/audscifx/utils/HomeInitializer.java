package com.suehay.audscifx.utils;

import com.suehay.audscifx.config.GuideConfig;
import com.suehay.audscifx.controller.HomeController;
import com.suehay.audscifx.model.AreaEntity;
import com.suehay.audscifx.model.ComponentEntity;
import com.suehay.audscifx.model.EmployeeEntity;
import com.suehay.audscifx.model.enums.Routes;
import com.suehay.audscifx.services.EmployeeService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URISyntaxException;

public class HomeInitializer {
    public static void initCellsFactories(FXMLLoader fxmlLoader) {
        var controller = ((HomeController) fxmlLoader.getController());
        controller.componentListView.cellFactoryProperty().set(new ComponentEntityCellFactory());
        controller.areasListView.setCellFactory(new AreaEntityCellFactory());
        controller.employeeListView.setCellFactory(new EmployeeEntityCellFactory());
        controller.employeeListView.getItems().addAll(EmployeeService.findAll());
    }

    public static void init() {
        var guideConfig = new GuideConfig();
        try {
            guideConfig.saveTemplates();
            guideConfig.chargeTemplates(Routes.TEST_TEMPLATES);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends HomeController> void initKeyCombination(Scene scene, T controller) {
        controller.initKeyCombinations(scene);
    }

    private static class ComponentEntityCellFactory implements javafx.util.Callback<javafx.scene.control.ListView<com.suehay.audscifx.model.ComponentEntity>, javafx.scene.control.ListCell<com.suehay.audscifx.model.ComponentEntity>> {
        @Override
        public ListCell<ComponentEntity> call(ListView<ComponentEntity> param) {
            return new ListCell<>() {
                @Override
                protected void updateItem(ComponentEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getLabel());
                    }
                }
            };
        }
    }

    private static class AreaEntityCellFactory implements javafx.util.Callback<ListView<AreaEntity>, ListCell<AreaEntity>> {
        @Override
        public ListCell<AreaEntity> call(ListView<AreaEntity> param) {
            return new ListCell<>() {
                @Override
                protected void updateItem(AreaEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getAreaName());
                    }
                }
            };
        }
    }

    private static class EmployeeEntityCellFactory implements javafx.util.Callback<ListView<EmployeeEntity>, ListCell<EmployeeEntity>> {
        @Override
        public ListCell<EmployeeEntity> call(ListView<EmployeeEntity> param) {
            return new ListCell<>() {
                @Override
                protected void updateItem(EmployeeEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getEmployeeName());
                    }
                }
            };
        }
    }

}