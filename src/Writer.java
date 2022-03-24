import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

class Writer {
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
		WriteALot np = new WriteALot ();
	}
}

class WriteALot implements ActionListener {
	JFrame frm;
	JMenuBar mnubr;
	JMenu fileMenu, editMenu, helpMenu;
	JMenuItem cutItem, copyItem, pasteItem, selectAll, saveFile, saveFileAs, openFile;
	JTextArea txtarea;

	WriteALot() {
		frm = new JFrame();
		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frm.setSize(500, 500);
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
		mnubr.setBounds(5, 0, (frm.getWidth()-5), 10);
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");

		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(saveFileAs);

		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(selectAll);
		mnubr.add(fileMenu);
		mnubr.add(editMenu);
		frm.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we)
			{ 
				String ObjButtons[] = {"Yes","No"};
				int PromptResult = JOptionPane.showOptionDialog(null, 
						"Are you sure you want to exit?", "Online Examination System", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
						ObjButtons,ObjButtons[1]);
				if(PromptResult==0)
				{
					System.exit(0);          
				}
			}
		});
		txtarea = new JTextArea();
		txtarea.setBounds(0, 11, frm.getWidth(), frm.getHeight());
		frm.add(mnubr);
		frm.add(txtarea);
		frm.setVisible(true);
		while(1==1){
		display();
		}
	}
	
	public void display(){
		txtarea.setBounds(0, 50, frm.getWidth(), frm.getHeight());
mnubr.setBounds(5, 50, (frm.getWidth()-5), 50);
	}

	public void actionPerformed(ActionEvent ae) {
		String act = ae.getActionCommand();
		if(act =="click"&& (ae.getSource() == fileMenu || ae.getSource()==editMenu)){
			JPopupMenu pm = new JPopupMenu("Options");
			pm.add(cutItem);
			pm.add(saveFile);
			pm.show(frm,50,50);
		}


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
				e.printStackTrace();
			} catch (IOException e) {
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
		this.setVisible((toggled==true)?false:true);	
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
