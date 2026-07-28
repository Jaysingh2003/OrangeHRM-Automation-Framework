package data;

import org.apache.poi.xssf.usermodel.*;
import java.io.*;

/**
 * Run this once to generate Excel test data files.
 * mvn exec:java -Dexec.mainClass="data.CreateTestData"
 */
public class CreateTestData {

    public static void main(String[] args) throws Exception {
        createLoginData();
        createEmployeeData();
        createCandidateData();
        System.out.println("Excel files created successfully.");
    }

    private static void createLoginData() throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("LoginData");
        Object[][] data = {
            {"username", "password", "expectedResult"},
            {"Admin",    "admin123", "pass"},
            {"badUser",  "admin123", "fail"},
            {"Admin",    "badPass",  "fail"},
            {"",         "",         "fail"}
        };
        writeSheet(sheet, data);
        write(wb, "src/test/java/data/LoginData.xlsx");
    }

    private static void createEmployeeData() throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("EmployeeData");
        Object[][] data = {
            {"firstName", "lastName", "employeeId"},
            {"John",      "Doe",      "EMP101"},
            {"Jane",      "Smith",    "EMP102"}
        };
        writeSheet(sheet, data);
        write(wb, "src/test/java/data/EmployeeData.xlsx");
    }

    private static void createCandidateData() throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("CandidateData");
        Object[][] data = {
            {"firstName", "lastName", "email",              "vacancy"},
            {"Alice",     "Johnson",  "alice@test.com",     "Software Engineer"},
            {"Bob",       "Williams", "bob@test.com",       "QA Engineer"}
        };
        writeSheet(sheet, data);
        write(wb, "src/test/java/data/CandidateData.xlsx");
    }

    private static void writeSheet(XSSFSheet sheet, Object[][] data) {
        for (int r = 0; r < data.length; r++) {
            XSSFRow row = sheet.createRow(r);
            for (int c = 0; c < data[r].length; c++) {
                row.createCell(c).setCellValue(data[r][c].toString());
            }
        }
    }

    private static void write(XSSFWorkbook wb, String path) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            wb.write(fos);
        }
        wb.close();
    }
}
