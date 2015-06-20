package ACTIONS;

import java.awt.event.ActionEvent;

import VIEW.MainWindowInterface;
import VIEW.PanelAppointmentsCertainDoctor;
import VIEW.PanelPacientsAgeRange;
import VIEW.PanelPacientsCertainDiagnostic;
import VIEW.PanelPacientsCertainOccupation;
import VIEW.PanelPrescriptionsCertainDiagnostic;
import VIEW.PanelPrescriptionsCertainDoctor;
import VIEW.PanelViewDepartments;
import VIEW.PanelViewDiagnostics;
import VIEW.PanelViewDoctors;
import VIEW.PanelViewEntity;
import VIEW.PanelViewPacients;
import VIEW.PanelViewPrescriptions;

public class ViewPanelActions extends ActionBaseClass {

	private String actionName;
	private String actionID;
	private static String queryBasedOnUserChoice;
	private static int panelDisplayed=0; //variable that helps knowing which panel is displayed - pacients, doctors, assistants etc
	
	private PanelViewEntity panelSortEntity;
	
	public ViewPanelActions(String actionName, String actionID){
		super(actionName);
		this.actionName = actionName;
		this.actionID = actionID;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(actionID){
			case "sortByFirstNamePacients":
				//remove previous content from PanelViewPacients JPanel component
				PanelViewPacients.getAllPacientsPanel().removeAll();
				//set query based on what action was chosen
				queryBasedOnUserChoice = "SELECT * FROM HR.PACIENTS ORDER BY FIRST_NAME";
				//create a PanelViewPacient object based on queryBasedOnUserChoice
				panelSortEntity = new PanelViewPacients(queryBasedOnUserChoice);
			break;	
			case "sortByLastNamePacients":
				//remove previous content from PanelViewPacients JPanel component
				PanelViewPacients.getAllPacientsPanel().removeAll();
				//set query based on what action was chosen
				queryBasedOnUserChoice = "SELECT * FROM HR.PACIENTS ORDER BY LAST_NAME";
				//create a PanelViewPacient object based on queryBasedOnUserChoice
				panelSortEntity = new PanelViewPacients(queryBasedOnUserChoice);
			case "sortByIDPacients":
				System.out.println("Panel displayed is: " + panelDisplayed);
				//remove previous content from PanelViewPacients JPanel component
				PanelViewPacients.getAllPacientsPanel().removeAll();
				//set query based on what action was chosen
				queryBasedOnUserChoice = "SELECT * FROM HR.PACIENTS ORDER BY PACIENT_ID";
				//create a PanelViewPacient object based on queryBasedOnUserChoice
				panelSortEntity = new PanelViewPacients(queryBasedOnUserChoice);	
				break;
			case "sortByFirstNameDoctors":
				//remove previous content from PanelViewPacients JPanel component
				PanelViewDoctors.getAllPacientsPanel().removeAll();
				//set query based on what action was chosen
				queryBasedOnUserChoice = "SELECT * FROM HR.EMPLOYEE ORDER BY FIRST_NAME";
				//create a PanelViewPacient object based on queryBasedOnUserChoice
				panelSortEntity = new PanelViewDoctors(queryBasedOnUserChoice);	
				break;
			case "sortByLastNameDoctors":
				//remove previous content from PanelViewPacients JPanel component
				PanelViewDoctors.getAllPacientsPanel().removeAll();
				//set query based on what action was chosen
				queryBasedOnUserChoice = "SELECT * FROM HR.EMPLOYEE ORDER BY LAST_NAME";
				//create a PanelViewPacient object based on queryBasedOnUserChoice
				panelSortEntity = new PanelViewDoctors(queryBasedOnUserChoice);	
				break;
			case "sortByIDDoctors":
				//remove previous content from PanelViewPacients JPanel component
				PanelViewDoctors.getAllPacientsPanel().removeAll();
				//set query based on what action was chosen
				queryBasedOnUserChoice = "SELECT * FROM HR.EMPLOYEE ORDER BY EMPLOYEE_ID";
				//create a PanelViewPacient object based on queryBasedOnUserChoice
				panelSortEntity = new PanelViewDoctors(queryBasedOnUserChoice);	
				break;
			case "sortBySummaryPrescriptions":
				PanelViewPrescriptions.getAllPrescriptionsPanel().removeAll();
				queryBasedOnUserChoice = "SELECT * FROM HR.PRESCRIPTIONS ORDER BY SUMMARY";
				panelSortEntity = new PanelViewPrescriptions(queryBasedOnUserChoice);	
				break;
			case "sortByIDPrescriptions":
				PanelViewPrescriptions.getAllPrescriptionsPanel().removeAll();
				queryBasedOnUserChoice = "SELECT * FROM HR.PRESCRIPTIONS ORDER BY PRESCRIPTION_ID";
				panelSortEntity = new PanelViewPrescriptions(queryBasedOnUserChoice);	
				break;
			case "sortByNameDiagnostics":
				PanelViewDiagnostics.getAllDiagnosticsPanel().removeAll();
				queryBasedOnUserChoice = "SELECT NAME FROM HR.DIAGNOSTICS ORDER BY NAME";
				panelSortEntity = new PanelViewDiagnostics(queryBasedOnUserChoice);
				break;
			case "sortByAnalysisDiagnostics":
				PanelViewDiagnostics.getAllDiagnosticsPanel().removeAll();
				queryBasedOnUserChoice = "SELECT ANALYSIS, NAME FROM HR.DIAGNOSTICS ORDER BY ANALYSIS";
				panelSortEntity = new PanelViewDiagnostics(queryBasedOnUserChoice);
				break;
			
			case "printTablePacients":
				PanelViewPacients.printTablePacients();
				break;
			case "printTableDoctors":
				PanelViewDoctors.printTableDoctors();
				break;
			case "printTableDepartments":
				PanelViewDepartments.printTableDepartments();
				break;
			case "printTableDiagnostics":
				PanelViewDiagnostics.printTableDiagnostics();
				break;
			case "printTablePrescriptions":
				PanelViewPrescriptions.printTablePrescriptions();
				break;
			case "printTablePrescriptionsDoctor":
				PanelPrescriptionsCertainDoctor.printTable();
				break;
			case "printTablePrescriptionsDiagnostic":
				PanelPrescriptionsCertainDiagnostic.printTable();
				break;
			case "printTablePacientsCertainOccupation":
				PanelPacientsCertainOccupation.printTable();
				break;
			case "printTablePacientsCertainDiag":
				PanelPacientsCertainDiagnostic.printTable();
				break;
			case "printTablePacientsAgeRange":
				PanelPacientsAgeRange.printTable();
				break;
			case "printTableAppointmentsDoctor":
				PanelAppointmentsCertainDoctor.printTable();
				break;
				
			case "exportExcelTablePacients":
			try {
				PanelViewPacients.createExcelPacients();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
			case "exportExcelTableDoctors":
				try{
					PanelViewDoctors.createExcelDoctors();
				}catch (Exception e){
					e.printStackTrace();//Prints this throwable and its backtrace to the standard error stream
				}
				break;
			case "exportExcelTableDepartments":
				try{
					PanelViewDepartments.createExcelDepartments();
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case "exportExcelTablePrescriptions":
				try{
					PanelViewPrescriptions.createExcelPrescriptions();
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case "exportExcelTablePrescriptionsDoctor":
				try{
					PanelPrescriptionsCertainDoctor.createExcelPrescriptionDoctor();
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case "exportExcelTablePrescriptionsCertainDiagnostic":
				try{
					PanelPrescriptionsCertainDiagnostic.createExcelPrescriptionsCertainDiagnostic();
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case "exportExcelTablePacientsCertainOccupation":
				try{
					PanelPacientsCertainOccupation.createExcel();
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case "exportExcelTablePacientsCertainDiag":
				try{
					PanelPacientsCertainDiagnostic.createExcel();
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case "exportExcelTablePacientsAgeRange":
				try{
					PanelPacientsAgeRange.createExcel();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				break;
			case "exportExcelTableAppointmentsDoctor":
				try{
					PanelAppointmentsCertainDoctor.createExcel();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				break;
			case "closeTablePacients":
				//remove pacients tab
				//MainWindowInterface.getMainTab().removeTabAt(getPanelDisplayed());
				break;
			case "closeTableDoctors":
				//MainWindowInterface.getMainTab().remove(1);
				break;
		}
	}

	public static int getPanelDisplayed() {
		return panelDisplayed;
	}

	public static void setPanelDisplayed(int panelDisplayed) {
		ViewPanelActions.panelDisplayed = panelDisplayed;
	}

	public static String getQueryBasedOnUserChoice() {
		return queryBasedOnUserChoice;
	}

	public static void setQueryBasedOnUserChoice(String queryBasedOnUserChoice) {
		ViewPanelActions.queryBasedOnUserChoice = queryBasedOnUserChoice;
	}
}
