package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.suehay.audscifx.config.GuideConfig;
import com.suehay.audscifx.model.*;
import com.suehay.audscifx.services.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController {
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
    public JFXListView<CheckBoxModel> ambienteDeControlListViewEvated;
    public JFXListView<CheckBoxModel> gestionYPrevencionListViewEvated;
    public JFXListView<CheckBoxModel> actividadesDeControlListViewEvated;
    public JFXListView<CheckBoxModel> informacionYComunicacionListViewEvated;
    public JFXListView<CheckBoxModel> supervicionYMonitoreoListViewEvated;
    @FXML
    public ListView<AreaEntity> areasListView;
    @FXML
    public ListView<EmployeeEntity> employeeListView;

    private AreaEntity latestArea;
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


    void visibilityChange(boolean home, boolean enterpriseCreation, boolean testCreation, boolean testEvaluate, boolean reportGeneration, boolean help, boolean config) {
        homeView.setVisible(home);
        enterpriseCreationView.setVisible(enterpriseCreation);
        testCreationView.setVisible(testCreation);
        componentEvaluationView.setVisible(testEvaluate);
        // reportGenerationView.setVisible(reportGeneration);
        // helpView.setVisible(help);
        // configView.setVisible(config);
    }

    @FXML
    public void onEnterpriseButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, true, false, false, false, false, false);
        initEnterpriseCreationView();
    }

    private void initEnterpriseCreationView() {
        this.areasListView.getItems().setAll(FXCollections.observableList(AreaService.findAll()));

        this.areasListView.getSelectionModel().select(0);
        this.employeeListView.getItems().clear();
        this.employeeListView.getItems().addAll(FXCollections.observableList(EmployeeService.findByAreaId(this.areasListView.getSelectionModel().getSelectedItem().getId())));
        this.employeeListView.refresh();

        // when an area is selected in the areasListView, the employeeListView should be updated with the employees of the selected area
        areasListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.areasListView.getSelectionModel().selectedItemProperty().addListener((observableValue, areaEntity, t1) -> {
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

        // set the items into the evated listviews
        setItems(employees, ambienteDeControlListViewEvated, actividadesDeControlListViewEvated, informacionYComunicacionListViewEvated, supervicionYMonitoreoListViewEvated, gestionYPrevencionListViewEvated);

    }


    private void setItems(List<EmployeeEntity> employees, JFXListView<CheckBoxModel> ambienteDeControlListView, JFXListView<CheckBoxModel> actividadesDeControlListView, JFXListView<CheckBoxModel> informacionYComunicacionListView, JFXListView<CheckBoxModel> supervicionYMonitoreoListView, JFXListView<CheckBoxModel> gestionYPrevencionListView) {
        ambienteDeControlListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        actividadesDeControlListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        informacionYComunicacionListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        supervicionYMonitoreoListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        gestionYPrevencionListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
    }

    private void setCellFactory(ListView<CheckBoxModel> listView) {
        listView.setCellFactory(param -> new ListCell<CheckBoxModel>() {
            @Override
            protected void updateItem(CheckBoxModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item.getChecked().getValue());
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        item.setChecked(new SimpleBooleanProperty(isSelected));
                    });

                    setText(item.getEmployee().getValue().getEmployeeName());
                    setGraphic(checkBox);
                }
            }
        });
    }


    @FXML
    public void onTestEvaluateButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, false, false, true, false, false, false);
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
        visibilityChange(false, false, false, false, false, false, true);
    }

    @FXML
    public void onHomeButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(true, false, false, false, false, false, false);
    }

    @FXML
    public void onAreaAddButtonClicked(MouseEvent mouseEvent) {
        AreaEntity areaEntity = new AreaEntity(this.areasListView.getItems().size() + 1, this.areaNameTextField.getText());
        if (this.areaNameTextField.getText().isEmpty()) return;
        this.areasListView.getItems().addAll(FXCollections.observableList(List.of(new AreaEntity(
                this.areasListView.getItems().size() + 1,
                this.areaNameTextField.getText()))));
        if (this.areasListView.getSelectionModel().getSelectedItem() != null)
            latestArea = areasListView.getItems().get(Math.max((this.areasListView.getSelectionModel().getSelectedItem().getId() - 1), 0));
        AreaService.saveArea(areaEntity.getId(), areaEntity.getAreaName());
    }

    @FXML
    public void onAreaRemoveButtonClicked(MouseEvent mouseEvent) {
        if (this.areasListView.getSelectionModel().getSelectedItem() == null) return;
        AreaService.deleteArea(this.areasListView.getSelectionModel().getSelectedItem());
        this.areasListView.getItems().remove(this.areasListView.getSelectionModel().getSelectedItem());
        this.areasListView.getSelectionModel().select(areasListView.getItems().get(Math.max(areasListView.getItems().size() - 1, 0)));
        latestArea = areasListView.getItems().get(Math.max((this.areasListView.getSelectionModel().getSelectedItem().getId() - 1), 0));
    }

    @FXML
    public void onEmployeeAddButtonClicked(MouseEvent mouseEvent) {
        if (this.employeeNameTextField.getText().isEmpty() || this.employeePositionTextField.getText().isEmpty() || this.areasListView.getSelectionModel().getSelectedItem() == null)
            return;
        latestArea = this.areasListView.getSelectionModel().getSelectedItem();
        int latestId = EmployeeService.getLatestId();
        var employee = EmployeeEntity.builder()
                                     .id(latestId + 1)
                                     .employeeName(this.employeeNameTextField.getText())
                                     .position(this.employeePositionTextField.getText())
                                     .build();
        this.employeeListView.getItems().add(employee);
        employeeListView.setPrefHeight(Math.min((employeeListView.getItems().size() * 25), 280));
        employee.setAreaId(latestArea.getId());
        EmployeeService.saveEmployee(employee.getId(), employee.getEmployeeName(), employee.getPosition(), employee.getAreaId());
        System.out.println(employee);
    }

    @FXML
    public void onEmployeeRemoveButtonClicked(MouseEvent mouseEvent) {
        if (this.employeeListView.getSelectionModel().getSelectedItem() == null) return;
        EmployeeService.deleteEmployee(this.employeeListView.getSelectionModel().getSelectedItem());
        this.employeeListView.getItems().remove(this.employeeListView.getSelectionModel().getSelectedItem());
        employeeListView.setPrefHeight(Math.min((employeeListView.getItems().size() * 25), 280));
    }

    public void onIFinishedButtonClicked(MouseEvent mouseEvent) {
        var testResultDB = GuideConfig.testTemplateResults.stream().filter(testResult -> testResult.getTest().getGuideVersion().equals(guideVersionChoiceBox.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
        var testCode = this.testCodeTextField.getText();
        var startTestDate = this.startTestDatePicker.getValue();
        var endTestDate = this.endTestDatePicker.getValue();
        var guideVersion = this.guideVersionChoiceBox.getValue();

        assert testResultDB != null;
        testResultDB.getTest().setCode(testCode);
        testResultDB.getTest().setStartDate(startTestDate.toString());
        testResultDB.getTest().setFinishDate(endTestDate.toString());
        testResultDB.getTest().setGuideVersion(guideVersion);

        TestService.saveTest(new TestEntity(testCode, startTestDate, endTestDate, LocalDate.parse(guideVersion)));
        AtomicInteger i = new AtomicInteger(1);
        AtomicInteger j = new AtomicInteger(1);
        AtomicInteger k = new AtomicInteger(1);
        AtomicInteger l = new AtomicInteger(1);
        testResultDB.getTest().getComponentTemplates().forEach(componentTemplate -> {
            componentTemplate.setId(i.getAndIncrement());
            componentTemplate.setTestCode(testCode);
            ComponentService.saveComponent(new ComponentEntity(componentTemplate.getId(), componentTemplate.getLabel(), componentTemplate.getTestCode()));
            componentTemplate.getRegulationTemplates().forEach(regulationTemplate -> {
                regulationTemplate.setId(j.getAndIncrement());
                regulationTemplate.setComponentId(componentTemplate.getId());
                RegulationService.saveRegulation(new RegulationEntity(regulationTemplate.getId(), regulationTemplate.getLabel(), regulationTemplate.getComponentId()));
                regulationTemplate.getQuestionTemplates().forEach(questionTemplate -> {
                    questionTemplate.setRegulationId(regulationTemplate.getId());
                    questionTemplate.setId(k.getAndIncrement());
                    QuestionService.saveQuestion(new QuestionEntity(
                            questionTemplate.getId(),
                            questionTemplate.getLabel(),
                            questionTemplate.getDescription(),
                            questionTemplate.getResult(),
                            questionTemplate.getLabel().substring(0, 2),
                            questionTemplate.getRegulationId(),
                            null));
                    questionTemplate.getSubQuestions().forEach(subQuestionTemplate -> {
                        subQuestionTemplate.setId(l.getAndIncrement());
                        subQuestionTemplate.setSuperquestionId(questionTemplate.getId());
                        QuestionService.saveQuestion(new QuestionEntity(
                                subQuestionTemplate.getId(),
                                subQuestionTemplate.getLabel(),
                                subQuestionTemplate.getDescription(),
                                subQuestionTemplate.getResult(),
                                subQuestionTemplate.getLabel().substring(0, 2),
                                questionTemplate.getRegulationId(),
                                subQuestionTemplate.getSuperquestionId()));
                    });
                });
            });
        });
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
        alert.setTitle("Successfully!!!");
        alert.setHeaderText("Evaluation created successfully!!!");
        alert.showAndWait();

    }


    public List<Integer> getIds(List<ObjectProperty<EmployeeEntity>> employees) {
        return employees.stream().map(ObjectProperty::get).map(EmployeeEntity::getId).toList();
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

}