import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class URLReader {
    public static void main(String[] args) throws Exception {

String u=null;


System.getProperties().put("proxySet", "true"); 
System.getProperties().put("proxyHost", "144.16.192.213"); 
System.getProperties().put("proxyPort", "8080");

		 Connection conn = null;
		 Statement stmt=null;
		 ResultSet r=null;
		
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                                                                                
    	conn = DriverManager.getConnection("jdbc:odbc:search"); 
		stmt = conn.createStatement();
		
		
	 String asd="c:\\java\\url.txt";
     FileOutputStream out1=new FileOutputStream(asd);
     OutputStreamWriter wr=new OutputStreamWriter(out1);
    System.out.println("select link from mainlink where query_id="+"'"+"hotmail india"+"'");
    
	r=stmt.executeQuery("select link from mainlink where query_id="+"'"+"hotmail india"+"'");
	r.next();
	/*while(r.next())
		{
	u=r.getString("link");
	System.out.println("u="+u);
		}*/
	//while(r.next())
	//	{
	u=r.getString("link");
	System.out.println("u="+u);
	URL yahoo = new URL(u);
	BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));

	String inputLine;

	while ((inputLine = in.readLine()) != null)
        {
          wr.write(inputLine);
          
	 //   System.out.println(inputLine);
	  
      }
    in.close();
    wr.flush();
    wr.close();
	conn.commit();
	stmt.close();
	conn.close();
}
}

