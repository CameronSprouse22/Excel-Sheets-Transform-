package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FormatedTypes {

    static Workbook workbook = new XSSFWorkbook();
    public static ObjectCell getDataFormating(ObjectCell cell){
        getDataFormating(cell.getCell());
        return cell;
    }

    public static void getDataFormating(Cell cell){
        CellStyle style = cell.getCellStyle();
        style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
    }

    public static CellStyle setStyleData(Workbook workbook2, ObjectCell cellObject){
        CellStyle style = workbook2.createCellStyle();
        Font fontSty = workbook2.createFont();

        if(cellObject==null || cellObject.getCell()==null) {
            return style;
        }
        else if(cellObject.getCellType() == EnumCellType.DATA_FORMULA){
            style=cellObject.getCell().getCellStyle();
        }
        else if(cellObject.getCellType() == EnumCellType.DATA_STATIC){
            style=cellStyleDATA_STATIC();
        }
        else if(cellObject.getCellType() == EnumCellType.HEADER){
            style=cellStyleHEADER();
        }
        else if(cellObject.getCellType() == EnumCellType.ATTACHED_FORMULA){
            style=cellStyleATTACHED_FORMULA();
        }
        else if(cellObject.getCellType() == EnumCellType.ATTACHED_COMMENT){
            style=cellStyleATTACHED_COMMENT();
        }
        else if(cellObject.getCellType() == EnumCellType.EMPTY_CELL){
            style=cellStyleEMPTY_CELL();
        }
        else if(cellObject.getCellType() == EnumCellType.NON_USED){
            style=cellStyleNON_USED();
        }
        return style;

    }

    private static CellStyle cellStyleDATA_STATIC(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.BLUE.getIndex());
        style.setFont(fontSty);

        return style;
    }


    private CellStyle cellStyleDATA_FORMULA(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.GREEN.getIndex());
        style.setFont(fontSty);

        return style;
    }

    private static CellStyle cellStyleHEADER(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.ORANGE.getIndex());
        style.setFont(fontSty);

        return style;
    }

    private static CellStyle cellStyleATTACHED_FORMULA(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.AQUA.getIndex());
        style.setFont(fontSty);

        return style;
    }

    private static CellStyle cellStyleATTACHED_COMMENT(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.DARK_GREEN.getIndex());
        style.setFont(fontSty);

        return style;
    }

    private static CellStyle cellStyleEMPTY_CELL(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFont(fontSty);

        return style;
    }

    private static CellStyle cellStyleNON_USED(){
        CellStyle style = workbook.createCellStyle();
        Font fontSty = workbook.createFont();

        fontSty.setBold(false);
        fontSty.setColor(IndexedColors.YELLOW.getIndex());
        style.setFont(fontSty);

        return style;
    }

}
