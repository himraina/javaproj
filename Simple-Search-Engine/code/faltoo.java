import java.net.*;
import java.util.*;
import java.io.*;
public class faltoo {
static String querystring;


public static void main(String args[]) throws Exception {
querystring="Neeraj SIngh's Gautam";

System.getProperties().put("proxySet", "true"); 
System.getProperties().put("proxyHost", "144.16.192.216"); 

System.getProperties().put("proxyPort", "7777");
/*for(int i=0;i<args.length;i++)
{
querystring +=args[i] =" ";
}*/

querystring.trim();
querystring =URLEncoder.encode(querystring);
System.out.println("in serarchyahoo "+querystring);
querystring =URLDecoder.decode(querystring);
System.out.println("in serarcdcdchyahoo "+querystring);


//querystring ="query=" +URLEncoder.encode(querystring);
/*try {
String thisline;
//URL u=new URL("http://www.google.com/search?q="+querystring);

//URL u=new URL("http://search.msn.com/results.asp?q=" +querystring);

URL u=new URL("http://www.altavista.com/sites/search/web?q=" +querystring);
//URL u=new URL("http://www.altavista.com/cgi-bin/query?pg=q&what=web&q=" +querystring);
 // URL u=new URL("http://www.lycos.com/srch/?lpv=1&loc=searchhp&query=" +querystring);
//URL u=new URL("http://www.northernlight.com/nlquery.fcg?qr=" +querystring);
//URL u=new URL("http://search.yahoo.com/bin/search?p=" +querystring);
//URL u=new URL("http://google.yahoo.com/bin/query?p=" +querystring);
DataInputStream theHTML =new DataInputStream(u.openStream());

OutputStreamWriter osw1 =new OutputStreamWriter(new FileOutputStream("c:\\java\\alta.txt")) ;
while ((thisline = theHTML.readLine()) !=null) {
//System.out.println(thisline);
osw1.write(thisline);
}
osw1.flush();
osw1.close() ;

}

catch (MalformedURLException e) {
//System.err.println(e);
System.out.println("i m here1");
}
catch (IOException e){
//System.err.println(e+"this is error");
System.out.println("i m here2");
}
*/
}} 

