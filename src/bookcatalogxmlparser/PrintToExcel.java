/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookcatalogxmlparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author Graf_Nameless
 */
public class PrintToExcel {
    private static SXSSFWorkbook wb;
    private static Sheet sheet;    
    private static Map<String, CellStyle> styles;
    public PrintToExcel()
    {
        wb = new SXSSFWorkbook();
        sheet = wb.createSheet("Книги");
        styles = new HashMap<>();
        SetStyleDateCell();
        SetHead();
    }
    public void SaveAndCloseExcel(String FileNameForSave) throws FileNotFoundException, IOException
    {
        try (FileOutputStream out = new FileOutputStream(FileNameForSave)) {
            wb.write(out);
        } 
    }
    public void InsertData(BookClass book, int i)
    {
        if(i >= 65535)
            return;        
        Row nexRow = sheet.createRow(i);
        Cell cell = nexRow.createCell(0);
        cell.setCellValue(book.getId());
        cell.setCellStyle(styles.get("cell_normal_border"));
        cell = nexRow.createCell(1); 
        cell.setCellValue(book.getAuthor());
        cell.setCellStyle(styles.get("cell_normal_border"));
        cell = nexRow.createCell(2); 
        cell.setCellValue(book.getTitle());
        cell.setCellStyle(styles.get("cell_normal_border"));
        cell = nexRow.createCell(3); 
        cell.setCellValue(book.getGenre());
        cell.setCellStyle(styles.get("cell_normal_border"));
        cell = nexRow.createCell(4); 
        cell.setCellValue(book.getPrice());
        cell.setCellStyle(styles.get("cell_normal_float"));
        cell = nexRow.createCell(5); 
        cell.setCellValue(book.getPublish_date());
        cell.setCellStyle(styles.get("cell_normal_date"));
        cell = nexRow.createCell(6); 
        cell.setCellValue(book.getDescription());
        cell.setCellStyle(styles.get("cell_normal_border"));
    }
    private void SetHead()
    {
        sheet.setColumnWidth(1,40*256);        
        sheet.setColumnWidth(2,50*256);        
        sheet.setColumnWidth(3,20*256);        
        sheet.setColumnWidth(4,15*256);        
        sheet.setColumnWidth(5,20*256);        
        sheet.setColumnWidth(6,200*256);
        Row headerRow = sheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("ID");
        cell = headerRow.createCell(1);     
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("Автор");
        cell = headerRow.createCell(2);
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("Название");
        cell = headerRow.createCell(3);
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("Жанр");
        cell = headerRow.createCell(4);
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("Цена");
        cell = headerRow.createCell(5);
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("Дата публикации");
        cell = headerRow.createCell(6);
        cell.setCellStyle(styles.get("cell_normal_border_B"));
        cell.setCellValue("Описание");
        
    }
    private static void SetStyleDateCell()
    {
        DataFormat df = wb.createDataFormat();
        CellStyle style = createBorderedStyle(wb);
        
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("dd.mm.yyyy"));
        styles.put("cell_normal_date", style);
        
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("0.00"));
        styles.put("cell_normal_float", style);        
        
        style = createBorderedStyle(wb);
        styles.put("cell_normal_border", style); 
        
        style = createBorderedStyle(wb);
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
        styles.put("cell_normal_border_B", style);
    }
    protected static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        return format.format(date);
    }
    private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
}
