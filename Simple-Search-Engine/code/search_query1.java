import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.Date;
import java.util.*;
public class search_query1 extends HttpServlet
{
   
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
      Date date =new Date();
	 //float msec1=date.getTime();
		int m111 = date.getMinutes();
        int s1 = date.getSeconds();
	    int k11=(m111*60)+s1;
	  response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

      String title = request.getParameter("srchval");
     // out.println(msec1);
	  out.println("<html>");
      out.println("<title>Search Engine Result for : ");
      out.println(title+"  ");
      out.println("</title>");
      out.println("<body bgcolor=black text=\"#C0C000\" LINK=\"#CCCCCC\" VLINK=white>");
      out.println("<FONT face=ARIAL>");
      out.println("<IMG SRC=\"c:\\java\\lol.gif\" width=270 height=90 >");
	  out.println("<table  width=480 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=\"center\"><table  width=477 border=0 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td  align=center>");
	  out.println("<form name=neeraj method=get action=http://127.0.0.1:8080/servlet/search_query1><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\"><b>SEARCH FOR:</b></FONT><IMG SRC=\"c:\\java\\space.gif\" width=15 height=1 ><input type=text name=srchval value=\""+title+"\" ");
	 // out.println(query);
	  out.println(" size=40 maxlength=2048>&nbsp;&nbsp;<input type=\"image\" src=\"c:\\java\\search.gif\" width=48 height=20 align=\"absmiddle\">");
      out.println("</td></tr></table></td></tr></table>");
	 

         int ccount=0;
	     Connection conn = null;
	     Statement stmt=null;
		 Connection conn3 = null;
		 Statement stmt3=null;
		 Statement stmt4=null;
		 Statement stmt5=null;
		 Statement stmt7=null;
		 ResultSet r3=null;
		 ResultSet r4=null;
	     ResultSet r5=null;
		 ResultSet r7=null;
	  try
      {
		  //out.println("<h3>hi</hi>");
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                                                                                
         conn = DriverManager.getConnection("jdbc:odbc:search");                           
         conn3 = DriverManager.getConnection("jdbc:odbc:search");                           
         stmt3 = conn3.createStatement();
		 stmt4 = conn3.createStatement();
		 stmt5 = conn3.createStatement();
         stmt = conn.createStatement();
		 stmt7 = conn3.createStatement();
	/*r5=stmt5.executeQuery("select * from querry_string where query_id="+"'"+q+"'");
	if(!r5.next())
	

		  {*/

         
//*************************************************************************		
		
		
		
		title=title.toLowerCase();
		stop st=new stop(title);
		yahoo1 mt=new yahoo1();
		//out.println("<h3>hi1</h3>");
		msn m1=new msn();
		//out.println("<h3>hi2</h3>");
		alta m2=new alta();
		findmsn msn=new findmsn();
		findyahoo yah=new findyahoo();
		findalta vista=new findalta();
		linkfinder lf =new linkfinder();
		ur1 us=new ur1();
		
		
//************************************************************************		
		 String pp[]=new String [10];
		 
		 String rem=null;
		try{
		 String arr[][]=st.retkey();
		//out.println("<h3>hi7</h3>");
		 if(arr[0][0].equals(""))
			{
		 rem=null;
		 String sq="insert into querry_string values('"+URLEncoder.encode(arr[1][0])+"','"+URLEncoder.encode(arr[1][1])+"','"+URLEncoder.encode(arr[1][2])+"','"+URLEncoder.encode(arr[1][3])+"','"+URLEncoder.encode(arr[1][4])+"','"+URLEncoder.encode(arr[1][5])+"','"+URLEncoder.encode(arr[1][6])+"','"+URLEncoder.encode(arr[1][7])+"','"+URLEncoder.encode(arr[1][8])+"','"+URLEncoder.encode(arr[1][9])+"','"+URLEncoder.encode(title)+"');";
          stmt.executeUpdate(sq);
			
			}
			else
			{
				
		for(int w=0;w<10;w++)
			{
			pp[w]=arr[1][w];
			}
			System.out.println(pp.length);
		int kount=0;
		for(int x=0;x<10;x++)
		{ 
		if(!pp[x].equals("")) {kount++;}
		
		}
	System.out.println(kount);
		rem=""+pp[0]+" "+pp[1]+" "+pp[2]+" "+pp[3]+" "+pp[4]+" "+pp[5]+" "+pp[6]+" "+pp[7]+" "+pp[8]+" "+pp[9]+" ";
	
		String sq="insert into querry_string values('"+URLEncoder.encode(arr[0][0])+"','"+URLEncoder.encode(arr[0][1])+"','"+URLEncoder.encode(arr[0][2])+"','"+URLEncoder.encode(arr[0][3])+"','"+URLEncoder.encode(arr[0][4])+"','"+URLEncoder.encode(arr[0][5])+"','"+URLEncoder.encode(arr[0][6])+"','"+URLEncoder.encode(arr[0][7])+"','"+URLEncoder.encode(arr[0][8])+"','"+URLEncoder.encode(arr[0][9])+"','"+URLEncoder.encode(title)+"');";
          stmt.executeUpdate(sq);
		
		}

		System.out.println("remoce"+rem);
			mt.searchyahoo(title);
		//out.println("<h3>hi</h3>");
		//msn m1=new msn();
		m1.searchmsn(title);
		//out.println("<h3>hi</h3>");
		//alta m2=new alta();
		m2.searchalta(title);
		msn.findmsn1();
		yah.findyahoo1();
		vista.findalta1();
		
		
		// out.println("<h3>"+sq+"</h3>");
		 //out.println("<h3>hi8</hi>");
		 
		 lf.linkfinder1(title);
		//	out.println("<h3>hi9</hi>");
			us.findos(title);
         //String sq="insert into query_string values('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"','"+arr[9]+"','"+title+"');";
         System.out.println("Sucess");
		 		 

//*******************************************************************************		 
	 }
		catch(Exception e2)
		  {
			System.out.println(e2+"error for same");
 //out.println("<h3>hiiiiiiiiiiiiiiiiii</hi>");
		  }
		  try{
	
	
	System.out.println(rem);
if(rem==null || rem=="" || rem.equals(null) || rem.equals(""))
			{
System.out.println("rem=null");

			}
			else
			  {
System.out.println("inside rem"+rem);
out.println("<br><font color=white> These stop words are not included in ranking <b><font color=red>"+rem+"</b></font></font>");		
		
			}
	
	
	String q=URLEncoder.encode(title);
	System.out.println(q);
	System.out.println("select * from mainlink,linkvalue  where linkvalue.link=mainlink.link and mainlink.query_id="+"'"+q+"'"+"order by rank desc");
	r3=stmt3.executeQuery("select * from mainlink,linkvalue  where mainlink.query_id="+"'"+q+"'"+" and linkvalue.link=mainlink.link order by rank desc");
	/*r7=stmt7.executeQuery("select count(*) from mainlink,linkvalue  where linkvalue.link=mainlink.link and mainlink.query_id="+"'"+q); 
	  r7.next();
	  int m=r7.getInt(1);
	  //r3.beforeFirst();
	  out.println(m);*/
	  out.println("<br>&nbsp;<table  width=480 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=center><table  width=969 border=0 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td width=769><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\">Searched the web for: <b>");
	  out.println(title);
		 Date date1 = new Date();
		int m222 = date1.getMinutes();
        int s2 = date1.getSeconds();
	    int k22=(m222*60)+s2;
	 //float msec2=date.getTime();
	 // out.println(msec2);
	 int  time=k22-k11;
	  out.println("</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>Search took <b>"+time+"</b>  seconds");
	  out.println("</td></tr></table></td></tr></table>");
	  out.println("");
//r3.next();	 
out.println("<div>");	
String man;
if(!r3.next()) 
		  {// out.println("<h2>No Matches found!<h2>");
ccount=0;
out.println("<p><br><br>");
out.println("<font size=3 color=white>");
out.println("<b>Sorry!</b>&nbsp;&nbsp;&nbsp;  No Results Found for &nbsp;&nbsp;&nbsp;<font color=red <b><i>"+title+"</i></b></font>");
out.println("<br><b>Try</b>&nbsp; your query with more specified query term...");
out.println("<br><br><br><br></font>");
		  }
		  else
		  {



			  ////////////IN ELSE///////////////////
out.println("<p><br>");
String tt=r3.getString("title");
tt=URLDecoder.decode(tt);
String ll=r3.getString("link");
ll=URLDecoder.decode(ll);
ccount++;
//out.println("<h3>hi3</hi>");
if(tt==null || tt=="" || tt.equals(null) ||tt.equals(""))
			  {
out.println("<a href=\""+ll+"\">Untitled</a>");


out.println("<br>");}
else{
	out.println("<a href=\""+ll+"\">"+tt+"</a><br>");

}

out.println("<font size=-1>");
//out.println("<a href=\""+ll+"\">"+tt+"</a>");
String cont=r3.getString("content");
cont=URLDecoder.decode(cont);
cont=cont.toLowerCase();
cont=cont.toLowerCase();
int l2=0;
String stt="";



for(int k1=0;k1<3;k1++)
			  {
	
	try{
	StringTokenizer stq=new StringTokenizer(title);
	String nn=stq.nextToken();
	nn=nn.toLowerCase();
	nn=nn.toLowerCase();
    System.out.println("nn="+nn);
	int i9=cont.indexOf(nn,l2);
	if(i9!=-1)
				  {
	//i=l1+temp11.length();
	l2=i9+70;
	//t=l1;
	//System.out.println("i9="+i9);
	stt=cont.substring(i9,i9+70);
	out.print(".....");
	out.print(stt);
	out.print("<br>");
	
	continue;
	}
	if(i9==-1)			  {
stt=cont.substring(0,55);
out.print(".....");
out.print(stt);
out.print("<br>");
break;
}
			  }catch(Exception e)
	{System.out.println(e);}

			  }

//stt=cont.substring(0,200);
/*
out.print(".....");
out.print(stt);
out.print("<br>");*/
			

out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;");

 out.println("<font color=\"#008000\"><i>");
 out.println(ll);
 out.println("</i><br></font></font>");

		 out.println("</p>");


///////////////////////////////////////
while(r3.next())
		  {
	out.println("<p><br>");
 tt=r3.getString("title");
tt=URLDecoder.decode(tt);
 ll=r3.getString("link");
ll=URLDecoder.decode(ll);
ccount++;
//out.println("<h3>hi3</hi>");
if(tt==null || tt=="" || tt.equals(null) ||tt.equals(""))
			  {
out.println("<a href=\""+ll+"\">Untitled</a>");


out.println("<br>");}
else{
	out.println("<a href=\""+ll+"\">"+tt+"</a><br>");

}

out.println("<font size=-1>");
//out.println("<a href=\""+ll+"\">"+tt+"</a>");
cont=r3.getString("content");
cont=URLDecoder.decode(cont);
 l2=0;
stt="";



for(int k1=0;k1<3;k1++)
			  {
	
	try{
	StringTokenizer stq=new StringTokenizer(title);
	String nn=stq.nextToken();
 //
	int i9=cont.indexOf(nn,l2);
	if(i9!=-1)
				  {
	//i=l1+temp11.length();
	l2=i9+70;
	//t=l1;
	//System.out.println("i9="+i9);
	stt=cont.substring(i9,i9+70);
	out.print(".....");
	out.print(stt);
	out.print("<br>");
	
	continue;
	}
	if(i9==-1)			  {
stt=cont.substring(0,55);
out.print(".....");
out.print(stt);
out.print("<br>");
break;
}
			  }catch(Exception e)
	{System.out.println(e);}

			  }

//stt=cont.substring(0,200);
/*
out.print(".....");
out.print(stt);
out.print("<br>");*/
			

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

//out.println("<br>&nbsp;<table  width=480 height=20 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=center><table  width=769 border=0 height=18 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td width=769><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\"></td></tr></table></td></tr></table></form>");
	 
		 }
		catch(Exception e2)
		  {
			System.out.println(e2);
 //out.println("<h3>hiiiiiiiiiiiiiiiiii</hi>");
		  }
	     
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
               if(conn != null)                                                       
                 {                                                                      
                   try                                                                 
                      { // conn.commit();  
				   conn.commit();
				   stmt.close();
                    conn.close();  
					 conn3.commit();
				    stmt3.close();
                    conn3.close(); 
					//stmt7.close();
                      }catch (SQLException ignored) {// out.println("<h3>hmni</hi>");
					  }                                        
                  }                                                                      
  out.println("<br>&nbsp;<table  width=480 height=20 border=0 cellspacing=0 cellpadding=1 bgcolor=\"#C0C000\"><tr><td  align=center><table  width=769 border=0 height=18 cellspacing=0 cellpadding=0 bgcolor=\"#606060\"><tr><td width=769><FONT SIZE=2 COLOR=\"#C0C000\" FACE=\"verdana\"><b><center>");
  out.println(ccount);
  out.println("</b>  No of Results Found for query term<b>  "+title+"</b></center></td></tr></table></td></tr></table></form>");
ccount=0;
			   }

    }
}

