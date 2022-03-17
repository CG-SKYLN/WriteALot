import java.util.*;
import java.util.function.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Tester {
	public static void main(String[] args) {
	 /*TODO
	  * Create logic and interface for finding files.
	  * Create logic and interface for opening/creating files.
	  * Create logic for writing to files and exiting editing mode.
	  * Create GUI that displays text entered and acts like a console.
	  * Possibly create and use buttons to navigate.
	  */
		
		
		
	}
}

class Wndw extends JFrame{
	JFrame f;
	Object[][] cmps = new Object[10][2];
	public Wndw(int w, int h) {
		setSize(w,h);
		cmps[0][0] = new JMenu(); cmps[0][1] = "Menu1";
	}
	
}

abstract class display{
	public static void load(JFrame f) {
		
	}
	
	public static Wndw update(Wndw f, JComponent cur, JComponent nex) {
		Wndw w = f;
		JComponent n1 = (JComponent) Data.find(f.cmps, cur.getName());
		JComponent n2 = (JComponent) Data.find(f.cmps, nex.getName());
		
		w
	}
}

abstract class Data{
	public static Object find(Object[][] obs, String name) {
		Object found= null;
		for(Object[] i: obs) {
			found = (i[1]==name)?i:null;
		}
		return found;
	}
	
}