import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class ur {

int count[];
int arr[][];
int darr[]=new int[5000];

static	String query;
   
	/*public static void main(String args[])
	{
	ur fd=new ur();
	try{
	fd.findos();
    //int	i=fd.min(0,0);
	//System.out.println("i="+i);
	}
	catch(Exception ee)
		{
		System.out.println(ee);
		}
	
	}*/
	
	
	
	
	
	
	public void findos(String query1){
	//query=query1;
query=query1;
try{

String u=null;


System.getProperties().put("proxySet", "true"); 
System.getProperties().put("proxyHost", "144.16.192.213"); 
System.getProperties().put("proxyPort", "8080");

		 Connection conn5 = null;
		 Statement stmt=null;
		 Statement stmt1=null;
		 Statement stmt2=null;
		 ResultSet r=null;
		 ResultSet r2=null;
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                                                                                
    	conn5 = DriverManager.getConnection("jdbc:odbc:search"); 
		stmt = conn5.createStatement();
		stmt1 = conn5.createStatement();
		stmt2 = conn5.createStatement();
		

 //  System.out.println("select link from mainlink where query_id="+"'"+query+"'");
    
	r=stmt.executeQuery("select link from mainlink where query_id="+"'"+URLEncoder.encode(query)+"'");
	
	r2=stmt2.executeQuery("select * from querry_string where query_id="+"'"+URLEncoder.encode(query)+"'");
	//r=stmt.executeQuery("select link from mainlink where query_id="+"'"+"india+pakistan"+"'");
	//r2=stmt2.executeQuery("select * from querry_string where query_id="+"'"+"india+pakistan"+"'");
	r2.next();
	int headpoint=0,titlepoint=0,h1point=0,h2point=0,h3point=0,linkpoint=0;
	String kw[]=new String[10];
	
	kw[0]=r2.getString("key_word1");
    System.out.println(kw[0]);
	kw[1]=r2.getString("key_word2");
    System.out.println(kw[1]);
	kw[2]=r2.getString("key_word3");
    System.out.println(kw[2]);
	kw[3]=r2.getString("key_word4");
    System.out.println(kw[3]);
	kw[4]=r2.getString("key_word5");
    System.out.println(kw[4]);
	kw[5]=r2.getString("key_word6");
    System.out.println(kw[5]);
	kw[6]=r2.getString("key_word7");
    System.out.println(kw[6]);
	kw[7]=r2.getString("key_word8");
    System.out.println(kw[7]);
	kw[8]=r2.getString("key_word9");
    System.out.println(kw[8]);
	kw[9]=r2.getString("key_word10");
    System.out.println(kw[9]);
	int kount=0;
	for(int x=0;x<10;x++)
		{ 
		if(!kw[x].equals("")) {kount++;}
		
		}
	System.out.println(kount);
	//r.next();
	while(r.next())
		{
	
	 String asd="c:\\java\\url.txt";
     FileOutputStream out1=new FileOutputStream(asd);
     OutputStreamWriter wr=new OutputStreamWriter(out1);	
		u=r.getString("link");
	u=u.toLowerCase();
	System.out.println("u="+u);
		
            //head=URLEncoder.encode(str2.substring(i1,i2));
			//head=head.toLowerCase();
			//System.out.println("Head="+head);
            int cou2=0;
		   for(int i=0;i<kount;i++)
			{
				if(u.indexOf(kw[i],0)!=-1)
					cou2++;
			}
				System.out.println("linkcount="+cou2);  	
				linkpoint=cou2*115;
	
	
	
	
	
	//while(r.next())
	//	{
	//u=r.getString("link");
	//System.out.println("u="+u);
	try{

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
			head=head.toLowerCase();
			//System.out.println("Head="+head);
            int cou=0;
		   for(int i=0;i<kount;i++)
			{
				if(head.indexOf(kw[i],0)!=-1)
					cou++;
			}
				//System.out.println("Headcount="+cou);  
				   //System.exit(0);		
			headpoint=cou*110;
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
			h1[k]=h1[k].toLowerCase();
			System.out.println("H1="+h1[k]);
			status=0;
			k++;
			continue;
			       }
				}
				int coun2=0;
				//  System.out.println("kount="+kount);
			// System.out.println("h1len="+h1.length);
			 for(int l=0;l<2;l++)
			{
				 String tt=h1[l];
			//System.out.println("tt"+tt);
			if(tt!=null) {//System.out.println("no");}
				
				// System.out.println("yes");
			 for(int i=0;i<kount;i++)
			{
				//System.out.println("kw="+kw[i]);//System.out.println(title.indexOf("Singh",0));
				
				
				
				if(tt.indexOf(kw[i],0)!=-1)
					coun2++;
			
			}
			//System.out.println("coun"+coun2);
			}
			
			}
				//System.out.println("h1count="+coun2); 
			
                   //System.exit(0);		
			h1point=103*coun2;

			
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
			h2[k]=h2[k].toLowerCase();
			//System.out.println("H2="+h2[k]);
			status=0;
			k++;			continue;		

                   }
				}
			
                  int coun1=0;
				 // System.out.println("kount="+kount);
			 for(int l=0;l<3;l++)
			{
				 String tt=h2[l];
			 
			 if(tt!=null){
			 for(int i=0;i<kount;i++)
			{
				//System.out.println("kw="+kw[i]);//System.out.println(title.indexOf("Singh",0));
				if(tt.indexOf(kw[i],0)!=-1)
					coun1++;
			}}}
				//System.out.println("h2count="+coun1);  //System.exit(0);		
			
		h2point=102*coun1;

			
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
			h3[k]=h3[k].toLowerCase();
			//System.out.println("H3="+h3[k]);
			status=0;
			k++;
			continue;		

                   }
				}
			
                  int coun=0;
				//  System.out.println("kount="+kount);
			 for(int l=0;l<3;l++)
			{
				 String tt=h3[l];
			if(tt!=null)
				{
			 for(int i=0;i<kount;i++)
			{
				//System.out.println("kw="+kw[i]);//System.out.println(title.indexOf("Singh",0));
				if(tt.indexOf(kw[i],0)!=-1)
					coun++;
			}}}
				//System.out.println("h3count="+coun); 
				   h3point=101*coun;
				   
				   
				   //System.exit(0);		
			
    

status=0;

System.out.println("len"+len);

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
			title=title.toLowerCase();
			//String temp3=title.toLowerCase();
		//	System.out.println("title="+title);
                 int cou1=0;
		// System.out.println("kount="+kount);
		  for(int i=0;i<kount;i++)
			{
				//System.out.println("kw="+kw[i]);//System.out.println(title.indexOf("Singh",0));
				if(title.indexOf(kw[i],0)!=-1)
					cou1++;
			}
				System.out.println("titlecount="+cou1);  
				titlepoint=cou1*105;
				   
				   //System.exit(0);		
			

status=0;
//////////////////////////////

//System.out.println("len"+len);
 int i3=0;
 i2=0;
String content="";
	  String asd1="c:\\java\\content.txt";
     FileOutputStream out2=new FileOutputStream(asd1);
     OutputStreamWriter wr1=new OutputStreamWriter(out2);
 
 
 for(int i=0;i<len-1;i++)
            {
             
		 if(status==0)
             str3=str2.substring(i,i+1);	
		 else
		 str3=str2.substring(i,i+1);
//             	System.out.println("status="+status);      	System.out.println("hi"+str3+"hi");System.in.read();
		if (status==0 && str3.equalsIgnoreCase(">"))
			{
			i1=i+1;
                  status=1;
			continue;


			}
            else if(status==1 && str3.equalsIgnoreCase("<"))
			{
                   i2=i;
                if(str2.substring(i1,i2).indexOf("&nbsp;",0)==-1)
                {
//content="neeraj singh";
content=str2.substring(i1,i2);              
content.toLowerCase();
//System.out.println(""+content);//System.in.read();
				//content=str2.substring(i1,i2);
		//System.out.println(content);
		wr1.write(content.toLowerCase());
		status=0;
		continue;}



// if(str2.equals(""))
                // { 
		
               
                else
                { 
		status=0;
		continue;
                }
   }
			
			
                   //System.exit(0);		
		
}
    wr1.flush();
    wr1.close();



FileInputStream fin1=new FileInputStream("c:\\java\\content.txt");
          byte c[]=new byte[fin1.available()];
          fin1.read(c,0,c.length);
          String str5=new String(c,0,c.length);

//System.out.println("length"+str5.length());

////////////////////////////
//public void findpos(){
int i=0;
i1=0;i2=0;i3=0;
int np=0,nt=0,l1=0,countx=0;
double rank;
//try{
//System.out.println("outside");
//String s="Neeraj";
//StringTokenizer st=new StringTokenizer(s.toLowerCase());
int counter=kount;
//System.out.println("kount"+kount);
count=new int[counter];
int t=0;
//System.out.println("kw"+kw[0]);
if(counter==1)
	{
		//System.out.println("counter"+kount);
		String temp11=kw[0];
		//System.out.println("lowercase="+temp11.toLowerCase());
		while((l1=str5.indexOf(temp11.toLowerCase(),i))!=-1)
	{
	
	i=l1+temp11.length();
	t=l1;
	//System.out.println("i1="+l1);
	//arr[i2][i3]=i1;
	//i3++;
	countx++;
	
	}
	//r=(100)+((5000-l1)/(5000/100))+countx/1000;
	rank=100*countx;
System.out.println("Ranking"+rank);

}
else
	{

//////////////////////////////////////////
arr=new int[counter][200];

//System.out.println("kw"+kw[i]);
//System.out.println("kount"+kount);
//while(st.hasMoreTokens())
for(int y=0;y<kount;y++)
{
 String temp=kw[y];

//System.out.println("kw"+kw[y]);
// System.out.println("temp="+temp);
temp=temp.toLowerCase();
//System.out.println("temp"+temp);

	if(str5.indexOf(temp,i)!=-1)
	{
	np++;
	}

i=0;
i1=0;

while((i1=str5.indexOf(temp.toLowerCase(),i))!=-1)
	{
	
	i=i1+temp.length();//System.out.println("i1="+i1);
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
	{
	//System.out.println("count[i]="+count[i]);
nt=nt+count[i];}
//System.out.println("nt="+nt);
/*for(i=0;i<counter;i++)
	{
for(i2=0;i2<10;i2++)

		{
			if(arr[i][i2]!=0)
System.out.println("arr[i][i2]="+arr[i][i2]);
		}
	}*/

double c1=100.0,c2=5000.0,c3;
c3=10*c1;
int sum=0,sum1=0;

for(int m=0;m<np-1;m++)
	{ 
	//System.out.println("firstloop");
		for(int n=m+1;n<np;n++)
		{
		//System.out.println("secontloop");
		
		sum=sum+min(m,n);
	//System.out.println("mainmin"+sum);
		}
	sum1=sum1+sum;
	}
//System.out.println("min"+sum1);
int sum2=0;
		for(k=0;k<np-1;k++)
				{
				sum2=sum2+(np-k);
				}
//System.out.println("min2 "+sum2);
if(sum2==0) sum2=1;
//System.out.println("min3 "+nt/c3);
rank=(c1*np)+((c2-(sum1/sum2))/(c2/c1))+nt/c3;
System.out.println("Ranking"+rank);
}


double total=rank+headpoint+titlepoint+h1point+h2point+h3point+linkpoint;
System.out.println("total Ranking"+total);
/////////////////////////////////
/*if(u.equals("")) System.out.println("ue");
if(u.equals(null)) System.out.println("un");
if(u=="") System.out.println("u==e");
if(u==null) System.out.println("u==n");*/
if(total==0 || u.equals("")) 
			{ continue;}
else{

String sq="insert into linkvalue values('"+u+"','"+head+"','"+title+"','"+h1[0]+"','"+h1[1]+"','"+h2[0]+"','"+h2[1]+"','"+h2[2]+"','"+h3[0]+"','"+h3[1]+"','"+h3[2]+"','"+URLEncoder.encode(str5)+"','"+total+"')";
stmt1.executeUpdate(sq);}

}
	catch(Exception er)
			{System.out.println(er);
			}

		
		
		}

   System.out.println("before");
	//conn5.flush();
	conn5.commit();
	stmt.close();
	stmt2.close();
	stmt1.close();
	conn5.close();
System.out.println("after");
}
catch(Exception e)
	{System.out.println(e);
	}



}
////////////////////



/////////////////////
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









