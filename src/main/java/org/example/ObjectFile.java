package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectFile {

    ArrayList<Sheet> sheets=new ArrayList<Sheet>();
    ArrayList<ObjectSheet> objectsheets=new ArrayList<ObjectSheet>();

    public ObjectFile(String fileName) throws IOException {
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
        System.out.println("SHEETS: "+numberOfSheets);
        //loop through each of the sheets
        for(int i=0; i < numberOfSheets; i++){

            //Get the nth sheet from the workbook

            Sheet sheet = workbook.getSheetAt(i);
            sheets.add(sheet);
            //objectsheets.add(new ObjectSheet(sheet));
        }
    }

    public ArrayList<Sheet> getSheets(){
        return sheets;
    }
}
