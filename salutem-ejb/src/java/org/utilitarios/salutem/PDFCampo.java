/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package org.utilitarios.salutem;

/**
 *
 * @author luis
 */
public class PDFCampo {

    private String type;
    private Object value;
    private char textAlign;
    private String style;
    private int colspan;
    private int rowspan;
    private int size;
    private String borderSide;

    public PDFCampo(String type, Object value, char textAlign, String style, int colspan, int rowspan, int size, String border) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = textAlign;
        this.style = style;
        this.colspan = colspan != 0 ? colspan : 1;
        this.rowspan = rowspan != 0 ? rowspan : 1;
        this.size = size != 0 ? size : 10;
        this.borderSide = border;

    }

    public PDFCampo(String type, Object value) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = 'C';
        this.style = "";
        this.colspan = 1;
        this.rowspan = 1;
        this.size = 10;
        this.borderSide = ""; //NO_BORDER
    }

    public PDFCampo(String type, Object value, String style) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = 'C';
        this.style = style;
        this.colspan = colspan != 0 ? colspan : 1;
        this.rowspan = 1;
        this.size = 10;
        this.borderSide = ""; //NO_BORDER
    }

    public PDFCampo(String type, Object value, String style, String border) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = 'C';
        this.style = style;
        this.colspan = colspan != 0 ? colspan : 1;
        this.rowspan = 1;
        this.size = 10;
        this.borderSide = border;
    }

    public PDFCampo(String type, Object value, int colspan, int rowspan, String border) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = 'C';
        this.style = "";
        this.colspan = colspan;
        this.rowspan = rowspan;
        this.size = 10;
        this.borderSide = border;
    }

    public PDFCampo(String type, Object value, int colspan, String border) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = 'C';
        this.style = "";
        this.colspan = colspan;
        this.rowspan = 1;
        this.size = 10;
        this.borderSide = border;
    }

    public PDFCampo(String type, Object value, String style, int colspan, String border) {
        this.type = type != null ? type : "String";
        this.value = value != null ? value : "";
        this.textAlign = 'C';
        this.style = style;
        this.colspan = colspan;
        this.rowspan = 1;
        this.size = 10;
        this.borderSide = border;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the textAlign
     */
    public char getHorizontalAlign() {
        return textAlign;
    }

    /**
     * @param textAlign the textAlign to set
     */
    public void setHorizontalAlign(char textAlign) {
        this.textAlign = textAlign;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the colspan
     */
    public int getColspan() {
        return colspan;
    }

    /**
     * @param colspan the colspan to set
     */
    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    /**
     * @return the rowspan
     */
    public int getRowspan() {
        return rowspan;
    }

    /**
     * @param rowspan the rowspan to set
     */
    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the borderSide
     */
    public String getBorderSide() {
        return borderSide;
    }

    /**
     * @param borderSide the borderSide to set
     */
    public void setBorderSide(String borderSide) {
        this.borderSide = borderSide;
    }

}
