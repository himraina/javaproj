package com.nebulais.ossearch;

import java.io.*;
import java.util.*;

public class DirectoryFilter implements FilenameFilter {
    private Vector exclude;
    private File root;

    public DirectoryFilter(File r, Vector e) {
        root=r;
        exclude=e;
    }
    public boolean accept(File dir,String name) {
        int ex_sz = exclude.size();
        File exd;
        File directory = new File(dir,name);
        if(directory.isDirectory() && directory.canRead()) {
            for(int j=0;j<ex_sz;j++) {
                exd = new File(root,exclude.elementAt(j).toString());
                if(exd.getAbsolutePath().compareTo(directory.getAbsolutePath())==0) {
                    return(false);
                }
            }
            return(true);
        }
        return(false);
    }
}