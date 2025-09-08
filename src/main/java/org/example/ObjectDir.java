package org.example;

import java.io.File;
import java.util.ArrayList;

public class ObjectDir {
    String dirPath="";
    ArrayList<File> files=new ArrayList<File>();
    ArrayList<String> dirs=new ArrayList<String>();

    public ObjectDir(String dirPath){
        this.dirPath=dirPath;
    }

    public boolean isEmpty(){
        return files.isEmpty() && dirs.isEmpty();
    }

    public boolean containsDir(){
        return dirs.isEmpty();
    }

    public boolean containsFile(){
        return files.isEmpty();
    }

    public boolean isEquivalent(String fileName){
        return false;
    }

    public boolean neededFilePresent(String fileName){
        return false;
    }




}
