import java.io.*;
import java.util.*;
import java.net.*;

public class stop {
String str;
stop(String st)
{
str=st;
}

public String[][] retkey()throws Exception
{
String arr[][]=new String[2][10];
for(int i=0;i<10;i++)
arr[0][i]="";
for(int i=0;i<10;i++)
arr[1][i]="";
int status=0;
//String str="neeraj in india and for world";
int i=0,j=0;
//try{
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
	temp2=st1.nextToken();//System.out.print(temp2+"aa ");
	if(temp2.equalsIgnoreCase(temp))
 	{
	status=1;
	//System.out.print(temp2+" aa ");
	arr[1][j]=temp2;
	j++;
	break;
	}
	}
if(status==1)
{
status=0;
continue;
}
arr[0][i]=temp;
i++;
//System.out.println(temp);

}
for(i=0;i<j;i++)
System.out.println(arr[1][i]);
for(i=0;i<10;i++)
System.out.println(arr[0][i]);
//}catch(Exception e)
//	{System.out.println(e);}
return(arr);
}
}