package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.suehay.audscifx.config.EntityManagerProvider;
import com.suehay.audscifx.config.GuideConfig;
import com.suehay.audscifx.model.*;
import com.suehay.audscifx.model.common.Properties;
import com.suehay.audscifx.model.templates.ComponentTemplate;
import com.suehay.audscifx.model.templates.QuestionTemplate;
import com.suehay.audscifx.model.templates.RegulationTemplate;
import com.suehay.audscifx.model.templates.TestResult;
import com.suehay.audscifx.services.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController {
    private final GuideConfig guideConfig = new GuideConfig();
    @FXML
    public ChoiceBox<String> guideVersionChoiceBox;
    @FXML
    public JFXListView<CheckBoxModel> gestionYPrevencionListView = new JFXListView<>();
    @FXML
    public JFXListView<CheckBoxModel> ambienteDeControlListView = new JFXListView<>();
    @FXML
    public JFXListView<CheckBoxModel> actividadesDeControlListView = new JFXListView<>();
    @FXML
    public JFXListView<CheckBoxModel> informacionYComunicacionListView = new JFXListView<>();
    @FXML
    public JFXListView<CheckBoxModel> supervicionYMonitoreoListView = new JFXListView<>();
    @FXML
    public JFXListView<CheckBoxModel> ambienteDeControlListViewEvated;
    @FXML
    public JFXListView<CheckBoxModel> gestionYPrevencionListViewEvated;
    @FXML
    public JFXListView<CheckBoxModel> actividadesDeControlListViewEvated;
    @FXML
    public JFXListView<CheckBoxModel> informacionYComunicacionListViewEvated;
    @FXML
    public JFXListView<CheckBoxModel> supervicionYMonitoreoListViewEvated;
    @FXML
    public ListView<AreaEntity> areasListView;
    @FXML
    public ListView<EmployeeEntity> employeeListView;
    @FXML
    public BorderPane homePane;
    @FXML
    public VBox leftOptionsPane;
    @FXML
    public JFXButton enterpriseButton;
    @FXML
    public JFXButton testCreationButton;
    @FXML
    public JFXButton testEvaluateButton;
    @FXML
    public JFXButton reportGenerationButton;
    @FXML
    public JFXButton helpButton;
    @FXML
    public JFXButton configButton;
    @FXML
    public JFXButton homeButton;
    @FXML
    public AnchorPane homeView;
    @FXML
    public JFXListView<ComponentEntity> componentsListView;
    @FXML
    public BarChart<String, Integer> testResultChart;
    @FXML
    public AnchorPane enterpriseCreationView;
    @FXML
    public TextField areaNameTextField;
    @FXML
    public JFXButton areaAddButton;
    @FXML
    public JFXButton areaRemoveButton;
    @FXML
    public TextField employeeNameTextField;
    @FXML
    public TextField employeePositionTextField;
    @FXML
    public JFXButton employeeAddButton;
    @FXML
    public JFXButton employeeRemoveButton;
    @FXML
    public AnchorPane testCreationView;
    @FXML
    public TextField testCodeTextField;
    @FXML
    public DatePicker startTestDatePicker;
    @FXML
    public DatePicker endTestDatePicker;
    @FXML
    public AnchorPane componentEvaluationView;
    @FXML
    public AnchorPane printReportView;
    @FXML
    public AnchorPane helpView;
    @FXML
    public AnchorPane configurationView;
    @FXML
    public JFXButton updateDatabaseProperties;
    @FXML
    public JFXButton guideRechargeButton;
    @FXML
    public TextField databaseConfigTextField;
    @FXML
    public TextField userConfigTextField;
    @FXML
    public TextField passwordConfigTextField;
    @FXML
    public ListView<RegulationEntity> ambCntrlRegulationEntityListView;
    @FXML
    public TreeView<QuestionEntity> ambCntrlQuestionEntityTreeView;
    @FXML
    public ListView<RegulationEntity> gestPrevRegulationEntityListView;
    @FXML
    public TreeView<QuestionEntity> gestPrevQuestionEntityTreeView;
    public ListView<RegulationEntity> actvCntrlRegulationEntityListView;
    public TreeView<QuestionEntity> actvCntrlQuestionEntityTreeView;
    public ListView<RegulationEntity> infMonRegulationEntityListView;
    public TreeView<QuestionEntity> infMonQuestionEntityTreeView;
    public ListView<RegulationEntity> supMonRegulationEntityListView;
    public TreeView<QuestionEntity> supMonQuestionEntityTreeView;
    @FXML
    public JFXButton ambContSaveButton;
    @FXML
    public CheckBox ambContYesCheckBox;
    @FXML
    public CheckBox ambContNoCheckBox;
    @FXML
    public Label ambContTextLabel;
    @FXML
    public JFXTextArea ambContTextArea;
    @FXML
    public Label gestPrevTextLabel1;
    @FXML
    public JFXTextArea gestPrevTextArea1;
    @FXML
    public CheckBox gestPrevNoCheckBox1;
    @FXML
    public JFXButton gestPrevSaveButton1;
    @FXML
    public CheckBox gestPrevYesCheckBox1;
    @FXML
    public Label actContTextLabel11;
    @FXML
    public JFXTextArea actContTextArea11;
    @FXML
    public CheckBox actContNoCheckBox11;
    @FXML
    public JFXButton actContSaveButton11;
    @FXML
    public CheckBox actContYesCheckBox11;
    @FXML
    public JFXButton infMonSaveButton;
    @FXML
    public CheckBox infMonYesCheckBox;
    @FXML
    public CheckBox infMonNoCheckBox;
    @FXML
    public Label infMonTextLabel;
    @FXML
    public JFXTextArea infMonTextArea;
    @FXML
    public JFXButton supMonSaveButton;
    @FXML
    public CheckBox supMonYesCheckBox;
    @FXML
    public CheckBox supMonNoCheckBox;
    @FXML
    public Label supMonTextLabel;
    @FXML
    public JFXTextArea supMonTextArea;
    @FXML
    private AreaEntity latestArea;

    @FXML
    public void onEnterpriseButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, true, false, false, false, false, false);
        initEnterpriseCreationView();
    }

    void visibilityChange(boolean home, boolean enterpriseCreation, boolean testCreation, boolean testEvaluate, boolean reportGeneration, boolean help, boolean config) {
        homeView.setVisible(home);
        enterpriseCreationView.setVisible(enterpriseCreation);
        testCreationView.setVisible(testCreation);
        componentEvaluationView.setVisible(testEvaluate);
        printReportView.setVisible(reportGeneration);
        helpView.setVisible(help);
        configurationView.setVisible(config);
    }

    private void initEnterpriseCreationView() {
        areasListView.getItems().setAll(FXCollections.observableList(AreaService.findAll()));
        if (areasListView.getItems().isEmpty()) {
            areasListView.setPrefHeight(0);
            employeeListView.setPrefHeight(0);
            return;
        }

        // when an area is selected in the areasListView, the employeeListView should be updated with the employees of the selected area
        areasListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setAreasListViewSelectionModel();
        areasListView.getSelectionModel().select(0);
        latestArea = areasListView.getSelectionModel().getSelectedItem();
        employeeListView.getItems().clear();
        employeeListView.getItems().addAll(FXCollections.observableList(EmployeeService.findByAreaId(areasListView.getSelectionModel().getSelectedItem().getId())));
        employeeListView.setPrefHeight(Math.min((employeeListView.getItems().size() * 25), 280));
        areasListView.setPrefHeight(Math.min((areasListView.getItems().size() * 25), 280));
    }

    private void setAreasListViewSelectionModel() {
        areasListView.getSelectionModel().selectedItemProperty().addListener((observableValue, areaEntity, t1) -> {
            if (t1 != null) {
                Platform.runLater(() -> {
                    latestArea = areasListView.getSelectionModel().getSelectedItem();
                    employeeListView.getItems().clear();
                    System.out.println(latestArea.getId());
                    System.out.println(EmployeeService.findByAreaId(latestArea.getId()).toString());
                    employeeListView.getItems().setAll(FXCollections.observableList(EmployeeService.findByAreaId(latestArea.getId())));
                    employeeListView.setPrefHeight(Math.min((employeeListView.getItems().size() * 25), 280));
                    /*if (employeeListView.getItems().isEmpty()) {
                        employeeListView.setPlaceholder(new Label());
                    }
                    employeeListView.refresh();*/
                });
            }
        });
    }

    void initTestResultChart() {
        // with GuideConfig.testResults init testresultchart with the testresults by component
        var series = new XYChart.Series<String, Integer>();
        series.setName("Resultados de las pruebas");
        try {
            guideConfig.chargeTestResults();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for (TestResult testResult : GuideConfig.testResults) {
            series.getData().add(new XYChart.Data<>(
                    testResult.getTest().getComponentTemplates().get(i).getLabel(),
                    testResult.getTestResultData().getComponentsRessults().get(testResult.getTest().getComponentTemplates().get(i++).getLabel()).getYesCount()
            ));
        }
        testResultChart.getData().add(series);
    }

    @FXML
    public void onTestCreationButtonClicked(MouseEvent mouseEvent) {
        initTestCreationView();
        visibilityChange(false, false, true, false, false, false, false);
    }

    void initTestCreationView() {
        guideVersionChoiceBox.getItems().setAll(GuideConfig.testTemplateResults.stream().map(testResult -> testResult.getTest().getGuideVersion()).collect(Collectors.toList()));
        guideVersionChoiceBox.getSelectionModel().selectFirst();
        var employees = EmployeeService.findAll();

        // setCellFactory into the listviews
        setCellFactory(ambienteDeControlListView);
        setCellFactory(actividadesDeControlListView);
        setCellFactory(informacionYComunicacionListView);
        setCellFactory(supervicionYMonitoreoListView);
        setCellFactory(gestionYPrevencionListView);

        // set the items into the evated listviews
        setCellFactory(ambienteDeControlListViewEvated);
        setCellFactory(actividadesDeControlListViewEvated);
        setCellFactory(informacionYComunicacionListViewEvated);
        setCellFactory(supervicionYMonitoreoListViewEvated);
        setCellFactory(gestionYPrevencionListViewEvated);

        // set the items into the listviews
        setItems(employees, ambienteDeControlListView, actividadesDeControlListView, informacionYComunicacionListView, supervicionYMonitoreoListView, gestionYPrevencionListView);

        // set the items into the evaluated listviews
        setItems(employees, ambienteDeControlListViewEvated, actividadesDeControlListViewEvated, informacionYComunicacionListViewEvated, supervicionYMonitoreoListViewEvated, gestionYPrevencionListViewEvated);

    }

    private void setCellFactory(ListView<CheckBoxModel> listView) {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CheckBoxModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item.getChecked().getValue());
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> item.setChecked(new SimpleBooleanProperty(isSelected)));

                    setText(item.getEmployee().getValue().getEmployeeName());
                    setGraphic(checkBox);
                }
            }
        });
    }

    private void setItems(List<EmployeeEntity> employees, JFXListView<CheckBoxModel> ambienteDeControlListView, JFXListView<CheckBoxModel> actividadesDeControlListView, JFXListView<CheckBoxModel> informacionYComunicacionListView, JFXListView<CheckBoxModel> supervicionYMonitoreoListView, JFXListView<CheckBoxModel> gestionYPrevencionListView) {
        ambienteDeControlListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        actividadesDeControlListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        informacionYComunicacionListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        supervicionYMonitoreoListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        gestionYPrevencionListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
    }

    @FXML
    public void onTestEvaluateButtonClicked(MouseEvent mouseEvent) {
        // todo init guide config test results list and change the results when the user click on save button
        initTestEvaluationViewRegulationListView(
                1,
                ambCntrlRegulationEntityListView);
        initTestEvaluationViewRegulationListView(
                2,
                gestPrevRegulationEntityListView);
        initTestEvaluationViewRegulationListView(
                3,
                actvCntrlRegulationEntityListView);
        initTestEvaluationViewRegulationListView(
                4,
                infMonRegulationEntityListView);
        initTestEvaluationViewRegulationListView(
                5,
                supMonRegulationEntityListView);

        setRegulationEntityListViewSelectionModel(
                ambCntrlQuestionEntityTreeView,
                ambCntrlRegulationEntityListView,
                ambContTextLabel,
                ambContTextArea,
                ambContYesCheckBox,
                ambContNoCheckBox);
        setRegulationEntityListViewSelectionModel(
                gestPrevQuestionEntityTreeView,
                gestPrevRegulationEntityListView,
                gestPrevTextLabel1,
                gestPrevTextArea1,
                gestPrevYesCheckBox1,
                gestPrevNoCheckBox1);
        setRegulationEntityListViewSelectionModel(
                actvCntrlQuestionEntityTreeView,
                actvCntrlRegulationEntityListView,
                actContTextLabel11,
                actContTextArea11,
                actContYesCheckBox11,
                actContNoCheckBox11);
        setRegulationEntityListViewSelectionModel(
                infMonQuestionEntityTreeView,
                infMonRegulationEntityListView,
                infMonTextLabel,
                infMonTextArea,
                infMonYesCheckBox,
                infMonNoCheckBox);
        setRegulationEntityListViewSelectionModel(
                supMonQuestionEntityTreeView,
                supMonRegulationEntityListView,
                supMonTextLabel,
                supMonTextArea,
                supMonYesCheckBox,
                supMonNoCheckBox);

        visibilityChange(false, false, false, true, false, false, false);
    }

    private void initTestEvaluationViewRegulationListView(Integer componentId, ListView<RegulationEntity> regulationEntityListViews) {
        var regulations = RegulationService.getRegulationsByComponentId(componentId);
        regulationEntityListViews.getItems().clear();
        regulationEntityListViews.setCellFactory(new RegulationEntityCellFactory());
        regulationEntityListViews.getItems().addAll(regulations);
    }

    private void setRegulationEntityListViewSelectionModel(
            TreeView<QuestionEntity> questionEntityTreeView,
            ListView<RegulationEntity> regulationEntityListView, Label label, JFXTextArea jfxTextArea,
            CheckBox checkBox, CheckBox checkBox1) {
        questionEntityTreeView.setCellFactory(param -> new TreeCell<>() {
            @Override
            protected void updateItem(QuestionEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getLabel());
                    setGraphic(null);
                }
            }
        });
        regulationEntityListView.getSelectionModel().selectedItemProperty().addListener((observableValue, regulationEntity, t1) -> {
            if (t1 != null) Platform.runLater(() -> rechargeTreeView(
                    t1,
                    questionEntityTreeView,
                    label,
                    jfxTextArea,
                    checkBox,
                    checkBox1));

        });
        questionEntityTreeView.setShowRoot(false);
    }

    private void rechargeTreeView(
            RegulationEntity t1,
            TreeView<QuestionEntity> questionEntityTreeView,
            Label label,
            JFXTextArea jfxTextArea,
            CheckBox checkBox,
            CheckBox checkBox1) {
        questionEntityTreeView.setRoot(null);
        var questions = QuestionService.getQuestionsByRegulationId(t1.getId());
        var root = new TreeItem<>(new QuestionEntity());
        root.setExpanded(true);
        for (QuestionEntity questionEntity : questions) {
            var question = new TreeItem<>(questionEntity);
            question.setExpanded(true);
            if (questionEntity.getSuperquestionId() == null) {
                root.getChildren().add(question);
            } else {
                for (TreeItem<QuestionEntity> questionEntityTreeItem : root.getChildren()) {
                    if (questionEntityTreeItem.getValue().getId().equals(questionEntity.getSuperquestionId())) {
                        questionEntityTreeItem.getChildren().add(question);
                        // when you select a item of the tree view the label of the question will be set into the label
                        // and the description of the question will be set into the text area

                    }
                }
            }
        }
        questionEntityTreeView.setRoot(root);
        questionEntityTreeView.getSelectionModel().selectedItemProperty().addListener((observableValue, questionEntity1, t11) -> {
            if (t11 != null) {
                label.setText(t11.getValue().getLabel());
                jfxTextArea.setText(t11.getValue().getDescription());
                checkBox.setSelected(t11.getValue().getResult() != null && t11.getValue().getResult());
                checkBox1.setSelected(t11.getValue().getResult() != null && !t11.getValue().getResult());
            }
        });
        questionEntityTreeView.setPrefHeight(questionEntityTreeView.getChildrenUnmodifiable().size() * 25);
        questionEntityTreeView.refresh();
    }

    @FXML
    public void onReportGenerationButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, false, false, false, true, false, false);
    }

    @FXML
    public void onHelpButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, false, false, false, false, true, false);
    }

    @FXML
    public void onConfigButtonClicked(MouseEvent mouseEvent) {
        initConfigurationView();
        visibilityChange(false, false, false, false, false, false, true);
    }

    private void initConfigurationView() {
        // set the textfields with the properties values
        databaseConfigTextField.setText(EntityManagerProvider.DATABASE);
        userConfigTextField.setText(EntityManagerProvider.USER);
        passwordConfigTextField.setText(EntityManagerProvider.PASSWORD);
    }

    @FXML
    public void onHomeButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(true, false, false, false, false, false, false);
    }

    @FXML
    public void onAreaAddButtonClicked(MouseEvent mouseEvent) {
        // if the database
        Integer areaId = AreaService.getLatestAreaId();
        if (areasListView.getItems().stream().anyMatch(areaEntity -> areaEntity.getAreaName().equals(areaNameTextField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // customize the alert
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("El nombre del area ya existe!!!");
            areaNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            alert.showAndWait();
            return;
        }
        AreaEntity areaEntity = new AreaEntity(areaId == null ? 1 : areaId + 1, areaNameTextField.getText());
        if (areaNameTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // customize the alert
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("El nombre del area esta vacio!!!");
            areaNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            alert.showAndWait();
            return;
        }
        areasListView.getItems().addAll(FXCollections.observableList(List.of(new AreaEntity(
                areaId == null ? 1 : areaId + 1,
                areaNameTextField.getText()))));
        if (areasListView.getSelectionModel().getSelectedItem() != null)
            latestArea = areasListView.getItems().get(Math.max((areasListView.getSelectionModel().getSelectedItem().getId() - 1), 0));
        AreaService.saveArea(areaEntity.getId(), areaEntity.getAreaName());
        areasListView.setPrefHeight(Math.min((areasListView.getItems().size() * 25), 280));
        // set areaNameTextField border color to black with transparency 0.5
        areaNameTextField.setStyle("-fx-border-color: rgba(0,0,0,0.1)");
        setAreasListViewSelectionModel();
    }

    @FXML
    public void onAreaRemoveButtonClicked(MouseEvent mouseEvent) {
        if (areasListView.getSelectionModel().getSelectedItem() == null) return;
        AreaService.deleteArea(areasListView.getSelectionModel().getSelectedItem().getId());
        areasListView.getItems().remove(areasListView.getSelectionModel().getSelectedItem());
        areasListView.setPrefHeight(Math.min((areasListView.getItems().size() * 25), 280));
        if (areasListView.getItems().isEmpty()) {
            return;
        }
        areasListView.getSelectionModel().select(areasListView.getItems().get(areasListView.getItems().size() - 1));
        latestArea = areasListView.getItems().get((areasListView.getItems().size() - 1));
    }

    @FXML
    public void onEmployeeAddButtonClicked(MouseEvent mouseEvent) {
        if (employeeNameTextField.getText().isEmpty() || employeePositionTextField.getText().isEmpty() || areasListView.getSelectionModel().getSelectedItem() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            // customize the alert
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("El nombre del empleado o el cargo esta vacio!!!");
            // set border color red with transparency
            employeeNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            employeePositionTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            alert.showAndWait();
            return;
        }
        if (employeePositionTextField.getText().length() > 50) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // customize the alert
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("El cargo del empleado no puede tener mas de 50 caracteres!!!");
            // set border color red with transparency
            employeePositionTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            alert.showAndWait();
            return;
        }
        if (employeeNameTextField.getText().length() > 50) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // customize the alert
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("El nombre del empleado no puede tener mas de 50 caracteres!!!");
            // set border color red with transparency
            employeeNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            alert.showAndWait();
            return;
        }
        var employees = EmployeeService.findAllByAreaId(latestArea.getId());
        if (employees.stream().anyMatch(employeeEntity -> employeeEntity.getEmployeeName().equals(employeeNameTextField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // customize the alert
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error!!!");
            alert.setHeaderText("El nombre del empleado ya existe!!!");
            // set border color red with transparency
            employeeNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            alert.showAndWait();
            return;
        }
        Integer latestId = EmployeeService.getLatestId();
        var employee = EmployeeEntity.builder()
                                     .id(latestId == null ? 1 : latestId + 1)
                                     .employeeName(employeeNameTextField.getText())
                                     .position(employeePositionTextField.getText())
                                     .build();
        employeeListView.getItems().add(employee);
        employeeListView.setPrefHeight(Math.min((employeeListView.getItems().size() * 25), 280));
        var area = latestArea;
        employee.setAreaId(area.getId());
        EmployeeService.saveEmployee(
                employee.getId(),
                employee.getEmployeeName(),
                employee.getPosition(),
                employee.getAreaId());
        System.out.println(employee);
        // set employeeNameTextField and employeePositionTextField border color to black with transparency 0.5
        employeeNameTextField.setStyle("-fx-border-color: rgba(0,0,0,0.1)");
        employeePositionTextField.setStyle("-fx-border-color: rgba(0,0,0,0.1)");
    }

    @FXML
    public void onEmployeeRemoveButtonClicked(MouseEvent mouseEvent) {
        if (employeeListView.getSelectionModel().getSelectedItem() == null) return;
        EmployeeService.deleteEmployee(employeeListView.getSelectionModel().getSelectedItem());
        employeeListView.getItems().remove(employeeListView.getSelectionModel().getSelectedItem());
        employeeListView.setPrefHeight(Math.min((employeeListView.getItems().size() * 25), 280));
    }

    public void onIFinishedButtonClicked(MouseEvent mouseEvent) {
        var testResultDB = GuideConfig.testTemplateResults.stream().filter(testResult -> testResult.getTest().getGuideVersion().equals(guideVersionChoiceBox.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
        var testCode = testCodeTextField.getText();
        var startTestDate = startTestDatePicker.getValue();
        var endTestDate = endTestDatePicker.getValue();
        var guideVersion = guideVersionChoiceBox.getValue();

        assert testResultDB != null;
        testResultDB.getTest().setCode(testCode);
        testResultDB.getTest().setStartDate(startTestDate.toString());
        testResultDB.getTest().setFinishDate(endTestDate.toString());
        testResultDB.getTest().setGuideVersion(guideVersion);

        TestService.saveTest(new TestEntity(
                testCode,
                startTestDate,
                endTestDate,
                LocalDate.parse(guideVersion)));
        AtomicInteger i = new AtomicInteger(1);
        AtomicInteger j = new AtomicInteger(1);
        AtomicInteger k = new AtomicInteger(1);
        for (ComponentTemplate componentTemplate : testResultDB.getTest().getComponentTemplates()) {
            componentTemplate.setId(i.getAndIncrement());
            componentTemplate.setTestCode(testCode);
            ComponentService.saveComponent(new ComponentEntity(componentTemplate.getId(), componentTemplate.getLabel(), componentTemplate.getTestCode()));
            for (RegulationTemplate regulationTemplate : componentTemplate.getRegulationTemplates()) {
                regulationTemplate.setId(j.getAndIncrement());
                regulationTemplate.setComponentId(componentTemplate.getId());
                RegulationService.saveRegulation(new RegulationEntity(regulationTemplate.getId(), regulationTemplate.getLabel(), regulationTemplate.getComponentId()));
                for (QuestionTemplate questionTemplate : regulationTemplate.getQuestionTemplates()) {
                    questionTemplate.setRegulationId(regulationTemplate.getId());
                    questionTemplate.setId(k.getAndIncrement());
                    QuestionService.saveQuestion(new QuestionEntity(
                            questionTemplate.getId(),
                            questionTemplate.getLabel(),
                            questionTemplate.getDescription(),
                            questionTemplate.getResult(),
                            questionTemplate.getLabel().substring(0, 2),
                            regulationTemplate.getId(),
                            null));
                    for (QuestionTemplate subQuestionTemplate : questionTemplate.getSubQuestions()) {
                        subQuestionTemplate.setId(k.getAndIncrement());
                        subQuestionTemplate.setSuperquestionId(questionTemplate.getId());
                        QuestionService.saveQuestion(new QuestionEntity(
                                subQuestionTemplate.getId(),
                                subQuestionTemplate.getLabel(),
                                subQuestionTemplate.getDescription(),
                                subQuestionTemplate.getResult(),
                                subQuestionTemplate.getLabel().substring(0, 2),
                                regulationTemplate.getId(),
                                subQuestionTemplate.getSuperquestionId()));
                    }
                }
            }
        }
        var ambienteDeControlId =
                getIds(ambienteDeControlListView.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var actividadesDeControlId =
                getIds(actividadesDeControlListView.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var informacionYComunicacionId =
                getIds(informacionYComunicacionListView.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var supervicionYMonitoreoId =
                getIds(supervicionYMonitoreoListView.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var gestionYPrevencionId =
                getIds(gestionYPrevencionListView.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());

        var ambienteDeControlIdEvated =
                getIds(ambienteDeControlListViewEvated.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var actividadesDeControlIdEvated =
                getIds(actividadesDeControlListViewEvated.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var informacionYComunicacionIdEvated =
                getIds(informacionYComunicacionListViewEvated.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var supervicionYMonitoreoIdEvated =
                getIds(supervicionYMonitoreoListViewEvated.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
        var gestionYPrevencionIdEvated =
                getIds(gestionYPrevencionListViewEvated.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());

        testResultDB.setEvaluatorComponents(Stream.of( // add all the evaluator components
                                                       ambienteDeControlId.stream().map(employeeId -> new EvaluatorComponentEntity(employeeId, 1)).toList(),
                                                       gestionYPrevencionId.stream().map(employeeId -> new EvaluatorComponentEntity(employeeId, 2)).toList(),
                                                       actividadesDeControlId.stream().map(employeeId -> new EvaluatorComponentEntity(employeeId, 3)).toList(),
                                                       informacionYComunicacionId.stream().map(employeeId -> new EvaluatorComponentEntity(employeeId, 4)).toList(),
                                                       supervicionYMonitoreoId.stream().map(employeeId -> new EvaluatorComponentEntity(employeeId, 5)).toList()
                                                     ).flatMap(List::stream).toList());
        testResultDB.setEvaluatedComponents(Stream.of(
                ambienteDeControlIdEvated.stream().map(employeeId -> new EvaluatedComponentEntity(employeeId, 1)).toList(),
                gestionYPrevencionIdEvated.stream().map(employeeId -> new EvaluatedComponentEntity(employeeId, 2)).toList(),
                actividadesDeControlIdEvated.stream().map(employeeId -> new EvaluatedComponentEntity(employeeId, 3)).toList(),
                informacionYComunicacionIdEvated.stream().map(employeeId -> new EvaluatedComponentEntity(employeeId, 4)).toList(),
                supervicionYMonitoreoIdEvated.stream().map(employeeId -> new EvaluatedComponentEntity(employeeId, 5)).toList()
                                                     ).flatMap(List::stream).toList());
        EvaluatedComponentService.saveEvaluatedComponents(testResultDB.getEvaluatedComponents());
        EvaluatorComponentService.saveEvaluatorComponents(testResultDB.getEvaluatorComponents());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // customize the alert
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Perfecto!!!");
        alert.setHeaderText("Evaluacion creada correctamente!!!");
        alert.showAndWait();

    }


    public List<Integer> getIds(List<ObjectProperty<EmployeeEntity>> employees) {
        return employees.stream().map(ObjectProperty::get).map(EmployeeEntity::getId).toList();
    }

    @FXML
    public void onUdateDatabaseButtonClicked(MouseEvent mouseEvent) {
        // show an alert to confirm the update
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // customize the alert
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion!!!");
        alert.setHeaderText("Estas seguro que quieres actualizar las propiedades??");
        alert.setContentText("Si atualizas las propiedades y no son correctas, la aplicacion no funcionara correctamente!!!");
        alert.showAndWait();
        if (alert.getResult() != ButtonType.OK) return;
        // update the properties
        EntityManagerProvider.saveProperties(new Properties(userConfigTextField.getText(), passwordConfigTextField.getText(),
                                                            databaseConfigTextField.getText(), "audsci"));
        // show an alert to get a success message
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        // customize the alert
        alert1.setAlertType(Alert.AlertType.INFORMATION);
        alert1.setTitle("Perfecto!!!");
        alert1.setHeaderText("Propiedades actualizadas correctamente!!!");
        alert1.setContentText("Debe reiniciar la aplicacion para que los cambios tengan efecto!!!");
        alert1.showAndWait();
    }

    @FXML
    public void onRechargeGuidesButtonClicked(MouseEvent mouseEvent) {
        try {
            // show an alert to confirm the recharge
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            // customize the alert
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion!!!");
            alert.setHeaderText("Estas seguro que quieres recargar las guias??");
            alert.showAndWait();
            if (alert.getResult() != ButtonType.OK) return;
            // recharge the guides
            guideConfig.updateGuidesTemplates();
            guideConfig.saveTestTemplates();
            guideConfig.chargeTestTemplates();
            // init cells factories
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void onAmbCntrlListViewTestMouseEntered(MouseEvent mouseEvent) {
        // set the width of te listview plus one waiting 50 millis beteewn plus
        animateListView(ambCntrlRegulationEntityListView);
    }

    private void animateListView(ListView<RegulationEntity> regulationEntityListView) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(100), new KeyValue(regulationEntityListView.prefWidthProperty(), 400)));
        timeline.play();
    }

    @FXML
    public void onAmbCntrlListViewTestMouseExited(MouseEvent mouseEvent) {
        ambCntrlRegulationEntityListView.setPrefWidth(30);
    }

    @FXML
    public void onGestPrevListViewTestMouseEntered(MouseEvent mouseEvent) {
        // set the width of te listview plus one waiting 50 millis beteewn plus
        animateListView(gestPrevRegulationEntityListView);
    }

    @FXML
    public void onGestPrevListViewTestMouseExited(MouseEvent mouseEvent) {
        gestPrevRegulationEntityListView.setPrefWidth(30);
    }

    @FXML
    public void onActvCntrlListViewTestMouseEntered(MouseEvent mouseEvent) {
        animateListView(actvCntrlRegulationEntityListView);
    }

    @FXML
    public void onActvCntrlListViewTestMouseExited(MouseEvent mouseEvent) {
        actvCntrlRegulationEntityListView.setPrefWidth(30);
    }

    @FXML
    public void onInfMonListViewTestMouseEntered(MouseEvent mouseEvent) {
        animateListView(infMonRegulationEntityListView);
    }

    @FXML
    public void onInfMonListViewTestMouseExited(MouseEvent mouseEvent) {
        infMonRegulationEntityListView.setPrefWidth(30);
    }

    @FXML
    public void onSupMonListViewTestMouseEntered(MouseEvent mouseEvent) {
        animateListView(supMonRegulationEntityListView);
    }

    @FXML
    public void onSupMonListViewTestMouseExited(MouseEvent mouseEvent) {
        supMonRegulationEntityListView.setPrefWidth(30);
    }

    @FXML
    public void onAmbContSaveButtonClicked(MouseEvent mouseEvent) {
        try {
            guideConfig.saveTestResultList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onGestPrevSaveButtonClicked(MouseEvent mouseEvent) {
        try {
            guideConfig.saveTestResultList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onActContSaveButtonClicked(MouseEvent mouseEvent) {
        try {
            guideConfig.saveTestResultList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAmbContCheckBoxClicked(MouseEvent mouseEvent) {
        // set the result of the question that match with the selected in the tree view to true, and set the no check box to false
        checkBoxChanged(ambCntrlQuestionEntityTreeView, true, ambContNoCheckBox);
    }

    private void checkBoxChanged(TreeView<QuestionEntity> treeView, boolean result, CheckBox checkBox) {
        var questionEntity = treeView.getSelectionModel().getSelectedItem().getValue();
        questionEntity.setResult(result);
        QuestionService.saveQuestion(questionEntity);
        checkBox.setSelected(false);
    }

    @FXML
    public void onAmbContNoCheckBoxClicked(MouseEvent mouseEvent) {
        // set the result of the question that match with the selected in the tree view to false
        checkBoxChanged(ambCntrlQuestionEntityTreeView, false, ambContYesCheckBox);
    }

    @FXML
    public void onAmbContTextAreaChanged(KeyEvent inputMethodEvent) {
        // set the description of the question that match with the selected in the tree view to the text area text
        textAreaChanged(ambCntrlQuestionEntityTreeView, ambContTextArea, inputMethodEvent);
    }

    private void textAreaChanged(TreeView<QuestionEntity> treeView, JFXTextArea jfxTextArea, KeyEvent inputMethodEvent) {
        if (inputMethodEvent.getCode() == KeyCode.ENTER) {
            var questionEntity = treeView.getSelectionModel().getSelectedItem().getValue();
            questionEntity.setDescription(jfxTextArea.getText());
            QuestionService.saveQuestion(questionEntity);
        }
    }

    @FXML
    public void onInfMonSaveButtonClicked(MouseEvent mouseEvent) {try {
        guideConfig.saveTestResultList();
    } catch (URISyntaxException | IOException e) {
        throw new RuntimeException(e);
    }}

    @FXML
    public void onInfMonCheckBoxClicked(MouseEvent mouseEvent) {
        // set the result of the question that match with the selected in the tree view to true, and set the no check box to false
        checkBoxChanged(infMonQuestionEntityTreeView, true, infMonNoCheckBox);
    }

    @FXML
    public void onInfMonNoCheckBoxClicked(MouseEvent mouseEvent) {
        // set the result of the question that match with the selected in the tree view to false
        checkBoxChanged(infMonQuestionEntityTreeView, false, infMonYesCheckBox);
    }

    @FXML
    public void onInfMonTextAreaChanged(KeyEvent inputMethodEvent) {
        // set the description of the question that match with the selected in the tree view to the text area text
        textAreaChanged(infMonQuestionEntityTreeView, infMonTextArea, inputMethodEvent);
    }

    @FXML
    public void onSupMonSaveButtonClicked(MouseEvent mouseEvent) {
        try {
            guideConfig.saveTestResultList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onSupMonCheckBoxClicked(MouseEvent mouseEvent) {
        // set the result of the question that match with the selected in the tree view to true, and set the no check box to false
        checkBoxChanged(supMonQuestionEntityTreeView, true, supMonNoCheckBox);
    }

    @FXML
    public void onSupMonNoCheckBoxClicked(MouseEvent mouseEvent) {
        // set the result of the question that match with the selected in the tree view to false
        checkBoxChanged(supMonQuestionEntityTreeView, false, supMonYesCheckBox);
    }

    @FXML
    public void onSupMonTextAreaChanged(KeyEvent inputMethodEvent) {
        // set the description of the question that match with the selected in the tree view to the text area text
        textAreaChanged(supMonQuestionEntityTreeView, supMonTextArea, inputMethodEvent);
    }

    @FXML

    public void onActConYesCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(actvCntrlQuestionEntityTreeView, true, actContNoCheckBox11);
    }

    @FXML

    public void onActConNoCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(actvCntrlQuestionEntityTreeView, false, actContYesCheckBox11);
    }

    @FXML
    public void onActContTextAreaChanged(KeyEvent inputMethodEvent) {
        textAreaChanged(actvCntrlQuestionEntityTreeView, actContTextArea11, inputMethodEvent);
    }

    @FXML

    public void onGestPrevYesCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(gestPrevQuestionEntityTreeView, true, gestPrevNoCheckBox1);
    }

    @FXML

    public void onGestPrevNoCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(gestPrevQuestionEntityTreeView, false, gestPrevYesCheckBox1);
    }

    @FXML

    public void onGesPrevTextAreaChanged(KeyEvent inputMethodEvent) {
        textAreaChanged(gestPrevQuestionEntityTreeView, gestPrevTextArea1, inputMethodEvent);
    }


    @Data
    public static class CheckBoxModel {
        private ObjectProperty<EmployeeEntity> employee;
        private BooleanProperty checked;

        public CheckBoxModel(EmployeeEntity employee, boolean checked) {
            this.employee = new SimpleObjectProperty<>(employee);
            this.checked = new SimpleBooleanProperty(checked);
        }

    }

    private static class RegulationEntityCellFactory implements javafx.util.Callback<ListView<com.suehay.audscifx.model.RegulationEntity>, ListCell<com.suehay.audscifx.model.RegulationEntity>> {
        @Override
        public ListCell<RegulationEntity> call(ListView<RegulationEntity> param) {
            return new ListCell<>() {
                @Override
                protected void updateItem(RegulationEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getLabel());
                    }
                }
            };
        }
    }
}