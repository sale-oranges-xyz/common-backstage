package com.github.geng.excel.writer.part;

import java.util.List;

/**
 * 写入excel 一页记录
 */
public class ExcelSheet {

    private List<ExcelRow> rows;                 // 总的行记录数据
    private String sheetName;                    // 页脚名称

    // constructors ============================================
    public ExcelSheet(List<ExcelRow> rows, String sheetName) {
        this.rows = rows;
        this.sheetName = sheetName;
    }

    // getters / setters =======================================
    public void setRows(List<ExcelRow> rows) {
        this.rows = rows;
    }
    public List<ExcelRow> getRows() {
        return rows;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
    public String getSheetName() {
        return sheetName;
    }
}
