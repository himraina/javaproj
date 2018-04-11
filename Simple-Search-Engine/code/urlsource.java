import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class urlsource {
static	String query;
    public static void urlsource1(String query1) throws Exception {
	query=query1;
String u=null;


System.getProperties().put("proxySet", "true"); 
System.getProperties().put("proxyHost", "144.16.192.213"); 
System.getProperties().put("proxyPort", "8080");

		 Connection conn = null;
		 Statement stmt=null;
		 Statement stmt1=null;
		 Statement stmt2=null;
		 ResultSet r=null;
		 ResultSet r2=null;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                                                                                
    	conn = DriverManager.getConnection("jdbc:odbc:search"); 
		stmt = conn.createStatement();
		stmt1 = conn.createStatement();
		stmt2 = conn.createStatement();
		

   System.out.println("select link from mainlink where query_id="+"'"+query+"'");
    
	r=stmt.executeQuery("select link from mainlink where query_id="+"'"+URLEncoder.encode(query)+"'");
	
	r2=stmt2.executeQuery("select * from querry_string where query_id="+"'"+URLEncoder.encode(query)+"'");
	//r=stmt.executeQuery("select link from mainlink where query_id="+"'"+"iit%27s"+"'");
	//r.next();
	while(r.next())
		{
	
	 String asd="c:\\java\\url.txt";
     FileOutputStream out1=new FileOutputStream(asd);
     OutputStreamWriter wr=new OutputStreamWriter(out1);	
		u=r.getString("link");
	System.out.println("u="+u);
		
	//while(r.next())
	//	{
	//u=r.getString("link");
	//System.out.println("u="+u);
	URL yah = new URL(u);
	BufferedReader in = new BufferedReader(new InputStreamReader(yah.openStream()));

	String inputLine;

	while ((inputLine = in.readLine()) != null)
        {
          wr.write(inputLine);
          
	 //   System.out.println(inputLine);
	  
      }
    in.close();
    wr.flush();
    wr.close();

		

String str3,str4="";
String head,title;
int i1=0,i2=0,status=0,j1=0,j2=0,k=0;

String h1[]=new String[10];
String h2[]=new String[10];
String h3[]=new String[10];
//String h2[]=new String[10];


//String asd="c:\\java\\link.txt";
//FileOutputStream out1=new FileOutputStream(asd);
//OutputStreamWriter wr=new OutputStreamWriter(out1);

FileInputStream fin=new FileInputStream("c:\\java\\url.txt");
          byte b[]=new byte[fin.available()];
          fin.read(b,0,b.length);
          String str2=new String(b,0,b.length);
//StringTokenizer st=new StringTokenizer(str1);
 //while(st.hasMoreTokens())
 // st.nextToken();
  // System.out.println(str2);
       int len=str2.length();
		System.out.println("length="+len);

            for(int i=0;i<len-7;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+6);	
		 else
		 str3=str2.substring(i,i+1);
//             	System.out.println("status="+status);      	System.out.println("hi"+str3+"hi");System.in.read();
		if (status==0 && str3.equalsIgnoreCase("<HEAD>"))
			{
			i1=i+6;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
			
			//System.out.println("Head="+str2.substring(i1,i2));
			//status=0;
			break;		

                   }
				}
			head=URLEncoder.encode(str2.substring(i1,i2));
			System.out.println("Head="+head);
                  
				   //System.exit(0);		
			
status=0;



for(int i=0;i<len-4;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+4);	
		 else
		 str3=str2.substring(i,i+1);
//             	System.out.println("status="+status);      	System.out.println("hi"+str3+"hi");System.in.read();
		if (status==0 && str3.equalsIgnoreCase("<H1>"))
			{
			i1=i+4;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
			h1[k]=new String();
			h1[k]=URLEncoder.encode(str2.substring(i1,i2));
			System.out.println("H1="+h1[k]);
			status=0;
			k++;
			continue;
			       }
				}
			
                   //System.exit(0);		
			

			
	status=0;
	k=0;


for(int i=0;i<len-4;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+4);	
		 else
		 str3=str2.substring(i,i+1);
//             	System.out.println("status="+status);      	System.out.println("hi"+str3+"hi");System.in.read();
		if (status==0 && str3.equalsIgnoreCase("<H2>"))
			{
			i1=i+4;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
			
			h2[k]=new String();
			h2[k]=URLEncoder.encode(str2.substring(i1,i2));
			System.out.println("H2="+h2[k]);
			status=0;
			k++;			continue;		

                   }
				}
			
                   //System.exit(0);		
			
		

			
  status=0;
	k=0;


for(int i=0;i<len-4;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+4);	
		 else
		 str3=str2.substring(i,i+1);
//             	System.out.println("status="+status);      	System.out.println("hi"+str3+"hi");System.in.read();
		if (status==0 && str3.equalsIgnoreCase("<H3>"))
			{
			i1=i+4;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
			h3[k]=new String();
			h3[k]=URLEncoder.encode(str2.substring(i1,i2));
			System.out.println("H3="+h3[k]);
			status=0;
			k++;
			continue;		

                   }
				}
			
                   //System.exit(0);		
			
    

status=0;



  for(int i=0;i<len-9;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+7);	
		 else
		 str3=str2.substring(i,i+1);
//             	System.out.println("status="+status);      	System.out.println("hi"+str3+"hi");System.in.read();
		if (status==0 && str3.equalsIgnoreCase("<TITLE>"))
			{
			i1=i+7;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
			break;		

                   }
				}
			title=URLEncoder.encode(str2.substring(i1,i2));
			System.out.println("title="+title);
                   //System.exit(0);		
			

status=0;
//////////////////////////////

 int i3=0;
 i2=0;
String content="";
	  String asd1="c:\\java\\content.txt";
     FileOutputStream out2=new FileOutputStream(asd1);
     OutputStreamWriter wr1=new OutputStreamWriter(out2);
 
 
 for(int i=0;i<len-7;i++)
            {
             
		 if(status==0)
		  str3=str2.substring(i,i+7);	
		 else
		 str3=str2.substring(i,i+9);
			
            	//System.out.println("status="+status);      	
            //System.out.println("hi"+str3+"hi");System.in.read();
			if (status==0 && str3.equalsIgnoreCase("<script"))
			{
			i3=i2;
		i1=i;System.out.println("i3,i1="+i3+" "+i1);
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("</script>"))
			{
                   i2=i+9;System.out.println("i2="+i2);
                str4=str4+str2.substring(i3,i1);
		status=0;
		continue;
		}
              
   		}
	//System.out.println("str4="+str4);
		
/*str4=str4+str2.substring(i2,len);
System.out.println("str4="+str4);
System.out.println();
System.out.println();
*/
	len=str4.length();System.out.println("length="+len);
        status=0;
 	for(int i=0;i<len-1;i++)
            {
             
		str3=str4.substring(i,i+1);	
		//System.out.println("hi"+str3+"hi");
		if (status==0 && str3.equalsIgnoreCase(">"))
			{
			i1=i+1;
                  status=1;
			continue;


			}//end if
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
                if(str4.substring(i1,i2).indexOf("&nbsp;",0)==-1)
                {
		
               
			   //System.out.println(str4.substring(i1,i2));//System.in.read();
		content=str4.substring(i1,i2);
		//System.out.println(content);
		wr1.write(content);
		status=0;
		continue;
		}
      
                else
                { 
		status=0;
		continue;
                }
   			}
			


}

    wr1.flush();
    wr1.close();











/////////////////////////////////
String sq="insert into linkvalue values('"+u+"','"+head+"','"+title+"','"+h1[0]+"','"+h1[1]+"','"+h2[0]+"','"+h2[1]+"','"+h2[2]+"','"+h3[0]+"','"+h3[1]+"','"+h3[2]+"');";
stmt1.executeUpdate(sq);



		}


	conn.commit();
	stmt.close();
	conn.close();




}
}

