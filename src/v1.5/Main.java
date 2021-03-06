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
        while(1==1) {
        	np.display();
        }
    }
}

class WriteALot implements ActionListener {
    private JFrame frm;
    private JMenuBar mnubr;
    private JMenu fileMenu, editMenu;
    private JMenuItem cutItem, copyItem, pasteItem, selectAll, saveFile, saveFileAs, openFile;
    private JScrollPane scr;
    private JPanel mnpnl, txtpnl;
    private JTextArea txtarea;
    private FileManage flmg = new FileManage();
    public WriteALot() {
        frm = new JFrame("WriteALot");
        frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frm.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
        txtarea.setLineWrap(true);
        txtarea.setWrapStyleWord(true);
        txtpnl = new JPanel();
        txtpnl.setBounds(0, 26, frm.getWidth(), frm.getHeight());
        txtpnl.setLayout(new BoxLayout(txtpnl, BoxLayout.Y_AXIS));
        txtarea.setBounds(0, 26, frm.getWidth(), frm.getHeight());
        txtpnl.add(txtarea);
        scr = new JScrollPane(txtpnl);
        scr.setBounds(0, 26, frm.getWidth(), frm.getHeight());
        mnpnl = new JPanel();
        mnubr.setBounds(0, 0, frm.getWidth(), 25);
        mnpnl.add(mnubr);
        frm.add(mnpnl);
        frm.add(scr);
        frm.setVisible(true);
    }

    public void display() {
        mnpnl.setBounds(0, 0, frm.getWidth(), 25);
        mnubr.setBounds(0, 0, frm.getWidth(), 25);
        scr.setBounds(0, 25, frm.getWidth(), frm.getHeight());
        txtarea.setBounds(0, 25, frm.getWidth(), frm.getHeight());
    }

    public void actionPerformed(ActionEvent ae) {
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
                scr.remove(txtpnl);
                txtpnl.remove(txtarea);
                
                txtarea = flmg.open();
                txtarea.setBounds(0, 25, frm.getWidth(), frm.getHeight());
                txtarea.setLineWrap(true);
                txtarea.setWrapStyleWord(true);
                txtpnl.add(txtarea);
                frm.add(scr);
                txtarea.update(txtarea.getGraphics());
                txtarea.setCaretPosition(txtarea.getText().length());
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
    public void save(JTextArea tx) throws NullPointerException, IOException {
        newtxt = tx.getText();
        try (final BufferedWriter writer = Files.newBufferedWriter(path,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE);) {
            PrintWriter printer = new PrintWriter(path.toString());
            printer.close();
            writer.write(newtxt);
            writer.flush();
            oldtxt = newtxt;
        } catch (NullPointerException e) {
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
        return ((newtxt == "\n" || newtxt == "" || newtxt == null)?
			0:
		(oldtxt == "\n" || oldtxt == "" || oldtxt == null)?
			2:	
		(oldtxt == newtxt)?
			1:
			0);
    }

}
