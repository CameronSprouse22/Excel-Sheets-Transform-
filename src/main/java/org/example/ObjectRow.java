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

        System.out.println();
        for(int x=0; x < objectCells.length; x++) {

            if(objectCells[x]!=null) {
                System.out.print("["+objectCells[x].toString()+"]");
                if(startNunNullXcord==null) {
                    startNunNullXcord=x;
                    endNunNullXcord=x;
                    isNullRow=false;
                }
                if(endNunNullXcord < x) {
                    endNunNullXcord=x;
                }

            }else {
                System.out.print("[N]");
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
            }else if(currentGap || lineLenghtWithGap==0){
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
            System.out.print("("+longestLineLenghtWithGap+")");
        }
        gapLenght=longestLineLenghtWithGap;
        startGap=startOfLineCurrent;
        endGap=endOfLine;
        if(endOfLine != -1 && objectCells[endOfLine] != null) {
            System.out.println(longestLineLenghtWithGap +")))) "+objectCells[endOfLine].toString());
        }else{
            System.out.println(longestLineLenghtWithGap +")))) N");
        }

        System.out.println("");
        System.out.println(">>>>>>> line lenght with gap:"+longestLineLenghtWithGap+" from "+startOfLineCurrent+" to "+endOfLine);
        System.out.println(">>>>>>> line lenght with gap:"+longestLineLenghtWithGap+" from "+startGap+" to "+endGap);
        System.out.println();
    }

    @Override
    public String toString(){
        String str="";
        for(int x=0; x < objectCellsArray.size(); x++) {
            if(objectCellsArray.get(x) != null) {
                str+="("+x+")["+objectCellsArray.get(x).toString()+"] | ";
            }else{
                str+="("+x+")[N] | ";
            }

        }
        return str;
    }


}
