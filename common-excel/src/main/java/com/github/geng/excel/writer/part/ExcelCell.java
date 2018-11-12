package com.github.geng.excel.writer.part;

/**
 * 写入excel 单元格记录
 */
public class ExcelCell {

    private String value;            // 暂时使用String 类型

    // constructors =======================================
    public ExcelCell(String value) {
        this.value = value;
    }

    // getters / setters ==================================
    public void setValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
