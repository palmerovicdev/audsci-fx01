package com.suehay.audscifx.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ListActionView;
import org.controlsfx.control.ListSelectionView;

public class HomeController {
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
    public JFXListView componentsListView;
    @FXML
    public BarChart testResultChart;
    @FXML
    public AnchorPane enterpriseCreationView;
    @FXML
    public ListActionView areasListView;
    @FXML
    public ListActionView employeeListView;
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
    public ListSelectionView evaluatorsControlAmbListSelectView;
    @FXML
    public ListSelectionView evaluatorsGestionPrevListSelectView;
    @FXML
    public ListSelectionView evaluatorsActivitiesCntrlListSelectView;
    @FXML
    public ListSelectionView evaluatorsInformacionComListSelectView;
    public ListSelectionView evaluatorsSupervisionMonitListSelectView;
    @FXML
    public ListSelectionView evaluatedControlAmbListSelectView;
    @FXML
    public ListSelectionView evaluatedGestationPrevListSelectView;
    @FXML
    public ListSelectionView evaluatedActivitiesCntrlListSelectView;
    @FXML
    public ListSelectionView evaluatedInformacionComListSelectView;
    @FXML
    public ListSelectionView evaluatedSupervicionMonitListSelectView;
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
    public void onAreaAddButtonClicked(MouseEvent mouseEvent) {}

    @FXML
    public void onAreaRemoveButtonClicked(MouseEvent mouseEvent) {}

    @FXML
    public void onEmployeeAddButtonClicked(MouseEvent mouseEvent) {}

    @FXML
    public void onEmployeeRemoveButtonClicked(MouseEvent mouseEvent) {}
}