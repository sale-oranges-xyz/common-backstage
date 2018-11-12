package com.github.geng.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.geng.excel.reader.ExcelReader;
import com.github.geng.excel.reader.ExcelReaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;


/**
 * excel处理工具
 */
@Slf4j
public class ExcelManger {

	//=================================================================
	//fields
	private String excelPath;		  	//excel路径
	private File excelFile;			  	//excel文件
	private boolean onlyReadOneSheet; 	//是否只读第一个页签
	//=================================================================
	//constructor
	/**
	 * @param excelPath excel 文件路径
	 * @param onlyReadOneSheet 是否只读第一页 true 是 | false 读全部 
	 */
	public ExcelManger(String excelPath, boolean onlyReadOneSheet) {
		this.excelPath = excelPath;
		this.onlyReadOneSheet = onlyReadOneSheet;
	}
	
	/**
	 * @param excelFile excel文件
	 * @param onlyReadOneSheet 是否只读第一页 true 是 | false 读全部
	 */
	public ExcelManger(File excelFile, boolean onlyReadOneSheet) {
		this.excelFile = excelFile;
		this.onlyReadOneSheet = onlyReadOneSheet;
	}
	//=================================================================
	//contents
	
	/**
	 * 读取excle
	 * @return 记录
	 * @throws IOException 异常
	 */
	public List<Row> readExcel() throws IOException{
		//创建workbook
		Workbook workbook = this.createReadWorkbook();
		if (null == workbook) {
			throw new IOException("读取excel文件失败，请确认");
		}
		
		int sheetCount = 1;//需要操作的sheet数量
		Sheet sheet = null; 
		if (onlyReadOneSheet) {
			sheet = workbook.getSheetAt(0);
		} else {
			sheetCount = workbook.getNumberOfSheets();//获取可以操作的总数量
		}
		
		//开始读取excel
		List<Row> rowList = new ArrayList<Row>();
		for(int t=0 ; t < sheetCount ; t++) {
			
			if(!onlyReadOneSheet) {  
                sheet = workbook.getSheetAt(t);  
            } 
			//获取最后行号  
            int lastRowNum = sheet.getLastRowNum(); 
            Row row;
          
            if (lastRowNum > 0) {
          	  log.debug("开始读取【"+sheet.getSheetName()+"】内容");
          	  for (int i=0 ; i<=lastRowNum; i++) {
          		 row = sheet.getRow(i);
          		 rowList.add(row);
          		 //打印每一行信息,这一步需要单独处理
//          		 for (int j = 0; j < row.getLastCellNum(); j++) {
//          			 String value = this.getCellValue(row.getCell(j));
//          			 if (!value.equals("")) {
//          				System.out.print(value + "|");
//          			 }
//          		 }
//          		 System.out.println(" ");
          	  }
            }
		}
		return rowList;
	}
	
	/**
	 * @return excel 工作簿对象
	 */
	public Workbook createReadWorkbook() throws IOException {
		//获取文件读取类
		ExcelReader excelReader = ExcelReaderFactory.createExcelReader(this.getExtendName());

		log.debug("文件后缀名未知,依次使用xls,xlsx方式读取");
		Workbook workbook = excelReader.readExcel(excelFile);
		if (null == workbook) {
			workbook = excelReader.readExcel(excelFile);
		}
		
		if (null == workbook) {
			throw new IOException("读取excel文件失败，请确认");
		}
		
		return workbook;
	}


	/**
	 * 获取excel单元格内容
	 * @param cell excel 单元格对象
	 * @return 对应内容
	 */
	public String getCellValue(Cell cell) {
		Object result = "";
		if (cell != null) {  
			CellType cellType = cell.getCellTypeEnum();
			
			if (CellType.NUMERIC == cellType) {
				result = cell.getNumericCellValue();
			}
			
			if (CellType.STRING == cellType) {
				result = cell.getStringCellValue();
			}
			
			if (CellType.BOOLEAN == cellType) {
				result = cell.getBooleanCellValue();
			}
			
			if (CellType.FORMULA == cellType) {
				result = cell.getCellFormula();
			}
			
			if (CellType.ERROR == cellType) {
				result = cell.getErrorCellValue();
			}
        }  
        return result.toString(); 
	}
	
	//=============================================================
	//private methods
	/**
	 * <pre>
	 * 获取文件扩展名
	 * 文件路径存在时根据路径获取文件
	 * </pre>
	 * @return
	 * @throws IOException
	 */
	private String getExtendName() throws IOException{
		//文件路径优先查找
		if (StringUtils.hasText(excelPath)) {
			//根据路径创建excel file
			Path path = Paths.get(excelPath);
			if (Files.exists(path)) {
				excelFile = path.toFile();
			}else {
				throw new IOException("excel文件未找到");
			}
			
			return excelPath.substring(excelPath.lastIndexOf(".")+1);
		}
		
		if (excelFile.exists()) {
			String fileName = excelFile.getName();
			return fileName.substring(fileName.lastIndexOf(".")+1);
		}
		
		throw new IOException("excel文件未找到");
	}
}
