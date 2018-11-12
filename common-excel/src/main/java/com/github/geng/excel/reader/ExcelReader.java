package com.github.geng.excel.reader;

import java.io.File;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelReader {
	
	/**
	 * 通过文件读取excel
	 * @param file 读取excel文件
	 * @return excel 工作簿对象
	 */
	Workbook readExcel(File file) throws IOException;


}
