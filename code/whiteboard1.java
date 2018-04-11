import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class whiteboard1{
	public static void main(String args[])throws IOException
	{
		wboard new1=new wboard();   // create the GUI for the client
		new1.setVisible(true);    // show it to him

        //new1.connect() ;   // make the connection

	}}

class wboard extends JFrame implements WindowListener,ActionListener, MouseMotionListener, MouseListener, ItemListener{
JButton line,oval,rect,fill,free;
JPanel p1;
JComboBox colours;
JLabel lb;
String str=new String();
String sng=new String();
String str1[]={"Default","Blue","Yellow","Red","Green"} ;
int tt=0,k=0,x1=0,x2=0,y1=0,y2=0,t1=0,t2=0,t3=0,t4=0;
int prev=0,t0=0,type=0;
wboard()
{
 try
		{
	FileOutputStream fw = new FileOutputStream("draw.txt");
OutputStreamWriter osw = new OutputStreamWriter(fw);
osw.write("");
osw.close();
		}
		catch(Exception e1)
	{
	}

 line=new JButton("line");
 oval=new JButton("Oval");
 rect=new JButton("Rect");
 fill=new JButton("Fill");
 free=new JButton("Free");
 colours=new JComboBox(str1);
 /*colours.addItem(makeObj("default"));
 colours.addItem(makeObj("blue"));
 colours.addItem(makeObj("yellow"));
 colours.addItem(makeObj("green"));
 colours.addItem(makeObj("red"));*/
 lb=new JLabel("Colours");
p1=new JPanel();
// p2=new JPanel();

 p1.add(line);
 p1.add(oval);
 p1.add(rect);
 p1.add(free);
 p1.add(fill);
 p1.add(lb); 
 p1.add(colours);
 line.addActionListener(this);
 oval.addActionListener(this);
 rect.addActionListener(this);
 free.addActionListener(this);
 fill.addActionListener(this);
 addMouseListener(this);
 addMouseMotionListener(this);
// colours.addActionListener(this);
 colours.addItemListener(this);
 addWindowListener(this);
 str="white";
// p2.setLayout(new BorderLayout());
 //p2.add(p1,BorderLayout.NORTH);
 getContentPane().add(p1,BorderLayout.CENTER);
 setBounds(0,0,800,575);
}
public void actionPerformed(ActionEvent e)
{
 if(e.getSource()==line)
 prev=1;
 else if(e.getSource()==oval)
 prev=2;
 else if(e.getSource()==rect)
 prev=3;
 else if(e.getSource()==free)
 prev=4;
 if(e.getSource()==fill) {
	 if(type==0)
  type=1;
	 else
		 type=0 ;
  repaint();
 }}
public void itemStateChanged(ItemEvent e)
{
 if(e.getItemSelectable()==colours)
 {
 str=(String)colours.getSelectedItem();
 }
}
public void mouseClicked(MouseEvent me) 
{
 }
public void mousePressed(MouseEvent me)
{
 x1=me.getX();
 y1=me.getY();
}
public void mouseReleased(MouseEvent me)
{
	k=1 ;
 x2=me.getX();
 y2=me.getY();

 try
	{
FileOutputStream fw = new FileOutputStream("draw.txt",true);
OutputStreamWriter osw = new OutputStreamWriter(fw);
 osw.write(str+"	"+type+"	"+prev+"	"+x1+"	"+x2+"	"+y1+"	"+y2+"\n");
 osw.close();	
	}
	catch(Exception e)
		{
	System.out.println(e);
	}
 
 repaint();

}
public void mouseEntered(MouseEvent me)
{												
}
public void mouseExited(MouseEvent me)
{
}

public void mouseDragged(MouseEvent me)
{ 
 x2=me.getX();
 y2=me.getY();
 k=0 ;
 repaint();
//update(getGraphics());
 
 }
public void mouseMoved(MouseEvent me)
{

 /*if(prev==4)
 {
  x1=x2;
  y1=y2;
  x2=me.getX();
  y2=me.getY();       }update(getGraphics());*/}
/*public void update(Graphics g)
{
 paint(g);
}*/
public void paint(Graphics g)
{ 
  if(k==0)
	{
//	  System.out.println("hi here");
 
 if(prev==1)
 g.drawLine(x1,y1,x2,y2);
 else if(prev==2)
 g.drawOval(x1,y1,x2-x1,y2-y1);
 else if(prev==3)
 g.drawRect(x1,y1,x2-x1,y2-y1);
 else if(prev==4)
 g.drawLine(x1,y1,x2,y2);
 
 if(type==1 && str!="Default")
 {
   if(str=="Red")
  g.setColor(Color.red);
 else if(str=="Green")
  g.setColor(Color.green);
 else if(str=="Blue")
  g.setColor(Color.blue);
 else if(str=="Yellow")
 g.setColor(Color.yellow);

 if(prev==1)
 g.drawLine(x1,y1,x2,y2);
 else if(prev==2)
 g.fillOval(x1,y1,x2-x1,y2-y1);
 else if(prev==3)
 g.fillRect(x1,y1,x2-x1,y2-y1);
 else if(prev==4)
 g.drawLine(x1,y1,x2,y2);
}   
	}

	else if(k==1)
	{
	try{
BufferedReader ub = new BufferedReader(new FileReader("draw.txt"));
String temp1=new String();


while((temp1=ub.readLine())!=null)
		{
 StringTokenizer st = new StringTokenizer(temp1,"\t");

	String s1 = st.nextToken();
	tt = Integer.parseInt(st.nextToken());
	t0 = Integer.parseInt(st.nextToken());
	t1 = Integer.parseInt(st.nextToken());
	t2 = Integer.parseInt(st.nextToken());
	t3 = Integer.parseInt(st.nextToken());
	t4 = Integer.parseInt(st.nextToken());

 if(tt==0 && t0==1)
 g.drawLine(t1,t3,t2,t4);
 else if(t0==2)
 g.drawOval(t1,t3,t2-t1,t4-t3);
 else if(t0==3)
 g.drawRect(t1,t3,t2-t1,t4-t3);
 else if(t0==4)
 g.drawLine(t1,t3,t2,t4);
// System.out.println("hi");
 
 if(tt==1 && sng!="Default")
 {
   if(sng=="Red")
  g.setColor(Color.red);
 else if(sng=="Green")
  g.setColor(Color.green);
 else if(sng=="Blue")
  g.setColor(Color.blue);
 else if(sng=="Yellow")
 g.setColor(Color.yellow);

 if(t0==1)
 g.drawLine(t1,t3,t2,t4);
 else if(t0==2)
 g.fillOval(t1,t3,t2-t1,t4-t3);
 else if(t0==3)
 g.fillRect(t1,t3,t2-t1,t4-t3);
 else if(t0==4)
 g.drawLine(t1,t3,t2,t4);
 }
		}
	//k=0 ;
ub.close() ;
	}
	catch(Exception e)
		{
	System.out.println(e);
	}
}
}
void stop()
	{
	System.exit(0);
	}
public void windowClosing(WindowEvent e)
{
System.exit(0);
}
public void windowClosed(WindowEvent e)
{	
	System.exit(0);
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}

}
