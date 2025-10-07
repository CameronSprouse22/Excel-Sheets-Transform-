package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class CellFormater {
    public static void getCellFormating(Cell cell){
        CellStyle style=cell.getCellStyle();
        // TO DO
    }
    public static void setCellFormating(Cell cell, Cell formatedCell){
        CellStyle style=formatedCell.getCellStyle();
        cell.setCellStyle(style);
        // TO DO
    }
    public  static CellStyle getCellStyle(Cell cell){
        return cell.getCellStyle();
    }


}
