package com.github.geng.excel.reader;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取xlsx格式excel文件
 *
 */
@Slf4j
public class XlsxExcelReader implements ExcelReader {


	public Workbook readExcel(File file) throws IOException {
		try {
			return new XSSFWorkbook(file);
		} catch (InvalidFormatException e) {
			log.error("创建xlsx格式excel workbook失败,原因:", e);
			return null;
		}
	}
	
}
