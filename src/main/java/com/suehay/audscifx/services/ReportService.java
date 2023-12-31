package com.suehay.audscifx.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.suehay.audscifx.model.EvaluatedComponentEntity;
import com.suehay.audscifx.model.EvaluatorComponentEntity;
import com.suehay.audscifx.model.templates.TestResult;

import java.io.FileOutputStream;
import java.util.stream.Collectors;

import static com.suehay.audscifx.model.enums.ReportFonts.*;

public class ReportService {
    private static final String FILE = "c:/temp/FirstPdf.pdf";
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
            addFirstPage(document);
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

    private static void addFirstPage(Document document)
            throws DocumentException {
        var firstPage = new Paragraph();
        addEmptyLine(firstPage, 1);
        addText(firstPage, "\t\t\tAUDITORIA DE CONTROL INTERNO", CAT_FONT.getFont(), 2);
        addText(firstPage, "\tAutocontrol: " + testResult.getTest().getCode(), CAT_FONT.getFont(), 1);
        addText(firstPage, "\tControl Integral: \t Supervicion o control parcial: \t Autocontrol: \t Otras: ", SMALL_BOLD.getFont(), 1);
        addText(firstPage, "\tArea de regulacion y control:  ", SMALL_BOLD.getFont(), 1);
        addText(firstPage, "\tActividad controlada: ", SMALL_BOLD.getFont(), 1);
        addText(firstPage, "\tFecha de realizacion: ", SMALL_BOLD.getFont(), 4);
        addText(firstPage, "\tPersonal evaluador: ", SMALL_BOLD.getFont(), 1);
        firstPage.add(createEmployeesTable(0));
        addText(firstPage, "\tPersonal evaluado: ", SMALL_BOLD.getFont(), 1);
        firstPage.add(createEmployeesTable(1));
        document.add(firstPage);
        document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) paragraph.add(new Paragraph(" "));
    }

    private static void addText(Paragraph paragraph, String text, Font font, int lines) {
        paragraph.add(new Paragraph(text, font));
        addEmptyLine(paragraph, lines);
    }

    private static Chapter createEmployeesTable(int employeeType) throws BadElementException {
        var catPart = new Chapter(new Paragraph(new Anchor("Evaluators", CAT_FONT.getFont())), 1);
        var subCatPart = catPart.addSection(new Paragraph("Evaluators", SUB_FONT.getFont()));
        String[] headers = {"Evaluador", "Cargo", "Firma"};
        int rows = 1000, cols = 3;
        var data = new String[rows][cols];
        var employees = EmployeeService.findAll()
                                       .stream()
                                       .filter(employeeEntity -> employeeType == 0 ?
                                               testResult
                                                       .getEvaluatorComponents()
                                                       .stream()
                                                       .map(EvaluatorComponentEntity::getEmployeeId)
                                                       .toList()
                                                       .contains(employeeEntity.getId()) :
                                               testResult
                                                       .getEvaluatedComponents()
                                                       .stream()
                                                       .map(EvaluatedComponentEntity::getEmployeeId)
                                                       .toList()
                                                       .contains(employeeEntity.getId()))
                                       .collect(Collectors.toSet());
        var i = 0;
        for (var employee : employees) data[i++] = new String[]{employee.getEmployeeName(), employee.getPosition(), ""};
        createTable(subCatPart, headers, rows, cols, data);
        return catPart;
    }

    private static void createTable(Section subCatPart, String[] headers, int rows, int cols, String[][] data) {
        var table = new PdfPTable(cols);
        for (String header : headers) {
            var cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        table.setHeaderRows(1);
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                table.addCell(data[row][col]);
        subCatPart.add(table);
    }

    private static void createList(Section subCatPart) {
        var list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }
}