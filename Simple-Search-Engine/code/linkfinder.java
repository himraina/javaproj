import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;

public class linkfinder 
{
	static String title;
public static void linkfinder1(String title1) 
	{
		title=title1;
		int i1=0,i2=0,status=0,j=0,j1=0,k,m=0,n=0;
		int count1=0,count2=0,count3=0;
		try{
		Connection conn = null;
		Statement stmt=null;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                                                                                
    //     conn = DriverManager.getConnection("jdbc:odbc:search"); 
		
		conn = DriverManager.getConnection("jdbc:odbc:search"); 
		stmt = conn.createStatement();



		String str21,str31,str11;
		String arr1[]=new String[35];
		String arr2[]=new String[15];
		FileInputStream fin1=new FileInputStream("c:\\java\\msnlink.txt");
          byte b[]=new byte[fin1.available()];
          fin1.read(b,0,b.length);
          String str1=new String(b,0,b.length);
		
		FileInputStream fin2=new FileInputStream("c:\\java\\yahoolink.txt");
          byte c[]=new byte[fin2.available()];
          fin2.read(c,0,c.length);
          String str2=new String(c,0,c.length);
		
		FileInputStream fin3=new FileInputStream("c:\\java\\altalink.txt");
          byte d[]=new byte[fin3.available()];
          fin3.read(d,0,d.length);
          String str3=new String(d,0,d.length);
		
		System.out.println("Hello World!");
/*try{
String asd="c:\\java\\mainlink.txt";
FileOutputStream out1=new FileOutputStream(asd);
OutputStreamWriter wr=new OutputStreamWriter(out1);*/






//    yahoo link extraction
        int len=str2.length();
		System.out.println("length2="+len);
       for(int i=0;i<len-6;i++)
            {
          if(count1==12) break;
		 else
				{
		 if(status==0)
             str21=str2.substring(i,i+5);	
		 else
		 str21=str2.substring(i,i+5);
		 if (status==0 && str21.equalsIgnoreCase("link="))
			{
			i1=i+5;
                  status=1;
			continue;
				}
            else if(status==1 && str21.equalsIgnoreCase("link="))
					{
                   i2=i;
				   arr1[j]=new String();
			arr1[j]=str2.substring(i1,i2);
			
			System.out.println(arr1[j]);		
				//System.out.println(str2.substring(i1,i2));
				
				
				//wr.write(str2.substring(i1,i2));
				
				
				
				
				status=0;
				count1++;
				i=i-5;j++;
				continue;
                    }

									}}
		
		/*wr.flush();
		wr.close();

		}catch(Exception e)
		{System.out.println(e);}*/


/*try{
String asd1="c:\\java\\mainlink.txt";
FileOutputStream out2=new FileOutputStream(asd1,true);
OutputStreamWriter wr1=new OutputStreamWriter(out2);	*/
		
status=0;		
		System.out.println(j);		
m=j;
i2=0;i1=0;
//altavista link extraction

 int len3=str3.length();
		System.out.println("length3="+len3);
       for(int i=0;i<len3-6;i++)
            {
          if(count3==8) break;
		 else
				{
		 if(status==0)
             str31=str3.substring(i,i+5);	
		 else
		 str31=str3.substring(i,i+5);
		 if (status==0 && str31.equalsIgnoreCase("link="))
			{
			i1=i+5;
                  status=1;
			continue;
				}
            else if(status==1 && str31.equalsIgnoreCase("link="))
					{
                   
				   i2=i;

				    //arr2[j1]=new String();
			//arr2[j1]=str3.substring(i1,i2);
			
			 System.out.println("arraylength"+m);
			 for(k=0;k<m;k++)
						{
				if(str3.substring(i1,i2).equalsIgnoreCase(arr1[k])) break;
						
						
						}
							
			
			if(k==m)
						{
					//System.out.println(j);		
					//arr2[j]=new String();
					arr2[n]=str3.substring(i1,i2);
						
			//wr1.write(str3.substring(i1,i2));
			
			
			System.out.println(arr2[n]);
			//System.out.println(str3.substring(i1,i2));		
				status=0;
				count3++;
				i=i-5;
				n++;
				continue;
                    }
				else {
					status=0;
					i=i-5;
					continue;
					}
					
					
					
					
					}
				}}


	/*	wr1.flush();
		wr1.close();

		}catch(Exception e)
		{System.out.println(e);}*/


        System.out.println("nextlength"+(m+n));
		for(int p=0;p<n;p++)
			{
arr1[m+p]=arr2[p];
			}

m=m+n;
for(int p=0;p<m;p++)
			{
System.out.println("arrvalue"+arr1[p]);
			}


System.out.println("nextlength"+m);
n=0;

/*try{
String asd2="c:\\java\\mainlink.txt";
FileOutputStream out3=new FileOutputStream(asd2,true);
OutputStreamWriter wr2=new OutputStreamWriter(out3);*/	




i1=0;
i2=0;
status=0;
int len1=str1.length();

		System.out.println("length1="+len1);
       for(int i=0;i<len1-6;i++)
            {
          if(count2==5) break;
		 else
				{
		 if(status==0)
             str11=str1.substring(i,i+5);	
		 else
		 str11=str1.substring(i,i+5);
		 if (status==0 && str11.equalsIgnoreCase("link="))
			{
			i1=i+5;
                  status=1;
			continue;
				}
            else if(status==1 && str11.equalsIgnoreCase("link="))
					{
                   i2=i;
				 //  System.out.println("i2  "+i2);
			System.out.println("arraylengthmm"+m);
			System.out.println("arraylengthn"+n);
			System.out.println(i1);
			System.out.println(i2);
			System.out.println(str1.substring(i1,i2));
			for(k=0;k<m;k++)
						{
				if(str1.substring(i1,i2).equalsIgnoreCase(arr1[k])) break;
						
						
						}
							
			
			if(k==m)
						{
			
			
			
			
			
			
			arr2[n]=str1.substring(i1,i2);
			//wr2.write(str1.substring(i1,i2));			
			System.out.println(arr2[n]);
			
			//System.out.println(str1.substring(i1,i2));		
				status=0;
				count2++;
				i=i-5;
				n++;
				continue;
                    }
				
				else {
					status=0;
					i=i-5;
					continue;
					}
				}}}


/*wr2.flush();
wr2.close();
}catch(Exception e)
		{System.out.println(e);}*/
System.out.println(n);

 System.out.println("maxlength"+(m+n));
 
		for(int p=0;p<n;p++)
			{
arr1[m+p]=arr2[p];
			}
m=m+n;
try{
for(int i=0;i<m;i++)
		{
String sq="insert into mainlink values('"+arr1[i]+"','"+URLEncoder.encode(title)+"');";
stmt.executeUpdate(sq);
		}

}catch(Exception e)
		{System.out.println(e);}
conn.commit();
stmt.close();
conn.close(); 
		
}catch(Exception e)
		{System.out.println(e);}
	}
}
