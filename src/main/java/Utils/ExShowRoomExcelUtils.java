package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExShowRoomExcelUtils
{
    public static Map<String, Map<String, String>> readExcelData(String filePath, String sheetName) throws IOException {
        Map<String, Map<String, String>> excelPrices = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            Map<String, Integer> columnIndices = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndices.put(cell.getStringCellValue(), cell.getColumnIndex());
            }

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                String model = currentRow.getCell(columnIndices.get("Model")).getStringCellValue();
                String variant = currentRow.getCell(columnIndices.get("Variant")).getStringCellValue();
                String state = currentRow.getCell(columnIndices.get("State")).getStringCellValue();
                //String color=  currentRow.getCell(columnIndices.get("Color")).getStringCellValue();
                int exShowRoomPrice = (int) currentRow.getCell(columnIndices.get("Ex-ShowRoomPrice")).getNumericCellValue();

                String key = model + "|" + variant + "|" + state;
                Map<String, String> priceDetails = new HashMap<>();
                priceDetails.put("Ex-ShowRoomPrice", String.valueOf(exShowRoomPrice)); // Add Ex-ShowRoomPrice
                excelPrices.put(key, priceDetails);
            }
        }

        return excelPrices;
    }

    public static Map<String, Map<String, String>> readExcelDataRonin(String filePath, String sheetName) throws IOException {
        Map<String, Map<String, String>> excelPrices = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            Map<String, Integer> columnIndices = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndices.put(cell.getStringCellValue(), cell.getColumnIndex());
            }

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                String model = currentRow.getCell(columnIndices.get("Model")).getStringCellValue();
                String variant = currentRow.getCell(columnIndices.get("Variant")).getStringCellValue();
                String state = currentRow.getCell(columnIndices.get("State")).getStringCellValue();
                String color=  currentRow.getCell(columnIndices.get("Color")).getStringCellValue();
                int exShowRoomPrice = (int) currentRow.getCell(columnIndices.get("Ex-ShowRoomPrice")).getNumericCellValue();

                String key = model + "|" + variant + "|" +color+ "|" +state;
                Map<String, String> priceDetails = new HashMap<>();
                priceDetails.put("Ex-ShowRoomPrice", String.valueOf(exShowRoomPrice)); // Add Ex-ShowRoomPrice
                excelPrices.put(key, priceDetails);
            }
        }

        return excelPrices;
    }


    private static final Map<String, String> stateMapping;

    static {
        stateMapping = new HashMap<>();
        stateMapping.put("Andaman and Nicobar Islands", "Andaman");
        stateMapping.put("Andhra Pradesh", "Andhra pradesh");
        stateMapping.put("Arunachal Pradesh", "Northeast - Arunachal Pradesh");
        stateMapping.put("Dadra and Nagar Haveli", "Silvasa");
        stateMapping.put("Jammu and Kashmir", "Jammu & Kashmir");
        stateMapping.put("Madhya Pradesh", "Madhya pradesh");
        stateMapping.put("Manipur", "Northeast - Manipur");
        stateMapping.put("Mizoram", "Northeast - Mizoram");
        stateMapping.put("Nagaland", "Northeast - Nagaland");
        stateMapping.put("Puducherry", "Pondicherry");
        stateMapping.put("Tamil Nadu", "Tamilnadu");
        stateMapping.put("Tripura", "Northeast -Tripura");
        stateMapping.put("Uttar Pradesh", "Uttar pradesh");
    }

    public static String MappedStateName(String state) {
        return stateMapping.getOrDefault(state, state);
    }


    public static Map<String, String> createVariantMapping(Map<String, Map<String, String>> excelPrices) {
        Map<String, String> variantMap = new HashMap<>();

        // Iterate through Excel data to build full variant names
        for (Map<String, String> row : excelPrices.values()) {
            String variant = row.get("Variant");
            System.out.println("Variant: " + variant);
            String color = row.get("Color");
            String fullExcelVariant = variant + " " + color; // e.g., "1CH BASE LIGHTNING BLACK"
            System.out.println("Full Excel Variant: " + fullExcelVariant);
            String uiVariant = "TVS RONIN - " + variant.replace("1CH", "").replace("2CH", "").trim() + " " + color;
            variantMap.put(uiVariant.toUpperCase(), fullExcelVariant.toUpperCase());
        }

        // Manually add or adjust mappings if needed for completeness
        variantMap.put("TVS RONIN - BASE LIGHTNING BLACK", "1CH BASE LIGHTNING BLACK");
        variantMap.put("TVS RONIN - BASE MAGMA RED", "1CH BASE MAGMA RED");
        variantMap.put("TVS RONIN - MID GLACIER SILVER", "2CH MID GLACIER SILVER");
        variantMap.put("TVS RONIN - MID CHARCOAL EMBER", "2CH MID CHARCOAL EMBER");
        variantMap.put("TVS RONIN - TOP NIMBUS GREY", "2CH TOP NIMBUS GREY");
        variantMap.put("TVS RONIN - TOP MIDNIGHT BLUE", "2CH TOP MIDNIGHT BLUE");

        return variantMap;
    }
}
