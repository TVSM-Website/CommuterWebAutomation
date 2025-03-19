package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class PageSpeedExcelUtils {
    private static int row;
    private static int column;
    public static File file;
    public static FileInputStream inputStream;
    public static XSSFWorkbook wb;
    public static Sheet sheet;

    public static void excel_initiation(String path) throws IOException {
        file = new File(path);
        inputStream = new FileInputStream(file);
        wb = new XSSFWorkbook(inputStream);
        if (wb.getSheet("PageSpeed") == null) {
            wb.createSheet("PageSpeed");
            sheet = wb.getSheet("PageSpeed");
            Row row1 = sheet.createRow(0);
            Cell cell1 = row1.createCell(0);
            cell1.setAsActiveCell();
        } else {
            wb.getSheet("PageSpeed");
            wb.removeSheetAt(wb.getActiveSheetIndex());
        }
    }

    public static void excel_createRow(String path,int newrow) throws IOException {
        file = new File(path);
        inputStream = new FileInputStream(file);
        wb = new XSSFWorkbook(inputStream);
        sheet = wb.getSheet("PageSpeed");
        Row row1 = sheet.createRow(newrow);
        Cell cell1 = row1.createCell(0);
        cell1.setAsActiveCell();
    }

    public static void setCellValue(String path, String value) throws IOException {
        row =  sheet.getActiveCell().getRow();
        column = sheet.getActiveCell().getColumn() + 1;
        Row newrow = sheet.getRow(row);
        if (newrow==null)
            {
                newrow = sheet.createRow(row);
            }
        Cell cell = newrow.createCell(column);
        sheet.autoSizeColumn(column);
        cell.setCellValue(value);
        cell.setAsActiveCell();

        FileOutputStream fos = new FileOutputStream(path);
        wb.write(fos);
        fos.close();
//        inputStream.close();
    }
}


