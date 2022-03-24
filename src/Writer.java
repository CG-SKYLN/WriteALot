import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class Thing {
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
		WriteALot np = new WriteALot ();
	}
}

class WriteALot implements ActionListener {
	JFrame frm;
	JMenuBar mnubr;
	TogMenu fileMenu, editMenu, helpMenu;
	JMenuItem cutItem, copyItem, pasteItem, selectAll, saveFile, saveFileAs, openFile;
	JTextArea txtarea;

	WriteALot() {
		frm = new JFrame();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cutItem = new JMenuItem("Cut");
		copyItem = new JMenuItem("Copy");
		pasteItem = new JMenuItem("Paste");
		selectAll = new JMenuItem("Select All");

		saveFile = new JMenuItem("Save");
		saveFileAs = new JMenuItem("Save As");
		openFile = new JMenuItem("Open");

		copyItem.addActionListener(this);
		cutItem.addActionListener(this);
		selectAll.addActionListener(this);
		pasteItem.addActionListener(this);

		saveFile.addActionListener(this);
		saveFileAs.addActionListener(this);
		openFile.addActionListener(this);
		mnubr = new JMenuBar();
		mnubr.setBounds(5, 5, 400, 40);
		fileMenu = new TogMenu("File");
		editMenu = new TogMenu("Edit");

		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(saveFileAs);

		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(selectAll);
		mnubr.add(fileMenu);
		mnubr.add(editMenu);
		mnubr.add(helpMenu);
		txtarea = new JTextArea();
		txtarea.setBounds(5, 30, 460, 460);
		frm.add(mnubr);
		frm.add(txtarea);
		frm.setLayout(null);
		frm.setSize(500, 500);
		frm.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cutItem) {
			txtarea.cut();
		} else if (ae.getSource() == pasteItem) {
			txtarea.paste();
		} else if (ae.getSource() == copyItem) {
			txtarea.copy();
		} else if (ae.getSource() == selectAll) {
			txtarea.selectAll();
		} else if (ae.getSource() == saveFile) {
			try {
				FileManage.save("testFile", txtarea);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getSource() == saveFileAs) {

		} else if (ae.getSource() == openFile) {

		}
	}

}

class TogMenu extends JMenu implements Toggleable{
	boolean toggled=false;	
	
	TogMenu(String s){
		super(s);
	}
	
	public void toggleThis() {
		
	}
}

abstract class FileManage {
	public static void save(String nm, JTextArea tx) throws IOException, FileNotFoundException {

		final String content = tx.getText();
	    final Path path = Paths.get(System.getProperty("user.home")+"/Downloads"+nm+".txt");
		try (
		        final BufferedWriter writer = Files.newBufferedWriter(path,
		            StandardCharsets.UTF_8, StandardOpenOption.CREATE);
		    ) {
		        writer.write(content);
		        writer.flush();
		    }
		catch(FileNotFoundException e) {
			File f = new File(path.toString());
		}
	}
	public static void saveAs(String nm) throws IOException, FileNotFoundException {

		File f = new File(System.getProperty("user.home") + "/Downloads/" + nm + ".txt");
	}
	public static void open(String nm) throws IOException, FileNotFoundException{
		
		
		
		
	}
	
}

interface Toggleable{
	boolean toggled = false;
	public void toggleThis();
	}
