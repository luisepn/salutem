/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package org.salutem.utilitarios;

/**
 *
 * @author luis
 */
public final class PDFCampo {

    private String type;
    private Object value;
    private char textAlign;
    private String style;
    private int colspan;
    private int rowspan;
    private int size;
    private String borderSide;
    private int defaultSize = 8;

    /**
     *
     * @param type
     * @param value
     * @param textAlign
     * @param style
     * @param colspan
     * @param rowspan
     * @param size
     * @param borderSide
     */
    public PDFCampo(String type, Object value, char textAlign, String style, int colspan, int rowspan, int size, String borderSide) {
        inicializar(type, value, textAlign, style, colspan, rowspan, size, borderSide);
    }

    /**
     *
     * @param value
     * @param textAlign
     * @param style
     * @param colspan
     * @param rowspan
     * @param size
     * @param borderSide
     */
    public PDFCampo(Object value, char textAlign, String style, int colspan, int rowspan, int size, String borderSide) {
        inicializar(type, value, textAlign, style, colspan, rowspan, size, borderSide);
    }

    /**
     *
     * @param type
     * @param value
     * @param colspan
     * @param borderSide
     */
    public PDFCampo(String type, Object value, int colspan, String borderSide) {
        inicializar(type, value, 'L', "", colspan, 1, defaultSize, borderSide);
    }

    /**
     *
     * @param value
     * @param style
     * @param borderSide
     */
    public PDFCampo(Object value, String style, String borderSide) {
        inicializar(null, value, 'L', style, 1, 1, defaultSize, borderSide);
    }

    /**
     *
     * @param value
     * @param textAlign
     * @param style
     */
    public PDFCampo(Object value, char textAlign, String style) {
        inicializar(null, value, textAlign, style, 1, 1, defaultSize, "");
    }

    /**
     *
     * @param value
     * @param textAlign
     */
    public PDFCampo(Object value, char textAlign) {
        inicializar(null, value, textAlign, "", 1, 1, defaultSize, "");
    }

    /**
     *
     * @param type
     * @param value
     * @param style
     * @param borderSide
     */
    public PDFCampo(String type, Object value, String style, String borderSide) {
        inicializar(type, value, 'L', style, 1, 1, defaultSize, borderSide);
    }

    /**
     *
     * @param type
     * @param value
     */
    public PDFCampo(String type, Object value) {
        inicializar(type, value, 'L', "", 1, 1, defaultSize, "");
    }
    /**
     *
     * @param value
     */
    public PDFCampo( Object value) {
        inicializar(null, value, 'L', "", 1, 1, defaultSize, "");
    }

    /**
     *
     * @param type
     * @param value
     * @param textAlign
     * @param style
     * @param colspan
     * @param rowspan
     * @param size
     * @param borderSide
     */
    private void inicializar(String type, Object value, char textAlign, String style, int colspan, int rowspan, int size, String borderSide) {
        this.type = type != null ? type : "String";
        this.value = value;
        this.textAlign = textAlign;
        this.style = style;
        this.colspan = colspan != 0 ? colspan : 1;
        this.rowspan = rowspan != 0 ? rowspan : 1;
        this.size = size != 0 ? size : defaultSize;
        this.borderSide = borderSide;
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
     * @param borderSide
     */
    public void setBorderSide(String borderSide) {
        this.borderSide = borderSide;
    }

    /**
     * @return the defaultSize
     */
    public int getDefaultSize() {
        return defaultSize;
    }

    /**
     * @param defaultSize the defaultSize to set
     */
    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

}
