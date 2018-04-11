import java.io.*;
import java.util.*;
import java.net.*;

class findquerry{
int count[];
int arr[][];
int darr[]=new int[150];

public static void main(String args[])
	{
	findquerry fd=new findquerry();
	try{
	fd.findpos();
    //int	i=fd.min(0,0);
	//System.out.println("i="+i);
	}
	catch(Exception ee)
		{
		System.out.println(ee);
		}
	
	}


public void findpos(){
int i=0,i1,i2=0,i3=0,np=0,nt=0,l1=0,countx=0;
double r;
try{
FileInputStream fin=new FileInputStream("c:\\java\\search.txt");
          byte b[]=new byte[fin.available()];
          fin.read(b,0,b.length);
          String str2=new String(b,0,b.length);
String s="Neeraj";
StringTokenizer st=new StringTokenizer(s.toLowerCase());
int counter=st.countTokens();
count=new int[counter];
int t=0;
if(counter==1)
	{
		String temp11=st.nextToken();
		while((l1=str2.indexOf(temp11,i))!=-1)
	{
	
	i=l1+temp11.length();
	t=l1;
	System.out.println("i1="+l1);
	//arr[i2][i3]=i1;
	//i3++;
	countx++;
	
	}
	//r=(100)+((5000-l1)/(5000/100))+countx/1000;
	r=100*countx;
System.out.println("Ranking"+r);

}
else
	{

//////////////////////////////////////////
arr=new int[counter][80];


while(st.hasMoreTokens())
{
 String temp=st.nextToken();

 System.out.println("temp="+temp);


	if(str2.indexOf(temp,i)!=-1)
	{
	np++;
	}

i=0;
i1=0;

while((i1=str2.indexOf(temp,i))!=-1)
	{
	
	i=i1+temp.length();System.out.println("i1="+i1);
	arr[i2][i3]=i1;
	i3++;
	count[i2]=count[i2]+1;
	
	}
i3=0;
i2++;
i=0;
}
System.out.println("np="+np);

for(i=0;i<counter;i++)
	{System.out.println("count[i]="+count[i]);
nt=nt+count[i];}
System.out.println("nt="+nt);
for(i=0;i<counter;i++)
	{
for(i2=0;i2<10;i2++)

		{
			if(arr[i][i2]!=0)
System.out.println("arr[i][i2]="+arr[i][i2]);
		}
	}

double c1=100.0,c2=5000.0,c3;
c3=10*c1;
int sum=0,sum1=0;

for(int m=0;m<np-1;m++)
	{ 
	System.out.println("firstloop");
		for(int n=m+1;n<np;n++)
		{
		//System.out.println("secontloop");
		
		sum=sum+min(m,n);
	System.out.println("mainmin"+sum);
		}
	sum1=sum1+sum;
	}
System.out.println("min"+sum1);
int sum2=0;
		for(int k=0;k<np-1;k++)
				{
				sum2=sum2+(np-k);
				}
System.out.println("min2 "+sum2);
System.out.println("min3 "+nt/c3);
r=(c1*np)+((c2-(sum1/sum2))/(c2/c1))+nt/c3;
System.out.println("Ranking"+r);
}}
catch(Exception e)
	{System.out.println(e);
	}

}


public int min(int i,int j)
{
for(int y=0;y<150;y++)
	darr[y]=-1;//System.out.println("secontloop");
  int g=0;
	for(int y=0;y<80;y++)
		{
		for(int x=0;x<80;x++)
			{//System.out.println("secontloop");
		if(arr[i][y]!=0 & arr[j][x]!=0)
			{
			darr[g]=arr[i][y]-arr[j][x];//System.out.println("secontloop");
			if(darr[g]<0)
				darr[g]=-(darr[g]);
	//System.out.println("secontloop");
			g++;	
			}
			}
		}
	int min=darr[0];//System.out.println("secontloop");
	for(int x=1;x<150;x++)
		{
		if(darr[x]==-1)
			return min;//System.out.println("secontloop");
	if(darr[x]<min)
		min=darr[x];
			
		}
	return(min);
}
}
