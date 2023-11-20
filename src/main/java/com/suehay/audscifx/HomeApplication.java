package com.suehay.audscifx;

import com.suehay.audscifx.config.GuideConfig;
import com.suehay.audscifx.controller.HomeController;
import com.suehay.audscifx.model.AreaEntity;
import com.suehay.audscifx.model.ComponentEntity;
import com.suehay.audscifx.model.EmployeeEntity;
import com.suehay.audscifx.repository.AreaRepository;
import com.suehay.audscifx.services.EmployeeService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ListSelectionView;

import java.io.IOException;
import java.net.URISyntaxException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home-view.fxml"));
        var parent=fxmlLoader.load();
        initCellsFactories(fxmlLoader);
        init();
        Scene scene = new Scene(fxmlLoader.getRoot(), 1080, 700);
        stage.setTitle("AUDSCI");
        stage.setScene(scene);
        stage.show();
    }

    private static void initCellsFactories(FXMLLoader fxmlLoader) {
        var controller = ((HomeController) fxmlLoader.getController());
        controller.componentsListView.cellFactoryProperty().set(new ComponentEntityCellFactory());
        controller.areasListView.setCellFactory(new AreaEntityCellFactory());
        controller.employeeListView.setCellFactory(new EmployeeEntityCellFactory());
        controller.employeeListView.getItems().addAll(EmployeeService.findAll());
    }

    public void init(){
        GuideConfig guideConfig=new GuideConfig();
        try {
            guideConfig.saveTestTemplates();
            guideConfig.chargeTestTemplates();
            // init cells factories
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    // implement a cell factory for all the ListSelectionView<EmployeeEntity>


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
    private static class EmployeeEntityCheckBoxViewCellFactory implements javafx.util.Callback<CheckListView<EmployeeEntity>, CheckBoxListCell<EmployeeEntity>> {
        @Override
        public CheckBoxListCell<EmployeeEntity> call(CheckListView<EmployeeEntity> param) {
            return new CheckBoxListCell<>() {
                @Override
                public void updateItem(EmployeeEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getEmployeeName());
                    }
                }
            };
        }
    }

}