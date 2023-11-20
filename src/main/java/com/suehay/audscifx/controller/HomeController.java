package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.suehay.audscifx.model.AreaEntity;
import com.suehay.audscifx.model.ComponentEntity;
import com.suehay.audscifx.model.EmployeeEntity;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ListActionView;
import org.controlsfx.control.ListSelectionView;

public class HomeController {
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
    public JFXListView<AreaEntity> areasListView;
    @FXML
    public JFXListView<EmployeeEntity> employeeListView;
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
    public TextField guideVersionTestTextField;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatorsControlAmbListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatorsGestionPrevListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatorsActivitiesCntrlListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatorsInformacionComListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatorsSupervisionMonitListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatedControlAmbListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatedGestationPrevListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatedActivitiesCntrlListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatedInformacionComListSelectView;
    @FXML
    public ListSelectionView<EmployeeEntity> evaluatedSupervicionMonitListSelectView;
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
    }

    @FXML
    public void onTestCreationButtonClicked(MouseEvent mouseEvent) {
        visibilityChange(false, false, true, false, false, false, false);
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
        if (this.areaNameTextField.getText().isEmpty()) return;
        this.areasListView.getItems().add(new AreaEntity(this.areasListView.getItems().size() + 1, this.areaNameTextField.getText()));
        if (this.areasListView.getSelectionModel().getSelectedItem() != null)
            latestArea = areasListView.getItems().get(Math.max((this.areasListView.getSelectionModel().getSelectedItem().getId() - 1), 0));
        areasListView.getItems().addListener((ListChangeListener<? super AreaEntity>) c -> {
            if (!areasListView.getItems().isEmpty()) {
                areasListView.getSelectionModel().select(latestArea);
            }
        });
    }

    @FXML
    public void onAreaRemoveButtonClicked(MouseEvent mouseEvent) {
        if (this.areasListView.getSelectionModel().getSelectedItem() == null) return;
        latestArea = areasListView.getItems().get(Math.max((this.areasListView.getSelectionModel().getSelectedItem().getId() - 1), 0));
        this.areasListView.getItems().remove(this.areasListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onEmployeeAddButtonClicked(MouseEvent mouseEvent) {
        if (this.employeeNameTextField.getText().isEmpty() || this.employeePositionTextField.getText().isEmpty()) return;
        var employee = EmployeeEntity.builder()
                                     .id(this.employeeListView.getItems().size() + 1)
                                     .employeeName(this.employeeNameTextField.getText())
                                     .position(this.employeePositionTextField.getText())
                                     .build();
        this.employeeListView.getItems().add(employee);
        employee.setAreaId(this.areasListView.getSelectionModel().getSelectedItem().getId());
        System.out.println(employee);
    }

    @FXML
    public void onEmployeeRemoveButtonClicked(MouseEvent mouseEvent) {
        if (this.employeeListView.getSelectionModel().getSelectedItem() == null) return;
        this.employeeListView.getItems().remove(this.employeeListView.getSelectionModel().getSelectedItem());
    }
}