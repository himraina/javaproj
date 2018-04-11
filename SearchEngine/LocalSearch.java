import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LocalSearch extends HttpServlet 
{
  String searchKey;
  
  final int NUMBER_SPECIAL_CHARS=45;    // Number of Special Character entity names supported.
  // Search state transitions. First find top level pages (from the index page)
  // Search the above pages first then search all other pages.
  final byte FIND_TOP_LEVEL_PAGES=0;
  final byte SEARCH_TOP_LEVEL_PAGES=1;
  final byte SEARCH_OTHER_PAGES=2;

  int hitsFound=0;      // No of occurrences of search String.
  static final byte URLCOUNT=2;
  boolean pageOpened=false;  // Flag to indicate if index page opened OK.
  boolean proxyDetected=false; // Flag to indicate if a proxy server or fire wall has been detected.
  int topLevelSearch;      // Search the index page links first.
  Vector topLevelPages;    // Page names in the index page.
  Vector nextLevelPages;   // Lower level pages.
  Vector pageNames=new Vector();   // Visited links should be stored.
  Vector searchedIs=new Vector();   // All the match found elements should be stored here.
  Vector pageMatch=new Vector();
  public void doGet(HttpServletRequest req,HttpServletResponse res)
                   throws ServletException,IOException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
	out.println("<HTML><HEAD><TITLE>Allsoft Search Result</TITLE></HEAD>");
//	out.println("<BODY> <H3> Search Results </H3> <BR>");
	if(pageNames.size()>0)
	   pageNames.removeAllElements();
	if(searchedIs.size()>0)
	   searchedIs.removeAllElements();
	if(pageMatch.size()>0)
	   pageMatch.removeAllElements();
	searchKey=req.getParameter("srch");
	out.println("<BODY> <H3> Search Results</H3>");
	if(searchKey.length()<3)
	  out.println("U must enter atleast three characters in textfield");
    else
	{
	  starting();
	}
	int total=searchedIs.size();
	out.println("<BR><H4>The total number of matches is "+total+"</H4><BR>");
    for(int i=0;i<searchedIs.size();i++)
    {
	  out.println("<a href=\"http://localhost/"+pageMatch.elementAt(i)+"\">");
      out.println(searchedIs.elementAt(i)+"<BR><BR>");
	  out.println("</a>");
    } 
	System.out.println("The total number of matches is: "+pageMatch.size());
	out.println(" </BODY> </HTML>");
  }

  public void starting()
  {
    
	// State 1: search the index page,remembering all the links on index page.
	topLevelSearch=FIND_TOP_LEVEL_PAGES;
	topLevelPages=new Vector();
	nextLevelPages=new Vector();
    
	// Check to see if a proxy is being used. If so then we use IP Address rather than host name.
	proxyDetected=detectProxyServer();

	startSearch();
  }


  // Function: detectProxyServer
  // Purpose: attempt to see if a proxy server or firewall is blocking a connection back to the 
  // originating server. If so then the variable proxyDetected is set to true and all future connections
  // to the server will use the IPAddress(if passed as a parameter.)
  final boolean detectProxyServer()
  {
    DataInputStream dis=null;
	String url="";

	// Allow for non-Demon Home page
	url="http://localhost:8080/index.htm";
	//Attempt to connect to this url
	try
	{
	  URL doc=new URL(url);
	  dis=new DataInputStream(doc.openStream());
	}
	catch(Exception e)
	{
	  // Unable to connect. This may be an incorrect applet parameter. Let's assume though it's a 
	  // proxy server that's stopping use using the hostname.
	  return true;
	}
	return false;
  }

  public void startSearch()
  {
	DataInputStream dis=null;
	String url=null;
	try
	{
	  url="http://localhost:8080/index.htm";
	  URL doc=new URL(url);
	  dis=new DataInputStream(doc.openStream());
	}
	catch(Exception e)
	{
	  System.out.println(e.toString());
	}
	if(dis!=null)
	  pageOpened=true;
    if(pageOpened==false)
	  System.out.println("Cannot connect to server");
    else 
	{
	  // Search the first page. Any links on the indexPage are saved and searched next. 
	  searchPage(dis,url);
	}
	// State2 : Search links found in the index page. 
	topLevelSearch=SEARCH_TOP_LEVEL_PAGES;
	for(int i=0;i<topLevelPages.size();i++)
	{
	  checkLink((String)topLevelPages.elementAt(i));
	}
	// State3: Spider all other pages.
	topLevelSearch=SEARCH_OTHER_PAGES;
	for(int i=0;i<nextLevelPages.size();i++)
	{
	  checkLink((String)nextLevelPages.elementAt(i));
	}
  }

  // Purpose- read all lines on a page - extracting local links and
  // Checking for the presence of the search string.
  public void searchPage(DataInputStream dis,String url)
  {
    try
	{
	  String input;   // Raw line read in.
	  String upperCaseInput;   // Upper version of the above.
	  String link;    // HTML link found.
	  String temp;
	  String title="";   // page title.

	  // Read a line.
	  while((input=dis.readLine())!=null)
	  {
		System.out.println(url);
		// Convert to UpperCase (make comparisions easier).
		upperCaseInput=input.toUpperCase();
		// Check for document title.
		temp=parseForTitle(input,upperCaseInput,dis);
		// If a title has been found then remember it. So, that it can be displayed on the results.
		if(temp!=null && temp.length()>0)
		  title=temp;
		// Check for match after title has been found.
		if(title.length()>0 && temp==null)
		  checkMatch(input,url,title);
		// Check to see if this line contains a link. 
		link=parseForLink(upperCaseInput,input);
		if(link!=null)
		{
		  if(topLevelSearch==FIND_TOP_LEVEL_PAGES)
		     topLevelPages.addElement(link);
		  else if(topLevelSearch==SEARCH_TOP_LEVEL_PAGES)
		     nextLevelPages.addElement(link);
		  else checkLink(link);
		}
	  }
	}
	catch(IOException e)
	{
	  System.out.println(e.toString());
	}
  }

  // Purpose- scan a line of text looking for the title of the pages.  Eg:- <TITLE>Allsoft Page </TITLE>
  // Titles may be multi-line so this routine reads from the document until the </TITLE> tah has been 
  // read or 25 characters read (max meaning full length of title)(same as AltaVista!)
  public String parseForTitle(String rawInput,String input,DataInputStream dis)
  {
    int i,j,k,l;
	int titleLength=0;    // keep track of title length as only first 25 characters are displayed.
	int start=0;
	String temp;
	StringBuffer title=new StringBuffer("");
	boolean foundTag=false;

	try
	{
	  // Search for <TITLE> tag. Can the TITLE tag have spaces? e.g < TITLE  > (assume not!)
	  i=input.indexOf("<TITLE");
	  if(i!=-1)
	  {
	    // Allow for <HTML><HEAD><TITLE>Title</TITLE></HEAD>
		j=input.indexOf(">",i);
		if(j!=-1)
		{
		  while(titleLength<=25 && foundTag==false)
		  {
		    start=j+1;
			for(k=start;k<rawInput.length();k++)
			{
			  if(foundTag==false && rawInput.charAt(k)!='<')
			  {
			    titleLength++;
				title.append(rawInput.charAt(k));
			  }
			  else foundTag=true;
			}
			// Continue reading from doc if </title> not found.
			if(foundTag==false)
			{
			  rawInput=dis.readLine();
			  j=-1;
			}
		  }
		  // Remove leading and trailing spaces.
		  temp=title.toString();
		  return(temp.trim());
		}
	  }
	}
	catch(IOException e){}
	return (null);    // No title found.
  }

  // Purpose- scan a line of text looking for links to other pages. 
  // The following types of links are currently supported.
  // 1. Normal links, e.g, <A href="page.html">Allsoft</A>
  // 2. Frames, e.g, <FRAME scrolling=yes src="contus.html">
  final String parseForLink(String upperCaseInput,String input)
  {
    int i,j,k,l;
	String temp=null;
	String link=null;

	// Look for links to other pages. Eg:- Normal links <A>..</A>
    i=upperCaseInput.indexOf("HREF");
	if(i!=-1)
	{
	  // Locate position of the quote marks.
	  j=upperCaseInput.indexOf("\"",i);
	  k=upperCaseInput.indexOf("\"",j+1);
	  // Locate position of </a>
	  l=upperCaseInput.indexOf("</A>",i);
	  // If double quotes were not found then try using single quote marks.
	  if(j==-1 || k==-1 || (j>1 && k==-1))
	  {
	    j=upperCaseInput.indexOf("\'",i);
		k=upperCaseInput.indexOf("\'",j+1);
	  }
	  // Remove leading trailing spaces.
      if(j!=-1 && k!=-1)
	  {
	    temp=input.substring(j+1,k);    // Extrct the link name.
		link=temp.trim();    // Remove leading and trailing spaces.
		return(link);
	  }
	}
	// 2. Frames,
	i=upperCaseInput.indexOf("FRAME");
	if(i!=-1)
	{
	  l=upperCaseInput.indexOf("SRC",i);   // Locate position of source tag.
	  if(l!=-1)
	  {
	    // Locate position of quote marks.
		j=upperCaseInput.indexOf("\"",l);
		k=upperCaseInput.indexOf("\"",j+1);
		// If double quotes were not found then try single quote marks
		if(j==-1)
		{
		  j=upperCaseInput.indexOf("\"",i);
		  k=upperCaseInput.indexOf("\"",j+1);
		}
		// Remove leading and trailing spaces.
		if(j!=-1 && k!=-1)
		{
		  temp=input.substring(j+1,k);   // Extract the link name
		  link=temp.trim();
		  return(link);
		}
	  }
	}
	return(null);
  }

  // Purpose - scan a line of text to see if the search string is present.
  // If so then add the line of list of matches.
  public void checkMatch(String input,String url,String title)
  {
    System.out.println(url);
	String searchLine=removeHTMLTags(input);   // Remove HTML tags before search.
	// If the line contains non-HTML text then search it.
//	System.out.println(url);
	if(searchLine.length()>0)
	{
	  String lower1=searchLine.toLowerCase();
      String lower2=searchKey.toLowerCase();
	  if(lower1.indexOf(lower2)!=-1)
	  {
	    // Found it display the match.

	    addToList(url,searchLine,title);
		hitsFound++;
	  }	
	}
  }

  // Purpose - remove HTML tags from a line (Eg, <BR>). The algorithm is a bit simplistic in that it 
  // cannot handle HTML tags split over one line.
  final String removeHTMLTags(String inputLine)
  {
    StringBuffer outputLine=new StringBuffer("");
	boolean foundTag=false;
	for(int i=0;i<inputLine.length();i++)
	{
	  if(inputLine.charAt(i)=='<')
	     foundTag=true;
      else if(inputLine.charAt(i)=='>')
	     foundTag=false;
	  else if(foundTag==false)
	      outputLine.append(inputLine.charAt(i));
	}
	return (outputLine.toString());
  }

  // Purpose- checks validy of a link. If the link is valid 
  // it's added to the list of visited links and then followed.
  public void checkLink(String link)
  {
    URL doc;  // URL link.
	DataInputStream dis=null;
	int i;
	boolean qualifiedLink=false;
	if(link.startsWith("#"))  // Skip the link if it's just an offset in this document.
	  return;
    if((i=link.indexOf("#"))!=-1)
	{
	  String substr=link.substring(0,i);
	  link=substr;
	}
	if(checkAlreadyFound(link))
	  return;
    // Ignore not-HTML links and start page.
	if( (link.startsWith("mailto:")) || (link.startsWith("wais:")) ||
	    (link.startsWith("gopher:")) || (link.startsWith("newsrc:")) ||
		(link.startsWith("ftp:")) || (link.startsWith("nntp:")) ||
		(link.startsWith("telnet:")) || (link.startsWith("news:")) ||
		link.equalsIgnoreCase("index.asp") ||
		(link.equalsIgnoreCase("index.htm")))
       return;
    // Check that it is not out side link. (Eg, www.RGP-Javaline.com)
    if(link.indexOf("http:")!=-1)
	{
	  String pageName="";
	  if(proxyDetected)
	     pageName="http://localhost";
      // This is a fully qualified link. Eg, " http://www.allsoft-india.com/index.htm"
	  qualifiedLink=true;
	  // If the link doesn't contain the local host name or IPAddress then
	  // it's an external link. So, ignore it.
	  if(link.indexOf(pageName)==-1)
	     return;
	}
	// Check that it's a HTML page.
	if( link.indexOf(".asp")==-1 && link.indexOf(".ASP")==-1 &&
	    link.indexOf(".txt")==-1 && link.indexOf(".TXT")==-1 &&
		link.indexOf(".phtml")==-1 && link.indexOf(".PHTML")==-1 )
      return ;
    incrementPages(link);   // valid link - add it to the array of visited links. 
	// Follow link and read it's contents.
    try
	{
	  doc=new URL("http://localhost/"+link);
	  dis=new DataInputStream(doc.openStream());
	  searchPage(dis,link);
	}
	catch(IOException e)  
    {
	  System.out.println(e.toString());
	}
  }

  // Purpose- add a page visited by the search thread to the list of visited pages.
  // This prevents the same link from being followed if it on multiple pages.
  public void incrementPages(String page)
  {
    // Check if page already indexed.
	if(checkAlreadyFound(page))
	  return;
    pageNames.addElement(page);
  }
  
    // Purpose - checks to see if a page has already been visited by the search thread.
  boolean checkAlreadyFound(String page)
  {
    if(pageNames.size()==0)
	   return false;
    
	// Check this is a new one.
	for(int i=1;i<pageNames.size();i++)
	{
	  String pageName=(String)pageNames.elementAt(i);
	  if(pageName.equalsIgnoreCase(page))
	    return(true);
	}
	return(false);
  }

  // Purpose - add a page to the list of matches in the results Area. The Page Title and
  // matching text displayed. The page name is also stored so that the URL can be jumped to.
  public void addToList(String page,String line,String title)
  {
    String translatedTitle=title;
	String translatedLine=line;
	if(title.indexOf("&")!=-1 && title.indexOf(";")==-1)
	{
	  // Check for HTML special characters .   Eg:- &quot; &ccedil; etc.
	  translatedTitle=translateSpecialChars(title);
	}
	if(line.indexOf("&")!=-1 && line.indexOf(";")==-1)
	{
	  translatedLine=translateSpecialChars(line);
	}
    String found="Title:\" "+translatedTitle+" \" Text: "+translatedLine;
	searchedIs.addElement(found);
	pageMatch.addElement(page);
  }

  // Purpose - convert special characters in the page title to Unicode
  // Characters so they are displayed properly.
  final protected String translateSpecialChars(String title)
  {
    int start;
	int i;
	// HTML representation of selected extended chars
	String rawString[]={
						 "&aacute;","&acirc;","&aelig;",
						 "&agrave;","&auml;","&ccedil;",
						 "&eacute;","&ecirc;","&egrave;",
				         "&euml;","&icirc;","&iuml;",
				         "&ocirc;","&ouml;","&szlig;",
				         "&uuml;","&yuml;","&copy;",
				         "&pound;","&reg;","&lt;",
				         "&gt;","&amp;","&quot;",
 					     "&atilde;","&aring;","&igrave;",
					     "&iacute;","&eth;","&ntilde;",
					     "&ograve;","&oacute;","&otilde;",
					     "&divide;","&oslash;","&ugrave;",
					     "&uacute;","&ucirc;","&yacute;",
					     "&thorn;","&times;","&nbsp;",
					     "&sect;","&cent;","&deg;"
					   };
    // Unicode representation of above
	char translatedChar[]={
							'\u00e1','\u00e2','\u00e6',
							'\u00e0','\u00e4','\u00e7',
							'\u00e9','\u00ea','\u00e8',
							'\u00eb','\u00ee','\u00ef',
							'\u00f4','\u00f6','\u00df',
							'\u00fc','\u00ff','\u00a9',
							'\u00a3','\u00ae','\u003c',
							'\u003e','\u0026','\u0022',
							'\u00e3','\u00e5','\u00ec',
							'\u00ed','\u00f0','\u00f1',
							'\u00f2','\u00f3','\u00f5',
							'\u00f7','\u00f8','\u00f9',
							'\u00fa','\u00fb','\u00fd',
							'\u00fe','\u00d7','\u00a0',
							'\u00a7','\u00a2','\u00b0'
						 };
    StringBuffer translated=new StringBuffer("");
	String titleString=title;

	//Check the title for each of the above HTML special chars
	for(int loop=0;loop<NUMBER_SPECIAL_CHARS;loop++)
	{
	  if(translated.length()>0)
	  {
	    titleString=translated.toString();
		translated=new StringBuffer("");
	  }
	  start=titleString.indexOf(rawString[loop]);
	  if(start!=-1)
	  {
	    //HTML special character found so replace it 
		// with the unicode equalent for display.
		for(i=0;i<start;i++)
		  translated.insert(i,titleString.charAt(i));
        translated.append(translatedChar[loop]);
		for(i=start+rawString[loop].length();i<titleString.length();i++)
		  translated.append(titleString.charAt(i));
	  }
	}
	return (translated.length()==0) ? titleString : translated.toString();
  }

}
