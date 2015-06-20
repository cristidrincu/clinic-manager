package MODEL;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import ACTIONS.ViewActions;
import VIEW.MainWindowInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;

public class FilesDisplay {
	private JPanel textPanePanel;
	private JScrollPane scrollPane;
	private JTextPane pane;
	
	private JFileChooser fc;
	private int option;
	
	private StyledDocument doc;
	private static int fileOpenedFlag;
	
	public FilesDisplay(){
		fc = new JFileChooser();
		fc.setPreferredSize(new Dimension(700,500));
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setMultiSelectionEnabled(false);
		option =fc.showOpenDialog(MainWindowInterface.getMainContainer());
	}

	public static int getFileOpenedFlag() {
		return fileOpenedFlag;
	}

	public static void setFileOpenedFlag(int fileOpenedFlag) {
		FilesDisplay.fileOpenedFlag = fileOpenedFlag;
	}
	
	public void addFileToTab(){
		if(option == JFileChooser.APPROVE_OPTION){
			//get selected file
			File file = fc.getSelectedFile();
			try{
				//clear out current document
				pane = new JTextPane();
				pane.setStyledDocument(doc = new DefaultStyledDocument());
				//read in txt file
				FileReader fin = new FileReader(file);
				BufferedReader br = new BufferedReader(fin);
				char buffer[] = new char[4096];
				int len;
				while((len = br.read(buffer, 0, buffer.length))!=-1){
					doc.insertString(doc.getLength(), new String(buffer,0,len), null);
				}
				System.out.println("File " + file.getName() + " loaded!");
				
				scrollPane = new JScrollPane(pane);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane.setPreferredSize(new Dimension(700,400));
				scrollPane.setMinimumSize(new Dimension(10, 10));
				textPanePanel = new JPanel();
				textPanePanel.setLayout(new BorderLayout());
				textPanePanel.add(scrollPane);
				
				MainWindowInterface.getMainContainer().add(MainWindowInterface.getOpenedFilesTab());
				MainWindowInterface.getOpenedFilesTab().addTab("Currently viewing file "+file.getName(), textPanePanel);
				MainWindowInterface.setTabOpenedFlag(2);
				
			}catch(BadLocationException exc){
				System.out.println("Error loading: " + file.getName());
			}catch(FileNotFoundException fnfe){
				System.out.println("File not found!");
			}catch(IOException ioe){
				System.out.println(ioe.getMessage());
			}
		}else if(option == JFileChooser.CANCEL_OPTION){
			return;
		}
	}
	
}

