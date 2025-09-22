package org.example;

import java.util.ArrayList;

public class ObjectRow {

    ArrayList<ObjectCell> objectCellsArray=new ArrayList<>();
    Integer startNunNullXcord;
    Integer endNunNullXcord;

    Integer startGap;
    Integer endGap;
    Integer gapLenght;

    Boolean isNullRow=true;

    public ObjectRow(ObjectCell[] objectCells) {

        int lineLenghtWithGap=0;
        int longestLineLenghtWithGap=0;
        int startOfLineCurrent=-1;
        int endOfLine=-1;
        boolean currentGap=false;

        for(int x=0; x < objectCells.length; x++) {
            if(objectCells[x]!=null) {
                System.out.print(objectCells[x].toString()+" *");
                if(startNunNullXcord==null) {
                    startNunNullXcord=x;
                    endNunNullXcord=x;
                    isNullRow=false;
                }
                if(endNunNullXcord < x) {
                    endNunNullXcord=x;
                }

            }
            objectCellsArray.add(objectCells[x]);


            //Cell has value
            //System.out.print(objectCells[x].toString()+" | ");

            if(objectCells[x]!=null && objectCells[x].containsData()){
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
        gapLenght=longestLineLenghtWithGap;
        startGap=startOfLineCurrent;
        endGap=endOfLine;
        System.out.println("");
        System.out.println(">>>>>>> line lenght with gap:"+longestLineLenghtWithGap+" from "+startOfLineCurrent+" to "+endOfLine);

    }


}
