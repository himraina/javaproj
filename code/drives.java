import java.io.*;

class  drives
{
	public static void main(String[] args) 
	{
		String str[]=new String[26];
		File file;
		str[0]=new String("A:\\");
		int ii=1;
		for(char ch='C';ch<='Z';ch++)
		{
			String drivename=new String(ch+":\\");
			file=new File(drivename);
			//System.out.println(drivename+":");
			if(file.exists())
			{
				str[ii]=drivename;
				ii++;
				
			}
		}
		
		for(int i=0;i<str.length;i++)
		{
			if(str[i]!=null)
		System.out.println(str[i]);
			
	}
}
}