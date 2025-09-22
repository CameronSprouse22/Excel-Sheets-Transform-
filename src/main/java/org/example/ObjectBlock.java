package org.example;

import java.util.ArrayList;

public class ObjectBlock {

    ArrayList<ObjectCell> blockArray;
    ArrayList<ObjectRow> ObjectRows=new ArrayList<>();
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
    ObjectCell[][] gridNonRawDate;
    int rowColStart;
    int commonLengthWithGap;
    ObjectCell[][] gridRawDate;
    ObjectCell[] rowHeaders;
    ObjectCell[][] cellsGridData;
    ObjectCell[][] cellsGridNonData;
    ArrayList<Integer> rowLengthsIncludingGap=new ArrayList<>();

    public ObjectBlock(ArrayList<ObjectCell> blockArray, int maxX, int maxY) {

        this.blockArray=blockArray;
        this.maxX=maxX;
        this.maxY=maxY;
        System.out.println("NEW BLOCK with maxX:"+maxX+" maxY:"+maxY+" and size:"+blockArray.size());
        grid=new ObjectCell[maxX][maxY];
        rowHeaders=new ObjectCell[maxX];
        gridNonRawDate=new ObjectCell[maxX][maxY];
        gridRawDate=new ObjectCell[maxX][maxY];

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

        for (int x = 0; x < grid.length; x++) {
            ObjectCell[] currentRow=new ObjectCell[rangeMaxX];
            System.out.println("-----"+rangeMaxX);
            for (int y = 0; y < grid[x].length; y++) {
                currentRow[y]=grid[x][y];
            }
            ObjectRows.add(new ObjectRow(currentRow));
        }


        System.out.println();
        System.out.println("------------------------------------------");
//
//        if(grid!=null && grid.length>1) {
//            for (int x = 0; x < grid.length; x++) {
//                if(grid[x][3]!=null) {
//                    System.out.print("["+grid[x][3].toString() + "] | ");
//                }else  {
//                    System.out.print("[null] | ");
//                }
//            }
//        }
//        System.out.println();
//
//
//
//        height=rangeMaxY-rangeMinY;
//        width=rangeMaxX-rangeMinX;
//
//
//
//        System.out.println("MIN Max Y:"+rangeMaxY+"--->"+rangeMinY);
//        System.out.println("MIN Max X:"+rangeMaxX+"--->"+rangeMinX);
//
//        System.out.println("Height:"+height);
//        System.out.println("Width:"+width);

//
//        if (height == 1) {
//            if(width == 1)  {
//                setToLabelOnly();
//            }
//            else{
//                setToLabelAndFormula();
//            }
//            return;
//        } else if(width == 1 )  {
//            setToList();
//            return;
//        }else{
//            findDataStructureByCommonLengths();
//
//            xCommonLength=findMostCommonColLength();
//            yCommonLength=findMostCommonRowLength();
//
//            System.out.println("xCommonLength:"+xCommonLength);
//            System.out.println("yCommonLength:"+yCommonLength);
//
//            setToDataTable();
//        }
//
//        CommonFunctions.ObjectBlockPrint(grid);
//        findRowHeaders();

    }




    private void getRowLengthsIncludingGap() {

        for(int y=rangeMinY;y< rangeMaxY;y++) {
            boolean isHeader=true;
            int lineLenghtWithGap=0;
            int longestLineLenghtWithGap=0;
            int startOfLineCurrent=-1;
            int endOfLine=-1;

            for(int x=rangeMinX;x<rangeMaxX;x++) {
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
            rowLengthsIncludingGap.add(longestLineLenghtWithGap);
            System.out.println();
            System.out.println("LINE "+ y +" Longest line lenght with gap:"+longestLineLenghtWithGap+" from "+startOfLineCurrent+" to "+endOfLine);
        }
    }

    private void findRowHeaders() {
        System.out.println();

        for(int y=rangeMinY;y< rangeMaxY;y++) {
            boolean isHeader=true;
            int lineLenghtWithGap=0;
            int longestLineLenghtWithGap=0;
            int startOfLineCurrent=-1;
            int endOfLine=-1;

            for(int x=rangeMinX;x<rangeMaxX;x++) {
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
            rowLengthsIncludingGap.add(longestLineLenghtWithGap);
            System.out.println();
            System.out.println("LINE "+ y +" Longest line lenght with gap:"+longestLineLenghtWithGap+" from "+startOfLineCurrent+" to "+endOfLine);
        }
        System.out.println("COL HEADERS:");
        commonLengthWithGap=getMostCommonInt(rowLengthsIncludingGap);
        for(int y=rangeMinY;y< rangeMaxY;y++) {
            if(rowLengthsIncludingGap.get(y)==commonLengthWithGap || rowLengthsIncludingGap.get(y)==(commonLengthWithGap-1)) {
                if(rowLengthsIncludingGap.get(y+1)==commonLengthWithGap) {
                    rowColStart=y;
                    for (int i = 0; i < rangeMaxX+1; i++) {
                        System.out.print("["+grid[i][y].toString() + "]| ");
                        if(grid[i][y] != null){
                            rowHeaders[i] = grid[y][i];
                            gridNonRawDate[y][i]=grid[y][i];
                            grid[i][y].setcellDis("HEADING");
                        }else{
                            rowHeaders[i]=null;
                        }

                    }

                            break;
                }
            }
        }
    }


    private void findRowHeadersdelLater() {
        System.out.println();
        ArrayList<Integer> rowLengths=new ArrayList<>();
        for(int y=rangeMaxY;y>= rangeMinY;y--) {
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
            rowLengths.add(longestLineLenghtWithGap);
            System.out.println();
            System.out.println("LINE "+ y +" Longest line lenght with gap:"+longestLineLenghtWithGap+" from "+startOfLineCurrent+" to "+endOfLine);
        }
        System.out.println("COL HEADERS:");
        int commonLength=getMostCommonInt(rowLengths);
        for(int y=0;y< rangeMaxY;y++) {
            if(rowLengths.get(y)==commonLength || rowLengths.get(y)==(commonLength-1)) {
                if(rowLengths.get(y+1)==commonLength) {
                    for (int i = 0; i < rangeMaxX; i++) {
                        System.out.print(grid[i][y].toString() + " | ");
                        rowHeaders[i] = grid[y][i];
                    }

                    break;
                }
            }
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
