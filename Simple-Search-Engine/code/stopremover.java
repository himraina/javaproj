import java.io.*;
import java.util.*;
import java.net.*;

public class stopremover{
String str;
stopremover(String st)
{
str=st;
}

public String[] retkey()throws Exception
{
String arr[]=new String[10];
for(int i=0;i<10;i++)
arr[i]="";
int status=0;
int i=0;
FileInputStream fin=new FileInputStream("c:\\java\\stop.txt");
byte b[]=new byte[fin.available()];
fin.read(b,0,b.length);
String str2=new String(b,0,b.length);
StringTokenizer st2=new StringTokenizer(str);
String temp2=null;
while(st2.hasMoreTokens())
{
 String temp=st2.nextToken();
 //System.out.println("temp="+temp);
StringTokenizer st1=new StringTokenizer(str2);
while(st1.hasMoreTokens())
	{
	temp2=st1.nextToken();//System.out.print(temp2+" ");
	if(temp2.equalsIgnoreCase(temp))
 	{
	status=1;
	break;
	}
	}
if(status==1)
{
status=0;
continue;
}
arr[i]=temp;
i++;
System.out.println(temp);

}

return(arr);
}
}