package VIEW;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HelpView implements ListSelectionListener {
	//objects for file reading
	File file;
	BufferedReader reader = null;
	String textFileContents = null;
	
	private JPanel mainHelpContainer;
	
	private JPanel helpSubjectsContainer = new JPanel();
	private JList helpSubjects;
	
	private JPanel textContainer = new JPanel();
	private JScrollPane textScroller;
	private JTextArea text = new JTextArea(10,1);//a text area component having 10 rows and 1 column
	
	private JSplitPane splitPane;
	private JLabel helpSubjectsLabel = new JLabel("Help Subjects");
	private JLabel helpSubjectIndicator = new JLabel("You are reading the welcome message");
	
	private String[] helpListSubjects = new String[5];
	private String[] helpStrings = new String[5];
	
	private String welcomeText = "Welcome to the application. Please browse through the subjects displayed on the left in order to find out what you are seeking!";
	private String helpListSubject0 = "Creating new entities";
	private String helpListSubject1 = "Sending emails";
	private String helpListSubject2 = "Creating appointments";
	private String helpListSubject3 = "Editing entities in the database";
	private String helpListSubject4 = "Viewing and sorting entities in the database";
	
	private String helpText1 = "In order to insert new files, go to File->New and select what kind of object you would like to insert!";
	
	public HelpView(){
		text.setWrapStyleWord(true);
		text.setText(welcomeText);	
		
		
		helpListSubjects[0] = helpListSubject0;
		helpListSubjects[1] = helpListSubject1;
		helpListSubjects[2] = helpListSubject2;
		helpListSubjects[3] = helpListSubject3;
		helpListSubjects[4] = helpListSubject4;
		
		
		helpStrings[0]=helpText1;
		
		helpSubjects = new JList(helpListSubjects);
		helpSubjects.setPreferredSize(new Dimension(400,300));
		helpSubjects.addListSelectionListener(this);
	
		helpSubjectsContainer.setPreferredSize(new Dimension(400,400));
		helpSubjectsContainer.add(helpSubjectsLabel);
		helpSubjectsContainer.add(helpSubjects);
		
		text.setLineWrap(true);
		textScroller = new JScrollPane(text);
		textScroller.setPreferredSize(new Dimension(400,300));
		textContainer.setPreferredSize(new Dimension(400,400));
		textContainer.add(helpSubjectIndicator);
		textContainer.add(textScroller);
		
		mainHelpContainer = new JPanel();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,helpSubjectsContainer, textContainer);
		splitPane.setDividerLocation(400);
		
		mainHelpContainer.add(splitPane);
		
		Object msg[] = {mainHelpContainer};
		Object options[] = {"Ok"};
		JOptionPane.showOptionDialog(null, msg, "Help files and applications documentation!",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
		        null);
	}

	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			switch(helpSubjects.getSelectedIndex()){
				case 0:
					file = new File("../APLICATIE_POLICLINCA/HelpFiles/helpFileIntroduction.txt");
					StringBuffer contents1 = new StringBuffer();
					try{
						reader = new BufferedReader(new FileReader(file));
						while((textFileContents=reader.readLine())!=null){
							contents1.append(textFileContents).append(System.getProperty("line.separator"));
						}
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, ioe.getMessage());
					}
					text.setText(contents1.toString());
					helpSubjectIndicator.setText("You are reading: "+(String) helpSubjects.getSelectedValue());
					break;
				case 1:
					file = new File("../APLICATIE_POLICLINCA/HelpFiles/helpFileSendingEmail.txt");
					StringBuffer contents2 = new StringBuffer();
					try{
						reader = new BufferedReader(new FileReader(file));
						while((textFileContents=reader.readLine())!=null){
							contents2.append(textFileContents).append(System.getProperty("line.separator"));
						}
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, ioe.getMessage());
					}
					text.setText(contents2.toString());
					helpSubjectIndicator.setText("You are reading: "+(String) helpSubjects.getSelectedValue());
					break;
				case 2:
					file = new File("../APLICATIE_POLICLINCA/HelpFiles/helpFileCreatingAppointments.txt");
					StringBuffer contents3 = new StringBuffer();
					try{
						reader = new BufferedReader(new FileReader(file));
						while((textFileContents=reader.readLine())!=null){
							contents3.append(textFileContents).append(System.getProperty("line.separator"));
						}
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, ioe.getMessage());
					}
					text.setText(contents3.toString());
					helpSubjectIndicator.setText("You are reading: "+(String) helpSubjects.getSelectedValue());
					break;
				case 3:
					file = new File("../APLICATIE_POLICLINCA/HelpFiles/helpFileEditingEntities.txt");
					StringBuffer contents4 = new StringBuffer();
					try{
						reader = new BufferedReader(new FileReader(file));
						while((textFileContents = reader.readLine())!=null){
							contents4.append(textFileContents).append(System.getProperty("line.separator"));
						}
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, ioe.getMessage());
					}
					text.setText(contents4.toString());
					helpSubjectIndicator.setText("You are reading: "+(String) helpSubjects.getSelectedValue());
					break;
				case 4:
					file = new File("../APLICATIE_POLICLINCA/HelpFiles/helpFileViewingSorting.txt");
					StringBuffer contents5 = new StringBuffer();
					try{
						reader = new BufferedReader(new FileReader(file));//the contents of the file reader are stored in a buffered reader
						while((textFileContents = reader.readLine())!=null){//not the end of file
							contents5.append(textFileContents).append(System.getProperty("line.separator"));
						}
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, ioe.getMessage());
					}
					text.setText(contents5.toString());
					helpSubjectIndicator.setText("You are reading: "+(String) helpSubjects.getSelectedValue());
					break;
			}
		}
			
	}
		
}
