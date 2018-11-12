package com.github.geng.excel.writer;

import com.github.geng.excel.writer.part.ExcelCell;
import com.github.geng.excel.writer.part.ExcelRow;
import com.github.geng.excel.writer.part.ExcelSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;


/**
 * excel 输出接口
 */
public class ExcelWriter {

    public Workbook createWriteWorkbook(List<ExcelSheet> excelSheets) {
        //创建HSSFWorkbook对象
        Workbook workbook = new HSSFWorkbook();

        excelSheets.forEach((excelSheet) -> {
            //创建HSSFSheet对象
            Sheet sheet = workbook.createSheet(excelSheet.getSheetName());

            //创建HSSFRow对象
            for (int i = 0; i < excelSheet.getRows().size(); i++) {
                Row row = sheet.createRow(i);

                //创建HSSFCell对象
                ExcelRow excelRows = excelSheet.getRows().get(i);
                List<Object> excelCells = excelRows.getCellList();
                for (int j = 0; j < excelCells.size(); j++)  {
                    //设置单元格的值
                    this.translateCellValue(row.createCell(j), excelCells.get(j));
                }
            }
        });

        // 返回创建后对象
        return workbook;
    }

    public Workbook createWriteWorkbook(ExcelSheet wroteSheet) {
        List<ExcelSheet> wroteSheetList = new ArrayList<>();
        wroteSheetList.add(wroteSheet);
        return this.createWriteWorkbook(wroteSheetList);
    }

    // private methods ===========================================
    private void translateCellValue(Cell cell, Object value) {
        // TODO 暂时全部转为String
        cell.setCellValue(value.toString());
    }
}
