/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utilitarios.salutem;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
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

    public static SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    public PDFDocument(String name, String pageSize, boolean rotate) {
        try {
            Calendar c = Calendar.getInstance();
            tempFile = File.createTempFile("DocumentoTemporal" + c.getTimeInMillis(), ".pdf");
            PdfWriter writer = new PdfWriter(tempFile);
            pdfDocument = new PdfDocument(writer);

            switch (pageSize) {
                case "A0":
                    document = new Document(pdfDocument, rotate ? PageSize.A0.rotate() : PageSize.A0);
                    break;
                case "A1":
                    document = new Document(pdfDocument, rotate ? PageSize.A1.rotate() : PageSize.A1);
                    break;
                case "A2":
                    document = new Document(pdfDocument, rotate ? PageSize.A2.rotate() : PageSize.A2);
                    break;
                case "A3":
                    document = new Document(pdfDocument, rotate ? PageSize.A3.rotate() : PageSize.A3);
                    break;
                case "A4":
                    document = new Document(pdfDocument, rotate ? PageSize.A4.rotate() : PageSize.A4);
                    break;
                case "A5":
                    document = new Document(pdfDocument, rotate ? PageSize.A5.rotate() : PageSize.A5);
                    break;
                case "A6":
                    document = new Document(pdfDocument, rotate ? PageSize.A6.rotate() : PageSize.A6);
                    break;
                case "A7":
                    document = new Document(pdfDocument, rotate ? PageSize.A7.rotate() : PageSize.A7);
                    break;
                case "A8":
                    document = new Document(pdfDocument, rotate ? PageSize.A8.rotate() : PageSize.A8);
                    break;
                case "A9":
                    document = new Document(pdfDocument, rotate ? PageSize.A9.rotate() : PageSize.A9);
                    break;
                case "A10":
                    document = new Document(pdfDocument, rotate ? PageSize.A10.rotate() : PageSize.A10);
                    break;
                case "B0":
                    document = new Document(pdfDocument, rotate ? PageSize.B0.rotate() : PageSize.B0);
                    break;
                case "B1":
                    document = new Document(pdfDocument, rotate ? PageSize.B1.rotate() : PageSize.B1);
                    break;
                case "B2":
                    document = new Document(pdfDocument, rotate ? PageSize.B1.rotate() : PageSize.B2);
                    break;
                case "B3":
                    document = new Document(pdfDocument, rotate ? PageSize.B3.rotate() : PageSize.B3);
                    break;
                case "B4":
                    document = new Document(pdfDocument, rotate ? PageSize.B4.rotate() : PageSize.B4);
                    break;
                case "B5":
                    document = new Document(pdfDocument, rotate ? PageSize.B5.rotate() : PageSize.B5);
                    break;
                case "B6":
                    document = new Document(pdfDocument, rotate ? PageSize.B6.rotate() : PageSize.B6);
                    break;
                case "B7":
                    document = new Document(pdfDocument, rotate ? PageSize.B7.rotate() : PageSize.B7);
                    break;
                case "B8":
                    document = new Document(pdfDocument, rotate ? PageSize.B8.rotate() : PageSize.B8);
                    break;
                case "B9":
                    document = new Document(pdfDocument, rotate ? PageSize.B9.rotate() : PageSize.B9);
                    break;
                case "B10":
                    document = new Document(pdfDocument, rotate ? PageSize.B10.rotate() : PageSize.B10);
                    break;
                case "LETTER":
                    document = new Document(pdfDocument, rotate ? PageSize.LETTER.rotate() : PageSize.LETTER);
                    break;
                case "LEGAL":
                    document = new Document(pdfDocument, rotate ? PageSize.LEGAL.rotate() : PageSize.LEGAL);
                    break;
                case "TABLOID":
                    document = new Document(pdfDocument, rotate ? PageSize.TABLOID.rotate() : PageSize.TABLOID);
                    break;
                case "LEDGER":
                    document = new Document(pdfDocument, rotate ? PageSize.LEDGER.rotate() : PageSize.LEDGER);
                    break;
                case "EXECUTIVE":
                    document = new Document(pdfDocument, rotate ? PageSize.EXECUTIVE.rotate() : PageSize.EXECUTIVE);
                    break;
                default:
                    document = new Document(pdfDocument, rotate ? PageSize.A4.rotate() : PageSize.A4);
            }

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

    public void agregarTabla(List<PDFCampo> titulos, List<PDFCampo> campos, float[] columnas, char align, PDFCampo titulo, boolean salto) {
        UnitValue[] uv = new UnitValue[columnas.length];
        int i = 0;
        for (float f : columnas) {
            uv[i++] = new UnitValue(UnitValue.PERCENT, f);
        }

        Table table = new Table(uv);
        table.setWidth(UnitValue.createPercentValue(100));
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
        if (titulos != null) {
            for (PDFCampo t : titulos) {
                table.addCell(crearCelda(t));
            }
        }
        if (campos != null) {
            for (PDFCampo c : campos) {
                table.addCell(crearCelda(c));
            }
        }

        document.add(table);
        if (salto) {
            agregarLineas(1);
        }

    }

    public void agregarParrafo(PDFCampo campo, boolean salto) {
        document.add((Paragraph) getCampo("Paragraph", campo));
        if (salto) {
            agregarLineas(1);
        }
    }

    public void agregarLineas(int numero) {
        for (int i = 0; i < numero; i++) {
            document.add(new Paragraph("\n"));
        }
    }

    public void agregarImagen(byte[] imagen, int height, char horizontalAlign) {
        if (imagen != null) {
            Image element = new Image(ImageDataFactory.create(imagen));
            element.setHeight(height);
            switch (horizontalAlign) {
                case 'L':
                    element.setHorizontalAlignment(HorizontalAlignment.LEFT);
                    break;
                case 'C':
                    element.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    break;
                case 'R':
                    element.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                    break;
                default:
                    break;
            }
            document.add(element);
        }
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
                element.setBorderLeft(new SolidBorder(new Float(0.10)));
            }
            if (campo.getBorderSide().contains("R")) {
                element.setBorderRight(new SolidBorder(new Float(0.10)));
            }
            if (campo.getBorderSide().contains("T")) {
                element.setBorderTop(new SolidBorder(new Float(0.10)));
            }
            if (campo.getBorderSide().contains("B")) {
                element.setBorderBottom(new SolidBorder(new Float(0.10)));
            }
        }

        return element;
    }
}
