package com.suehay.audscifx.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.suehay.audscifx.model.EvaluatedComponentEntity;
import com.suehay.audscifx.model.EvaluatorComponentEntity;
import com.suehay.audscifx.model.templates.ComponentTemplate;
import com.suehay.audscifx.model.templates.RegulationTemplate;
import com.suehay.audscifx.model.templates.TestResult;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.suehay.audscifx.model.enums.ReportFonts.*;

public class ReportService {
    private static final String FILE = "/media/victor/E808BD0F08BCDE2E1/15Work/AUDSCI/app-02/AUDSCI-FX01/src/main/resources/reports/report.pdf";
    private static TestResult testResult;

    public static void printReport(TestResult testResult) {
        ReportService.testResult = testResult;
        create();
    }

    public static void create() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            var firstPage = new Paragraph();
            addEmptyLine(firstPage, 1);
            addText(firstPage, "\t\t\tAUDITORIA DE CONTROL INTERNO", CAT_FONT.getFont(), 2);
            addText(firstPage, "\tAutocontrol: " + testResult.getTest().getCode(), CAT_FONT.getFont(), 1);
            addText(firstPage, "\tControl Integral: \t Supervicion o control parcial: \t Autocontrol: \t Otras: ", SMALL_NORMAL.getFont(), 1);
            addPage(0, document, firstPage);
            for (int i = 1; i < 5; i++) addPage(1, document, new Paragraph());

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("ACI");
        document.addSubject("Using AUDSCI");
        document.addKeywords("AUDSCI, PDF, iText");
        document.addAuthor("Administrator");
        document.addCreator("Administrator");
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) paragraph.add(new Paragraph(" "));
    }

    private static void addText(Paragraph paragraph, String text, Font font, int lines) {
        paragraph.add(new Paragraph(text, font));
        addEmptyLine(paragraph, lines);
    }

    private static void addPage(int component, Document document, Paragraph page)
            throws DocumentException {
        addText(page, "\tArea de regulacion y control:  ", SMALL_NORMAL.getFont(), 1);
        addText(page, "\tActividad controlada: ", SMALL_NORMAL.getFont(), 1);
        addText(page, "\tFecha de realizacion: ", SMALL_NORMAL.getFont(), 4);
        addText(page, "\tPersonal evaluador: ", SMALL_NORMAL.getFont(), 1);
        var evaluatorComponenents = EvaluatorComponentService.findAllByComponentId(component+1);
        var evaluatedComponenents = EvaluatedComponentService.findAllByComponentId(component+1);
        var table = createEmployeesTable(component, 0, evaluatorComponenents, evaluatedComponenents);
        page.add(table);
        addText(page, "\tPersonal evaluado: ", SMALL_NORMAL.getFont(), 1);
        table = createEmployeesTable(component, 1, evaluatorComponenents, evaluatedComponenents);
        page.add(table);
        addSummary(page);
        document.add(page);
        if (component == 4) {
            addText(page, "\t__________________________________________", SUB_FONT.getFont(), 1);
            addText(page, "\t\t\tDirector General.", SUB_FONT.getFont(), 1);
            return;
        }
        document.newPage();
    }

    private static Paragraph createEmployeesTable(int component, int employeeType, Collection<? extends EvaluatorComponentEntity> evaluatorComponenents, Collection<? extends EvaluatedComponentEntity> evaluatedComponenents) {
        var catPart = new Paragraph(new Anchor( employeeType == 0 ?"Evaluadores":"Evaluados", CAT_FONT.getFont()));
        String[] headers = { employeeType == 0 ?"Evaluador":"Evaluado", "Cargo", "Firma"};
        int rows = 1000, cols = 3;
        var data = new String[rows][cols];
        var employees = EmployeeService.findAll()
                                       .stream()
                                       .filter(employeeEntity -> employeeType == 0 ?
                                               evaluatorComponenents
                                                       .stream()
                                                       .toList()
                                                       .stream()
                                                       .anyMatch(evaluatorComponentEntity -> evaluatorComponentEntity.getEmployeeId().equals(employeeEntity.getId())) :
                                               evaluatedComponenents
                                                       .stream()
                                                       .toList()
                                                       .stream()
                                                       .anyMatch(evaluatedComponentEntity -> evaluatedComponentEntity.getEmployeeId().equals(employeeEntity.getId())))
                                       .collect(Collectors.toSet());
        var i = 0;
        for (var employee : employees) data[i++] = new String[]{employee.getEmployeeName(), employee.getPosition(), ""};
        createTable(catPart, headers, rows, cols, data);
        return catPart;
    }

    private static void addSummary(Paragraph page) {
        addEmptyLine(page, 1);
        addText(page, "\tObjetivo de control: ", SUB_FONT.getFont(), 5);
        addText(page, "\tCriterios revisados: ", SUB_FONT.getFont(), 5);
        addText(page, "\tResumen de hallazgos: ", SUB_FONT.getFont(), 1);
        createSummary(page);
        addText(page, "\tConclusiones: ", SUB_FONT.getFont(), 8);
        addText(page, "\tCausas y condiciones: ", SUB_FONT.getFont(), 8);
        addText(page, "\tRecomendaciones: ", SUB_FONT.getFont(), 8);
    }

    private static void createTable(Paragraph subCatPart, String[] headers, int rows, int cols, String[][] data) {
        var table = new PdfPTable(cols);
        for (String header : headers) {
            var cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        table.setHeaderRows(1);
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                table.addCell(new PdfPCell(new Phrase(data[row][col])));
        subCatPart.add(table);
    }

    private static void createSummary(Paragraph page) {
        var i = new AtomicInteger();
        testResult.getTest().getComponentTemplates().forEach(component -> {
            addText(page, "\t" + i.getAndIncrement() + "\t" + component.getLabel(), SMALL_NORMAL.getFont(), 1);
            addComponentSummary(page, component);
        });
    }

    private static void addComponentSummary(Paragraph page, ComponentTemplate component) {
        var i = new AtomicInteger();
        component.getRegulationTemplates().forEach(regulation -> {
            addText(page, "\t" + i.getAndIncrement() + "\t" + regulation.getLabel(), SMALL_NORMAL.getFont(), 1);
            addRegulationSummary(page, regulation);
        });
    }

    private static void addRegulationSummary(Paragraph page, RegulationTemplate regulation) {
        var i = new AtomicInteger();
        regulation.getQuestionTemplates().forEach(question -> {
            if (!question.getResult())
                addText(page, "\t" + i.getAndIncrement() + "\tNo se cumple: " + question.getLabel(), SMALL_NORMAL.getFont(), 1);
            if (!question.getSubQuestions().isEmpty()) {
                var j = new AtomicInteger();
                question.getSubQuestions().forEach(subQuestion -> {
                    if (!subQuestion.getResult())
                        addText(page, "\t" + j.getAndIncrement() + " - " + subQuestion.getId() + "\tNo se cumple: " + subQuestion.getLabel(),
                                SMALL_NORMAL.getFont(), 1);
                });
            }
        });
    }
}