package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ObjectCell {
    Cell cell;
    EnumCellType cellType;
    EnumDataType dataType;
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
    boolean isFormula;
    String Stringvalue;
    String cellDis;
    String cellUseRef;
    Boolean cellBeingPulled;

    public ObjectCell(Cell cell){
        this.cell = cell;
        this.x = cell.getColumnIndex();
        this.y = cell.getRowIndex();
        this.hasValue = CommonFunctions.containsRawData(cell) || cell.getCellType()==CellType.FORMULA;
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

    public EnumCellType getCellType() {
        return cellType;
    }

    public void setCellType(String cellTypeString) {
        cellType= EnumCellType.valueOf(cellTypeString.toUpperCase());
    }

    public boolean isFormula() {
        return isFormula;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setcellDis(String cellDis) {this.cellDis=cellDis;}
    public String getcellDis() {return cellDis;}
}
