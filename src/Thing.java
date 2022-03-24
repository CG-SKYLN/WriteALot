import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
public class Thing
{
  public static void main (String[]args)
  {
    WriteALot np = new WriteALot ();
  }
}

class WriteALot implements ActionListener
{
  JFrame frm;
  JMenuBar mnubr;
  JMenu fileMenu, editMenu, helpMenu;
  JMenuItem cutItem, copyItem, pasteItem, selectAll, saveFile, saveFileAs, openFile;
  JTextArea txtarea;
    WriteALot ()
  {
    frm = new JFrame ();
    cutItem = new JMenuItem ("Cut");
    copyItem = new JMenuItem ("Copy");
    pasteItem = new JMenuItem ("Paste");
    selectAll = new JMenuItem ("Select All");
    
    saveFile = new JMenuItem ("Save");
    saveFileAs = new JMenuItem ("Save As");
    openFile = new JMenuItem ("Open");
    
    copyItem.addActionListener (this);
    cutItem.addActionListener (this);
    selectAll.addActionListener (this);
    pasteItem.addActionListener (this);

	saveFile.addActionListener(this);
	saveFileAs.addActionListener(this);
	openFile.addActionListener(this);
    mnubr = new JMenuBar ();
    mnubr.setBounds (5, 5, 400, 40);
    fileMenu = new JMenu ("File");
    editMenu = new JMenu ("Edit");
    
    fileMenu.add(openfile);
    fileMenu.add(savefile);
    fileMenu.add(saveAs);
    
    editMenu.add (cutItem);
    editMenu.add (copyItem);
    editMenu.add (pasteItem);
    editMenu.add (selectAll);
    mnubr.add (fileMenu);
    mnubr.add (editMenu);
    mnubr.add (helpMenu);
    txtarea = new JTextArea ();
    txtarea.setBounds (5, 30, 460, 460);
    frm.add (mnubr);
    frm.add (txtarea);
    frm.setLayout (null);
    frm.setSize (500, 500);
    frm.setVisible (true);
  }
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource () == cutItem)
      {
	txtarea.cut ();
      }
    else if (ae.getSource () == pasteItem)
      {
	txtarea.paste ();
      }
    else if (ae.getSource () == copyItem)
      {
	txtarea.copy ();
      }
    else if (ae.getSource () == selectAll)
      {
	txtarea.selectAll ();
      }
    else if (ae.getSource() == saveFile){
    	txtarea.save();
    }
  }

}

class FileManage{
    public static void save() throws IOException , FileNotFoundException{
	

        File f = new File("testFile.txt");
    }
}
