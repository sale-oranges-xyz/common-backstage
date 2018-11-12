package com.github.geng.excel.writer.part;

import java.util.List;

/**
 * 写入excel 行记录
 */
public class ExcelRow {

    private List<Object> cellList;   // 每行的单元格记录

    // constructors ======================================
    public ExcelRow(List<Object> cellList) {
        this.cellList = cellList;
    }

    // getters / setters =================================
    public void setCellList(List<Object> cellList) {
        this.cellList = cellList;
    }
    public List<Object> getCellList() {
        return cellList;
    }
}
