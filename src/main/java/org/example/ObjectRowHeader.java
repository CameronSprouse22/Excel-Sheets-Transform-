package org.example;

public class ObjectRowHeader extends ObjectRow {
    Integer rowHeaderStart;
    public ObjectRowHeader(ObjectCell[] objectCells, Integer rowHeader) {
        super(objectCells);
        rowHeaderStart=rowHeader;
    }

    public Integer getRowHeaderStart() {
        return rowHeaderStart;
    }
}
