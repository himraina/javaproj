import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.Date;

public class search extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {

      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();
      String query = request.getParameter("srchval");
      out.println("<html>");
      out.println("<title>Search Engine Result for : ");
      out.println(query+"  ");
      out.println("</title>");
      out.println("<body bgcolor=black text=\"#C0C000\" LINK=\"#CCCCCC\" VLINK=white>");
      out.println("<FONT face=ARIAL>");
      out.println("<IMG SRC=\"c:\\java\\lol.gif\" width=270 height=90 >");
	  out.println("<table  width=480 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=\"center\"><table  width=477 border=0 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td  align=center>");
	  out.println("<form name=neeraj method=get action=http://127.0.0.1:8080/servlet/search><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\"><b>SEARCH FOR:</b></FONT><IMG SRC=\"c:\\java\\space.gif\" width=15 height=1 ><input type=text name=srchval value=\""+query+"\" ");
	 // out.println(query);
	  out.println(" size=40 maxlength=2048>&nbsp;&nbsp;<input type=\"image\" src=\"c:\\java\\search.gif\" width=48 height=20 align=\"absmiddle\">");
      out.println("</td></tr></table></td></tr></table>");
	  Date date =new Date();
	  double msec1=date.getTime();
	  
	  
	     Connection conn3 = null;
		 Statement stmt3=null;
		 Statement stmt4=null;
		 Statement stmt5=null;
		 ResultSet r3=null;
		 ResultSet r4=null;
	  
	  
	  // Connection conn = null;
	  // Statement stmt=null;
      try
      {
		  //out.println("<h3>hi</h3>");
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                                                                                
         conn3 = DriverManager.getConnection("jdbc:odbc:search");                           
         
		stmt3 = conn3.createStatement();
		stmt4 = conn3.createStatement();
		stmt5 = conn3.createStatement();
	//	System.out.println("select link from mainlink where query_id="+"'"+query+"'");
    
	String q=URLEncoder.encode(query);
	System.out.println(q);
	System.out.println("select * from mainlink,linkvalue  where linkvalue.link=mainlink.link and mainlink.query_id="+"'"+q+"'"+"order by rank asc");
	r3=stmt3.executeQuery("select * from mainlink,linkvalue  where linkvalue.link=mainlink.link and mainlink.query_id="+"'"+q+"'"+"order by rank desc");
	
	//r4=stmt2.executeQuery("select * from querry_string where query_id="+"'"+URLEncoder.encode(query)+"'");
	//r=stmt.executeQuery("select link from mainlink where query_id="+"'"+"iit%27s"+"'");
	  
	  out.println("<br>&nbsp;<table  width=480 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=center><table  width=769 border=0 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td width=769><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\">Searched the web for: <b>");
	  out.println(query);
	  double msec2=date.getTime();
	  double time=msec2-msec1;
	  out.println("</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search took <b>"+time+"</b>  seconds</td></tr></table></td></tr></table>");
	  out.println("");
r3.next();

out.println("<div>");	
String man;
if(!r3.next()) 
		  {// out.println("<h2>No Matches found!<h2>");
 out.println("<p><br><br>");
out.println("<font size=3 color=white>");
out.println("<b>Sorry!</b>&nbsp;&nbsp;&nbsp;  No Results Found for &nbsp;&nbsp;&nbsp;<font color=red <b><i>"+query+"</i></b></font>");
out.println("<br><b>Try</b>&nbsp; your query with more specified query term...");
out.println("<br><br><br><br></font>");
		  }
		  else
		  {
while(r3.next())
		  {
	out.println("<p><br>");
String tt=r3.getString("title");
tt=URLDecoder.decode(tt);
String ll=r3.getString("link");
ll=URLDecoder.decode(ll);
//out.println("<h3>hi3</hi>");
out.println("<a href=\""+ll+"\">");
out.println(tt);
out.println("</a><br>");
//out.println(tt);
//out.println("\">");
out.println("<font size=-1>");
//out.println("<a href=\""+ll+"\">"+tt+"</a>");
String cont=r3.getString("content");
cont=URLDecoder.decode(cont);
int l2=0;
for(int k1=0;k1<3;k1++)
			  {
	int i9=cont.indexOf("india",l2);
	if(i9!=-1)
				  {
	//i=l1+temp11.length();
	l2=i9+40;
	//t=l1;
	//System.out.println("i9="+i9);
	String stt=cont.substring(i9,i9+70);
out.print(".....");
out.print(stt);
out.print("<br>");
			  }}

out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;");

 out.println("<font color=\"#008000\"><i>");
 out.println(ll);
 out.println("</i><br></font></font>");

		 out.println("</p>");
		// r3.next();
		  }
out.println("<br><br></form>");		  }
//out.println("<center><table  width=480 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=\"center\"><table  width=477 border=0 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td  align=center>");
//out.println("<form method=get name=singh action=http://127.0.0.1:8080/servlet/search1><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\"><b>SEARCH FOR:</b></FONT><IMG SRC=\"c:\\java\\space.gif\" width=15 height=1 ><input type=text name=srchval1 value=\""+query+"\" ");
//out.println(" size=40>&nbsp;&nbsp;<input type=\"image\" src=\"c:\\java\\search.gif\" width=48 height=20 align=\"absmiddle\">");
//out.println("</td></tr></table></td></tr></table>");

out.println("<br>&nbsp;<table  width=480 height=20 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=center><table  width=769 border=0 height=18 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td width=769><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\"></td></tr></table></td></tr></table></form>");
	 



		//stmt = conn.createStatement();
		/*stopremover st=new stopremover(title);
		yahoo1 mt=new yahoo1();
		out.println("<h3>hi</hi>");
		msn m1=new msn();
		out.println("<h3>hi</hi>");
		alta m2=new alta();
		findmsn msn=new findmsn();
		findyahoo yah=new findyahoo();
		findalta vista=new findalta();
		linkfinder lf =new linkfinder();
		urlsource us=new urlsource();
		try{
		mt.searchyahoo(title);
		out.println("<h3>hi</hi>");
		//msn m1=new msn();
		m1.searchmsn(title);
		out.println("<h3>hi</hi>");
		//alta m2=new alta();
		m2.searchalta(title);
		msn.findmsn1();
		yah.findyahoo1();
		vista.findalta1();
		String arr[]=st.retkey();
		out.println("<h3>hi7</hi>");
		 String sq="insert into querry_string values('"+URLEncoder.encode(arr[0])+"','"+URLEncoder.encode(arr[1])+"','"+URLEncoder.encode(arr[2])+"','"+URLEncoder.encode(arr[3])+"','"+URLEncoder.encode(arr[4])+"','"+URLEncoder.encode(arr[5])+"','"+URLEncoder.encode(arr[6])+"','"+URLEncoder.encode(arr[7])+"','"+URLEncoder.encode(arr[8])+"','"+URLEncoder.encode(arr[9])+"','"+URLEncoder.encode(title)+"');";
          stmt.executeUpdate(sq);
		 out.println("<h3>"+sq+"</h3>");
		 out.println("<h3>hi8</hi>");
		 
		 lf.linkfinder1(title);
			out.println("<h3>hi9</hi>");
			us.urlsource1(title);
         //String sq="insert into query_string values('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"','"+arr[9]+"','"+title+"');";
         //System.out.println("Sucess");
		 		 
         }
		catch(Exception e2)
		  {
			System.out.println(e2);
 out.println("<h3>hiiiiiiiiiiiiiiiiii</hi>");
		  }*/
	     
       }catch(SQLException e)                                                      
         {                                                                         
          out.println("SQLException: " + e.getMessage() + "<BR>");               
          while((e = e.getNextException()) != null)                              
          out.println(e.getMessage() + "<BR>");                               
          }catch(ClassNotFoundException e)                                           
            {                                                                         
             out.println("ClassNotFoundException: " + e.getMessage() + "<BR>");     
             }finally                                                                   
              {                                                                         
                //Clean up resources, close the connection.                            
               if(conn3 != null)                                                       
                 {                                                                      
                   try                                                                 
                      { // conn.commit();  
				   conn3.commit();
					stmt3.close();
                        conn3.close();                                                    
                      }catch (SQLException ignored) {// out.println("<h3>hmni</hi>");
					  }                                        
                  }                                                                      
               }

    }
}

