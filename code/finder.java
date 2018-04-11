import java.io.*;
import java.util.*;
public class finder{
public static void main(String args[])throws Exception{
String str3,str4;
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
			head=str2.substring(i1,i2);
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
			h1[k]=str2.substring(i1,i2);
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
			h2[k]=str2.substring(i1,i2);
			System.out.println("H1="+h2[k]);
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
			h3[k]=str2.substring(i1,i2);
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
			title=str2.substring(i1,i2);
			System.out.println("title="+title);
                   //System.exit(0);		
			
}
}