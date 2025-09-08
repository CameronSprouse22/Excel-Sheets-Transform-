package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ObjectCell {
    Cell cell;
    private final int x;
    private final int y;
    boolean isDetachedFormula;
    boolean isAttachedFormula;
    boolean isRawData;
    boolean isRowHeader;
    boolean isColHeader;
    boolean isVoidSpace;
    boolean isVerbatimString;
    boolean hasValue;
    String Stringvalue;

    public ObjectCell(Cell cell){
        this.cell = cell;
        this.x = cell.getColumnIndex();
        this.y = cell.getRowIndex();
        this.hasValue = CommonFunctions.containsRawData(cell);
        this.Stringvalue= CommonFunctions.getStringPrintValue(cell);
    }

    public ObjectCell(int x, int   y){
        this.cell = null;
        this.x = x;
        this.y = y;
        this.hasValue = false;
        this.Stringvalue=null;
    }

    public Enum<CellType> getType(){
        return cell.getCellType();
    }

    public Cell getCell() {
        return cell;
    }

    @Override
    public String toString(){
        return Stringvalue;
    }

    public boolean containsData() {
        return hasValue;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
