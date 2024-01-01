package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeView;
import com.suehay.audscifx.config.EntityManagerProvider;
import com.suehay.audscifx.config.GuideConfig;
import com.suehay.audscifx.model.*;
import com.suehay.audscifx.model.common.Properties;
import com.suehay.audscifx.model.common.Result;
import com.suehay.audscifx.model.common.TestResultData;
import com.suehay.audscifx.model.templates.*;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController {
    private static final KeyCodeCombination kc = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.ALT_ANY),
            kc2 = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.ALT_ANY),
            kc3 = new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.ALT_ANY),
            kc4 = new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.ALT_ANY),
            kc5 = new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.ALT_ANY),
            kc6 = new KeyCodeCombination(KeyCode.DIGIT6, KeyCombination.ALT_ANY),
            kc7 = new KeyCodeCombination(KeyCode.DIGIT7, KeyCombination.ALT_ANY);
    private static final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private final GuideConfig guideConfig = new GuideConfig();
    @FXML
    public JFXListView<CheckBoxModel> gestionYPrevencionListView = new JFXListView<>(),
            ambienteDeControlListView = new JFXListView<>(),
            actividadesDeControlListView = new JFXListView<>(),
            informacionYComunicacionListView = new JFXListView<>(),
            supervicionYMonitoreoListView = new JFXListView<>(),
            ambienteDeControlListViewEvated = new JFXListView<>(),
            gestionYPrevencionListViewEvated = new JFXListView<>(),
            actividadesDeControlListViewEvated = new JFXListView<>(),
            informacionYComunicacionListViewEvated = new JFXListView<>(),
            supervicionYMonitoreoListViewEvated = new JFXListView<>();
    @FXML
    public JFXButton enterpriseButton,
            testCreationButton,
            testEvaluateButton,
            reportGenerationButton,
            helpButton,
            configButton,
            homeButton,
            areaAddButton,
            employeeAddButton,
            employeeRemoveButton,
            updateDatabaseProperties,
            areaRemoveButton,
            guideRechargeButton,
            ambContSaveButton,
            gestPrevSaveButton1,
            actContSaveButton11,
            infMonSaveButton,
            supMonSaveButton;
    @FXML
    public TextField areaNameTextField,
            employeeNameTextField,
            employeePositionTextField,
            testCodeTextField,
            databaseConfigTextField,
            userConfigTextField,
            passwordConfigTextField;
    @FXML
    public AnchorPane homeView,
            enterpriseCreationView,
            testCreationView,
            componentEvaluationView,
            printReportView,
            helpView,
            configurationView;
    @FXML
    public DatePicker startTestDatePicker,
            endTestDatePicker;
    @FXML
    public CheckBox ambContYesCheckBox,
            ambContNoCheckBox,
            gestPrevNoCheckBox1,
            gestPrevYesCheckBox1,
            actContNoCheckBox11,
            actContYesCheckBox11,
            infMonYesCheckBox,
            infMonNoCheckBox,
            supMonYesCheckBox,
            supMonNoCheckBox;
    @FXML
    public Label actContTextLabel11,
            supMonTextLabel,
            infMonTextLabel,
            gestPrevTextLabel1,
            ambContTextLabel;
    @FXML
    public JFXTextArea ambContTextArea,
            gestPrevTextArea1,
            actContTextArea11,
            infMonTextArea,
            supMonTextArea;
    @FXML
    public TreeView<QuestionEntity> ambCntrlQuestionEntityTreeView = new JFXTreeView<>(),
            gestPrevQuestionEntityTreeView = new JFXTreeView<>(),
            actvCntrlQuestionEntityTreeView = new JFXTreeView<>(),
            infMonQuestionEntityTreeView = new JFXTreeView<>(),
            supMonQuestionEntityTreeView = new JFXTreeView<>();
    @FXML
    public ListView<RegulationEntity> infMonRegulationEntityListView = new ListView<>(),
            gestPrevRegulationEntityListView = new ListView<>(),
            ambCntrlRegulationEntityListView = new ListView<>(),
            actvCntrlRegulationEntityListView = new ListView<>(),
            supMonRegulationEntityListView = new ListView<>();
    @FXML
    public ChoiceBox<String> guideVersionChoiceBox = new ChoiceBox<>();
    @FXML
    public BorderPane homePane;
    @FXML
    public VBox leftOptionsPane;
    @FXML
    public JFXListView<ComponentEntity> componentsListView = new JFXListView<>();
    @FXML
    public ListView<AreaEntity> areasListView = new ListView<>();
    @FXML
    public ListView<EmployeeEntity> employeeListView = new ListView<>();
    private AreaEntity latestArea;

    @FXML
    public void onAreaAddButtonClicked(MouseEvent mouseEvent) {
        var areaId = AreaService.getLatestAreaId();
        if (areasListView.getItems().stream().anyMatch(areaEntity -> areaEntity.getAreaName().equals(areaNameTextField.getText()))) {
            showAlert(Alert.AlertType.ERROR, "Error!!!", "El nombre del area ya existe!!!", null);
            areaNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            return;
        }
        var areaEntity = new AreaEntity(areaId == null ? 1 : areaId + 1, areaNameTextField.getText());
        if (areaNameTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error!!!", "El nombre del area esta vacio!!!", null);
            areaNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            return;
        }
        areasListView.getItems().addAll(FXCollections.observableList(List.of(new AreaEntity(
                areaId == null ? 1 : areaId + 1,
                areaNameTextField.getText()))));
        if (areasListView.getSelectionModel().getSelectedItem() != null)
            latestArea = areasListView.getItems().get(Math.max((areasListView.getSelectionModel().getSelectedItem().getId() - 1), 0));
        AreaService.saveArea(areaEntity.getId(), areaEntity.getAreaName());
        areasListView.setPrefHeight(Math.min((areasListView.getItems().size() * 25), 280));
        areaNameTextField.setStyle("-fx-border-color: rgba(0,0,0,0.1)");
        setAreasListViewSelectionModel();
    }

    private static void showAlert(Alert.AlertType error, String title, String headerText, String contentText) {
        alert.setAlertType(error);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        if (contentText != null) alert.setContentText(contentText);
        alert.showAndWait();
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
                });
            }
        });
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
            showAlert(Alert.AlertType.ERROR, "Error!!!", "El nombre del empleado o el cargo esta vacio!!!", null);
            employeeNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            employeePositionTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            return;
        }
        if (employeePositionTextField.getText().length() > 50) {
            showAlert(Alert.AlertType.ERROR, "Error!!!", "El cargo del empleado no puede tener mas de 50 caracteres!!!", null);
            employeePositionTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            return;
        }
        if (employeeNameTextField.getText().length() > 50) {
            showAlert(Alert.AlertType.ERROR, "Error!!!", "El nombre del empleado no puede tener mas de 50 caracteres!!!", null);
            employeeNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            return;
        }
        var employees = EmployeeService.findAllByAreaId(latestArea.getId());
        if (employees.stream().anyMatch(employeeEntity -> employeeEntity.getEmployeeName().equals(employeeNameTextField.getText()))) {
            showAlert(Alert.AlertType.ERROR, "Error!!!", "El nombre del empleado ya existe!!!", null);
            employeeNameTextField.setStyle("-fx-border-color: rgba(255, 0, 0, 0.5)");
            return;
        }
        var latestId = EmployeeService.getLatestId();
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

    @FXML
    public void onUdateDatabaseButtonClicked(MouseEvent mouseEvent) {
        showAlert(Alert.AlertType.CONFIRMATION, "Confirmacion!!!", "Estas seguro que quieres actualizar las propiedades??", "Si atualizas " +
                "las propiedades y no son correctas, la aplicacion no funcionara correctamente!!!");
        if (alert.getResult() != ButtonType.OK) return;
        // update the properties
        EntityManagerProvider.saveProperties(new Properties(userConfigTextField.getText(), passwordConfigTextField.getText(),
                                                            databaseConfigTextField.getText(), "audsci"));
        showAlert(Alert.AlertType.INFORMATION, "Perfecto!!!", "Propiedades actualizadas correctamente!!!", "Debe reiniciar la aplicacion para que los cambios tengan efecto!!!");
    }

    @FXML
    public void onRechargeGuidesButtonClicked(MouseEvent mouseEvent) {
        try {
            // show an alert to confirm the recharge
            showAlert(Alert.AlertType.CONFIRMATION, "Confirmacion!!!", "Estas seguro que quieres recargar las guias??", null);
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
        animateListView(ambCntrlRegulationEntityListView);
    }

    private void animateListView(ListView<RegulationEntity> regulationEntityListView) {
        var timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(100), new KeyValue(regulationEntityListView.prefWidthProperty(), 400)));
        timeline.play();
    }

    @FXML
    public void onAmbCntrlListViewTestMouseExited(MouseEvent mouseEvent) {
        ambCntrlRegulationEntityListView.setPrefWidth(30);
    }

    @FXML
    public void onGestPrevListViewTestMouseEntered(MouseEvent mouseEvent) {
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
        saveButtonClickedLogic();
    }

    private void saveButtonClickedLogic() {
        try {
            var tests = TestService.findAll();
            chargeTestResultsFromDB();
            guideConfig.saveTestResultList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void chargeTestResultsFromDB() {
        var testList = TestService.findAll();
        var testTemplate = testList.stream().map(testEntity -> TestTemplate.builder()
                                                                           .code(testEntity.getCode())
                                                                           .startDate(testEntity.getStartDate().toString())
                                                                           .finishDate(testEntity.getFinishDate().toString())
                                                                           .guideVersion(testEntity.getGuideVersion().toString())
                                                                           .build()).toList().get(0);
        var componentList = ComponentService.findAllByTestCode(testList.get(0).getCode());
        var componentTemplates = componentList.stream().map(componentEntity -> ComponentTemplate.builder()
                                                                                                .id(componentEntity.getId())
                                                                                                .label(componentEntity.getLabel())
                                                                                                .testCode(componentEntity.getTestCode())
                                                                                                .build()).toList();
        testTemplate.setComponentTemplates(componentTemplates);
        var evaluatedComponentList = new ArrayList<EvaluatedComponentEntity>();
        var evaluatorComponentList = new ArrayList<EvaluatorComponentEntity>();
        var testResultData = new TestResultData();
        componentTemplates.forEach(componentTemplate -> {
            var regulationList = RegulationService.getRegulationsByComponentId(componentTemplate.getId());
            var regulationTemplates = regulationList.stream().map(regulationEntity -> RegulationTemplate.builder()
                                                                                                        .id(regulationEntity.getId())
                                                                                                        .componentId(regulationEntity.getComponentId())
                                                                                                        .label(regulationEntity.getLabel())
                                                                                                        .build()).toList();
            evaluatedComponentList.addAll(EvaluatedComponentService.findAllByComponentId(componentTemplate.getId()));
            evaluatorComponentList.addAll(EvaluatorComponentService.findAllByComponentId(componentTemplate.getId()));
            testResultData.getComponentsRessults().put(componentTemplate.getLabel(), new Result());
            componentTemplate.setRegulationTemplates(regulationTemplates);
            regulationTemplates.forEach(regulationTemplate -> {
                var questionList = QuestionService.getQuestionsByRegulationId(regulationTemplate.getId());
                var questionTemplates = fillTestResultData(componentTemplate, questionList, testResultData);
                regulationTemplate.setQuestionTemplates(questionTemplates);
                questionTemplates.forEach(questionTemplate -> {
                    var subQuestionList = QuestionService.getQuestionsBySuperQuestionId(questionTemplate.getId());
                    var subQuestionTemplates = fillTestResultData(componentTemplate, subQuestionList, testResultData);
                    questionTemplate.setSubQuestions(subQuestionTemplates);
                });
            });
        });
        GuideConfig.testResults.add(new TestResult(testResultData, evaluatedComponentList, evaluatorComponentList, testTemplate));
    }

    private static List<QuestionTemplate> fillTestResultData(ComponentTemplate componentTemplate, List<QuestionEntity> subQuestionList, TestResultData testResultData) {
        return subQuestionList.stream().map(subQuestionEntity -> {
            if (subQuestionEntity.getResult() != null) {
                var yesCount = testResultData.getComponentsRessults().get(componentTemplate.getLabel()).getYesCount();
                var noCount = testResultData.getComponentsRessults().get(componentTemplate.getLabel()).getNoCount();
                if (subQuestionEntity.getResult())
                    testResultData.getComponentsRessults().get(componentTemplate.getLabel()).setYesCount(yesCount != null ? yesCount + 1 : 0);
                else
                    testResultData.getComponentsRessults().get(componentTemplate.getLabel()).setNoCount(noCount != null ? noCount + 1 : 0);
            }
            return QuestionTemplate.builder()
                                   .id(subQuestionEntity.getId())
                                   .regulationId(subQuestionEntity.getRegulationId())
                                   .code(subQuestionEntity.getCode())
                                   .label(subQuestionEntity.getLabel())
                                   .description(subQuestionEntity.getDescription())
                                   .result(subQuestionEntity.getResult())
                                   .build();
        }).toList();
    }

    @FXML
    public void onAmbContTextAreaChanged(KeyEvent inputMethodEvent) {
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
    public void onGestPrevSaveButtonClicked(MouseEvent mouseEvent) {
        saveButtonClickedLogic();
    }

    @FXML
    public void onActContSaveButtonClicked(MouseEvent mouseEvent) {
        saveButtonClickedLogic();
    }

    @FXML
    public void onAmbContCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(ambCntrlQuestionEntityTreeView, true, ambContNoCheckBox);
    }

    private void checkBoxChanged(TreeView<QuestionEntity> treeView, boolean result, CheckBox checkBox) {
        var questionEntity = treeView.getSelectionModel().getSelectedItem().getValue();
        questionEntity.setResult(result);
        QuestionService.saveQuestion(questionEntity);
        checkBox.setSelected(false);
    }

    @FXML
    public void onInfMonSaveButtonClicked(MouseEvent mouseEvent) {saveButtonClickedLogic();}

    @FXML
    public void onInfMonCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(infMonQuestionEntityTreeView, true, infMonNoCheckBox);
    }

    @FXML
    public void onAmbContNoCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(ambCntrlQuestionEntityTreeView, false, ambContYesCheckBox);
    }

    @FXML
    public void onInfMonNoCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(infMonQuestionEntityTreeView, false, infMonYesCheckBox);
    }

    @FXML
    public void onInfMonTextAreaChanged(KeyEvent inputMethodEvent) {
        textAreaChanged(infMonQuestionEntityTreeView, infMonTextArea, inputMethodEvent);
    }

    @FXML
    public void onSupMonSaveButtonClicked(MouseEvent mouseEvent) {
        saveButtonClickedLogic();
    }

    @FXML
    public void onSupMonCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(supMonQuestionEntityTreeView, true, supMonNoCheckBox);
    }

    @FXML
    public void onSupMonNoCheckBoxClicked(MouseEvent mouseEvent) {
        checkBoxChanged(supMonQuestionEntityTreeView, false, supMonYesCheckBox);
    }

    @FXML
    public void onSupMonTextAreaChanged(KeyEvent inputMethodEvent) {
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

    public void onIFinishedButtonClicked(MouseEvent mouseEvent) {
        var testResultDB = GuideConfig.testTemplates.stream().filter(testResult -> testResult.getTest().getGuideVersion().equals(guideVersionChoiceBox.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
        var testCode = testCodeTextField.getText();
        var startTestDate = startTestDatePicker.getValue();
        var endTestDate = endTestDatePicker.getValue();
        var guideVersion = guideVersionChoiceBox.getValue();

        if (testResultDB == null) return;

        testResultDB.getTest().setCode(testCode);
        testResultDB.getTest().setStartDate(startTestDate.toString());
        testResultDB.getTest().setFinishDate(endTestDate.toString());
        testResultDB.getTest().setGuideVersion(guideVersion);

        TestService.saveTest(new TestEntity(
                testCode,
                startTestDate,
                endTestDate,
                LocalDate.parse(guideVersion)));
        var i = new AtomicInteger(1);
        var j = new AtomicInteger(1);
        var k = new AtomicInteger(1);
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
        List<Integer> ambienteDeControlId = getIdsList(ambienteDeControlListView),
                actividadesDeControlId = getIdsList(actividadesDeControlListView),
                informacionYComunicacionId = getIdsList(informacionYComunicacionListView),
                supervicionYMonitoreoId = getIdsList(supervicionYMonitoreoListView),
                gestionYPrevencionId = getIdsList(gestionYPrevencionListView),
                ambienteDeControlIdEvated = getIdsList(ambienteDeControlListViewEvated),
                actividadesDeControlIdEvated = getIdsList(actividadesDeControlListViewEvated),
                informacionYComunicacionIdEvated = getIdsList(informacionYComunicacionListViewEvated),
                supervicionYMonitoreoIdEvated = getIdsList(supervicionYMonitoreoListViewEvated),
                gestionYPrevencionIdEvated = getIdsList(gestionYPrevencionListViewEvated);
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
        showAlert(Alert.AlertType.INFORMATION, "Perfecto!!!", "Evaluacion creada correctamente!!!", null);

    }

    private List<Integer> getIdsList(JFXListView<CheckBoxModel> ambienteDeControlListView) {
        return getIds(ambienteDeControlListView.getItems().stream().filter(checkBoxModel -> checkBoxModel.getChecked().getValue()).map(CheckBoxModel::getEmployee).toList());
    }

    public List<Integer> getIds(List<ObjectProperty<EmployeeEntity>> employees) {
        return employees.stream().map(ObjectProperty::get).map(EmployeeEntity::getId).toList();
    }

    public void initKeyCombinations(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (kc.match(event)) onHomeButtonClicked(null);
            else if (kc2.match(event)) onEnterpriseButtonClicked(null);
            else if (kc3.match(event)) onTestCreationButtonClicked(null);
            else if (kc4.match(event)) onTestEvaluateButtonClicked(null);
            else if (kc5.match(event)) onReportGenerationButtonClicked(null);
            else if (kc6.match(event)) onHelpButtonClicked(null);
            else if (kc7.match(event)) onConfigButtonClicked(null);
        });
    }

    @FXML
    public void onHomeButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(true, false, false, false, false, false, false);
    }

    @FXML
    public void onEnterpriseButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, true, false, false, false, false, false);
        initEnterpriseCreationView();
    }

    @FXML
    public void onTestCreationButtonClicked(MouseEvent mouseEvent) {
        initTestCreationView();
        visibilityChange(false, false, true, false, false, false, false);
    }

    @FXML
    public void onTestEvaluateButtonClicked(MouseEvent mouseEvent) {
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

    void initTestCreationView() {
        guideVersionChoiceBox.getItems().setAll(GuideConfig.testTemplates.stream().map(testResult -> testResult.getTest().getGuideVersion()).collect(Collectors.toList()));
        guideVersionChoiceBox.getSelectionModel().selectFirst();
        var employees = EmployeeService.findAll();
        setCellsFactories();
        // set the items into the listviews
        setItems(employees, ambienteDeControlListView, actividadesDeControlListView, informacionYComunicacionListView, supervicionYMonitoreoListView, gestionYPrevencionListView);
        // set the items into the evaluated listviews
        setItems(employees, ambienteDeControlListViewEvated, actividadesDeControlListViewEvated, informacionYComunicacionListViewEvated, supervicionYMonitoreoListViewEvated, gestionYPrevencionListViewEvated);

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

    private void initConfigurationView() {
        databaseConfigTextField.setText(EntityManagerProvider.DATABASE);
        userConfigTextField.setText(EntityManagerProvider.USER);
        passwordConfigTextField.setText(EntityManagerProvider.PASSWORD);
    }

    private void setCellsFactories() {
        setCellFactory(ambienteDeControlListView);
        setCellFactory(actividadesDeControlListView);
        setCellFactory(informacionYComunicacionListView);
        setCellFactory(supervicionYMonitoreoListView);
        setCellFactory(gestionYPrevencionListView);
        setCellFactory(ambienteDeControlListViewEvated);
        setCellFactory(actividadesDeControlListViewEvated);
        setCellFactory(informacionYComunicacionListViewEvated);
        setCellFactory(supervicionYMonitoreoListViewEvated);
        setCellFactory(gestionYPrevencionListViewEvated);
    }

    private void setItems(List<EmployeeEntity> employees, JFXListView<CheckBoxModel> ambienteDeControlListView, JFXListView<CheckBoxModel> actividadesDeControlListView, JFXListView<CheckBoxModel> informacionYComunicacionListView, JFXListView<CheckBoxModel> supervicionYMonitoreoListView, JFXListView<CheckBoxModel> gestionYPrevencionListView) {
        ambienteDeControlListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        actividadesDeControlListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        informacionYComunicacionListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        supervicionYMonitoreoListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
        gestionYPrevencionListView.getItems().setAll(employees.stream().map(employeeEntity -> new CheckBoxModel(employeeEntity, false)).toList());
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

    private void setCellFactory(ListView<CheckBoxModel> listView) {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CheckBoxModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    var checkBox = new CheckBox();
                    checkBox.setSelected(item.getChecked().getValue());
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> item.setChecked(new SimpleBooleanProperty(isSelected)));

                    setText(item.getEmployee().getValue().getEmployeeName());
                    setGraphic(checkBox);
                }
            }
        });
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