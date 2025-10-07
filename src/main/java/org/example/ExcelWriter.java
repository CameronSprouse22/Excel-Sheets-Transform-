package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class ExcelWriter {

    static String tempDirectoryPath = "TEMP_EXCEL_FILES\\";

    public static void deleteTempFiles(){
        File dir = new File(tempDirectoryPath);
        if (dir.isDirectory()){
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

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


    public static boolean writeUseDataTypesExcelFileFrom2DArray(String filePath, ObjectCell[][] grid){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("excel-sheet");

        for(int y = 0; y < grid[0].length; y++){
            Row row = sheet.createRow(y);
            for(int x = 0; x < grid.length; x++){
                if(grid[x][y]!= null && grid[x][y].containsData()) {

                    row.createCell(x).setCellValue(grid[x][y].getcellDis());
                    row.getCell(x).setCellStyle(FormatedTypes.setStyleData(workbook,grid[x][y]));

                    //if(grid[x][y].getCell().getCellStyle() != null)
                        //row.getCell(x).setCellStyle(grid[x][y].getCell().getCellStyle());
                }else{
                    row.createCell(x);
                }
            }
        }

        try {
            System.out.println(tempDirectoryPath+filePath);
            FileOutputStream out = new FileOutputStream(new File(tempDirectoryPath+filePath));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
