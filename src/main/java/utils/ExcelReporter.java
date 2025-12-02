package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelReporter {
    private Workbook workbook;
    private Sheet sheet;
    private int rowNum;
    
    public ExcelReporter(String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        rowNum = 0;
        createHeader();
    }
    
    private void createHeader() {
        Row headerRow = sheet.createRow(rowNum++);
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        
        String[] headers = {"Item #", "Price", "Quantity", "Subtotal", 
                           "Calculated Total", "Website Total", "Match", "Timestamp"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
    
    public void addCartData(List<Double> prices, List<Integer> quantities, 
                            double calculatedTotal, double websiteTotal) {
        // Add each item
        for (int i = 0; i < prices.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            double price = prices.get(i);
            int qty = quantities.get(i);
            double subtotal = price * qty;
            
            row.createCell(0).setCellValue("Item " + (i + 1));
            row.createCell(1).setCellValue(price);
            row.createCell(2).setCellValue(qty);
            row.createCell(3).setCellValue(subtotal);
        }
        
        // Add totals row
        Row totalRow = sheet.createRow(rowNum++);
        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);
        
        Cell totalLabel = totalRow.createCell(0);
        totalLabel.setCellValue("TOTAL");
        totalLabel.setCellStyle(boldStyle);
        
        totalRow.createCell(4).setCellValue(calculatedTotal);
        totalRow.createCell(5).setCellValue(websiteTotal);
        
        boolean match = Math.abs(calculatedTotal - websiteTotal) < 0.01;
        Cell matchCell = totalRow.createCell(6);
        matchCell.setCellValue(match ? "YES" : "NO");
        
        CellStyle matchStyle = workbook.createCellStyle();
        Font matchFont = workbook.createFont();
        matchFont.setColor(match ? IndexedColors.GREEN.getIndex() : IndexedColors.RED.getIndex());
        matchFont.setBold(true);
        matchStyle.setFont(matchFont);
        matchCell.setCellStyle(matchStyle);
        
        totalRow.createCell(7).setCellValue(LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // Auto-size columns
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    public void saveReport(String fileName) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            workbook.close();
            System.out.println("✓ Excel report saved: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to save Excel report: " + e.getMessage());
        }
    }
}