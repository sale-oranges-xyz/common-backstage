package com.github.geng.excel.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 读取xls格式的excel文件
 * geng
 */
@Slf4j
public class XlsExcelReader implements ExcelReader {

	public Workbook readExcel(File file) throws IOException {
		try {
			return new HSSFWorkbook(new FileInputStream(file));
		} catch (IOException e) {
			log.error("创建 xls格式的excel workbook失败,原因:", e);
			return null;
		}
	}
}
