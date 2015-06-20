package ACTIONS;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import MODEL.AppointmentDisplay;
import VIEW.MainWindowInterface;
import VIEW.PanelLatestDiagnostics;
import VIEW.PanelLatestPacients;
import VIEW.PanelViewEntity;

public class WelcomePanelsActions extends ActionBaseClass {
	
	private String actionID;
	
	public WelcomePanelsActions(String actionName, String actionID){
		super(actionName);
		this.actionID = actionID;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(MainWindowInterface.getTabOpenedFlag()==1){//if the main tab is opened
			MainWindowInterface.getMainTab().removeAll();//remove all previous tabs
			MainWindowInterface.removeComponent(MainWindowInterface.getMainTab());//remove component
			ViewActions.setExistingDepartmentsTabView(1);//reset the existing tab counter
			ViewActions.setExistingDiagnosticsTabView(1);//reset the existing tab counter
			ViewActions.setExistingDoctorsTabView(1);//reset the existing tab counter
			ViewActions.setExistingPacientsTabView(1);//reset the existing tab counter
			ViewActions.setExistingPrescriptionsTabView(1);//reset the existing tab counter
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getWelcomeTab());
		}else if(MainWindowInterface.getTabOpenedFlag()==2){//if the files tab is opened
			MainWindowInterface.getOpenedFilesTab().removeAll();//remove all tabs from it
			MainWindowInterface.removeComponent(MainWindowInterface.getOpenedFilesTab());//remove it from main container
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getWelcomeTab());//add the welcome tab to main container
		}else if(MainWindowInterface.getTabOpenedFlag()==3){//repors tabs are opened
			MainWindowInterface.getReportsTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getReportsTab());
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getWelcomeTab());//add the welcome tab to main container
		}else if(MainWindowInterface.getTabOpenedFlag()==4){//quick search tab
			MainWindowInterface.getQuickSearchTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getQuickSearchTab());
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getWelcomeTab());//add the welcome tab to main container
		}
		switch(actionID){
			case "showWelcomePanels"://show initial panels when application starts running
//				MainWindowInterface.getWelcomeTab().add(PanelLatestPacients.getPanelName(),PanelLatestPacients.getPanelLatestResultsPacients());//add initial tab
//				MainWindowInterface.getWelcomeTab().add(PanelLatestDiagnostics.getPanelName(),PanelLatestDiagnostics.getPanelLatestResultsDiagnostics());//add initial tab
				MainWindowInterface.getWelcomeTab().add(AppointmentDisplay.getPanelName(), AppointmentDisplay.getPanelAllAppointments());
				MainWindowInterface.setTabOpenedFlag(0);//set the flag to 0 in order to know that the main tab is being shown
				break;
			case "closeWelcomePanelsPacients":
				MainWindowInterface.getWelcomeTab().remove(PanelLatestPacients.getPanelLatestResultsPacients());
				//decrement the welcome tab object as we are removing them
				MainWindowInterface.setWelcomeTabObject(1);
				break;
			case "closeWelcomePanelsDiagnostics":
				MainWindowInterface.getWelcomeTab().remove(PanelLatestDiagnostics.getPanelLatestResultsDiagnostics());
				//decrement the welcome tab object as we are removing them
				MainWindowInterface.setWelcomeTabObject(1);
				break;
			case "printWelcomePanelsPacients":
				PanelLatestPacients.printTablePacients();
				break;
			case "printWelcomePanelsDiagnostics":
				PanelLatestDiagnostics.printTableDiagnostics();
				break;
		}		
	}	
}
