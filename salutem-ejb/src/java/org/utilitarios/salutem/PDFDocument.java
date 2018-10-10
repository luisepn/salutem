/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utilitarios.salutem;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.test.annotations.WrapToTest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WrapToTest
public class PDFDocument {

    public PDFDocument(String nombre) {
        try {
            Calendar c = Calendar.getInstance();
            File temp = File.createTempFile("DocumentoTemporal" + c.getTimeInMillis(), ".pdf");
        } catch (IOException ex) {
            Logger.getLogger(PDFDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Cell crearCelda(PDFCampo campo) {
        Cell celda = new Cell(campo.getRowspan(), campo.getColspan());
        celda.setFontSize(campo.getSize());

        switch (campo.getVerticalAlign()) {
            case 'T':
                celda.setVerticalAlignment(VerticalAlignment.TOP);
                break;
            case 'M':
                celda.setVerticalAlignment(VerticalAlignment.MIDDLE);
                break;
            case 'B':
                celda.setVerticalAlignment(VerticalAlignment.BOTTOM);
                break;
        }

        switch (campo.getHorizontalAlign()) {
            case 'L':
                celda.setHorizontalAlignment(HorizontalAlignment.LEFT);
                break;
            case 'C':
                celda.setHorizontalAlignment(HorizontalAlignment.CENTER);
                break;
            case 'R':
                celda.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                break;
        }

        switch (campo.getStyle()) {
            case 'I':
                celda.setItalic();
                break;
            case 'B':
                celda.setBold();
                break;
            case 'U':
                celda.setUnderline();
                break;
        }

        Paragraph texto;
        switch (campo.getType()) {
            case "String":
                texto = new Paragraph((String) campo.getValue());
                break;
        }

        campo.setValue(celda);

        return null;
    }

    private void agregarTabla(List<PDFCampo> titulos, List<PDFCampo> campos) {
        Table table = new Table(8);

        for (PDFCampo t : titulos) {
            table.addCell(crearCelda(t));
        }

        for (PDFCampo c : campos) {

        }

    }

    public void createPdf(File dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(new Paragraph("Hello World!"));
        }
    }

}
