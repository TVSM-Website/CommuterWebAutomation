package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ORPExcelUtils
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

            while (rowIterator.hasNext())
            {
                Row currentRow = rowIterator.next();
                String model = currentRow.getCell(columnIndices.get("Model")).getStringCellValue();
                String variant = currentRow.getCell(columnIndices.get("Variant")).getStringCellValue();
                String state = currentRow.getCell(columnIndices.get("State")).getStringCellValue();
                double onRoadPrice = currentRow.getCell(columnIndices.get("OnRoadPrice")).getNumericCellValue();
                //int exShowRoomPrice = (int) currentRow.getCell(columnIndices.get("Ex-ShowRoomPrice")).getNumericCellValue();

                String key = model + "|" + variant + "|" + state;
                Map<String, String> priceDetails = new HashMap<>();
                priceDetails.put("OnRoadPrice", String.valueOf(onRoadPrice));
                //priceDetails.put("Ex-ShowRoomPrice", String.valueOf(exShowRoomPrice)); // Add Ex-ShowRoomPrice
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

    private static final Map<String, String> stateMappingRaider;

    static {
        stateMappingRaider = new HashMap<>();
        stateMappingRaider.put("Andaman And Nicobar Islands", "Andaman");
        stateMappingRaider.put("Andhra Pradesh", "Andhra pradesh");
        stateMappingRaider.put("Arunachal Pradesh", "Northeast - Arunachal Pradesh");
        stateMappingRaider.put("Dadra And Nagar Haveli", "Silvasa");
        stateMappingRaider.put("Jammu And Kashmir", "Jammu & Kashmir");
        stateMappingRaider.put("Madhya Pradesh", "Madhya pradesh");
        stateMappingRaider.put("Manipur", "Northeast - Manipur");
        stateMappingRaider.put("Mizoram", "Northeast - Mizoram");
        stateMappingRaider.put("Nagaland", "Northeast - Nagaland");
        stateMappingRaider.put("Puducherry", "Pondicherry");
        stateMappingRaider.put("Tamil Nadu", "Tamilnadu");
        stateMappingRaider.put("Tripura", "Northeast -Tripura");
        stateMappingRaider.put("Uttar Pradesh", "Uttar pradesh");
    }

    public static String MappedStateNameRaider(String state) {
        return stateMappingRaider.getOrDefault(state, state);
    }
}
