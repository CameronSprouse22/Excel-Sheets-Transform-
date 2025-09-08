package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelFunctions {

    public boolean getCells(String fileName) throws IOException {

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
        return true;
    }

}
