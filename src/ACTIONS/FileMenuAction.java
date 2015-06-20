package ACTIONS;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import MODEL.FilesDisplay;
import VIEW.MainWindowInterface;

public class FileMenuAction extends ActionBaseClass {
	
	private String actionIDString;
	
	//class constructor
	public FileMenuAction(String action, String actionIDString){
		super(action);
		this.actionIDString = actionIDString;
	}
	
	public FileMenuAction(String fileActionName, String tooltip, String actionIDString){
		super(fileActionName, tooltip);
		this.actionIDString=actionIDString;
	}
	
	public FileMenuAction(String fileActionName, KeyStroke key, String tooltip, String actionIDString){
		this(fileActionName, tooltip, actionIDString);
		this.actionIDString=actionIDString;
	}
	
	//class methods
	public void actionPerformed(ActionEvent ae){
		//this section of code deals with the existing tabs displaying database extracted data
		if(MainWindowInterface.getTabOpenedFlag()==0){//if the welcome tab is opened
			MainWindowInterface.getWelcomeTab().removeAll();//remove all tabs from it
			MainWindowInterface.removeComponent(MainWindowInterface.getWelcomeTab());//remove it
			MainWindowInterface.setTabOpenedFlag(2);//set the flag to 2, indicating that now the file display is in effect
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getOpenedFilesTab());//add files the tab to the main container
			
		}else if(MainWindowInterface.getTabOpenedFlag()==1) {//if the main tab is opened
			MainWindowInterface.getMainTab().removeAll();//remove all tabs from main tab
			MainWindowInterface.removeComponent(MainWindowInterface.getMainTab());//remove it
			ViewActions.setExistingDepartmentsTabView(1);//reset the existing tab counter
			ViewActions.setExistingDiagnosticsTabView(1);//reset the existing tab counter
			ViewActions.setExistingDoctorsTabView(1);//reset the existing tab counter
			ViewActions.setExistingPacientsTabView(1);//reset the existing tab counter
			ViewActions.setExistingPrescriptionsTabView(1);//reset the existing tab counter
			MainWindowInterface.setTabOpenedFlag(2);//set the flag to 2, indicating that now the file display is in effect
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getOpenedFilesTab());//add the files tab to the main container
		}else if(MainWindowInterface.getTabOpenedFlag()==3){//the reports tab is opened
			MainWindowInterface.getReportsTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getReportsTab());
			MainWindowInterface.setTabOpenedFlag(2);
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getOpenedFilesTab());//add the files tab to the main container
		}else if(MainWindowInterface.getTabOpenedFlag()==4){//the quick search tab is opened
			MainWindowInterface.getQuickSearchTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getQuickSearchTab());
			MainWindowInterface.setTabOpenedFlag(2);
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getOpenedFilesTab());//add the files tab to the main container
		}
		switch(actionIDString){
		case "openFile":
			FilesDisplay fileDisplay = new FilesDisplay();
			fileDisplay.addFileToTab();
			break;
		case "saveFile":
			//get the file that is opened
			
			//check to see if new text has been added to the file - true, than append it to the end of the file, else do nothing
			break;
			case "exitProgram":
				System.exit(0);
			break;
		}
		MainWindowInterface.setTabOpenedFlag(2);
	}
}
