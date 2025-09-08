package org.example;

import java.util.ArrayList;

public class ObjectTable {
    ArrayList<String> rows=new ArrayList<String>();
    ArrayList<String> columns =new ArrayList<String>();

    Integer rawStartX;
    Integer rawEndX;
    Integer rawStartY;
    Integer rawEndY;

    Integer startX;
    Integer endX;
    Integer startY;
    Integer endY;

    ArrayList<ObjectCell> cells;
}
