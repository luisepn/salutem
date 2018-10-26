/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utilitarios.salutem;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.test.annotations.WrapToTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WrapToTest
public class PDFDocument {

    public Document document;
    public PdfDocument pdfDocument;
    public File tempFile;

    public PDFDocument(String nombre) {
        try {
            Calendar c = Calendar.getInstance();
            tempFile = File.createTempFile("DocumentoTemporal" + c.getTimeInMillis(), ".pdf");
            PdfWriter writer = new PdfWriter(tempFile);
            pdfDocument = new PdfDocument(writer);
            document = new Document(pdfDocument);
        } catch (IOException ex) {
            Logger.getLogger(PDFDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Recurso traerRecurso() {
        pdfDocument.close();
        try {
            Recurso retorno = new Recurso(Files.readAllBytes(Paths.get(tempFile != null ? tempFile.getAbsolutePath() : "")));
            Calendar c = Calendar.getInstance();
            retorno.setResourceName("Recurso_" + c.getTimeInMillis());
            return retorno;
        } catch (IOException ex) {
            Logger.getLogger(PDFDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Cell crearCelda(PDFCampo campo) {
        return (Cell) getCampo("Cell", campo);
    }

    public void agregarTabla(List<PDFCampo> titulos, List<PDFCampo> campos, int columnas, char align) {
        Table table = new Table(columnas);
        switch (align) {
            case 'L':
                table.setHorizontalAlignment(HorizontalAlignment.LEFT);
                break;
            case 'C':
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                break;
            case 'R':
                table.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                break;
            default:
                break;
        }

        for (PDFCampo t : titulos) {
            table.addCell(crearCelda(t));
        }

        for (PDFCampo c : campos) {
            table.addCell(crearCelda(c));
        }

        document.add(table);

    }

    public void agregarTabla(List<PDFCampo> titulos, List<PDFCampo> campos, float[] with, char align) {
        Table table = new Table(with);
        switch (align) {
            case 'L':
                table.setHorizontalAlignment(HorizontalAlignment.LEFT);
                break;
            case 'C':
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                break;
            case 'R':
                table.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                break;
            default:
                break;
        }

        for (PDFCampo t : titulos) {
            table.addCell(crearCelda(t));
        }

        for (PDFCampo c : campos) {
            table.addCell(crearCelda(c));
        }

        document.add(table);

    }

    public void agregarParrafo(PDFCampo campo) {
        document.add((Paragraph) getCampo("Paragraph", campo));
    }

    private Object getCampo(String tipo, PDFCampo campo) {

        String texto = new String();

        switch (campo.getType()) {
            case "String":
                texto = (String) campo.getValue();
                break;
            case "Integer":
                texto = ((Integer) campo.getValue()).toString();
                break;
            case "Double":
                texto = ((Double) campo.getValue()).toString();
                break;
            case "Float":
                texto = ((Float) campo.getValue()).toString();
                break;
            case "Boolean":
                texto = ((Boolean) campo.getValue()) ? "SÍ" : "NO";
                break;
        }

        BlockElement element;

        switch (tipo) {
            case "Cell":
                element = new Cell(campo.getRowspan(), campo.getColspan());
                Paragraph valor = new Paragraph(texto);
                ((Cell) element).add(valor);
                element.setBorder(Border.NO_BORDER);
                break;
            case "Paragraph":
                element = new Paragraph(texto);
                break;
            default:
                element = new Paragraph();
                break;
        }

        element.setFontSize(campo.getSize());

        switch (campo.getHorizontalAlign()) {
            case 'L':
                element.setTextAlignment(TextAlignment.LEFT);
                break;
            case 'C':
                element.setTextAlignment(TextAlignment.CENTER);
                break;
            case 'R':
                element.setTextAlignment(TextAlignment.RIGHT);
                break;
            default:
                break;
        }

        if (campo.getStyle() != null
                && !campo.getStyle().isEmpty()) {
            if (campo.getStyle().contains("I")) {
                element.setItalic();
            }
            if (campo.getStyle().contains("B")) {
                element.setBold();
            }
            if (campo.getStyle().contains("U")) {
                element.setUnderline();
            }
        }

        if (campo.getBorderSide() != null) {
            if (campo.getBorderSide().contains("L")) {
                element.setBorderLeft(new DoubleBorder(1));
            }
            if (campo.getBorderSide().contains("R")) {
                element.setBorderRight(new DoubleBorder(1));
            }
            if (campo.getBorderSide().contains("T")) {
                element.setBorderTop(new DoubleBorder(1));
            }
            if (campo.getBorderSide().contains("B")) {
                element.setBorderBottom(new DoubleBorder(1));
            }
        }

        return element;
    }
}
