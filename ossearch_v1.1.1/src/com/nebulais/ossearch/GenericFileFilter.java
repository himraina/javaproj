///////////////////////////////////////////////////////////////////
// Copyright (c) 2001 Nebula Internet Software, LLC
///////////////////////////////////////////////////////////////////
package com.nebulais.ossearch;

import java.io.*;
import java.util.*;
public class GenericFileFilter implements FilenameFilter {
    private File root;
    private Vector extensions;
    private Vector exFiles;
    public GenericFileFilter(File r, Vector ext, Vector exf) {
        root=r;
        extensions=ext;
        exFiles=exf;
    }

    /**
     * Implements FilenameFilter.accept method
     */
    public boolean accept(File dir,String name) {
        int sz = extensions.size();
        int exf_sz = exFiles.size();
        File file;
        File exf;
        for(int i=0;i<sz;i++) {
            if(name.endsWith(extensions.elementAt(i).toString())) {
                file = new File(dir,name);
                for(int j=0;j<exf_sz;j++) {
                    exf = new File(root,exFiles.elementAt(j).toString());
                    if(exf.getAbsolutePath().compareTo(file.getAbsolutePath())==0) {
                        return(false);
                    }
                }
                return(true);
            }
        }
        return(false);
    }
}