package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;

public class ObjectSheet {
    Sheet sheet;
    ArrayList<ObjectTable> tables = new ArrayList<>();
    ObjectCell[][] cellsGrid;
    HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();

    public ObjectSheet(Sheet sheet) {
        this.sheet = sheet;
        int maxColNum = 0;
        for (Row row : sheet) {
            if (row != null) {
                maxColNum = Math.max(maxColNum, row.getLastCellNum()); // getLastCellNum() is 1-based
            }
        }

        System.out.println("ADDING TOTAL " + maxColNum + " " + sheet.getLastRowNum());
        cellsGrid = new ObjectCell[maxColNum][sheet.getLastRowNum()];
        for (int y = 0; y < sheet.getLastRowNum(); y++) {
            for (int x = 0; x < maxColNum; x++) {
                if (sheet.getRow(y) != null && sheet.getRow(y).getCell(x) != null) {
                    cellsGrid[x][y] = new ObjectCell(sheet.getRow(y).getCell(x));
                    System.out.print("["+x + "," + y + "->" + cellsGrid[x][y].toString()+"]");
                } else {
                    cellsGrid[x][y] = new ObjectCell(x,y);
                    System.out.print("["+x + "," + y + "->null"+"]");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        findTables();
    }


    private void findTables() {
        System.out.println("");
        for (int x = 0; x < cellsGrid.length; x++) {
            for (int y = 0; y < cellsGrid[x].length; y++) {
                if (cellsGrid[x][y] != null) {
                    System.out.print("["+x + "," + y + "->" + cellsGrid[x][y].toString()+"]");
                } else {
                    System.out.print("["+x + "," + y + "->null"+"]");
                }
                System.out.print("|");

            }
            System.out.println("");
        }
        ArrayList<ObjectBlock> blocks = new ArrayList<>();


        ObjectCell[][] nonNullCells = cellsGrid;
        int counter=0;
        ObjectCell startingCell = getNextDataCell(nonNullCells);
        while (getNextDataCell(nonNullCells) != null) {
            ArrayList<ObjectCell> foundBlockArrayList = returnBlockMap(nonNullCells, startingCell.getX(), startingCell.getY());
            ObjectBlock blockFound = new ObjectBlock(foundBlockArrayList,cellsGrid.length,cellsGrid[0].length);
            blocks.add(blockFound);
            startingCell = getNextDataCell(nonNullCells);
        }
        System.out.println("------>" + blocks.size());


    }

    private ArrayList<ObjectCell> returnBlockMap(ObjectCell[][] cellsGridIn, int x, int y) {
        ArrayList<ObjectCell> outList = new ArrayList<>();

        outList.add(cellsGridIn[x][y]);

        cellsGridIn[x][y]=null;

        if(x>0 && cellsGridIn[(x - 1)][y]!=null && cellsGridIn[(x - 1)][y].containsData()) {
            outList.addAll( returnBlockMap( cellsGridIn, x-1,  y) );
        }
        if(x+1<cellsGridIn.length && cellsGridIn[(x + 1)][y]!=null && cellsGridIn[(x + 1)][y].containsData()) {
            outList.addAll( returnBlockMap( cellsGridIn, x+1,  y) );
        }
        if(y>0 && cellsGridIn[x][y-1]!=null && cellsGridIn[x][y-1].containsData()) {
            outList.addAll( returnBlockMap( cellsGridIn, x,  y-1) );
        }
        if(y+1<cellsGridIn[0].length && cellsGridIn[x][y+1]!=null && cellsGridIn[x][y+1].containsData()) {
            outList.addAll( returnBlockMap( cellsGridIn, x,  y+1) );
        }
        return outList;
    }

    private ObjectCell  getNextDataCell(ObjectCell[][] cellsGridInput) {
        ObjectCell firstNotNullCell=null;
        for(int x=0;x<cellsGridInput.length;x++){
            for(int y=0;y<cellsGridInput[x].length;y++){
                if(cellsGridInput[x][y]!=null && cellsGridInput[x][y].containsData()){
                    return cellsGridInput[x][y];
                }
            }
        }
        return null;
    }





    public String getValue(String row, String column){
        return data.get(row).get(column);
    }

    public boolean neesaryColumnsPresent(ArrayList<String> columns){
        return false;
    }

    public boolean neesaryRowsPresent(ArrayList<String> rows){
        return false;
    }

}
