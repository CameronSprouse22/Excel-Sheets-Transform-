package org.example;

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
        System.out.println("NEW BLOCK with maxX:"+maxX+" maxY:"+maxY+" and size:"+blockArray.size());
        grid=new ObjectCell[maxX][maxY];

        for(ObjectCell cell:blockArray) {

            grid[cell.getX()][cell.getY()] = cell;
//            System.out.print("["+cell.getX()+","+cell.getY()+"]");
//            System.out.println("---->,>"+cell.toString());
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



        height=rangeMaxY-rangeMinY;
        width=rangeMaxX-rangeMinX;



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
            return;
        } else if(width == 1 )  {
            setToList();
            return;
        }else{
            findDataStructureByCommonLengths();

            xCommonLength=findMostCommonColLength();
            yCommonLength=findMostCommonRowLength();

            System.out.println("xCommonLength:"+xCommonLength);
            System.out.println("yCommonLength:"+yCommonLength);

            setToDataTable();
        }

        CommonFunctions.ObjectBlockPrint(grid);
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

            for(int x=rangeMaxX;x>=rangeMinX;x--) {
                boolean currentGap=false;
                //Cell has value
                System.out.print(grid[x][y].toString()+" | ");
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

    private int getRowLenghtInludingGaps(int x){
        int current=0;
        int currentRowMax=0;
        for(int y=0;y<grid[0].length;y++) {
            if(grid[x][y]==null){
                current=0;
            }else{
                current++;
            }
            if(currentRowMax < current) {
                currentRowMax = current;
            }
        }
        return currentRowMax;
    }

    private Integer findMostCommonColLength() {
        ArrayList<Integer> colLenghtsTemp=new ArrayList<>();
        for(int x=0;x<grid.length;x++) {
            int current=0;
            int currentRowMax=0;
            for(int y=0;y<grid[0].length;y++) {
                if(grid[x][y]==null){
                    current=0;
                }else{
                    current++;
                }
                if(currentRowMax < current) {
                    currentRowMax = current;
                }
            }
            if(currentRowMax>0) {
                colLenghtsTemp.add(currentRowMax);
            }
        }
        return getMostCommonInt(colLenghtsTemp);
    }

    private Integer findMostCommonRowLength() {
        ArrayList<Integer> rowLenghtsTemp=new ArrayList<>();
        for(int y=0;y<grid[0].length;y++) {
            int current=0;
            int currentRowMax=0;
            for(int x=0;x<grid.length;x++) {
                if(grid[x][y]==null){
                    current=0;
                }else{
                    current++;
                }
                if(currentRowMax < current) {
                    currentRowMax = current;
                }
            }
            if(currentRowMax>0) {
                rowLenghtsTemp.add(currentRowMax);
            }
        }
        return getMostCommonInt(rowLenghtsTemp);
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

    public ObjectCell[][] getBlockGrid(){
        return grid;
    }



}
