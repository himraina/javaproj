import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.util.*;

/* <APPLET CODE="LocalSearch.class" WIDTH=650 HEIGHT=400>
 * <PARAM NAME="server" VALUE="http://localhost:8080/">
 * <PARAM NAME="indexName" VALUE="index.htm">
 * <EM>Sorry but the Search applet requires a Java aware
 * Web browser </EM>
 * </APPLET>
 */

public class LocalSearch1 extends Applet
{
  final int MAX_NUMBER_PAGES=100;       // default Limit of  Number of Pages to Read.
  
  final int BACKSPACE_CHARACTER=8;      // ASCII backspace.
  
  final int NUMBER_SPECIAL_CHARS=45;    // Number of Special Character entity names supported.

  Button search,clear,abort;            // GUI Buttons.

  TextField inputArea;                  // TextField used to enter search text in

  TextField statusArea;                 // TextField used to disply search status.

  java.awt.List resultsArea;            // List to display matches in.

  public String hostName;               // Host Name parameter read by the Applet(required).
 
  public String IPAddress;              // IP Address parameter read by the Applet (Optional).

  public int maxSearch = MAX_NUMBER_PAGES;     // Maximum number of pages to search(Optional).

  public boolean debugMode;             // TRUE-localhost     False-OnLine
  
  Vector pageNames;                     // Pages that have been visited.

  public String server;                 // Non-Demon home page starting point.

  public String indexName;				// Name of index page.(defaults to index.html or index.htm)

  SearchPages cp=null;    // Search Thread.

  Checkbox caseSensitive;
  Checkbox caseInsensitive;
  Checkbox matchWholeWord;

  public boolean matchCase=false;   // Flag to indicate if we need to match case.
  public boolean matchWord=false;   // Flag to indicate if we need to match the whole word.

  String versionNumber="V2.0";

  boolean packComponents;     // Set to true if size<600.

  Color bgColour;
  Color fgColour;

  Choice numPagesChoice;     // Option menu to select max number of pages to search.
  Vector pageMatch;   // Pages that contain the search word.
  
  public void init()
  {
 //   SecurityManager security=System.getSecurityManager();
    Panel p;
	getParameters();      // Read the Applet Parameters.
	setLayout(new BorderLayout());

	// If Applet size<600 pixels then reduce the length of TextFields , labels etc.
	// So that the display will OK. 
    if(size().width<=600)
	  packComponents=true;
    else packComponents=false;

	// This Panel consists of input Text Field Where the user enters the text to search for.
	// The buttons allow the search to be started, aborted and clear the applets output fields.
	
	p=new Panel();
	p.setLayout(new FlowLayout());
	Label lab=new Label("Search for: ");
	lab.setFont(new Font("TimesNewRoman",Font.BOLD,14));
	lab.setForeground(Color.blue);
	p.add(lab);
	
	if(packComponents)
	   inputArea=new TextField("",15);
    else
	   inputArea=new TextField("",20);
	p.add(inputArea);
	
	if(packComponents)
	{
	  search=new Button("Search");
	  search.setFont(new Font("TimesNewRoman",Font.BOLD,12));
	  search.setForeground(Color.green);
	}
	else
	{
	  search=new Button("Search");
	  search.setFont(new Font("TimesNewRoman",Font.BOLD,14));
	  search.setForeground(Color.green);
	}
	p.add(search);

	if (packComponents)
	{
	  clear = new Button ("clear");
	  clear.setFont (new Font ("TimesNewRoman", Font.BOLD, 12));
	  clear.setForeground(Color.white);
	}                       
	else
	{
	  clear = new Button (" clear ");
	  clear.setFont (new Font ("Helvetica", Font.BOLD, 14));
	  clear.setForeground(Color.white);
	}                       
	p.add (clear);

	if (packComponents)
	{
	  abort = new Button ("stop");
	  abort.setFont (new Font ("Helvetica", Font.BOLD, 12));
	  abort.setForeground(Color.red);
	}
	else
	{
	  abort = new Button (" stop ");
	  abort.setFont (new Font ("Helvetica", Font.BOLD, 14));
	  abort.setForeground(Color.red);
	}
	abort.disable();
	p.add (abort);
   
	if (packComponents)
		lab = new Label ("Pages");
	else
		lab = new Label (" Max. Pages:");
	lab.setFont (new Font ("Helvetica", Font.PLAIN, 12));
	p.add (lab);
	numPagesChoice = new Choice();
	//p.add (numPagesChoice);
	p.setForeground (fgColour);
	p.setBackground (bgColour);
	add ("North",p);

	// This Panel Lists the results. When an item from the list box is double clicked the URL is opened.
    p=new Panel();
    p.setLayout(new GridLayout(0,1));
	resultsArea=new java.awt.List(10,false);
    p.add(resultsArea);
	p.setForeground(fgColour);
	p.setBackground(bgColour);
	add("Center",p);

	p=new Panel();
	Label labVersion=new Label(versionNumber);
	labVersion.setFont(new Font("TimesNewRoman",Font.BOLD,12));
	p.add(labVersion);

	CheckboxGroup caseSense=new CheckboxGroup();
	
	// This TextField shows the status of the search to provide some feed back to the user. 
	// The Page Count is displayed. 

	if(packComponents)
	   statusArea=new TextField("",14);
    else 
	   statusArea=new TextField("",20);
	
	statusArea.setEditable(false);
	p.add(statusArea);

	if (packComponents)
		caseInsensitive = new Checkbox ("in-sensitive");
	else
		caseInsensitive = new Checkbox ("case in-sensitive");

	p.add (caseInsensitive);
	caseInsensitive.setCheckboxGroup (caseSense);

	if (packComponents)
		caseSensitive = new Checkbox ("sensitive" );
	else
		caseSensitive = new Checkbox ("case sensitive" );

	p.add (caseSensitive);
	caseSensitive.setCheckboxGroup (caseSense);
	caseSense.setCurrent (caseInsensitive);

	if (packComponents)
		matchWholeWord = new Checkbox ("whole word");
	else
		matchWholeWord = new Checkbox ("match whole word");

	p.add (matchWholeWord);
	p.setForeground (fgColour);
	p.setBackground (bgColour);
	add ("South",p);

	disableButtons();  // Disable Button until text entered.

	// Create Vector to hold pages that have been found and pages that contain the search text.
	pageNames=new Vector();
	pageMatch=new Vector();

	// Now that we know what the maxSearch parameter is fill in sensible page Numbers.
    for(int i=maxSearch/5;i<=maxSearch;i+=maxSearch/5)
	{
	  numPagesChoice.addItem(Integer.toString(i));
	}
	numPagesChoice.setFont(new Font("Helvetica",Font.BOLD,12));
	//Set the default Number of pages to be searched.
	numPagesChoice.select(0);
	maxSearch=maxSearch/5;
	inputArea.setBackground(Color.white);
  }


  // Function enable Buttons.

  public void enableButtons()
  {
    search.enable();
	clear.enable();
  }

  // Function disable Buttons.

  final void disableButtons()
  {
    search.disable();
	clear.disable();
  }

  // Funtion get Parameters. Purpose- read applet parameters.

  final void getParameters()
  {
    hostName=getParameter("hostname");
	IPAddress=getParameter("IPAddress");
	String num=getParameter("maxSearch");
	String arg=getParameter("debug");
	server=getParameter("server");
	indexName=getParameter("indexName");
	String colour=getParameter("bgColour");

	if(colour==null)
	{
	  bgColour=Color.lightGray;
    }
    else
	{
	  try
	  {
	    bgColour=new Color(Integer.parseInt(colour,16));
	  }
	  catch(NumberFormatException nfe)
	  {
	    bgColour=Color.lightGray;
	  }
	}
	
	colour=getParameter("fgColour");
	if(colour==null)
	{
	  fgColour=Color.black;
	}
	else
	{
	  try
	  {
	    fgColour=new Color(Integer.parseInt(colour,16));
	  }
	  catch(NumberFormatException nfe)
	  {
	    fgColour=Color.black;
	  }
	}

	//Check for missing parameters.
	if(hostName==null && server==null)
	{
	  statusArea.setText("Error-no host/server");
	  hostName="none";
	}

	maxSearch=(num == null) ? MAX_NUMBER_PAGES : Integer.parseInt(num);
  }

  // Display Parameter Information.
  public String[][] getParameterInfo()
  {
    String[][] info = {
	                    {"hostname","String","host name of site"},
						{"IPAddress","String","IPAddress of site"},
						{"maxSearch","String","Maximum number of pages to search"},
						{"debug","String","debug Mode"},
						{"server","String","Home Page URL"},
						{"indexName","String","Name of index Page"},
						{"bgColour","Color","Background color of applet"},
						{"fgColour","Color","Foreground color of applet"}
					   };
    return info;					   
  }

  // Display applet information.
  public String getAppletInfo()
  {
    return "Home Page Search Applet V2.0";
  }

  // Function keyDown
  // Purpose- enable or disable Buttons. When search text is entered the search and clear Buttons are
  // enabled. When no search text has been entered the buttons are disabled.
  public boolean keyDown(Event e,int nKey)
  {
    boolean boolDone=true; 
	String text;

	text=inputArea.getText();    // Read the search text.
	int n=text.length();

	if(nKey == BACKSPACE_CHARACTER)  // catch backspace character.
	{
	  boolDone=false;
	  n--;
	}
	else
	{
	  boolDone=false;
	  n++;
	}
	if(n>2)
	{
   	  enableButtons();
    }
	else
	{
	  disableButtons();
	}
	return boolDone;
  }

  // Purpose - This function handles all the GUI events.

  public boolean action(Event e,Object o)
  {
    String text;         // Search text entered by the user.
	String searchText;   // Lower case version of above.
	URL newURL=null;

	// Chech to see if the option menu has been selected.

	if(e.target instanceof Choice)
	{
	  Choice c=(Choice)e.target;
	  try
	  {
	    maxSearch=Integer.parseInt(c.getSelectedItem(),10);
	  }
	  catch(NumberFormatException ne)
	  {
	    maxSearch=MAX_NUMBER_PAGES;
	  }
	}

	// Check to see if a check box has been pressed.
	if(e.target instanceof Checkbox)
	{
	  if(caseSensitive.getState() == true)
	     matchCase=true;
      else matchCase=false;
	  if(matchWholeWord.getState() ==true)
	     matchWord=true;
      else matchWord=false;
	}

    // A Button has been pressed - which determine.
	if(e.target instanceof Button)
	{
	  if(e.target == search)
	  {
	    // Search button pressed - read in search text entered 
		text=inputArea.getText();
		// Make sure there's something to search for
		if(text.length()==0)
		  return(false);
		// New Search so clear the GUI out
		if(resultsArea.countItems()>0)
		     resultsArea.clear();
        disableButtons();
		abort.enable();
		statusArea.setText("");
		// Clear out previous search data
		pageNames.removeAllElements();
		pageMatch.removeAllElements();
		// We're off start the search thread
		cp=new SearchPages(this,hostName,text,maxSearch);
		cp.start();
	  }
	  else if(e.target == abort)
	  {
	    // Abort button Pressed - stop the thread.
		if(cp != null)
		  cp.stop();
		cp=null;
		// Enable Button for another search.
		enableButtons();
		abort.disable();
	  }
	  else
	  {
	    // Clear Button Pressed - clear all the fields and return.
		inputArea.setText("");
		statusArea.setText("");

		// Clear radio Buttons.
		caseSensitive.setState(false);
		caseInsensitive.setState(true);
		matchWholeWord.setState(false);

		// Clear option menu.
		numPagesChoice.select(0);
		try
		{
		  maxSearch=Integer.parseInt(numPagesChoice.getSelectedItem(),10);
		}
		catch(NumberFormatException ex)
		{
		  maxSearch=MAX_NUMBER_PAGES;
		}
		
		if(resultsArea.countItems()>0)
		    resultsArea.clear();
        cp=null;
	  }
	}

	// Selection made from the list of matches.
	if(e.target instanceof java.awt.List)
	{
	  java.awt.List list=(java.awt.List)e.target;
	  int index=list.getSelectedIndex();

	  // Extract the page name from the list.
	  if(index<pageMatch.size())
	  {
	    String URLSelected=(String)pageMatch.elementAt(index);
		try
		{
		  // If URL store then use it.
		  if(URLSelected.startsWith("http:") || URLSelected.startsWith("file:"))
		    newURL=new URL(URLSelected);
          else if(server==null)
		    newURL=new URL("http://www."+hostName+"/"+URLSelected);
          else newURL=new URL(server+URLSelected);
		}
		catch(MalformedURLException except){}
		getAppletContext().showDocument(newURL,"_self");
	  }
	}
	return true;   // We are done.
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

  // Purpose- adds a page visited by the search thread to the list of visited pages.
  // This prevents the same link from being followed if it on multiple pages.
  public void incrementPages(String page)
  {
    // Check if page already Indexed.
	if(checkAlreadyFound(page))
	   return;
    pageNames.addElement(page);
	
	// provide feedback to the user.
	statusArea.setText("Search Page: "+pageNames.size());
  }

  // Purpose - returns number of pages that the search thread has visited.
  public int getTotalPages()
  {
    return pageNames.size()-1;
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
  
  // Purpose - add a page to the list of matches in the results ListBox. The Page Title and
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
	resultsArea.addItem("Title:\" "+translatedTitle+"\" Text: "+translatedLine);
	pageMatch.addElement(page);
  }
}


//******************************************************************************************************
//										Class SearchPages
//******************************************************************************************************

// This Thread performs the search. The search starts with the index.html or index.htm page and then
// follows all local links. E.g:-<A href="contact.html"> Contacts </a>. 
// Note External links are ignored.

class SearchPages extends Thread
{
  // Search state transitions. First find top level pages (from the index page)
  // Search the above pages first then search all other pages.
  final byte FIND_TOP_LEVEL_PAGES=0;
  final byte SEARCH_TOP_LEVEL_PAGES=1;
  final byte SEARCH_OTHER_PAGES=2;

  String hostName;      // Host name of site. E.g:-Allsoft
  LocalSearch app;      // parent applet
  String textToFind;    // String to find.
  int maxPages;         // Maximim number of pages to visit.
  int hitsFound=0;      // No of occurrences of search String.
  static final byte URLCOUNT=2;
  boolean pageOpened=false;  // Flag to indicate if index page opened OK.
  boolean proxyDetected=false; // Flag to indicate if a proxy server or fire wall has been detected.
  int topLevelSearch;      // Search the index page links first.
  Vector topLevelPages;    // Page names in the index page.
  Vector nextLevelPages;   // Lower level pages.

  SearchPages(LocalSearch applet, String hn, String text, int maxSearch)
  {
    app=applet;
	hostName=hn;
	textToFind=text;
	maxPages=maxSearch;
  }

  public void run()
  {
    
    // State 1: search the index page,remembering all the links on index page.
	topLevelSearch=FIND_TOP_LEVEL_PAGES;
	topLevelPages=new Vector();
	nextLevelPages=new Vector();
    
	// Check to see if a proxy is being used. If so then we use IP Address rather than host name.
	proxyDetected=detectProxyServer();
	 
	startSearch();
    
	app.enableButtons();
	app.abort.disable();
     
	if(hitsFound == 0 && pageOpened == true)
	   app.statusArea.setText("No Matches Found");
    else if(hitsFound==1)
	   app.statusArea.setText(hitsFound+" Match Found");
    else app.statusArea.setText(hitsFound+" Matches Found");
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
	if(app.server==null)
	{
	  if(app.indexName==null)
	    url="http://www."+hostName+"/index.html";
      else url="http://www."+hostName+"/";
	}
	else
	{
	  if(app.indexName==null)
	    url=app.server+"index.html";
	  else url=app.server+app.indexName;  	
	}
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

  final void startSearch()
  {
    DataInputStream dis=null;
	String[] url={"",""};
	String currentPageName="";   // HTML page currently being searched.
    
	//Allow for non=Demon home page
	if(app.server==null)
	{
	  if(app.indexName==null)
	  {
	    url[0]="http://www."+hostName+"index.html";
		url[1]="http://www."+hostName+"index.htm";
	  }
	  else
	  {
	    url[0]="http://www."+hostName+"/"+app.indexName;
		url[1]="";
	  }
	}
	else
	{
	  if(app.indexName==null)
	  {
	    url[0]=app.server+"index.html";
		url[1]=app.server+"index.htm";
	  }
	  else
	  {
	    url[0]=app.server+app.indexName;
		url[1]="";
	  }
	}
    
	// If a proxy server/firewall has been detected then use the IPAddress (if supplied) of the 
	// originating server rather than the host name.
	if(proxyDetected && app.IPAddress!=null)
	{
	  if(app.indexName==null)
	  {
	    url[0]="http://"+app.IPAddress+"/index.html";
		url[1]="http://"+app.IPAddress+"/index.htm";
	  }
	  else
	  {
	    url[0]="http://"+app.IPAddress+"/"+app.indexName;
		url[1]="";
	  }
	}

    for(int i=0;i<URLCOUNT;i++)
	{
      System.out.println(url[i]);	  
	  try
	  {
	    currentPageName=url[i];
		URL doc=new URL(url[i]);
		dis=new DataInputStream(doc.openStream());
		
	  }
	  catch(Exception e)
	  {
	    continue;
	  }
	  if(dis!=null)
	  {
	    pageOpened=true;
		i=URLCOUNT;
	  }
	}
	if(pageOpened==false)
	{
	  app.statusArea.setText("cannot connect to server");
	  return;
	}
	else
	{
	  // Search the first page. Any links on the index page are saved and searched next.
	  searchPage(dis,currentPageName);
	}
    // State2: search links found in the index page.
	topLevelSearch=SEARCH_TOP_LEVEL_PAGES;
	for(int i=0;i<topLevelPages.size();i++)
	{
	  checkLink((String)topLevelPages.elementAt(i));
	  //Check the maximum number of pages to be searched has not been reached.
	  if(app.getTotalPages()>=maxPages)
	    return;
	}
	// State3: spider all other pages.
	topLevelSearch=SEARCH_OTHER_PAGES;
	for(int i=0;i<nextLevelPages.size();i++)
	{
	  checkLink((String)nextLevelPages.elementAt(i));
	  // Check the maximum number of pages to searched has not been reached.
	  if(app.getTotalPages()>=maxPages)
	    return;
	}
  }
  
  // Purpose- read all lines on a page - extracting local links and
  // Checking for the presence of the search string.
  final void searchPage(DataInputStream dis,String url)
  {
    try
	{
      String input;           // Raw line read in.
	  String upperCaseInput;  // Upper case version of the above
	  String link;            // HTML link found
	  String temp;            
	  String title="";        // Page Title;

	  // Read a line at a time.
	  while((input=dis.readLine())!=null)
	  {
	    // Convert to Upper case (makes comparisions easier)
		upperCaseInput=input.toUpperCase();
		// Check for document title
		temp=parseForTitle(input,upperCaseInput,dis);
		// If a title has been found then remember it. So, that it can displayed on the list box.
		if(temp!=null && temp.length()>0)
		  title=temp;
	    //Check for match after title has been found (Don't bother Searching the title though)
		if(title.length()>0 && temp==null)
		   checkMatch(input,url,title);
        
		// Check to see if this line contains a link
		link=parseForLink(upperCaseInput,input);
		if(link!=null)
		{
		  // Check if the maximum number of pages to search has been reached.
		  if(app.getTotalPages()>=maxPages)
		     return;
          if(topLevelSearch==FIND_TOP_LEVEL_PAGES)
		     topLevelPages.addElement(link);
          else if(topLevelSearch==SEARCH_TOP_LEVEL_PAGES)
		     nextLevelPages.addElement(link);
		  else checkLink(link);	  
		}
	  }
    }
	catch(IOException e){ }
  }

  // Purpose- scan a line of text looking for the title of the pages.  Eg:- <TITLE>Allsoft Page </TITLE>
  // Titles may be multi-line so this routine reads from the document until the </TITLE> tah has been 
  // read or 25 characters read (max meaning full length of title)(same as AltaVista!)
  final String parseForTitle(String rawInput,String input, DataInputStream dis)
  {
    int i,j,k,l; 
	int titleLength=0;  // Keep track of title length as only first 25 characters are displayed.
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
  final void checkMatch(String input,String url,String title)
  {
    String searchLine=removeHTMLTags(input);   // remove html tags before search.
    // If the line contains non - HTML text then search it.
	if(searchLine.length()>0)
	{
	  if(app.matchCase) // Check if case sensitive search
	  {
	    if(app.matchWord)  // Check if attempting to match whole word
		{
		  if(searchLine.indexOf(" "+textToFind+" ")!=-1  ||
 		          (searchLine.indexOf(textToFind)!=-1 && searchLine.length()==textToFind.length()) ||
				  searchLine.indexOf(" "+textToFind)!=-1  && textToFind.charAt(textToFind.length()-1)==
															    searchLine.charAt(searchLine.length()-1))
		  {
		   // Found it display the match
		   app.addToList(url,searchLine,title);
		   hitsFound++;	
		  }
		}
		else if(searchLine.indexOf(textToFind)!=-1)
		{
		  // Found it display the match
		  app.addToList(url,searchLine,title);
		  hitsFound++;
		}
	  }
	  else
	  {
	    String lower1=searchLine.toLowerCase();
		String lower2=textToFind.toLowerCase();
		// Check if attempting to match the whole word.
		if(app.matchWord)
		{
		  if(lower1.indexOf(" "+lower2+" ")!=-1 || 
		      (lower1.indexOf(lower2)!=-1 && lower1.length()== lower2.length()) ||
			  (lower1.indexOf(" "+lower2)!=-1 && lower2.charAt(lower2.length()-1) == 
																    lower1.charAt(lower1.length()-1)))
          {
		    // Found it display the match
			app.addToList(url,searchLine,title);
			hitsFound++;
		  }
		}
		else if(lower1.indexOf(lower2)!=-1)
		{
		  // Found it! Display the match
		  app.addToList(url,searchLine,title);
		  hitsFound++;
		}
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

  // Purpose- checks validity of a link. If the link is valid 
  // it's added to the list of visited links and then followed
  final void checkLink(String link)
  {
    URL doc;     // URL link
	DataInputStream dis=null;
	int i;
	boolean qualifiedLink=false;
	if(link.startsWith("#"))      // Skip the link if it's just an offset in this document
	  return;
    if((i=link.indexOf("#"))!=-1)
	{
	  String substr=link.substring(0,i);
	  link=substr;
	}
	if(app.checkAlreadyFound(link))
	  return;
    // Ignore not - HTML links and start page
	if( (link.startsWith("mailto:")) || (link.startsWith("wais:")) ||
	    (link.startsWith("gopher:")) || (link.startsWith("newsrc:")) ||
		(link.startsWith("ftp:")) || (link.startsWith("nntp:")) ||
		(link.startsWith("telnet:")) || (link.startsWith("news:")) ||
		(link.equalsIgnoreCase(app.indexName)) || link.equalsIgnoreCase("index.html") ||
		(link.equalsIgnoreCase("index.htm")))
       return;
    // Check that it is not out side link. (Eg, www.RGP-Javaline.com)
	if(link.indexOf("http:")!=-1)
	{
	  String pageName;
	  if(app.server==null)
	     pageName="http://www."+hostName;
      else pageName=app.server;
	  // Allow for local host being displayed as an IP Address rather than host name.
      if(proxyDetected && app.IPAddress!=null)
	     pageName="http://"+app.IPAddress;
      // This is a fully qualified link. Eg, " http://www.allsoft-india.com/index.htm"
	  qualifiedLink=true;
	  // If the link doesn't contain the local host name or IPAddress then
	  // it's an external link. So, ignore it.
	  if(link.indexOf(pageName)==-1)
	     return;
	}
	// Check that it's a HTML Page.
	if( link.indexOf(".htm")==-1 && link.indexOf(".HTM")==-1 &&
	    link.indexOf(".txt")==-1 && link.indexOf(".TXT")==-1 &&
		link.indexOf(".phtml")==-1 && link.indexOf(".PHTML")==-1 )
      return ;
    app.incrementPages(link);   // valid link - add it to the array of visited links. 
	// Follow link and read it's contents.
	try
	{
	  if(app.server==null)
	    doc=new URL("http://www."+hostName+"/"+link);
      else
	  {
	    if(link.startsWith("/"))
		{
 		  // Remove the "/" from the link as the server name has a terminating "/" 
          String temp=link.substring(1,link.length());
		  link=temp;
		}
		doc=new URL(app.server+link);
	  }
  	  // Link may be absolute,  Eg, www.allsoft-india.com/contus.html
	  if(qualifiedLink)
	    doc=new URL(link);
      // If a proxy server/firewall has been detected then use the IPAddress (if supplied)
	  // of the originating server rather than the host name.
	  if(proxyDetected && app.IPAddress!=null)
	    doc=new URL("http://"+app.IPAddress+"/"+link);
      dis=new DataInputStream(doc.openStream());
	  searchPage(dis,link);
	}
    catch(IOException e){}
  }
}