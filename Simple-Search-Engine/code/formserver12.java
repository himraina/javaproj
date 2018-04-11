import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class formserver12 extends HttpServlet
{
 public void init(ServletConfig cfg)
 {
  try{
  super.init(cfg);
     }
  catch(Exception e)
  {
   System.out.println(e);
   }
 }
public void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException
{
 
 resp.setContentType("text/html");
 String id=req.getParameter("elec_id");   
 String id1=req.getParameter("const_id");   
 
 PrintWriter out=resp.getWriter();
 out.println("<html><head><title>Insertion in table</title></head>");
 out.println("<body bgcolor=#fffdaa text=green>");
 
 
      Connection con=null;
      Statement stat=null;
        ResultSet r=null;      
try{

  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      con=DriverManager.getConnection("jdbc:odbc:proj","proj","proj");
      stat=con.createStatement();
System.out.println("select DOE from elec_const where elec_id="+"'"+id+"'"+" and const_id="+"'"+id1+"'");

r=stat.executeQuery("select DOE from elec_const where elec_id="+"'"+id+"'"+" and const_id="+"'"+id1+"'");

   
ResultSetMetaData metadata=r.getMetaData();
 int count=metadata.getColumnCount(); System.out.println("count="+count);

   if(!r.next()) 
     out.println("<h2>No Matches found!<h2>");
  else
 {         
 String str[]=new String[count];    
 for(int i=1;i<=count;i++)
{ str[i-1]=metadata.getColumnName(i);
  System.out.println("str[i-1]="+str[i-1]);
 }
 out.println("<table border=1><tr>");
 for(int i=0;i<count;i++)
 {
 out.println("<th bgcolor=yellow><text color=#001234>"+str[i]+"</th>");
  }
 out.println("</tr><br>");
out.println("<tr>");
for(int i=0;i<count;i++)
   {
    out.println("<td><text color=red>"+r.getString(str[i])+"</td>");

    }
 while(r.next())
 { 
   out.println("<tr>");
  for(int i=0;i<count;i++)
   {
    out.println("<td><text color=red>"+r.getString(str[i])+"</td>");

    }
  out.println("<tr>");
  }
out.println("</table>");
}
con.commit();
stat.close();
con.close();
} 
catch(Exception e)
{
 out.println(e);
}  

System.out.println("closed connection to con\n");
}
}