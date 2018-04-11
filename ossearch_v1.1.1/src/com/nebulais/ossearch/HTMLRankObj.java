package com.nebulais.ossearch;

import java.lang.*;
import java.util.*;
import java.io.*;
public class HTMLRankObj {
    private File file;
    private String title;
    private double score;
    private String url;

    private int kwlen;
    private Vector [] matches;

    String [] keywords;

    public HTMLRankObj(File f, String [] k, String u) throws RankException {
        try {
            file = f;
            keywords=k;
            url = u;
            StringBuffer word = new StringBuffer();
            StringBuffer temp = new StringBuffer();
            boolean open=false;
            char c;
            kwlen = keywords.length;
            matches = new Vector[kwlen];
            for(int i=0;i<kwlen;i++) {
                matches[i] = new Vector();
            }
            score=0.0;
            int ct=0;
            boolean tFound=false;
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis,1024);
            int val;
            byte [] line = new byte[1024];
            boolean extractingTitle=false;
            int kwl;
            int wl;
            String w;
            String kw;
            boolean inhead = true;

            // Extract words, check for matches
            while((val=bis.read(line,0,1024))!=-1) {
                for(int b=0;b<val;b++) {
                    c = (char) line[b];
                    if(extractingTitle) {
                        if(c=='<') {
                            title=temp.toString();
                            temp.setLength(0);
                            tFound=true;
                            extractingTitle=false;
                            open=true;
                        } else {
                            temp.append(c);
                        }
                    }

                    if(open) {
                        if(c=='!' && temp.length()==0) {
                            boolean commentStart=true;
                            boolean commentEnd=false;
                            int dash=0;
                            for(;b<val;) {
                                b++;
                                if(b>=val) {
                                    if((val=bis.read(line,0,1024))==-1) {
                                        break;
                                    } else {
                                        b=0;
                                    }
                                }
                                c = (char) line[b];
                                if(commentStart==true) {
                                    if(c=='-') {
                                        dash++;
                                        if(dash>=2) {
                                            commentStart=false;
                                            dash=0;
                                        }
                                    } else {
                                        break;
                                    }
                                } else if(commentEnd==true) {
                                    if(c=='>' && dash>=2) {
                                        open = false;
                                        word.setLength(0);
                                        break;
                                    } else if(c=='-') {
                                        dash++;
                                    } else {
                                        commentEnd=false;
                                        dash=0;
                                    }
                                } else {
                                    if(c=='-') {
                                        commentEnd=true;
                                        dash=1;
                                    }
                                }
                            }
                        } else if(c=='>') {
                            if(!tFound && temp.toString().equalsIgnoreCase("title")) {
                                extractingTitle=true;
                            }
                            if(inhead && temp.toString().equalsIgnoreCase("/head")) {
                                inhead=false;
                            } else if(inhead && temp.toString().toLowerCase().startsWith("meta ")) {
                                String meta = temp.toString().toLowerCase();
                                int d = meta.indexOf("description");
                                if(d<0) {
                                    d = meta.indexOf("keywords");
                                }
                                if(d>0) {
                                    meta = meta.substring(d);
                                    String quote = "\"";
                                    d = meta.indexOf(quote);
                                    if(d<0) {
                                        quote = "'";
                                        d = meta.indexOf(quote);
                                    }
                                    if(d>0) {
                                        StringTokenizer tok = new StringTokenizer(meta," -\t~!@#$%^&*()_+=[{]};:'\",.<>?/|\\",false);
                                        while(tok.hasMoreTokens()) {
                                            word = new StringBuffer(tok.nextToken());
                                            wl=word.length();
                                            w=word.toString().toLowerCase();
                                            if(wl!=0) {
                                                for(int j=0;j<kwlen;j++) {
                                                    kw = keywords[j];
                                                    kwl=kw.length();
                                                    if((kwl==wl) && kw.equalsIgnoreCase(w)) {
                                                        // award for match
                                                        score += 12.0;
                                                    } else if(wl>kwl && wl<=kwl+3 && w.startsWith(kw)) {
                                                        // award for close match
                                                        score += 4.0;
                                                    } else if(kwl>wl && kwl<=wl+3 && kw.startsWith(w)) {
                                                        // award for close match
                                                        score += 4.0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            open = false;
                            temp.setLength(0);
                            word.setLength(0);
                        } else if(c=='<') {
                            int sz=temp.length();
                            word.setLength(0);
                            for(int i=0;i<sz;i++) {
                                c = temp.charAt(i);
                                if(!Character.isLetterOrDigit(c)) {
                                    wl=word.length();
                                    w=word.toString().toLowerCase();
                                    if(wl!=0) {
                                        for(int j=0;j<kwlen;j++) {
                                            kw = keywords[j];
                                            kwl=kw.length();
                                            if((kwl==wl) && kw.equalsIgnoreCase(w)) {
                                                // award for match
                                                (matches[j]).addElement(new Integer(ct));
                                                score += 10.0;
                                            } else if(wl>kwl && wl<=kwl+3 && w.startsWith(kw)) {
                                                // award for close match
                                                score += 3.0;
                                            } else if(kwl>wl && kwl<=wl+3 && kw.startsWith(w)) {
                                                // award for close match
                                                score += 3.0;
                                            }
                                        }
                                        ct++;
                                        word.setLength(0);
                                    }
                                } else {
                                    word.append(c);
                                }
                            }
                            word.setLength(0);
                        } else {
                            temp.append(c);
                        }
                    } else {
                        if(!Character.isLetterOrDigit(c)) {
                            if(c=='<') {
                                open=true;
                            }
                            wl=word.length();
                            w=word.toString().toLowerCase();
                            if(wl!=0) {
                                for(int j=0;j<kwlen;j++) {
                                    kw = keywords[j];
                                    kwl=kw.length();
                                    if((kwl==wl) && kw.equalsIgnoreCase(w)) {
                                        // award for match
                                        (matches[j]).addElement(new Integer(ct));
                                        score += 10.0;
                                    } else if(wl>kwl && wl<=kwl+3 && w.startsWith(kw)) {
                                        // award for close match
                                        score += 3.0;
                                    } else if(kwl>wl && kwl<=wl+3 && kw.startsWith(w)) {
                                        // award for close match
                                        score += 3.0;
                                    }
                                }
                                ct++;
                                word.setLength(0);
                            }
                        } else {
                            word.append(c);
                        }
                    }
                }
            }
            bis.close();

            if(!tFound) {
                title=file.getName();
            }

            // Award 100 points for all keyword matches
            if(kwlen>=2) {
                boolean allMatch=true;
                for(int i=0;i<kwlen;i++) {
                    if((matches[i]).size()==0) {
                        allMatch=false;
                        break;
                    }
                }
                if(allMatch) {
                    score+=100;
                }
            }

            int kwDiff;
            int wDiff;
            Vector match1;
            int mlen1;
            Vector match2;
            int mlen2;

            // Award points to near by matches
            for(int i=0;i<kwlen;i++) {
                for(int j=i+1;j<kwlen;j++) {
                    kwDiff = j - i;
                    match1 = matches[i];
                    mlen1 = match1.size();
                    match2 = matches[j];
                    mlen2 = match2.size();
                    for(int m=0;m<mlen1;m++) {
                        for(int n=0;n<mlen2;n++) {
                            wDiff = ((Integer) match2.elementAt(n)).intValue() - ((Integer) match1.elementAt(m)).intValue();
                            score += 50.0/(Math.pow(1.0+(double) Math.abs(kwDiff-wDiff), 2.5));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw(new RankException(e.toString()));
        }
    }

    /**
     * Get the score
     */
    public double getScore() {
        return(score);
    }

    /**
     * Get the document title
     */
    public String getTitle() {
        return(title);
    }

    /**
     * Get the document URL
     */
    public String getURL() {
        return(url);
    }
}