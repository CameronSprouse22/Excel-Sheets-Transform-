package org.example;

import java.util.ArrayList;

public class ObjectBlock {

    ArrayList<ObjectCell> blockArray;
    ArrayList<ObjectRow> ObjectRows=new ArrayList<>();
    ArrayList<ObjectCol> ObjectCols=new ArrayList<>();
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
    Integer rowCommonLenghts;
    Integer colCommonLenghts;
    ObjectRowHeader rowHeader;


    ArrayList<Integer> rowLengthsIncludingGap=new ArrayList<>();

    public ObjectBlock(ArrayList<ObjectCell> blockArray, int maxX, int maxY) {

        this.blockArray=blockArray;
        this.maxX=maxX;
        this.maxY=maxY;
        grid=new ObjectCell[maxX][maxY];
        rowHeaders=new ObjectCell[maxX];
        gridNonRawDate=new ObjectCell[maxX][maxY];
        gridRawDate=new ObjectCell[maxX][maxY];
        cellsGridData=new ObjectCell[maxX][maxY];
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
        System.out.println();
        for (int y = 0; y < grid[0].length; y++) {
            ObjectCell[] currentRow=new ObjectCell[grid[0].length];
            for (int x = 0; x < grid.length; x++) {
                if(grid[x][y] != null) {
                    currentRow[x]=grid[x][y];
                    System.out.print("<"+grid[x][y]+">");
                }else{
                    currentRow[x]=null;
                    System.out.print("<N>>");
                }
            }
            //System.out.println("");
            ObjectRow objectRow = new ObjectRow(currentRow);
            ObjectRows.add(objectRow);
            //System.out.println("-->"+objectRow.gapLenght);

            if(objectRow.gapLenght >0){
                rowLengthsIncludingGap.add(objectRow.gapLenght);
            }
        }
        rowCommonLenghts=getMostCommonInt(rowLengthsIncludingGap);


        ArrayList<Integer> colLengthsIncludingGap=new ArrayList<>();
        System.out.println(" arry size "+grid[0].length);
        for (int x = 0; x < grid.length; x++) {
            ObjectCell[] currentCol=new ObjectCell[grid[0].length];
            for (int y = 0; y < grid[0].length; y++) {
                if(grid[x][y] != null) {
                    currentCol[y]=grid[x][y];
                    System.out.print("["+grid[x][y]+"]");
                }else{
                    currentCol[y]=null;
                    System.out.print("<N>>");
                }
            }
            System.out.println("");
            ObjectCol objectCol = new ObjectCol(currentCol);
            ObjectCols.add(objectCol);
            System.out.println("-->"+objectCol.gapLenght);

            if(objectCol.gapLenght >0){
                colLengthsIncludingGap.add(objectCol.gapLenght);
            }
        }
        colCommonLenghts=getMostCommonInt(colLengthsIncludingGap);


        System.out.println("COLLETH ROW->"+rowCommonLenghts+" COL->"+colCommonLenghts);
        System.out.println();
        System.out.println("------------------------------------------");




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
        rowHeader = findRowHeader();
        findDems();
        setAttachedCells();
        ExcelWriter.writeUseDisExcelFileFrom2DArray("testDESDONE" +".xlsx",grid);

    }

    private void setAttachedCells() {

        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[0].length; y++) {
                if(grid[x][y] != null &&  grid[x][y].getcellDis() == null) {
                    if(grid[x][y].isFormula){
                        grid[x][y].setcellDis("ATTACHED FORMULA");
                    }else{
                        grid[x][y].setcellDis("ATTACHED LABLE");
                    }
                    gridNonRawDate[x][y]=grid[x][y];
                }

            }
        }

    }

    private Integer findDems() {
        Integer colStart=null;

        for(int y=rowHeader.getRowHeaderStart()+1; y< (rowHeader.getRowHeaderStart() + colCommonLenghts-1); y++) {
            for(int x=ObjectRows.get(y).startGap; x <= ObjectRows.get(y).endGap; x++) {
                if(grid[x][y] != null) {
                    grid[x][y].setcellDis("DATA");
                    cellsGridData[x][y]=grid[x][y];
                }else{
//                    ObjectCell oj = new ObjectCell(x, y);
//                    oj.setcellDis("NO DATA");
//                    grid[x][y]= oj;
                }

            }
        }

        return null;
    }

    private ObjectRowHeader findRowHeader() {
        ObjectCell[] headRow=new ObjectCell[grid[0].length];
        boolean headerFound=false;
        Integer rowHeader=null;
        for(int x=0; x< ObjectRows.size(); x++) {
            System.out.println(x+"-->"+ObjectRows.get(x).gapLenght+" VS "+rowCommonLenghts);
            if(ObjectRows.get(x).gapLenght >= ( rowCommonLenghts-1) ){
                    rowHeader=x;
                    break;
            }
        }

        Integer colStart=null;
        Integer colEnd=null;
        for(int x=ObjectRows.get(rowHeader+1).startGap; x<=ObjectRows.get(rowHeader+1).endGap; x++) {
                colEnd=x;
                grid[colEnd ][rowHeader].setcellDis("HEADER");
                headRow[colEnd]=grid[colEnd ][rowHeader];
        }
        ObjectRowHeader objectRowHeader=new ObjectRowHeader(headRow,rowHeader);
        System.out.println("  HEADER ROW ");
        System.out.println(objectRowHeader.toString());
        return objectRowHeader;
    }

    private Integer findDataTableRange(int rowHeaderCord) {
        boolean headerFound=false;
        for(int x=rowHeaderCord; x< ObjectRows.size(); x++) {
            if(ObjectRows.get(x).gapLenght == rowCommonLenghts &&ObjectRows.get(x+1).gapLenght == rowCommonLenghts && ObjectRows.get(x+2).gapLenght == (1 - rowCommonLenghts)){
                return x;
          }
        }
        //TODO
        return null;
    }

    private Integer findDataTableBnt(int rowHeaderCord) {
        boolean headerFound=false;
        for(int x=rowHeaderCord; x< ObjectRows.size(); x++) {
            if(ObjectRows.get(x).gapLenght == rowCommonLenghts &&ObjectRows.get(x+1).gapLenght == rowCommonLenghts && ObjectRows.get(x+2).gapLenght == (1 - rowCommonLenghts)){
                return x;
            }
        }
        //TODO
        return null;
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

    private void findRowHeaders2() {
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
