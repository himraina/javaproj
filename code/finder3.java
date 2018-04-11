import java.io.*;
import java.util.*;
public class finder3{
public static void main(String args[])throws Exception{
String str3,str4="";
int i1=0,i2=0,status=0,i3=0,j2=0;
//String asd="c:\\java\\link.txt";
//FileOutputStream out1=new FileOutputStream(asd);
//OutputStreamWriter wr=new OutputStreamWriter(out1);

FileInputStream fin=new FileInputStream("c:\\java\\url1.txt");
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
		
               System.out.println(str4.substring(i1,i2));//System.in.read();
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
}			
}			

			
      

