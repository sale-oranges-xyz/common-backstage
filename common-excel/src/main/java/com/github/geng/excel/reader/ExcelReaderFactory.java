package com.github.geng.excel.reader;

/**
 * excel 读取文件工厂
 */
public class ExcelReaderFactory {

    /**
     * @param extendName 文件扩展名
     * @return 对应的读取操作类
     */
    public static ExcelReader createExcelReader(String extendName) {
        if (extendName.equals("xlsx")) {
            return new XlsxExcelReader();
        } else if (extendName.equals("xls")) {
            return new XlsExcelReader();
        } else {
            throw new NullPointerException(String.format("无法根据扩展名:%s找到对应的处理类", extendName));
        }
    }
}
