import java.io.*;
import java.util.*;
public class findmsn{
public static void findmsn1()throws Exception{
String str3,str4;
int i1=0,i2=0,status=0,j1=0,j2=0;
String asd="c:\\java\\msnlink.txt";
FileOutputStream out1=new FileOutputStream(asd);
OutputStreamWriter wr=new OutputStreamWriter(out1);

FileInputStream fin=new FileInputStream("c:\\java\\msn.txt");
          byte b[]=new byte[fin.available()];
          fin.read(b,0,b.length);
          String str2=new String(b,0,b.length);

       int len=str2.length();
		System.out.println("length="+len);

           




 for(int i=0;i<len-4;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+3);	
		 else
		 str3=str2.substring(i,i+1);
			
		if (status==0 && str3.equalsIgnoreCase("<i>"))
			{
			i1=i+3;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
			System.out.println("links="+str2.substring(i1,i2));			
			wr.write("link="+str2.substring(i1,i2));
			status=0;	
			continue;
                   }
				


}//end for
			
			
wr.write("link=");
wr.flush();		
wr.close();




}			
}			

			
      

