package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class ExcelUtil {
    private static final Logger log = LogManager.getLogger(ExcelUtil.class);

    public static Object[][] getTestData(String filePath, String sheetName) {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();

            for (int i = 1; i <= rows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                Object[] rowData = new Object[cols];
                for (int j = 0; j < cols; j++) {
                    Cell cell = row.getCell(j);
                    rowData[j] = cell == null ? "" : getCellValue(cell);
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            log.error("Failed to read Excel: {}", e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private static String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default      -> "";
        };
    }
}
