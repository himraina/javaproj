import java.applet.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;


    class time1 {

		public static void main (String args[]) throws Exception{
		Date date = new Date();
		
		int h = date.getHours();
        int m = date.getMinutes();
        int s = date.getSeconds();
        int dy = date.getDay();
        int dt = date.getDate();
        int mn = date.getMonth();
        int yr = date.getYear();
        System.out.println("min"+m);
       System.out.println("sec"+s);
int j=0;
		for(int i=0;i<1000000000;i++)
			{
		j++;
			}
        Date date1 = new Date();
        int m2 = date1.getMinutes();
        int s2 = date1.getSeconds();
       System.out.println("min"+m2);
       System.out.println("sec"+s2);
		}}