import java.net.*;
import java.io.*;
public class yahoo {
public static void main(String args[])
{
String querystring="Neeraj";

System.getProperties().put("proxySet", "true"); 
System.getProperties().put("proxyHost", "144.16.192.216"); 

System.getProperties().put("proxyPort", "7777");
System.out.println("args.length = "+args.length);
/*for(int i=0;i<args.length;i++)
{
querystring +=args[i] =" ";
}*/

querystring.trim();
querystring =URLEncoder.encode(querystring);
System.out.println(querystring);
//querystring ="query=" +URLEncoder.encode(querystring);
try {
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

while ((thisline = theHTML.readLine()) !=null) {
System.out.println(thisline);
}

}

catch (MalformedURLException e) {
System.err.println(e);
}
catch (IOException e){
System.err.println(e);
}

}} 
