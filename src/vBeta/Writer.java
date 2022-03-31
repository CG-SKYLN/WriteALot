import java.awt.Desktop;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

//Created by Caelan Grippa, March 2022
//Code Formatted by Tutorialspoint (https://www.tutorialspoint.com/online_java_formatter.htm)

class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        WriteALot np = new WriteALot();
    }
}

class WriteALot implements ActionListener {
    JFrame frm;
    JMenuBar mnubr;
    JMenu fileMenu, editMenu, helpMenu;
    JMenuItem cutItem, copyItem, pasteItem, selectAll, saveFile, saveFileAs,
    openFile;
	JScrollPane scr;
	JPanel mnpnl, txtpnl;
    JTextArea txtarea;
    FileManage flmg = new FileManage();
	Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();

    WriteALot() {
        frm = new JFrame("WriteALot");
        frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frm.setSize((int)(dimen.getWidth()), (int)(dimen.getHeight()));
        frm.setName("WriteALot");

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
            public void windowClosing(WindowEvent we) {
                if (!(flmg.isSaved(txtarea) == 0)) {
                    String ObjButtons[] = {
                        "Yes",
                        "No"
                    };
                    int PromptResult =
                        JOptionPane.showOptionDialog(null,
                            "Do you want to save changes?",
                            "WriteALot",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null, ObjButtons,
                            ObjButtons[1]);
                    if (PromptResult == 0) {
                        if (flmg.isSaved(txtarea) == 1) {
                            try {
                                flmg.save(txtarea);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        } else
                        if (flmg.isSaved(txtarea) == 2) {
                            try {
                                flmg.saveAs(txtarea);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else
                    if (PromptResult == 1) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
        txtarea = new JTextArea();
		txtpnl = new JPanel();
		txtpnl.setBounds(0,25,frm.getWidth(),frm.getHeight());
		txtpnl.add(txtarea);
        txtarea.setBounds(0, 25, frm.getWidth(), Integer.MAX_VALUE);
		scr = new JScrollPane(txtpnl);
		scr.setBounds(0,25,frm.getWidth(),frm.getHeight());
		
		mnpnl = new JPanel();
		mnpnl.setBounds(0,0,frm.getWidth(),25);
		mnpnl.add(mnubr);
        mnubr.setBounds(0, 0, frm.getWidth(), 25);
		
        frm.add(mnpnl);
        frm.add(scr);
        frm.setVisible(true);
        while (1 == 1) {
            display();
        }
    }

    public void display() {
		mnpnl.setBounds(0,0,frm.getWidth(),25);
		mnubr.setBounds(0, 0, frm.getWidth(), 25);
		scr.setBounds(0,25,frm.getWidth(),frm.getHeight());
        txtarea.setBounds(0, 25, frm.getWidth(), Integer.MAX_VALUE);
    }

    public void actionPerformed(ActionEvent ae) {
        String act = ae.getActionCommand();
        if (ae.getSource() == cutItem) {
            txtarea.cut();
			txtarea.update(txtarea.getGraphics());
        } else if (ae.getSource() == pasteItem) {
            txtarea.paste();
			txtarea.update(txtarea.getGraphics());
        } else if (ae.getSource() == copyItem) {
            txtarea.copy();
        } else if (ae.getSource() == selectAll) {
            txtarea.selectAll();
			txtarea.update(txtarea.getGraphics());
        } else if (ae.getSource() == saveFile) {
            try {
                flmg.save(txtarea);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == saveFileAs) {
            try {
                flmg.saveAs(txtarea);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == openFile) {
            try {
                frm.remove(scr);
                txtarea = flmg.open();
                frm.add(scr);
                scr.getComponent(0).getComponent(0).update(scr.getComponent(0).getComponent(0).getGraphics());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

class FileManage {
    private Path path;
    private String oldtxt = "\n";
    private String newtxt = "\n";

    public void save(JTextArea tx) throws IOException, FileNotFoundException {

        newtxt = tx.getText();
        try (final BufferedWriter writer = Files.newBufferedWriter(path,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE);) {
            PrintWriter printer = new PrintWriter(path.toString());
            printer.close();
            writer.write(newtxt);
            writer.flush();
            oldtxt = newtxt;
        } catch (FileNotFoundException e) {
            saveAs(tx);
        }
    }
    public void saveAs(JTextArea tx) throws IOException, FileNotFoundException {
        JFileChooser chooser = new JFileChooser();
        File f = new File("*.txt");
        chooser.setSelectedFile(f);
        int response = chooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            f = chooser.getSelectedFile();
        }
        newtxt = tx.getText();
        oldtxt = newtxt;
    }
    public JTextArea open() throws IOException, FileNotFoundException {
        //Code based off of answer from https://stackoverflow.com/questions/40255039/how-to-choose-file-in-java


        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Text Documents", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("File chosen : " +
                chooser.getSelectedFile().getName());
        }

        //My code
        path = Paths.get(chooser.getSelectedFile().getAbsolutePath());
        JTextArea txt = new JTextArea();
        try {
            txt.read(new BufferedReader(new FileReader(path.toString()), 1), 1);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
        }
        oldtxt = txt.getText();
        newtxt = oldtxt;
        return txt;
    }

    public int isSaved(JTextArea ctxt) {
        newtxt = ctxt.getText();
        if (newtxt == "\n" || newtxt == "" || newtxt == null) {
            return 0;
        } else if (oldtxt == "\n" || oldtxt == "" || oldtxt == null) {
            return 2;
        }
        return ((oldtxt == newtxt) ? 0 : 1);
    }

}