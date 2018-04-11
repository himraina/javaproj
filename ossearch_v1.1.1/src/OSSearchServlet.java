import java.io.*;
import java.util.*;
import java.awt.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.nebulais.ossearch.*;

public class OSSearchServlet extends HttpServlet implements SingleThreadModel {
    private boolean is_error = false;
    private Exception err;

    private File rootPath;
    private String rootUrl;
    private Vector files;
    private Vector urls;

    private Vector results;

    private PrintWriter out;
    private String [] keywords;

    /**
     * Initialize the servlet
     */
    public void init(ServletConfig config) throws ServletException	{
        super.init(config);

        try {
            // Read configuration file.
            files = new Vector();
            urls = new Vector();

            configure();
        } catch(Exception e) {
            is_error=true;
            err=e;
        }
    }

    /**
     * Returns blank search template
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        // first, set the "content type" header of the response
        res.setContentType("text/html");

        // Get the response's PrintWriter to return text to the client.
        out = res.getWriter();

        // Print any error messages from initialization
        if(is_error) {
            error(err,"Error in Initialization: <br><br>" + err.toString());
        }

        File file = new File(OSSearchConstants.SERVLET_DIR,OSSearchConstants.TEMPLATE);

        StringBuffer text = new StringBuffer();

        results = new Vector();

        StringBuffer word = new StringBuffer();

        try {
            // Open template document.
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis,1024);

            // Create results document from template
            int val;
            char c;
            boolean reading=false;
            byte [] line = new byte[1024];
            while((val=bis.read(line,0,1024))!=-1) {
                for(int b=0;b<val;b++) {
                    c = (char) line[b];
                    if(reading) {
                        if(c=='%') {
                            if(word.toString().equals("results")  ||
                                    word.toString().equals("keywords")) {
                                reading=false;
                            } else if(word.toString().equals("servlet_url")) {
                                text.append(OSSearchConstants.SERVLET_URL);
                                reading=false;
                            } else {
                                text.append("%");
                                text.append(word.toString());
                            }
                            word.setLength(0);
                        } else {
                            word.append(c);
                        }
                    } else if(c=='%') {
                        reading=true;
                    } else {
                        text.append(c);
                    }
                }
            }
            bis.close();
            if(reading) {
                text.append("%");
                text.append(word.toString());
            }

            // Write out the results document
            out.println(text.toString());

            out.close();
        } catch(FileNotFoundException e) {
            error(e,"Template not found: " + file + "<br><br>" + e.toString());
            return;
        }
        catch(Exception e) {
            error(e,e.toString() + "<br><br> " + text.toString());
            return;
        }
    }



    /**
     * Reads information from form and searches web documents.
     */
    public void doPost(HttpServletRequest req,
                       HttpServletResponse res) throws ServletException, IOException {

        // first, set the "content type" header of the response
        res.setContentType("text/html");

        // Get the response's PrintWriter to return text to the client.
        out = res.getWriter();

        File file = new File(OSSearchConstants.SERVLET_DIR,OSSearchConstants.TEMPLATE);

        StringBuffer text = new StringBuffer();

        results = new Vector();

        try {
            // Get the keywords
            String kw=req.getParameterValues("keywords")[0];
            StringTokenizer tok = new StringTokenizer(kw," -\t~!@#$%^&*()_+=[{]};:'\",.<>?/|\\",false);

            int len = tok.countTokens();
            Vector kwv = new Vector();
            for(int i=0;i<len;i++) {
                kw = tok.nextToken();
                if(isValidKeyword(kw)) {
                    kwv.addElement(kw);
                }
            }
            len = kwv.size();
            keywords = new String[len];
            for(int i=0;i<len;i++) {
            	keywords[i] = kwv.elementAt(i).toString();
        	}

            // Search web files and order into a list.
            int sz = files.size();
            String url;
            for(int i=0;i<sz;i++) {
                url = (String) urls.elementAt(i);
                try {
                    HTMLRankObj rank = new HTMLRankObj((File) files.elementAt(i),keywords,url);
                    insertResult(rank);
                } catch(RankException e) {
                    error(e,e.getMessage());
                    return;
                }
            }

            // Open template document.
            StringBuffer word = new StringBuffer();
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis,1024);

            // Create results document from template
            int val;
            char c;
            boolean reading=false;
            byte [] line = new byte[1024];
            while((val=bis.read(line,0,1024))!=-1) {
                for(int b=0;b<val;b++) {
                    c = (char) line[b];
                    if(reading) {
                        if(c=='%') {
                            if(word.toString().equals("results")) {
                                HTMLRankObj obj;
                                sz = Math.min(results.size(),15);
                                if(sz==0) {
                                    text.append("No Document Found");
                                }
                                for(int i=0;i<sz;i++) {
                                    obj = (HTMLRankObj) results.elementAt(i);
                                    text.append((new Integer(i+1)).toString());
                                    text.append(". <a href=\"");
                                    text.append(obj.getURL());
                                    text.append("\">");
                                    text.append(obj.getTitle());
                                    text.append("</a>");
                                    text.append("<br>\n");
                                }
                                reading=false;
                            } else if(word.toString().equals("keywords")) {
                                for(int i=0;i<len;i++) {
                                    text.append(keywords[i]);
                                    if(i<len-1) {
                                        text.append(" ");
                                    }
                                }
                                reading=false;
                            } else if(word.toString().equals("servlet_url")) {
                                text.append(OSSearchConstants.SERVLET_URL);
                                reading=false;
                            } else {
                                text.append("%");
                                text.append(word.toString());
                            }
                            word.setLength(0);
                        } else {
                            word.append(c);
                        }
                    } else if(c=='%') {
                        reading=true;
                    } else {
                        text.append(c);
                    }
                }
            }
            bis.close();
            if(reading) {
                text.append("%");
                text.append(word.toString());
            }

            // Write out the results document
            out.println(text.toString());

            out.close();
        } catch(FileNotFoundException e) {
            error(e,"Template not found: " + file + "<br><br>" + e.toString());
            return;
        }
        catch(Exception e) {
            error(e,e.toString() + "<br><br> " + text.toString());
            return;
        }
    }

    /**
     * Reads configuration file.
     */
    public void configure() throws IOException {
        File file = new File(OSSearchConstants.SERVLET_DIR,"data/ossearch/ossearch.cfg");

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis,1024);

        int val;
        char c;
        StringTokenizer tokenizer;
        StringBuffer temp = new StringBuffer();
        String word;
        Boolean recursive = new Boolean(false);
        Vector extensions=new Vector();
        Vector exDirs=new Vector();
        Vector exFiles=new Vector();
        boolean readingKeyword=false;
        boolean readingUrl=false;
        boolean readingPath=false;
        boolean readingRecursive=false;
        boolean readingExtensions=false;
        boolean readingExDirs=false;
        boolean readingExFiles=false;
        boolean end=false;
        byte [] line = new byte[1024];
        while(!end && (val=bis.read(line,0,1024))!=-1) {
            for(int b=0;b<val;b++) {
                c = (char) line[b];
                if(c=='#' || isWhiteSpace(c)) {
                    if(readingKeyword) {
                        word=temp.toString();
                        if(word.compareTo("root_url")==0) {
                            readingUrl=true;
                        } else if(word.compareTo("root_path")==0) {
                            readingPath=true;
                        } else if(word.compareTo("recursive")==0) {
                            readingRecursive=true;
                        } else if(word.compareTo("extensions")==0) {
                            readingExtensions=true;
                        } else if(word.compareTo("exclude_dirs")==0) {
                            readingExDirs=true;
                        } else if(word.compareTo("exclude_files")==0) {
                            readingExFiles=true;
                        } else if(word.compareTo("end")==0) {
                            end=true;
                            break;
                        }
                        readingKeyword=false;
                        temp.setLength(0);
                    } else if(readingUrl && temp.length()!=0) {
                        rootUrl=temp.toString();
                        if(!rootUrl.endsWith("/")) {
                            rootUrl += "/";
                        }
                        readingUrl=false;
                        temp.setLength(0);
                    } else if(readingPath && temp.length()!=0) {
                        rootPath=new File(temp.toString());
                        readingPath=false;
                        temp.setLength(0);
                    } else if(readingRecursive && temp.length()!=0) {
                        recursive=new Boolean(temp.toString().trim());
                        readingRecursive=false;
                        temp.setLength(0);
                    } else if(readingExtensions && temp.length()!=0) {
                        extensions.addElement(temp.toString());
                        temp.setLength(0);
                    } else if(readingExDirs && temp.length()!=0) {
                        exDirs.addElement(temp.toString());
                        temp.setLength(0);
                    } else if(readingExFiles && temp.length()!=0) {
                        exFiles.addElement(temp.toString());
                        temp.setLength(0);
                    }
                    if(c=='#') {
                        readingKeyword=true;
                        readingExtensions=false;
                        readingExDirs=false;
                        readingExFiles=false;
                    }
                } else {
                    temp.append(c);
                }
            }
        }
        bis.close();

        list(new GenericFileFilter(rootPath,extensions,exFiles),rootPath,"",exDirs,recursive.booleanValue());
    }

    public void list(GenericFileFilter filter, File dir, String rel, Vector exclude, boolean recursive) throws IOException {
        // Add to files and urls list
        String [] ls = dir.list(filter);
        int sz = ls.length;
        for(int i=0;i<sz;i++) {
            files.addElement(new File(dir,ls[i]));
            urls.addElement(rootUrl + rel + ls[i]);
        }

        // Recursively list
        if(recursive) {
            ls = dir.list(new DirectoryFilter(dir,exclude));
            sz = ls.length;
            for(int i=0;i<sz;i++) {
                list(filter,new File(dir,ls[i]),rel + ls[i] + "/",exclude,recursive);
            }
        }
    }

    /**
     * Returns an error message back to the web browser
     * @param ex Exception for printing the stack (passing null will cause the stack not to be printed)
     * @param error Message to display in error message.
     */
    public void error(Exception ex, String error) {
        String text = "<html><head><title>Error</title></head><body>Error occured!";
        text += "<br><br>Please report error to <a href=\"mailto:" + OSSearchConstants.ADMIN_EMAIL + "\">";
        text += OSSearchConstants.ADMIN_EMAIL + "</a>.<br><br>Use \"Back\" arrow to continue\n";
        text += "<br><br>";
        text += error;
        out.println(text);
        if(ex!=null) {
            ex.printStackTrace(out);
        }
        text = "</body></html>";
        out.println(text);
    }


    /**
     * Insert a search result into the results list
     * @param obj HTMLRankObj to insert into the results list.
     */
    public void insertResult(HTMLRankObj obj) {
        double score = obj.getScore();
        double test;
        int sz = results.size();
        boolean inserted=false;
        if(score>0.0) {
            for(int i=0;i<sz;i++) {
                test = ((HTMLRankObj) results.elementAt(i)).getScore();
                if(score>test) {
                    results.insertElementAt(obj,i);
                    inserted=true;
                    break;
                }
            }
            if(!inserted) {
                results.addElement(obj);
            }
        }
    }

    /**
     * Is the string a valid keyword or should it be excluded?
     */
    public boolean isValidKeyword(String kw) {
        int sz;
        if(kw.length()<=1) {
            return false;
        } else if(kw.length()==2) {
            sz = OSSearchConstants.EXCLUDE_2.length;
            for(int i=0;i<sz;i++) {
                if(kw.compareTo(OSSearchConstants.EXCLUDE_2[i])==0) {
                    return false;
                }
            }
        } else if(kw.length()==3) {
            sz = OSSearchConstants.EXCLUDE_3.length;
            for(int i=0;i<sz;i++) {
                if(kw.compareTo(OSSearchConstants.EXCLUDE_3[i])==0) {
                    return false;
                }
            }
        } else if(kw.length()==4) {
            sz = OSSearchConstants.EXCLUDE_4.length;
            for(int i=0;i<sz;i++) {
                if(kw.compareTo(OSSearchConstants.EXCLUDE_4[i])==0) {
                    return false;
                }
            }
        } else if(kw.length()==5) {
            sz = OSSearchConstants.EXCLUDE_5.length;
            for(int i=0;i<sz;i++) {
                if(kw.compareTo(OSSearchConstants.EXCLUDE_5[i])==0) {
                    return false;
                }
            }
        } else if(kw.length()==6) {
            sz = OSSearchConstants.EXCLUDE_6.length;
            for(int i=0;i<sz;i++) {
                if(kw.compareTo(OSSearchConstants.EXCLUDE_6[i])==0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Is the given character a whitespace character?
     */
    public boolean isWhiteSpace(char c) {
        if(c==' ' || c=='\r' || c=='\n' || c=='\t') {
            return(true);
        } else {
            return(false);
        }
    }
}