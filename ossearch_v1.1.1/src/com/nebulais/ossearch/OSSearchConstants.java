package com.nebulais.ossearch;

import java.io.*;
import java.awt.*;

public class OSSearchConstants {

	public static final String VERSION = "v";


    public static final String ADMIN_EMAIL = "yourname@yourdomain.com";

    /**
     * The servlet's base directory
     */
    public static final File SERVLET_DIR = new File("/your/home/directory/servlets");

    /**
     * The url to the servlet's directory (include http://)
     */
    public static final String SERVLET_URL = "http://www.yoururl.com/your/servlets/directory/";

  
  
    /** Two letter words to exclude from search */
    public static final String [] EXCLUDE_2 = { "an", "as", "at", "be", "by", "or", "it", "he", "is", "to",
            "in", "do", "so", "of", "no", "on", "me", "us", "am" };

    /** Three letter words to exclude from search */
    public static final String [] EXCLUDE_3 = { "and", "are", "but", "can", "why", "she", "him", "her", "who",
            "how", "was", "the", "did", "for", "had", "its", "you", "use" };

    /** Four letter words to exclude from search */
    public static final String [] EXCLUDE_4 = { "will", "what", "when", "does", "with", "have", "been", "much",
            "many", "some", "more", "most", "then", "were", "very", "used",
            "them", "come", "from", "just", "your", "must" };

    /** Five letter words to exclude from search */
    public static final String [] EXCLUDE_5 = { "could", "would", "where", "there", "their", "since" };

    /** Six letter words to exclude from search */
    public static final String [] EXCLUDE_6 = { "should" };

    //
    // ---------------------------------------------------------------------------------------------------------

    /**
      * Add Subscriber template
      */
    public static final String TEMPLATE = "ossearch_1.html";

}