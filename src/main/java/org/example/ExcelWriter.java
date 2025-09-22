package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class ExcelWriter {

    public static boolean writeExcelFileFrom2DArray(String filePath, ObjectCell[][] grid){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("excel-sheet");

        for(int y = 0; y < grid[0].length; y++){
            Row row = sheet.createRow(y);
            for(int x = 0; x < grid.length; x++){
                if(grid[x][y]!= null && grid[x][y].containsData()) {
                    row.createCell(x).setCellValue(grid[x][y].toString());
                }else{
                    row.createCell(x);
                }
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(filePath));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean writeUseDisExcelFileFrom2DArray(String filePath, ObjectCell[][] grid){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("excel-sheet");

        for(int y = 0; y < grid[0].length; y++){
            Row row = sheet.createRow(y);
            for(int x = 0; x < grid.length; x++){
                if(grid[x][y]!= null && grid[x][y].containsData()) {
                    row.createCell(x).setCellValue(grid[x][y].getcellDis());
                }else{
                    row.createCell(x);
                }
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(filePath));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
