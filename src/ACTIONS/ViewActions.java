package ACTIONS;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import VIEW.MainWindowInterface;
import VIEW.PanelViewDepartments;
import VIEW.PanelViewDiagnostics;
import VIEW.PanelViewDoctors;
import VIEW.PanelViewPacients;
import VIEW.PanelViewPrescriptions;

public class ViewActions extends ActionBaseClass {
	
	WelcomePanelsActions welcomeActions;
	
	private String actionID;
	private PanelViewPacients allPacients;
	private PanelViewDoctors allDoctors;
	//private PanelViewAssistants allAssistants;
	private PanelViewDepartments allDepartments;
	private PanelViewDiagnostics allDiagnostics;
	private PanelViewPrescriptions allPrescriptions;
	
	private static int existingPacientsTabView=1;//counter to know if tab is already created
	private static int existingDoctorsTabView=1;//counter to know if tab is already created
	private static int existingDiagnosticsTabView=1;//counter to know if tab is already created
	private static int existingPrescriptionsTabView=1;//counter to know if tab is already created
	private static int existingDepartmentsTabView=1;//counter to know if tab is already created
	
	//class constructor
	public ViewActions(String actionName, String tooltip, String actionID){
		super(actionName, tooltip);
		this.actionID=actionID;
	}
	
	//class methods
	public void actionPerformed(ActionEvent ae){
		
		//existingTabView++;//increment tab counter by default
		
		//remove default tab(aka welcome tab) from view and insert view tab (aka main tab)
		if(MainWindowInterface.getTabOpenedFlag()==0){
			MainWindowInterface.getWelcomeTab().removeAll();//removes all tabs on the welcome tab
			MainWindowInterface.removeComponent(MainWindowInterface.getWelcomeTab());//by default, this is the tab that is loaded and we remove it if it is opened
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getMainTab());//add main database container
			MainWindowInterface.setTabOpenedFlag(1);//we know that main tab is now opened
		}else if(MainWindowInterface.getTabOpenedFlag()==2){
			MainWindowInterface.getOpenedFilesTab().removeAll();//remove all tabs on the files container 
			MainWindowInterface.removeComponent(MainWindowInterface.getOpenedFilesTab());//this is the files tab, and we remove it if it's opened
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getMainTab());//add main database container
			MainWindowInterface.setTabOpenedFlag(1);
		}else if(MainWindowInterface.getTabOpenedFlag()==3){//reports tab is opened
			MainWindowInterface.getReportsTab().removeAll();
			MainWindowInterface.removeComponent(MainWindowInterface.getReportsTab());
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getMainTab());//add main database container
			MainWindowInterface.setTabOpenedFlag(1);
		}else if(MainWindowInterface.getTabOpenedFlag()==4){//the quick search is opened
			MainWindowInterface.getQuickSearchTab().removeAll();//remove all tabs on the quick search container 
			MainWindowInterface.removeComponent(MainWindowInterface.getQuickSearchTab());//this is the quick search tab, and we remove it if it's opened
			MainWindowInterface.getMainContainer().add(MainWindowInterface.getMainTab());//add main database container
			MainWindowInterface.setTabOpenedFlag(1);
		}
		
		switch(actionID){
			case "viewAllPacients":
				if(existingPacientsTabView>1){
					JOptionPane.showMessageDialog(null, "You already have the View All Pacients results displayed!");
					System.out.println(existingPacientsTabView);
				}else{
					
					allPacients = new PanelViewPacients(5);//create allPacients object based on default query string 5
					MainWindowInterface.getMainTab().addTab("View all pacients", allPacients.getAllPacientsPanel());//add allPacient object to viewTab
					MainWindowInterface.getMainTab().setVisible(true);
					existingPacientsTabView++;//increment tab counter
					//MainWindowInterface.setTabOpenedFlag(1);//set the flag to 1 in order to know that the main tab is being shown
				}					
			break;
			case "viewAllDoctors":		
				if(existingDoctorsTabView>1){
					JOptionPane.showMessageDialog(null, "You already have the View All Doctors results displayed!");
					System.out.println(existingDoctorsTabView);
				}else{
					
					allDoctors = new PanelViewDoctors(6);//create allDoctors object based on default query string 6
					MainWindowInterface.getMainTab().addTab("View all doctors", allDoctors.getAllPacientsPanel());//add allDoctors object to viewTab
					MainWindowInterface.getMainTab().setVisible(true);
					existingDoctorsTabView++;//increment tab counter
					//MainWindowInterface.setTabOpenedFlag(1);//set the flag to 1 in order to know that the main tab is being shown
				}
			break;
			case "viewAllDepartments":	
				if(existingDepartmentsTabView>1){
					JOptionPane.showMessageDialog(null, "You already have the View All Departments results displayed!");
					System.out.println(existingDepartmentsTabView);				
				}else{
					
					allDepartments = new PanelViewDepartments(10);//create allSuppliers object
					MainWindowInterface.getMainTab().addTab("View all departments", allDepartments.getAllDepartmentsPanel());//add allDepartments object to viewTab
					MainWindowInterface.getMainTab().setVisible(true);
					existingDepartmentsTabView++;//increment tab counter
					//MainWindowInterface.setTabOpenedFlag(1);//set the flag to 1 in order to know that the main tab is being shown
				}
				break;
			case "viewAllDiagnostics":
				if(existingDiagnosticsTabView>1){
					JOptionPane.showMessageDialog(null, "You already have the View All Diagnostics results displayed!");
					System.out.println(existingDepartmentsTabView);				
				}else{
					allDiagnostics = new PanelViewDiagnostics(12);//create allSuppliers object
					MainWindowInterface.getMainTab().addTab("View all diagnostics", allDiagnostics.getAllDiagnosticsPanel());//add allDepartments object to viewTab
					MainWindowInterface.getMainTab().setVisible(true);
					existingDiagnosticsTabView++;//increment tab counter
					//MainWindowInterface.setTabOpenedFlag(1);//set the flag to 1 in order to know that the main tab is being shown
				}
				break;
			case "viewAllPrescriptions":
				if(existingPrescriptionsTabView>1){
					JOptionPane.showMessageDialog(null, "You already have the View All Prescriptions results displayed!");
					System.out.println(existingDepartmentsTabView);				
				}else{
					allPrescriptions = new PanelViewPrescriptions(13);//create allSuppliers object
					MainWindowInterface.getMainTab().addTab("View all prescriptions", allPrescriptions.getAllPrescriptionsPanel());//add allDepartments object to viewTab
					MainWindowInterface.getMainTab().setVisible(true);
					existingPrescriptionsTabView++;//increment tab counter
					//MainWindowInterface.setTabOpenedFlag(1);//set the flag to 1 in order to know that the main tab is being shown
				}
				break;
			}
		MainWindowInterface.setTabOpenedFlag(1);//set the flag to 1 in order to know that the main tab is being shown
		}

	public static int getExistingPacientsTabView() {
		return existingPacientsTabView;
	}

	public static int getExistingDoctorsTabView() {
		return existingDoctorsTabView;
	}

	public static int getExistingDiagnosticsTabView() {
		return existingDiagnosticsTabView;
	}

	public static int getExistingPrescriptionsTabView() {
		return existingPrescriptionsTabView;
	}

	public static int getExistingDepartmentsTabView() {
		return existingDepartmentsTabView;
	}

	public static void setExistingPacientsTabView(int existingPacientsTabView) {
		ViewActions.existingPacientsTabView = existingPacientsTabView;
	}

	public static void setExistingDoctorsTabView(int existingDoctorsTabView) {
		ViewActions.existingDoctorsTabView = existingDoctorsTabView;
	}

	public static void setExistingDiagnosticsTabView(int existingDiagnosticsTabView) {
		ViewActions.existingDiagnosticsTabView = existingDiagnosticsTabView;
	}

	public static void setExistingPrescriptionsTabView(
			int existingPrescriptionsTabView) {
		ViewActions.existingPrescriptionsTabView = existingPrescriptionsTabView;
	}

	public static void setExistingDepartmentsTabView(int existingDepartmentsTabView) {
		ViewActions.existingDepartmentsTabView = existingDepartmentsTabView;
	}

	
}
