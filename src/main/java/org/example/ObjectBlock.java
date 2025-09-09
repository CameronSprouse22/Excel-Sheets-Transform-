package org.example;

import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;

public class ObjectBlock {

    ArrayList<ObjectCell> blockArray;
    boolean isVer;
    boolean containsTable;
    boolean containsImage;
    Integer maxX;
    Integer maxY;
    Integer rangeMinX=null;
    Integer rangeMinY=null;
    Integer rangeMaxX=null;
    Integer rangeMaxY=null;
    Integer rowLableMin=null;
    Integer rowLableMax=null;
    Integer colLableMin=null;
    Integer colLableMax=null;
    Integer height;
    Integer width;
    ArrayList<Integer> rowLenghts=new ArrayList<>();
    ArrayList<Integer> colLenghts=new ArrayList<>();
    Integer xCommonLength;
    Integer yCommonLength;
    ObjectCell[][] grid;
    ObjectCell[] columnHeaders;
    ObjectCell[] rowHeaders;
    ObjectCell[][] cellsGridData;
    ObjectCell[][] cellsGridNonData;

    public ObjectBlock(ArrayList<ObjectCell> blockArray, int maxX, int maxY) {

        this.blockArray=blockArray;
        this.maxX=maxX;
        this.maxY=maxY;
        grid=new ObjectCell[maxX][maxY];

        for(ObjectCell cell:blockArray) {
            grid[cell.getX()][cell.getY()] = cell;
            System.out.print("["+cell.getX()+","+cell.getY()+"]");
            if(rangeMaxX==null  || cell.getX()>rangeMaxX) {
                rangeMaxX=cell.getX();
            }
            if( rangeMaxY==null || cell.getY()>rangeMaxY) {
                rangeMaxY=cell.getY();
            }
            if(rangeMinX==null  || cell.getX()<rangeMinX ) {
                rangeMinX=cell.getX();
            }
            if(rangeMinY==null  || cell.getY()<rangeMinY) {
                rangeMinY=cell.getY();
            }
        }
        System.out.println();
        System.out.println("------------------------------------------");

        if(grid!=null && grid.length>1) {
            for (int x = 0; x < grid.length; x++) {
                if(grid[x][3]!=null) {
                    System.out.print("["+grid[x][3].toString() + "] | ");
                }else  {
                    System.out.print("[null] | ");
                }
            }
        }
        System.out.println();

        colLableMin=findColLength();
        rowLableMin=findRowLength();

        height=rangeMaxY-rangeMinY;
        width=rangeMaxX-rangeMinX;

        xCommonLength=getMostCommonInt(rowLenghts);
        yCommonLength=getMostCommonInt(colLenghts);

        System.out.println("MIN Max Y:"+rangeMaxY+"--->"+rangeMinY);
        System.out.println("MIN Max X:"+rangeMaxX+"--->"+rangeMinX);

        System.out.println("Height:"+height);
        System.out.println("Width:"+width);


        if (height == 1) {
            if(width == 1)  {
                setToLabelOnly();
            }
            else{
                setToLabelAndFormula();
            }
        } else if(width == 1 )  {
            setToList();
        }else{
            findDataStructureByCommonLengths();

            System.out.println("xCommonLength:"+xCommonLength);
            System.out.println("yCommonLength:"+yCommonLength);

            setToDataTable();
        }


        findRowHeaders();
        findColumnHeaders();


    }

    private void findRowHeaders() {
        for(int y=rangeMaxY;y>rangeMinY;y--) {
            boolean isHeader=true;
            int lineLenghtWithGap=0;
            int longestLineLenghtWithGap=0;
            int startOfLineCurrent=-1;
            int endOfLine=-1;

            for(int x=rangeMaxX;x>rangeMinX;x--) {
                boolean currentGap=false;
                //Cell has value
                if(grid[x][y]!=null && grid[x][y].containsData()){
                    if(startOfLineCurrent==-1) {
                        startOfLineCurrent=x;
                    }

                    if(currentGap){
                        currentGap=false;
                        lineLenghtWithGap++;
                    }
                    lineLenghtWithGap++;
                //Cell has no value
                }else if(currentGap){
                    if(lineLenghtWithGap>longestLineLenghtWithGap){
                        longestLineLenghtWithGap=lineLenghtWithGap;
                        endOfLine=x;
                        startOfLineCurrent=-1;
                    }
                    lineLenghtWithGap=0;
                }else{
                    currentGap=true;
                }

                if(lineLenghtWithGap>longestLineLenghtWithGap){
                    longestLineLenghtWithGap=lineLenghtWithGap;
                    endOfLine=x;
                }
            }
            System.out.println("LINE "+ y +" Longest line lenght with gap:"+longestLineLenghtWithGap+" from "+startOfLineCurrent+" to "+endOfLine);
        }
    }

    private void findColumnHeaders() {
    }

    private void setToDataTable() {
        System.out.println("setToDataTable");
    }

    private void setToList() {
        System.out.println("LIST");
    }

    private void setToLabelAndFormula() {
        System.out.println("Formula");
    }

    private void setToLabelOnly() {
        System.out.println("LabelOnly");
    }

    private void findDataStructureByCommonLengths() {
        ArrayList<Integer> commonLenghts=new ArrayList<>();
        ArrayList<Integer> rowLenghts=new ArrayList<>();

        for(int x=0;x<grid.length;x++) {
            int currentGridLenth=0;
            for(int y=0;y<grid[0].length;y++) {
                if(grid[x][y]!=null && grid[x][y].containsData()){
                    currentGridLenth++;
                }else{
                    if(currentGridLenth != 0){
                        commonLenghts.add(currentGridLenth);
                    }
                    currentGridLenth=0;
                }
            }
        }
        xCommonLength=getMostCommonInt(commonLenghts);

        for(int y=0;y<grid[0].length;y++) {
            int currentGridLenth=0;
            for(int x=0;x<grid.length;x++) {
                if(grid[x][y]!=null && grid[x][y].containsData()){
                    currentGridLenth++;
                }else{
                    if(currentGridLenth != 0){
                        rowLenghts.add(currentGridLenth);
                    }
                    currentGridLenth=0;
                }
            }
        }

        yCommonLength=getMostCommonInt(rowLenghts);

    }

    private Integer findColLength() {
        int returnValue=0;
        for(int x=0;x<grid.length;x++) {
            int currentMax=0;
            for(int y=0;y<grid[0].length;y++) {
                if(grid[x][y]!=null){
                    if(currentMax > returnValue) {
                        returnValue=currentMax;
                    }
                    colLenghts.add(currentMax);
                    currentMax=0;
                }else{
                    currentMax++;
                }
                if(currentMax > returnValue) {
                    returnValue=currentMax;
                }
                colLenghts.add(currentMax);
            }
        }
        return returnValue;
    }

    private Integer findRowLength() {
        int returnValue=0;

        for(int y=0;y<grid[0].length;y++) {
            int currentMax=0;
            for(int x=0;x<grid.length;x++) {
                if(grid[x][y]!=null){
                    if(currentMax > returnValue) {
                        returnValue=currentMax;
                    }
                    rowLenghts.add(currentMax);
                    currentMax=0;
                }else{
                    currentMax++;
                }
                if(currentMax > returnValue) {
                    returnValue=currentMax;
                }
                rowLenghts.add(currentMax);
            }
        }
        return returnValue;
    }

    public int getMostCommonInt(ArrayList<Integer> arr) {
        int max_count = 0;
        int maxfreq = 0;

        for (int i = 0; i < arr.size(); i++){
            int count = 0;
            for (int j = 0; j < arr.size(); j++){
                if (arr.get(i) == arr.get(j)){
                    count++;
                }
            }
            if (count > max_count){
                max_count = count;
                maxfreq = arr.get(i);
            }

        }
        return maxfreq;
    }

}
