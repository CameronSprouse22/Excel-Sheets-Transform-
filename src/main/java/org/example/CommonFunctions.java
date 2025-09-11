package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

public class CommonFunctions {


    public static boolean containsData(Cell cell) {
        if(cell == null) {
            return false;
        } else if(cell.getCellType().equals(CellType.STRING)) {
            return true;
        } else if(cell.getCellType().equals(CellType.NUMERIC)) {
            return true;
        } else if(cell.getCellType().equals(CellType.BOOLEAN)) {
            return true;
        } else if(cell.getCellType().equals(CellType.FORMULA)) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean containsRawData(Cell cell) {
        if(cell == null) {
            return false;
        } else if(cell.getCellType().equals(CellType.STRING)) {
            return true;
        } else if(cell.getCellType().equals(CellType.NUMERIC)) {
            return true;
        } else if(cell.getCellType().equals(CellType.BOOLEAN)) {
            return true;
        }else {
            return false;
        }
    }

    public boolean copyFile(String sourceFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);

        new File(sourceFile.getParentFile().getAbsoluteFile()+ "\\COPYTEST").mkdir();
        Files.copy(sourceFile.toPath(), new File(sourceFile.getParentFile().getAbsoluteFile() + "\\COPYTEST\\" + sourceFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
        return false;
    }

    public boolean readExcelFile(String fileName) throws IOException {

            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(fileName);

            //Create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;
            if(fileName.toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(fis);
            }else if(fileName.toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(fis);
            }

            //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();

            //loop through each of the sheets
            for(int i=0; i < numberOfSheets; i++){

                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);

                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext())
                {
                    String name = "";
                    String shortCode = "";

                    //Get the row object
                    Row row = rowIterator.next();

                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext())
                    {
                        //Get the Cell object
                        Cell cell = cellIterator.next();
                        CellType cellType = cell.getCellType();
                        if(cell.getCellType().equals(CellType.STRING)){
                            System.out.println("["+cell.getStringCellValue()+"]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else if(cell.getCellType().equals(CellType.NUMERIC)){
                            System.out.println("["+cell.getNumericCellValue()+"]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else if(cell.getCellType().equals(CellType.BOOLEAN)){
                            System.out.println("["+cell.getBooleanCellValue()+"]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else if(cell.getCellType().equals(CellType.BLANK)){
                            System.out.println("[BLANK]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else if(cell.getCellType().equals(CellType.FORMULA)){
                            System.out.println("["+cell.getCellFormula()+"]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else if(cell.getCellType().equals(CellType._NONE)){
                            System.out.println("[_NONE]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else if(cell.getCellType().equals(CellType.ERROR)){
                            System.out.println("[ERROR]"+ "["+cell.isPartOfArrayFormulaGroup()+"]"+ cellType);
                        }else {
                            System.out.println("[ELSE]");
                        }

                        //check the cell type and process accordingly
//                        switch(cell.getCellType()){
//                            case Cell.CELL_TYPE_STRING:
//                                if(shortCode.equalsIgnoreCase("")){
//                                    shortCode = cell.getStringCellValue().trim();
//                                }else if(name.equalsIgnoreCase("")){
//                                    //2nd column
//                                    name = cell.getStringCellValue().trim();
//                                }else{
//                                    //random data, leave it
//                                    System.out.println("Random data::"+cell.getStringCellValue());
//                                }
//                                break;
//                            case Cell.CELL_TYPE_NUMERIC:
//                                System.out.println("Random data::"+cell.getNumericCellValue());
//                        }
                    } //end of cell iterator

                } //end of rows iterator


            } //end of sheets for loop

            //close file input stream
            fis.close();

        return false;
    }

    public static String getStringPrintValue(Cell cell){
        if(cell == null) {
            return "[NULL OBJECT!]";
        } else if(cell.getCellType().equals(CellType.STRING)){
            return cell.getStringCellValue();
        }else if(cell.getCellType().equals(CellType.NUMERIC)){
            return  ""+cell.getNumericCellValue();
        }else if(cell.getCellType().equals(CellType.BOOLEAN)){
            return  ""+cell.getBooleanCellValue();
        }else if(cell.getCellType().equals(CellType.BLANK)){
            return  ""+cell.isPartOfArrayFormulaGroup();
        }else if(cell.getCellType().equals(CellType.FORMULA)){
            return  ""+cell.getCellFormula();
        }else if(cell.getCellType().equals(CellType._NONE)){
            return  ""+"[_NONE]";
        }else if(cell.getCellType().equals(CellType.ERROR)){
            return  ""+"[ERROR]";
        }else {
            return  ""+"[ELSE]";
        }
    }

    public static void ObjectBlockPrint(ObjectCell[][] cellsGrid)  {
        try {
            for (int y = 0; y < cellsGrid[0].length; y++) {
                for (int x = 0; x < cellsGrid.length; x++) {
                    if (cellsGrid[x][y] != null && cellsGrid[x][y] != null) {
                        System.out.print("[" + x + "," + y + "->" + cellsGrid[x][y].toString() + "]");
                    } else {
                        cellsGrid[x][y] = new ObjectCell(x, y);
                        System.out.print("[" + x + "," + y + "->null" + "]");
                    }
                }
                System.out.println("");
            }
            System.out.println("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
