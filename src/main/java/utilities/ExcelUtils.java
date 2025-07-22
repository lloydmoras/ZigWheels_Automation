package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class ExcelUtils {
 
    public FileInputStream fi; // Changed to non-static
    public FileOutputStream fo; 
    public XSSFWorkbook wb; 
    public XSSFSheet ws; 
    public XSSFRow row; 
    public XSSFCell cell; 

    public CellStyle style;  
    String fileName;
    String sheetName; // Added to store sheet name
 
    public ExcelUtils(String excelName, String sheetName) {
        this.fileName = excelName;
        this.sheetName = sheetName; // Store sheet name
        File filepath = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\Excelfile\\" + fileName);
        try {
            fi = new FileInputStream(filepath);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(sheetName); // Use ws for current sheet
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
 
    // uses instance variables

    public int getRowCount() throws IOException {
        return ws.getLastRowNum();
    }
 
    // uses instance variables
    public int getCellCount(int rownum) throws IOException {
        row = ws.getRow(rownum);
        if (row == null) { // Handle null row if it doesn't exist
            return 0;

        }
        return row.getLastCellNum();
    }
 
    // uses instance variables
    public String getCellData(int rownum, int colnum) throws IOException {
        row = ws.getRow(rownum);
        if (row == null) {
            return ""; // Return empty string if row doesn't exist
        }

        cell = row.getCell(colnum);
        String data;
        try {

            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }
        return data;
    }
 
    // Ensures resources are closed after operations that modify the file

    public void setCellData(int rownum, int colnum, String data) throws IOException {
        row = ws.getRow(rownum);
        if (row == null) {
            row = ws.createRow(rownum); // Create row if it doesn't exist
        }

        cell = row.createCell(colnum);
        cell.setCellValue(data);
        fo = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Excelfile\\" + fileName); // Re-open stream for writing
        wb.write(fo);
        fo.close();

    }
 
 
    public void fillGreenColor(int rownum, int colnum) throws IOException {
        row = ws.getRow(rownum);
        if (row == null) {
            row = ws.createRow(rownum);
        }

        cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }
 
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        fo = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Excelfile\\" + fileName);
        wb.write(fo);
        fo.close();

    }
 
    public void fillRedColor(int rownum, int colnum) throws IOException {
        row = ws.getRow(rownum);
        if (row == null) {
            row = ws.createRow(rownum);
        }

        cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }
 
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        fo = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Excelfile\\" + fileName);
        wb.write(fo);
        fo.close();
    }

    public void close() {
        try {
            if (wb != null) {
                wb.close();
            }

            if (fi != null) {
                fi.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
 