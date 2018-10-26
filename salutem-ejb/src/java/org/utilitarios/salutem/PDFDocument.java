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
import com.itextpdf.layout.borders.SolidBorder;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.entidades.salutem.Archivos;

@WrapToTest
public class PDFDocument {

    private Document document;
    private PdfDocument pdfDocument;
    private File tempFile;

    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

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

    public void agregarTabla(List<PDFCampo> titulos, List<PDFCampo> campos, int columnas, char align, PDFCampo titulo) {
        Table table = new Table(columnas);
        if (titulo != null) {
            table.addCell(crearCelda(titulo));
        }
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

    public void agregarTabla(List<PDFCampo> titulos, List<PDFCampo> campos, float[] with, char align, PDFCampo titulo) {
        Table table = new Table(with);
        if (titulo != null) {
            table.addCell(crearCelda(titulo));
        }
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
                texto = campo.getValue() != null ? (String) campo.getValue() : "";
                break;
            case "Integer":
                texto = campo.getValue() != null ? ((Integer) campo.getValue()).toString() : "";
                break;
            case "Double":
                texto = campo.getValue() != null ? ((Double) campo.getValue()).toString() : "";
                break;
            case "Float":
                texto = campo.getValue() != null ? ((Float) campo.getValue()).toString() : "";
                break;
            case "Boolean":
                texto = campo.getValue() != null ? ((Boolean) campo.getValue() ? "SÍ" : "NO") : "";
                break;
            case "Date":
                texto = campo.getValue() != null ? formatoFecha.format((Date) campo.getValue()) : "";
                break;
            case "Time":
                texto = campo.getValue() != null ? formatoHora.format((Date) campo.getValue()) : "";
                break;
            case "DateTime":
                texto = campo.getValue() != null ? formatoFechaHora.format((Date) campo.getValue()) : "";
                break;
            case "Selection":
                texto = campo.getValue() != null ? (String) campo.getValue() : "";
                break;
            case "File":
                texto = campo.getValue() != null ? ((Archivos) campo.getValue()).getNombre() : "";
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
                element.setBorderLeft(new SolidBorder(1));
            }
            if (campo.getBorderSide().contains("R")) {
                element.setBorderRight(new SolidBorder(1));
            }
            if (campo.getBorderSide().contains("T")) {
                element.setBorderTop(new SolidBorder(1));
            }
            if (campo.getBorderSide().contains("B")) {
                element.setBorderBottom(new SolidBorder(1));
            }
        }

        return element;
    }
}
